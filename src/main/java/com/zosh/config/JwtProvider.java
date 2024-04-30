package com.zosh.config;
import io.jsonwebtoken.security.WeakKeyException;//The specified key byte array is 200 bits which is not secure enough for any JWT HMAC-SHA algorithm.

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
	
	
//	private final String secretKey;
//    private final long validityInMilliseconds;


    private final Key secretKey;
    private final long validityInMilliseconds;

    public JwtProvider() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.validityInMilliseconds = 3600000; // 1 hour
    }

	//SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
			//hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public String generateToken(Authentication auth) {
		
		String jwt=Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+846000000))
				.claim("email",auth.getName())
				.signWith(secretKey).compact();
		
		return jwt;
	}
	
	public String getEmailFromJwtToken(String jwt) {
		jwt=jwt.substring(7);
		
		Claims claims=Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
		String email=String.valueOf(claims.get("email"));
		
		return email;
	}

}
