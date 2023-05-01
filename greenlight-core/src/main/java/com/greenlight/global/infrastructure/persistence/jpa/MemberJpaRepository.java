package com.greenlight.global.infrastructure.persistence.jpa;

import com.greenlight.global.domain.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
	Member findByMemberId(String memberId);
}
