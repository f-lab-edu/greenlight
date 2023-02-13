package com.greenlight.global.application.facade;

import com.greenlight.global.application.processor.MemberSignUpProcessor;
import org.springframework.stereotype.Service;

@Service
public class MemberManager {

	private final MemberSignUpProcessor memberSignUpProcessor;

	public MemberManager(MemberSignUpProcessor memberSignUpProcessor) {
		this.memberSignUpProcessor = memberSignUpProcessor;
	}

	public MemberSignUpProcessor.Result signUp(MemberSignUpProcessor.Command command) {
		return memberSignUpProcessor.execute(command);
	}
}
