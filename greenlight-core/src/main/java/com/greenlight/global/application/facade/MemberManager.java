package com.greenlight.global.application.facade;

import org.springframework.stereotype.Service;

import com.greenlight.global.application.processor.MemberSignInProcessor;
import com.greenlight.global.application.processor.MemberSignUpProcessor;

@Service
public class MemberManager {

	private final MemberSignUpProcessor memberSignUpProcessor;
	private final MemberSignInProcessor memberSignInProcessor;

	public MemberManager(MemberSignUpProcessor memberSignUpProcessor, MemberSignInProcessor memberSignInProcessor) {
		this.memberSignUpProcessor = memberSignUpProcessor;
		this.memberSignInProcessor = memberSignInProcessor;
	}

	public MemberSignUpProcessor.Result signUp(MemberSignUpProcessor.Command command) {
		return memberSignUpProcessor.execute(command);
	}

	public MemberSignInProcessor.Result signIn(MemberSignInProcessor.Command command) {
		return memberSignInProcessor.execute(command);
	}

}
