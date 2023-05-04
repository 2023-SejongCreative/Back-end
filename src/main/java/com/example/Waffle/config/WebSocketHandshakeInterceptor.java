package com.example.Waffle.config;

import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@RequiredArgsConstructor
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;


    //handshake전에 먼저 실행
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception{

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            //header에서 토큰 뽑기
            String accessToken = servletRequest.getServletRequest().getHeader("access_token");
            String refreshToken = servletRequest.getServletRequest().getHeader("refresh_token");

            System.out.println("socket atk: " + accessToken);

            //토큰 검증
            if (accessToken != null) {
                if (jwtTokenProvider.validateToken(accessToken)) {
                    // Redis 에 해당 accessToken logout 여부 확인
                    String isLogout = (String) redisTemplate.opsForValue().get(accessToken);

                    if (ObjectUtils.isEmpty(isLogout)) {
                        // 토큰으로부터 유저 정보를 받아서 저장
                        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                        // SecurityContext 에 객체 저장
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                    //검증 결과 저장
                    System.out.println("socket 인증 완료");
                    attributes.put("isValidToken", true);
                    return true;
                }
                // 어세스 토큰이 만료된 상황 && 리프레시 토큰 또한 존재하는 상황
                else if (refreshToken != null) {
                    // 리프레시 토큰이 만료
                    if (!jwtTokenProvider.validateToken(refreshToken)) {
                        throw new UserException(ErrorCode.UNAUTHORIZED);
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {

    }
}
