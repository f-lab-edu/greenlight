package com.greenlight.global.domain.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Member {

	private Long id;
	private String email;
	private String password;
	private String nickname;
	private String phone;
	private String role;

	public Member(Long id, String email, String password, String nickname, String phone, String role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.phone = phone;
		this.role = role;
	}
}
