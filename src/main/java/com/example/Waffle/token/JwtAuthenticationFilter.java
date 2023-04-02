package com.example.Waffle.token;

import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 토큰 받아오기
        String accessToken = jwtTokenProvider.resolveToken((HttpServletRequest) request, "Access_Token");
        String refreshToken = jwtTokenProvider.resolveToken((HttpServletRequest) request, "Refresh_Token");


        if(accessToken != null) {
            // 어세스 토큰값이 유효하다면 setAuthentication를 통해
            // security context에 인증 정보저장
            if(jwtTokenProvider.validateToken(accessToken)){
                // 토큰으로부터 유저 정보를 받아서 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                // SecurityContext 에 객체 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // 어세스 토큰이 만료된 상황 && 리프레시 토큰 또한 존재하는 상황
            else if (refreshToken != null) {
                // 리프레시 토큰이 유효하고 리프레시 토큰이 DB와 비교했을때 똑같다면
                if (jwtTokenProvider.validateToken(refreshToken)) {
                    // 리프레시 토큰으로 아이디 정보 가져오기
                    String email = jwtTokenProvider.getEmail(refreshToken);
                    // 새로운 어세스 토큰 발급
                    String newAccessToken = jwtTokenProvider.createToken(email, "Access");
                    // 헤더에 어세스 토큰 추가
                    jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                    // Security context에 인증 정보 넣기
                    Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);
                    // SecurityContext 에 객체 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                // 리프레시 토큰이 만료 || 리프레시 토큰이 DB와 비교했을때 똑같지 않다면
                else {
                    throw new UserException(ErrorCode.UNAUTHORIZED);
                }
            }
        }

        // 다음 Filter 실행
        chain.doFilter(request, response);
    }
}
