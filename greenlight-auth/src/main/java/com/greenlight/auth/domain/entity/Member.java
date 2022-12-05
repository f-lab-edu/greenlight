package com.greenlight.auth.domain.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.greenlight.auth.domain.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Member {
    private String memberId;
    private String name;
    private String email;
    private String password;
    private List<MemberRole> memberRoles = new ArrayList<>();

    @Builder
    public Member(String memberId, String name, String email, String password, List<MemberRole> memberRoles) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.memberRoles = Collections.singletonList(MemberRole.ROLE_MEMBER);
    }

    public void addMemberRole(MemberRole memberRole) {
        this.memberRoles.add(memberRole);
    }
}
