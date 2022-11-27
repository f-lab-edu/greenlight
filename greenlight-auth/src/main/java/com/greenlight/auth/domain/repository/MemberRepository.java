package com.greenlight.auth.domain.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.greenlight.auth.application.dto.TokenDTO;
import com.greenlight.auth.domain.entity.Member;

@Repository
public class MemberRepository {

    private static Map<String, Object> storege = new HashMap<>();

    public Member saveMember(Member member) {
        storege.put(member.getMemberId(), member);
        return (Member) storege.get(member.getMemberId());
    }

    public Optional<Member> findByMemberId(String memberId) {
        return Optional.ofNullable((Member) storege.get(memberId));
    }

    public TokenDTO saveRefreshToken(String memberId, TokenDTO tokenDTO) {
        storege.put(memberId, tokenDTO.getRefreshToken());
        return tokenDTO;
    }

}
