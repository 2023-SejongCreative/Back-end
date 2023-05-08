package com.example.Waffle.config;

import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestHeader;

@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        assert headerAccessor != null;

        if (headerAccessor.getCommand() == StompCommand.CONNECT) { // 연결 시에만 header 확인
            String accessToken = String.valueOf(headerAccessor.getNativeHeader("access_token"));
            String refreshToken = String.valueOf(headerAccessor.getNativeHeader("refresh_token"));

            //토큰 앞뒤로 []있어서 인증 안됨 -> 해당 부분 잘라주기
            accessToken = accessToken.substring(1, accessToken.length()-1);
            refreshToken = refreshToken.substring(1, refreshToken.length()-1);
            System.out.println("socket atk: " + accessToken);

            //토큰 검증
            if (accessToken != null) {
                if (jwtTokenProvider.validateToken(accessToken)) {
                    // Redis 에 해당 accessToken logout 여부 확인
                    String isLogout = (String) redisTemplate.opsForValue().get(accessToken);

                    if (ObjectUtils.isEmpty(isLogout)) {
                        String email = jwtTokenProvider.getEmail(accessToken);

                        System.out.println(email);

                        //검증 결과 저장
                        headerAccessor.addNativeHeader("User", email);
                        System.out.println("socket 인증 완료");
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
        }
        return message;
    }
}
