package shopcenter.com.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import shopcenter.com.service.JwtService;

@SuppressWarnings("deprecation")
@Service
public class JwtServiceImpl implements JwtService{
	@Value("${jwt.expiryHour}")
	private Long expiryHour;
	
	@Value("${jwt.signerKey}")
	private String secretKey;
	
	@Override
	public String generateToken(UserDetails user) {
		return generateToken(new HashMap<>(), user);
	}
	
	private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(userDetails.getUsername())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiryHour))
					.signWith(getKey(), SignatureAlgorithm.HS256)
					.compact();
	}
	
	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	@Override
	public boolean isValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername());
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
		final Claims claims = extraAllClaim(token);
		return claimResolver.apply(claims);
	}
	
	private Claims extraAllClaim(String token) {
		return Jwts.parser().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
	}
}
