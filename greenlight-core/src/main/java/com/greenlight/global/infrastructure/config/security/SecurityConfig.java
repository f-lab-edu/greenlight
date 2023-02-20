package com.greenlight.global.infrastructure.config.security;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.greenlight.global.domain.model.constants.common.GreenLightConstants;
import com.greenlight.global.domain.model.constants.member.MemberRole;
import com.greenlight.global.infrastructure.config.security.jwt.JwtAccessDeniedHandler;
import com.greenlight.global.infrastructure.config.security.jwt.JwtAuthenticationEntryPoint;
import com.greenlight.global.infrastructure.config.security.jwt.JwtSecurityConfig;
import com.greenlight.global.infrastructure.config.security.jwt.TokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CorsConfig corsConfig;

	private final TokenProvider tokenProvider;
	// 자격 증명
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	// 권한
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() throws Exception {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/favicon.ico", "/error");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.formLogin().disable()
				.httpBasic().disable()
				.csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)

				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and()
				.authorizeRequests()
				.antMatchers(GreenLightConstants.PATH_API_VERSION_1+"/user/**")
				.access("hasRole('"+ MemberRole.ROLE_USER.name() +"') or hasRole('"+ MemberRole.ROLE_MANAGER.name() +"') or hasRole('"+ MemberRole.ROLE_ADMIN.name() +"')")
				.antMatchers(GreenLightConstants.PATH_API_VERSION_1+"/manager/**")
				.access("hasRole('"+ MemberRole.ROLE_MANAGER.name() +"') or hasRole('"+ MemberRole.ROLE_ADMIN.name() +"')")
				.antMatchers(GreenLightConstants.PATH_API_VERSION_1+"/admin/**")
				.access("hasRole('"+ MemberRole.ROLE_ADMIN.name() +"')")
				.anyRequest()
				.permitAll()

				.and()
				.apply(new JwtSecurityConfig(tokenProvider))

				.and()
				.addFilter(corsConfig.corsFilter()) //crossorigin 커스텀변경
				.logout()
				.logoutUrl(GreenLightConstants.PATH_API_VERSION_1+"/member/logout")
//				.logoutRequestMatcher(new AntPathRequestMatcher(GreenLightConstants.PATH_API_VERSION_1+"/member/logout")) //요기서 토큰삭제하면 될듯
				.addLogoutHandler((request, response, authentication) -> {
					HttpSession session = request.getSession();
					if (session != null) {
						session.invalidate();
					}
				})
				.logoutSuccessUrl("/")
//				.invalidateHttpSession(true)
				.and()
				.build();
	}

}
