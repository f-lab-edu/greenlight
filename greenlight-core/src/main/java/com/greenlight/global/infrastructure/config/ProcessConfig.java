package com.greenlight.global.infrastructure.config;

import com.greenlight.global.application.processor.MemberSignUpProcessor;
import com.greenlight.global.domain.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfig {

	private final MemberRepository memberRepository;

	public ProcessConfig(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Bean
	public MemberSignUpProcessor memberJoinProcessor() {
		return new MemberSignUpProcessor(memberRepository);
	}
}
