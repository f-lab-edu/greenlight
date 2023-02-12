package com.domain.repository;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.domain.entity.Member;
import com.greenlight.auth.application.dto.TokenDTO;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<String, Object> storegeMember = new HashMap<>();
    private static Map<String, Object> storegeToken = new HashMap<>();

    public Member saveMember(Member member) {
        storegeMember.put(member.getMemberId(), member);
        return (Member) storegeMember.get(member.getMemberId());
    }

    public Optional<Member> findByMemberId(String memberId) {
        return Optional.ofNullable((Member) storegeMember.get(memberId));
    }

    public TokenDTO saveRefreshToken(String memberId, TokenDTO tokenDTO) {
        storegeToken.put(memberId, tokenDTO.getRefreshToken());
        storegeToken.put(tokenDTO.getRefreshToken(), storegeMember.get(memberId));
        return tokenDTO;
    }

    public Optional<Member> findByRefreshToken(String refreshToken) {
        return Optional.ofNullable((Member) storegeToken.get(refreshToken));
    }

}
