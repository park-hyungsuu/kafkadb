package com.hyungsuu.common.util;

import java.time.LocalDateTime;

import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.*;

@Slf4j
@Component
public class JwtTokenUtil {
	
	private static Date nowDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
	

	private static String secret;
	@Value("${jwt.secret.Key}")
    public void setsecretName(String secret) {
        JwtTokenUtil.secret = secret;
	}
	
	private static long jwtExpTime;
	@Value("${jwt.expTime}")
    public void setJwtExpTime(long jwtExpTime) {
        JwtTokenUtil.jwtExpTime = jwtExpTime;
    }
	/*
	 * TOKEN으로 유저명 찾아옴!!
	 * */
	public static String getUsernameFromToken(String token) {
		if(validateToken(token)) {
			return getClaimFromToken(token, Claims::getSubject);
		}
		return "";
	}
	
	/*
	 * TOKEN으로 데이터 찾아옴!!
	 * */
	public static String getClaimsDataFromToken(String name,String token) {
		if(validateToken(token)) {
			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			String realname = claims.get(name,String.class);
			return realname;
		}
		return "";
	}
	/*
	 * TOKEN으로 유효시간 찾아옴!!
	 * */
	
	public static Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);

	}
	public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		if(validateToken(token)) {
			final Claims claims = getAllClaimsFromToken(token);
			
			return claimsResolver.apply(claims);
		}
		return null;
	}
	private static Claims getAllClaimsFromToken(String token) {
		Claims claims= Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		log.debug("claims"+claims.toString());
		return claims;
	}
	
	public static Boolean isTokenExpired(String token) {
		Boolean validateToken = validateToken(token);
		if(validateToken) {
			Date expiration = getExpirationDateFromToken(token);
			int check = expiration.compareTo(nowDate);
			if(0==check){
				return false;
			}else if(1==check) {
				return true;
			}else if(-1==check) {
				return false;
			}else{
				return false;
			}
		}
		return validateToken;
	}
	
	/*
	 * 토큰생성
	 * */
	public static String generateToken(String userId, String userAuth, long JWT_TOKEN_VALIDITY) {
	
//		Date date =new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 60 * 1000);
		long date =System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 60 * 1000;
		Claims claims = Jwts.claims().setSubject(userId);
    	// 업무에 따라 추가 및 삭제 필요
		claims.put("jwtUserId", userId);
    	claims.put("jwtUserAuth", userAuth);
		return doGenerateToken(claims, JWT_TOKEN_VALIDITY, date);
	}
	
	
	public static String generateToken(String userId, String userAuth, long JWT_TOKEN_VALIDITY, long date) {
		
		Claims claims = Jwts.claims().setSubject(userId);
    	// 업무에 따라 추가 및 삭제 필요
		claims.put("jwtUserId", userId);
    	claims.put("jwtUserAuth", userAuth);
		return doGenerateToken(claims, JWT_TOKEN_VALIDITY, date);
	}
	private static String doGenerateToken(Map<String, Object> claims, long JWT_TOKEN_VALIDITY, long date) {
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(date))
				.setExpiration(new Date(date + JWT_TOKEN_VALIDITY * 60 *1000))
//				.signWith(SignatureAlgorithm.RS512, secret)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	public static Boolean validateToken(String token) {
		try{
			getAllClaimsFromToken(token);
			return true;
		} catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
        	log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
        	log.error("Expired JWT token{}",ex);
        } catch (UnsupportedJwtException ex) {
        	log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
        	log.error("JWT claims string is empty.{}",ex);
        }

    	return false;
	}
}