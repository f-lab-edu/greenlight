package com.greenlight.global.presentation.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenlight.global.application.processor.MemberSignInProcessor;

@Getter
@Setter
@ToString
public class SignInRequest {

    @NotNull
    @Size(min = 3, max = 50)
    private String memberId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    public MemberSignInProcessor.Command toCommand() {
        return new MemberSignInProcessor.Command(memberId, password);
    }

}
