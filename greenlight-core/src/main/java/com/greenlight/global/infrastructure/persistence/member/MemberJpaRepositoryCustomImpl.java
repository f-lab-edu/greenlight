package com.greenlight.global.infrastructure.persistence.member;

import static com.greenlight.global.domain.model.entity.QMember.member;
import com.greenlight.global.domain.model.entity.Member;
import com.greenlight.global.domain.repository.member.MemberJpaRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class MemberJpaRepositoryCustomImpl implements MemberJpaRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public MemberJpaRepositoryCustomImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public Member findByMemberId(String memberId) {
		return queryFactory
			.selectFrom(member)
			.where(member.memberId.eq(memberId))
			.fetchOne();
	}
}
