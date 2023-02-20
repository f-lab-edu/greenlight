package com.greenlight.global.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.greenlight.global.application.processor.MemberSignInProcessor;
import com.greenlight.global.application.processor.MemberSignUpProcessor;
import com.greenlight.global.domain.auth.Auth;
import com.greenlight.global.domain.repository.member.MemberRepository;

@Configuration
public class ProcessConfig {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final Auth auth;

	public ProcessConfig(MemberRepository memberRepository, PasswordEncoder passwordEncoder, Auth auth) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
		this.auth = auth;
	}

	@Bean
	public MemberSignUpProcessor memberSignUpProcessor() {
		return new MemberSignUpProcessor(memberRepository, passwordEncoder);
	}

	@Bean
	public MemberSignInProcessor memberSignInProcessor() {
		return new MemberSignInProcessor(auth);
	}
}
