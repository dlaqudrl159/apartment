package kr.co.dw.Service.Auth;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenProvider {

	@Value("${jwt.service.key}")
	private String serviceKey;
	
	private final long tokenValidMilliseconds = 1000L * 60 * 60; // 1시간
	
	public String createToken(String userId) {
		Claims claims = Jwts.claims().setSubject(userId);
		Date date = new Date();
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(date)
				.setExpiration(new Date(date.getTime() + tokenValidMilliseconds))
				.signWith(getSigningKey())
				.compact();
	}
	
	   private Key getSigningKey() {
	       byte[] keyBytes = serviceKey.getBytes(StandardCharsets.UTF_8);
	       return Keys.hmacShaKeyFor(keyBytes);
	   }
}
