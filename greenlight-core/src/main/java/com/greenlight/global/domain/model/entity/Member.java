package com.greenlight.global.domain.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Table(name = "member")
public class Member extends Base {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no")
	private Long no;

	@Column(unique = true, nullable = false, length = 45)
	private String memberId;

	@Column(nullable = false)
	private String password;

	@Column(length = 100)
	private String nickname;

	@Column(length = 11)
	private String phone;

	@Column(nullable = false, length = 100)
	private String roles;

	@Column(nullable = false, length = 1)
	@ColumnDefault("'Y'")
	private String activated;

	@Builder
	public Member(Long no, String memberId, String password, String nickname, String phone, String roles, String activated) {
		this.no = no;
		this.memberId = memberId;
		this.password = password;
		this.nickname = nickname;
		this.phone = phone;
		this.roles = roles;
		this.activated = activated;
	}

	public List<String> getRoleList() {
		if (this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<>();
	}
}
