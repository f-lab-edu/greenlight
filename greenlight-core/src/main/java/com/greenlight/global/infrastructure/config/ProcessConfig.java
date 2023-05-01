package com.greenlight.global.infrastructure.config;

import com.greenlight.global.application.processor.MemberSignInProcessor;
import com.greenlight.global.application.processor.MemberSignUpProcessor;
import com.greenlight.global.domain.auth.Auth;
import com.greenlight.global.domain.pwd.PasswordHelper;
import com.greenlight.global.infrastructure.persistence.MemberAdapterRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfig {

	private final MemberAdapterRepository memberAdapterRepository;
	private final PasswordHelper passwordHelper;
	private final Auth auth;

	public ProcessConfig(MemberAdapterRepository memberAdapterRepository, PasswordHelper passwordHelper, Auth auth) {
		this.memberAdapterRepository = memberAdapterRepository;
		this.passwordHelper = passwordHelper;
		this.auth = auth;
	}


	@Bean
	public MemberSignUpProcessor memberSignUpProcessor() {
		return new MemberSignUpProcessor(memberAdapterRepository, passwordHelper);
	}

	@Bean
	public MemberSignInProcessor memberSignInProcessor() {
		return new MemberSignInProcessor(auth);
	}
}
