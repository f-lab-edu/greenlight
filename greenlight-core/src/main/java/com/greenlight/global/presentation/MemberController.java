package com.greenlight.global.presentation;

import com.greenlight.global.application.facade.MemberManager;
import com.greenlight.global.application.processor.MemberSignUpProcessor;
import com.greenlight.global.presentation.request.SignUpRequest;
import com.greenlight.global.presentation.response.ApiResponse;
import com.greenlight.global.presentation.response.SignUpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/member", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

	private final MemberManager memberManager;

	public MemberController(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/signUp")
	public ApiResponse<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
		System.out.println("signUpRequest :" + signUpRequest.toString());
		MemberSignUpProcessor.Result result = memberManager.signUp(signUpRequest.toCommand());
		return ApiResponse.success(SignUpResponse.from(result));
	}
}
