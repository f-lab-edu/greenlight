package com.greenlight.global.infrastructure.config;

import com.greenlight.global.infrastructure.config.jwt.filter.JwtAuthenticationFilter;
import com.greenlight.global.infrastructure.config.jwt.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private AuthenticationConfiguration configuration;

	private final CorsConfig corsConfig;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		this.configuration = configuration;
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.csrf().disable()

			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()
			.formLogin().disable()
			.httpBasic().disable()

			// 1. 권한 체크해보자
			.authorizeRequests()
			.antMatchers("/api/v1/user/**")
			.access("hasRole('ROLE_USER') or hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/manage/**")
			.access("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/admin/**")
			.access("hasRole('ROLE_ADMIN')")
			.anyRequest()
			.permitAll()

			.and()
			.addFilter(corsConfig.corsFilter()) //crossorigin 커스텀변경
			.addFilterAt(new JwtAuthenticationFilter(authenticationManager(configuration)), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JwtAuthorizationFilter(authenticationManager(configuration)), UsernamePasswordAuthenticationFilter.class)
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //요기서 토큰삭제하면 될듯
//			.addLogoutHandler()
			.logoutSuccessUrl("/home")
			.and()
			.build();
	}

}
