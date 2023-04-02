package com.example.Waffle.token;

import com.example.Waffle.dto.TokenDto;
import com.example.Waffle.entity.TokenEntity;
import com.example.Waffle.repository.TokenRepository;
import com.example.Waffle.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secretkey}")
    private String secretKey;

    private Key key;

    //토큰 유효시간 설정
    private long accessTime = 30 * 60 * 1000L; //30분
    private long refreshTime = 7 * 24 * 60 * 60 * 1000L; //7일

    private final CustomUserDetailsService customUserDetailsService;
    private final TokenRepository tokenRepository;

    //secretKey 인코딩
    @PostConstruct
    protected void init(){
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    // ATK, RTK 생성
    public TokenDto createAllToken(String email) {
        return new TokenDto(createToken(email, "Access"), createToken(email, "Refresh"), refreshTime);
    }

    //JWT 토큰 생성
    public String createToken(String email, String type){
        Date now = new Date();

        long time = type.equals("Access") ? accessTime : refreshTime;

        //Payload - registered claim
        Claims claims = Jwts.claims()
                .setSubject(type)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTime));

        //Payload - private claim
        claims.put("email", email);

        String jwt = Jwts.builder()
                .setHeader(createHeader()) //헤더
                .setClaims(claims) //페이로드
                .signWith(key, SignatureAlgorithm.HS256) //Signature
                .compact();

        return jwt;
    }

    //토큰 Header 설정
    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ","JWT");
        header.put("alg","HS256"); // 해시 256 암호화
        return header;
    }

    //토큰에서 인증정보 조회
    public Authentication getAuthentication(String token){
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(this.getEmail(token));

        return new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
    }

    // 토큰에서 회원 정보(Email) 추출
    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }


    // 토큰 유효성, 만료일자 확인
    public boolean validateToken(String token) {
        try {
            //복호화
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // refreshToken 토큰 검증
    // db에 저장되어 있는 token과 비교
    public Boolean validateRefreshToken(String refreshToken) {

        // 1차 토큰 검증
        if(!validateToken(refreshToken)) return false;

        // DB에 저장한 토큰 비교
        Optional<TokenEntity> tokenEntity = tokenRepository.findByEmail(getEmail(refreshToken));

        return tokenEntity.isPresent() && refreshToken.equals(tokenEntity.get().getRefreshToken());
    }

    // Request의 Header에서 token 값 가져오기
    public String resolveToken(HttpServletRequest request, String name) {
        return request.getHeader(name);
    }

    // 어세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Access_Token", accessToken);
    }

    // 리프레시 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("Refresh_Token", refreshToken);
    }

}
