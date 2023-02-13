package com.greenlight.global.presentation.response;

import com.greenlight.global.application.processor.MemberSignUpProcessor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpResponse {

	private Long memberId;

	public static SignUpResponse from(MemberSignUpProcessor.Result result) {
		return new SignUpResponse(result.getMemberId());
	}

	public SignUpResponse(Long memberId) {
		this.memberId = memberId;
	}
}
