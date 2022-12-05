package com.greenlight.auth.configuration;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request
		, HttpServletResponse response
		, AuthenticationException authException) throws IOException {
		log.info("CustomAuthenticationEntryPoint ####");
		// 유효한 자격증명을 제공하지 않고 접근하려 할때 401
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}
}
