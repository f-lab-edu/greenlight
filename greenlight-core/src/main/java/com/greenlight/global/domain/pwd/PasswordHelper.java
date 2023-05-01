package com.greenlight.global.domain.pwd;

public interface PasswordHelper {
	String encode(String strToEnc);
	String decode(String strToDec);
}
