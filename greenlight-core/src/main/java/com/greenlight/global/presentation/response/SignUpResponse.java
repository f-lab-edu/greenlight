package com.greenlight.global.presentation.response;

import com.greenlight.global.application.processor.MemberSignUpProcessor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpResponse {

	private String memberId;

	public static SignUpResponse from(MemberSignUpProcessor.Result result) {
		return new SignUpResponse(result.getMemberId());
	}

	public SignUpResponse(String memberId) {
		this.memberId = memberId;
	}
}
