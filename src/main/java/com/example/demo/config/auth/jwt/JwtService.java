package com.example.demo.config.auth.jwt;

import com.example.demo.config.auth.CustomUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //todo
    // 암호화 키 generator 사이트를 통해 키를 생성해서 가져온다.
    // application-properties에 등록해서 사용하면 좋다.

    private static final String SECRET_KEY = "MtSCoZe73qdzicfTSKdlgwTT8I5Lkn5S";

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // claim 없이 token generate하기
//    public String generateToken(
//            CustomUserDetail customUserDetail
//    ){
//        return generateToken(new HashMap<>(),customUserDetail);
//    }

    public String generateToken(
            String subject
    ) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //만료 시점 설정
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                // 256-bit의 key를 사용해야한다.
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    //jwt를 검증하는 method
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUserNameFromJwtToken(token);
        return username.equals(userDetails.getUsername())&& ! isTokenExpired(token);
    }
    //jwt가 만료되었는지 확인하는 method
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    //현재 헤더에서 넘어온 jwt의 expriation을 추출하는 method
    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    // token으로 유저 이름 가져오기
    public String getUserNameFromJwtToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

}