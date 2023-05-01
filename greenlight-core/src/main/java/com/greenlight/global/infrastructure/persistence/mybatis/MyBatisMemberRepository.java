package com.greenlight.global.infrastructure.persistence.mybatis;

import com.greenlight.global.domain.model.entity.Member;
import com.greenlight.global.domain.repository.MemberRepository;
import org.mybatis.spring.SqlSessionTemplate;

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
