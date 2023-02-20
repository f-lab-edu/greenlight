package com.greenlight.global.presentation.response;

import lombok.Getter;
import lombok.ToString;

import com.greenlight.global.application.processor.MemberSignUpProcessor;

@Getter
@ToString
public class SignUpResponse {

	private int memberId;

	public static SignUpResponse from(MemberSignUpProcessor.Result result) {
		return new SignUpResponse(result.getMemberId());
	}

	public SignUpResponse(int memberId) {
		this.memberId = memberId;
	}
}
