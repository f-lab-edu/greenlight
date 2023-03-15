package com.greenlight.global.infrastructure.persistence;

import com.greenlight.global.domain.model.entity.Member;
import com.greenlight.global.domain.repository.MemberRepository;
import com.greenlight.global.infrastructure.persistence.jpa.MemberJpaRepository;

public class MemberAdapterRepository implements MemberRepository {

	private final MemberJpaRepository memberJpaRepository;

	public MemberAdapterRepository(MemberJpaRepository memberJpaRepository) {
		this.memberJpaRepository = memberJpaRepository;
	}

	@Override
	public int save(Member member) {
		return memberJpaRepository.save(member).getNo().intValue();
	}

	@Override
	public Member findByMemberId(String memberId) {
		return memberJpaRepository.findByMemberId(memberId);
	}

	/* //myBatis adapter
	private final MyBatisMemberRepository myBatisMemberRepository;

	public MemberAdapterRepository(MyBatisMemberRepository myBatisMemberRepository) {
		this.myBatisMemberRepository = myBatisMemberRepository;
	}

	@Override
	public int save(Member member) {
		return myBatisMemberRepository.save(member);
	}

	@Override
	public Member findByMemberId(String memberId) {
		return myBatisMemberRepository.findByMemberId(memberId);
	}
	*/
}
