package com.revature.lostchapterbackend.JWT;

import static java.util.Arrays.stream;
import static com.revature.lostchapterbackend.constants.SecurityConstant.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class TokenProvider {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	public String generateToken(UserDetail userDetail) {
		String[] claims = this.getClaimsFromUser(userDetail);
		
		return JWT.create().withIssuer(PROJECT_3).withAudience(PROJECT_3_ADMIN)
				.withIssuedAt(new Date()).withSubject(userDetail.getUsername())
				.withArrayClaim(AUTHORITIES, claims)
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(this.secretKey.getBytes()));
	}


	public List<GrantedAuthority> getAuthorities(String token) {
		String[] claims = this.getClaimsFromToken(token);
		return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	
	public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
		token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		return token;
	}

	public boolean isTokenValid(String username, String token) {
		JWTVerifier verifier = this.getJWTVerifier();
		return StringUtils.isNotEmpty(username) && !this.isTokenExpired(verifier, token);
	}

	public String extractToken(String authorizationHeader) {
		return authorizationHeader.substring(TOKEN_PREFIX.length());
	}
	
	public HttpHeaders getHeaderJWT(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(JWT_TOKEN_HEADER, token);
		
		return headers;
	}
	
	private boolean isTokenExpired(JWTVerifier verifier, String token) {
		Date expiration = verifier.verify(token).getExpiresAt();
		return expiration.before(new Date());
	}

	private JWTVerifier getJWTVerifier() {
		JWTVerifier verifier;
		
		try {
			Algorithm algo = Algorithm.HMAC512(this.secretKey);
			verifier = JWT.require(algo).withIssuer(PROJECT_3).build();
		} catch(JWTVerificationException e) {
			throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
		}
		
		return verifier;
	}

	public String getSubject(String token) {
		JWTVerifier verifier = getJWTVerifier();
		DecodedJWT decoder = verifier.verify(token);
		return decoder.getSubject();
	}
	
	private String[] getClaimsFromToken(String token) {
		JWTVerifier verifier = this.getJWTVerifier();
		return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
	}

	private String[] getClaimsFromUser(UserDetail userDetail) {
		List<String> authorities = new ArrayList<>();
		for(GrantedAuthority grantedAuthority : userDetail.getAuthorities()) {
			authorities.add(grantedAuthority.getAuthority());
		}
		return authorities.toArray(new String[0]);
	}
}
