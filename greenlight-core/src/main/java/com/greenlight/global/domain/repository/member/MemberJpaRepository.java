package com.greenlight.global.domain.repository.member;

import com.greenlight.global.domain.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long>, MemberJpaRepositoryCustom {
}
