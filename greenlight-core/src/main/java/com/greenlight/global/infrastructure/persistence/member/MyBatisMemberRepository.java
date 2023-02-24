package com.greenlight.global.infrastructure.persistence.member;

import org.mybatis.spring.SqlSessionTemplate;

import com.greenlight.global.domain.model.Member;
import com.greenlight.global.domain.repository.member.MemberRepository;

public class MyBatisMemberRepository implements MemberRepository {

    private final SqlSessionTemplate sqlSessionTemplateGreenLight;
    private final String MEMBER_NS = "greenlight.member.";

    public MyBatisMemberRepository(SqlSessionTemplate sqlSessionTemplateGreenLight) {
        this.sqlSessionTemplateGreenLight = sqlSessionTemplateGreenLight;
    }

    @Override
    public int save(Member member) {
        return sqlSessionTemplateGreenLight.insert(MEMBER_NS + "insertMember", member);
    }

    @Override
    public Member findByMemberId(String memberId) {
        return sqlSessionTemplateGreenLight.selectOne(MEMBER_NS + "selectByMemberId", memberId);
    }
}
