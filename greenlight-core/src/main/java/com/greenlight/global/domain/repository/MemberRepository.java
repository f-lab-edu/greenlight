package com.greenlight.global.domain.repository;

import com.greenlight.global.application.processor.MemberSignUpProcessor;
import com.greenlight.global.domain.model.Member;
import com.greenlight.global.infrastructure.config.MyBatisMapper;

@MyBatisMapper
public interface MemberRepository {

	Long save(MemberSignUpProcessor.Command command);
	Member findByEmail(String email);

}
