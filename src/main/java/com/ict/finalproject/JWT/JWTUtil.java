package com.ict.finalproject.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JWTUtil {

    private final SecretKey secretKey;

    // 생성자에서 JWT SecretKey를 초기화
    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        System.out.println("JWT Secret: " + secret); // secret 값 출력
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));  // SecretKey 객체 생성
    }

    // JWT 토큰에서 Claims 추출
    public Claims getClaims(String token) {
        try {
            // JWT 토큰을 파싱하여 Claims 반환
            return Jwts.parser()                 // 최신 버전에서는 parserBuilder() 사용
                    .setSigningKey(secretKey)           // setSigningKey에 SecretKey 전달
                    .build()
                    .parseClaimsJws(token)
                    .getBody();                         // Claims 객체 반환
        } catch (JwtException e) {
            e.printStackTrace();
            return null;  // JWT 파싱 오류가 발생하면 null 반환
        }
    }

    // JWT 토큰에서 사용자 ID를 추출하는 메서드
    public String getUserIdFromToken(String token) {
        Claims claims = getClaims(token);  // JWT 토큰에서 Claims 추출
        return claims != null ? claims.get("userid", String.class) : null;  // Claims에서 "userid" 키 값 추출
    }

    // JWT 토큰이 만료되었는지 확인하는 메서드
    public Boolean isExpired(String token) {
        Claims claims = getClaims(token);
        if (claims == null) {
            // Claims가 null인 경우 토큰이 유효하지 않음
            return true;
        }
        Date expiration = claims.getExpiration();
        System.out.println("토큰 만료 시간: " + expiration);
        return expiration.before(new Date());
    }

    // JWT 토큰 생성 메서드 예시
    public String createJwt(String userid, Long expiredMs) {
        return Jwts.builder()
                .claim("userid", userid)  // 사용자 ID 정보를 Claim으로 추가
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 발행 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))  // 만료 시간 설정
                .signWith(secretKey)  // 서명 설정
                .compact();
    }


    // HTTP 요청 헤더에서 JWT 토큰을 추출하는 메서드
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // 'Bearer ' 이후의 토큰 문자열을 추출하고, 앞뒤 공백을 제거하여 반환
            return bearerToken.substring(7).trim();
        }
        return null;
    }

    // JWT 토큰의 유효성을 검사하는 메서드
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            System.out.println("Claims: " + claims);  // Claims가 올바르게 파싱되었는지 확인
            return claims != null && !isExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("JWT 유효성 검증 실패: " + e.getMessage());
            return false;
        }
    }

    // JWT 토큰에서 권한 정보 추출
    public List<GrantedAuthority> getAuthorities(String token) {
        Claims claims = getClaims(token);
        if (claims == null) {
            return new ArrayList<>();  // Claims가 null이면 빈 권한 리스트 반환
        }
        String role = claims.get("role", String.class);
        if (role == null || role.isEmpty()) {
            return new ArrayList<>();  // role 정보가 없으면 빈 권한 리스트 반환
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}