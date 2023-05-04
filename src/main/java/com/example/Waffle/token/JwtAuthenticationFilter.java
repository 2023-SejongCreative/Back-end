package com.example.Waffle.token;

import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if(request == null){
            System.out.println("request null");
        }

        System.out.println("----------header start-----------");
        request.getHeaderNames().asIterator().forEachRemaining(
                headerName -> System.out.println(headerName + ": " + request.getHeader(headerName))
        );
        System.out.println("----------header end----------");

        // 헤더에서 JWT 토큰 받아오기
        String accessToken = jwtTokenProvider.resolveToken(request, "access_token");
        String refreshToken = jwtTokenProvider.resolveToken(request, "refresh_token");

        System.out.println("["+accessToken+"]");
        System.out.println("["+refreshToken+"]");

        if(request.getHeader("upgrade") != null){
            if(request.getHeader("upgrade").equals("websocket"))
                System.out.println("websocket connect request");
        }
        else if(accessToken != null) {
            // 어세스 토큰값이 유효하다면 setAuthentication를 통해
            // security context에 인증 정보저장
            if(jwtTokenProvider.validateToken(accessToken)){

                // Redis 에 해당 accessToken logout 여부 확인
                String isLogout = (String)redisTemplate.opsForValue().get(accessToken);

                if(ObjectUtils.isEmpty(isLogout)){
                    // 토큰으로부터 유저 정보를 받아서 저장
                    Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                    // SecurityContext 에 객체 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            // 어세스 토큰이 만료된 상황 && 리프레시 토큰 또한 존재하는 상황
            else if (refreshToken != null) {
                // 리프레시 토큰이 만료
                if (!jwtTokenProvider.validateToken(refreshToken)) {
                    throw new UserException(ErrorCode.UNAUTHORIZED);
                }
            }
        }

        // 다음 Filter 실행
        chain.doFilter(request, response);
    }
}
