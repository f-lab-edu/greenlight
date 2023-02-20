package com.greenlight.global.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@ToString
public class Member {

	private Long no;
	private String memberId;
	private String password;
	private String nickname;
	private String phone;
	private String roles;
	private Integer activated;
	private String ins_mbr_id;
	private String upd_mbr_id;
	private String create_tm;
	private String update_tm;

	@Builder
	public Member(Long no, String memberId, String password, String nickname, String phone, String roles, Integer activated, String ins_mbr_id, String upd_mbr_id, String create_tm, String update_tm) {
		this.no = no;
		this.memberId = memberId;
		this.password = password;
		this.nickname = nickname;
		this.phone = phone;
		this.roles = roles;
		this.activated = activated;
		this.ins_mbr_id = ins_mbr_id;
		this.upd_mbr_id = upd_mbr_id;
		this.create_tm = create_tm;
		this.update_tm = update_tm;
	}

	public List<String> getRoleList() {
		if (this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<>();
	}
}
