package com.greenlight.auth.configuration.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * request header에서 가져온 token을 filtering
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

		String servletPath = request.getServletPath();
		String jwtAccessToken = resolveAccessToken(request);

		if (servletPath.equals("/api/auth/refresh-token")) {
			filterChain.doFilter(request, response);
		} else {
			log.info("JwtFilter doFilterInternal");
			// 토큰이 정상일때 Authentication
			if (StringUtils.hasText(jwtAccessToken) && jwtTokenProvider.validateToken(jwtAccessToken)) {
				Authentication authentication = jwtTokenProvider.getAuthentication(jwtAccessToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				log.info("Security Context 에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), servletPath);
			} else {
				log.info("유효한 JWT 토큰이 없습니다, uri: {}", servletPath);
			}
			log.info("JwtFilter End");

			filterChain.doFilter(request, response);
		}
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getAuthorizationHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperties.getTokenHeaderPrefix())) {
        	log.info("bearerToken = {}", bearerToken);
            return bearerToken.substring(7);
        }
        return null;
    }
}
