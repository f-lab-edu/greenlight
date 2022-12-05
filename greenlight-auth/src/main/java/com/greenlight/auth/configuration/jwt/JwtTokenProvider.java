package com.greenlight.auth.configuration.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.greenlight.auth.application.dto.TokenDTO;
import com.greenlight.auth.domain.ErrorCode;
import com.greenlight.auth.exception.JwtException;

/**
 * 토큰의 생성과 유효성 검사
 */
@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String AUTHORITIES_DELIMITER = ",";

    private final Key key;
    private final String tokenHeaderPrefix;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);

        this.tokenHeaderPrefix = jwtProperties.getTokenHeaderPrefix();
        this.accessTokenValidityInMilliseconds = jwtProperties.getAccessTokenValidityInSeconds() * 1000L;
        this.refreshTokenValidityInMilliseconds = jwtProperties.getRefreshTokenValidityInSeconds() * 1000L;
    }

    public Optional<TokenDTO> generateTokenDTO(Authentication authentication) {
        String accessToken = createAccessToken(authentication);
        String refreshToken = createRefreshToken(authentication);

        return Optional.of(new TokenDTO(accessToken, refreshToken));
    }

    private String createAccessToken(Authentication authentication) {
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + this.accessTokenValidityInMilliseconds);

        return createToken(authentication, accessTokenExpiresIn);
    }

    private String createRefreshToken(Authentication authentication) {
        long now = (new Date()).getTime();
        Date refreshTokenExpiresIn = new Date(now + this.refreshTokenValidityInMilliseconds);

        return createToken(authentication, refreshTokenExpiresIn);
    }

    private String createToken(Authentication authentication, Date expiration) {
        // 권한 정보
        String authorities = generateStringToAuthorities(authentication);

        // JWT 생성
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
				.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private String generateStringToAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    /**
     * token에 담겨있는 정보를 이용해 Authentication 객체를 리턴
     */
    public Authentication getAuthentication(String token) {
        // 토큰 복호화
        Claims claims = parseClaims(token);
        List<SimpleGrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(AUTHORITIES_DELIMITER))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

	public boolean isExpired(String token) {
		Date expiredDate = parseClaims(token).getExpiration();
		// Token의 만료 날짜가 지금보다 이전인지 check
		return expiredDate.before(new Date());
	}

    /**
     * token의 유효성 검사
     */
    public boolean validateToken(String token) {
        try {
            // 만료일자
            return !parseClaims(token).getExpiration().before(new Date());
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new JwtException(ErrorCode.JWT_SIGNATURE_FAILED);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new JwtException(ErrorCode.JWT_EXPIRED_FAILED);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw new JwtException(ErrorCode.JWT_UNSUPPORTED_FAILED);
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new JwtException(ErrorCode.JWT_ILLIGAL_ARG_FAILED);
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
			.parseClaimsJws(token).getBody();
    }
}
