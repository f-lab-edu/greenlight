package com.greenlight.auth.configuration;

import com.greenlight.auth.configuration.jwt.JwtExceptionFilter;
import com.greenlight.auth.configuration.jwt.JwtProperties;
import com.greenlight.auth.configuration.jwt.JwtSecurityConfiguration;
import com.greenlight.auth.configuration.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final CorsFilter corsFilter;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	private final JwtTokenProvider jwtTokenProvider;
	private final JwtExceptionFilter jwtExceptionFilter;
	private final JwtProperties jwtProperties;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers(
			"/favicon.ico",
			"/error");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.httpBasic().disable()
			.csrf().disable()
			.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling()
			.authenticationEntryPoint(customAuthenticationEntryPoint)
			.accessDeniedHandler(customAccessDeniedHandler)
		.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)	// 토큰(JWT)
		.and()
			.authorizeRequests()
			.antMatchers("/api/auth/create-token").permitAll()
			.antMatchers("/api/auth/refresh-token").permitAll()
			.antMatchers("/api/signUp").permitAll()
			.anyRequest().authenticated()
		.and()
			.apply(new JwtSecurityConfiguration(jwtTokenProvider, jwtExceptionFilter, jwtProperties))
		.and().build();
	}

}
