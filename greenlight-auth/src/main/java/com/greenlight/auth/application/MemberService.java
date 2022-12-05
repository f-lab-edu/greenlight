package com.greenlight.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenlight.auth.domain.entity.Member;
import com.greenlight.auth.domain.repository.MemberRepository;
import com.greenlight.auth.exception.DuplicateUserException;
import com.greenlight.auth.ui.request.MemberRequest;
import com.greenlight.auth.ui.response.MemberResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponse signUp(MemberRequest memberRequestDto) {
        validateDuplicated(memberRequestDto.getMemberId());
        Member member = memberRepository.saveMember(
                Member.builder()
                        .memberId(memberRequestDto.getMemberId())
                        .name(memberRequestDto.getName())
                        .email(memberRequestDto.getEmail())
                        .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                        .build()
        );

        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .build()
                ;
    }

    public void validateDuplicated(String memberId) {
        if (memberRepository.findByMemberId(memberId).isPresent())
            throw new DuplicateUserException();
    }
}
