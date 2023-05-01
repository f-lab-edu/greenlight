package com.greenlight.global.infrastructure.pwd;

import com.greenlight.global.domain.pwd.PasswordHelper;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHelperImpl implements PasswordHelper {

	private final PasswordEncoder passwordEncoder;

	public PasswordHelperImpl(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String encode(String strToEnc) {
		return passwordEncoder.encode(strToEnc);
	}

	@Override
	public String decode(String strToDec) {
		return passwordEncoder.encode(strToDec);
	}
}
