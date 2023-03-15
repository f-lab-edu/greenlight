package com.greenlight.global.domain.repository;

import com.greenlight.global.domain.model.entity.Member;

public interface MemberRepository {
	int save(Member member);
	Member findByMemberId(String memberId);
}
