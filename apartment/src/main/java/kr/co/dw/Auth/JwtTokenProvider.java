package kr.co.dw.Auth;

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
	private String JWT_SERVICE_KEY;

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

	public String getUserId(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean validateToken(String token) {
	       try {
	           Jwts.parserBuilder()
	               .setSigningKey(getSigningKey())
	               .build()
	               .parseClaimsJws(token);
	           return true;
	       } catch (Exception e) {
	           return false;
	       }
	   }

	private Key getSigningKey() {
		byte[] keyBytes = JWT_SERVICE_KEY.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
