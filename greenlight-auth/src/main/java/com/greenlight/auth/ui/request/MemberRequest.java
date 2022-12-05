package com.greenlight.auth.ui.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequest {
    private String memberId;
    private String name;
    private String email;
    private String password;
}
