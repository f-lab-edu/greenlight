package com.greenlight.global.domain.repository.member;

import com.greenlight.global.domain.model.Member;

public interface MemberRepository {

	int save(Member member);
	Member findByMemberId(String memberId);

}
