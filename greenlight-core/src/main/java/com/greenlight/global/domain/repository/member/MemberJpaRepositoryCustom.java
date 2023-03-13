package com.greenlight.global.domain.repository.member;

import com.greenlight.global.domain.model.entity.Member;

public interface MemberJpaRepositoryCustom {
	Member findByMemberId(String memberId);
}
