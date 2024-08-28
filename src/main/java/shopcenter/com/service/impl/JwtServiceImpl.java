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
import shopcenter.com.enums.TokenType;
import shopcenter.com.exception.AppException;
import shopcenter.com.exception.ErrorCode;
import shopcenter.com.service.JwtService;

@SuppressWarnings("deprecation")
@Service
public class JwtServiceImpl implements JwtService{
	@Value("${jwt.expiryHour}")
	private Long expiryHour;
	
	@Value("${jwt.expiryDay}")
	private Long expiryDay;
	
	@Value("${jwt.accessKey}")
	private String accessKey;
	
	@Value("${jwt.resetKey}")
	private String resetKey;
	
	@Value("${jwt.refreshKey}")
	private String refreshKey;
	
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
//					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 10)) //test time token
					.signWith(getKey(TokenType.ACCESS_TOKEN), SignatureAlgorithm.HS256)
					.compact();
	}
	
	private String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(userDetails.getUsername())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * expiryDay))
					.signWith(getKey(TokenType.REFRESH_TOKEN), SignatureAlgorithm.HS256)
					.compact();
	}
	
	@Override
	public String generateResetToken(UserDetails user) {
		return generateResetToken(new HashMap<>(), user);
	}
	
	private String generateResetToken(Map<String, Object> claims, UserDetails userDetails) {
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(userDetails.getUsername())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
					.signWith(getKey(TokenType.RESET_TOKEN), SignatureAlgorithm.HS256)
					.compact();
	}
	
	private Key getKey(TokenType type) {
		switch(type) {
			case ACCESS_TOKEN -> {
				return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
			}
			case REFRESH_TOKEN -> {
				return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
			}
			case RESET_TOKEN -> {
				return Keys.hmacShaKeyFor(Decoders.BASE64.decode(resetKey));
			}
			default -> throw new AppException(ErrorCode.TOKEN_TYPE_INVALID);
		}
	}

	@Override
	public String extractUsername(String token, TokenType type) {
		return extractClaim(token, type, Claims::getSubject);
	}

	@Override
	public boolean isValid(String token, TokenType type, UserDetails userDetails) {
		final String username = extractUsername(token, type);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token, type);
	}
	
	private <T> T extractClaim(String token, TokenType type, Function<Claims, T> claimResolver){
		final Claims claims = extraAllClaim(token, type);
		return claimResolver.apply(claims);
	}
	
	private Claims extraAllClaim(String token, TokenType type) {
		return Jwts.parser().setSigningKey(getKey(type)).build().parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token, TokenType type) {
		return extractExpiration(token, type).before(new Date());
	}
	
	private Date extractExpiration(String token, TokenType type) {
		return extractClaim(token, type, Claims::getExpiration);
	}
	
	@Override
	public String generateRefreshToken(UserDetails user) {
		return generateRefreshToken(new HashMap<>(), user);
	}
}
