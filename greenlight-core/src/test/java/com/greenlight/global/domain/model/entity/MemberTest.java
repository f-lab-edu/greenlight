package com.greenlight.global.domain.model.entity;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.greenlight.global.infrastructure.persistence.jpa.MemberJpaRepository;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class MemberTest {

	@Autowired
	private MemberJpaRepository memberJpaRepository;

	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	//	@Commit
	@Test
	@DisplayName("저장 잘 되는지 확인")
	@Order(1)
	void testInsertAndSelect() throws Exception {

		Member member1 = Member.builder()
				.password(passwordEncoder.encode("a12345678!"))
				.memberId("tester1")
				.nickname("nickick1")
				.phone("01029991234")
				.roles("ROLE_MANAGER")
				.build();

		Member member2 = Member.builder()
				.password(passwordEncoder.encode("a12345678!"))
				.memberId("tester2")
				.nickname("nickick2")
				.phone("01029991234")
				.roles("ROLE_ADMIN")
				.build();

		memberJpaRepository.save(member1);
		memberJpaRepository.save(member2);

		List<Member> members = memberJpaRepository.findAll();

		for (Member member : members) {
			System.out.println("member = " + member);
		}

	}

	@Test
	@DisplayName("필수값 제외하고 저장 테스트")
	@Order(2)
	void testInsertReqValAndSelect() throws Exception {

		Member member1 = Member.builder()
				.password(passwordEncoder.encode("a12345678!"))
				.memberId("tester1")
				.roles("ROLE_MANAGER")
				.build();

		memberJpaRepository.save(member1);

		List<Member> members = memberJpaRepository.findAll();

		for (Member member : members) {
			System.out.println("member = " + member);
		}
	}

	@Test
	@DisplayName("쿼리 순서")
	@Order(3)
	void testQuerySeq() throws Exception {

		Member member1 = Member.builder()
				.password(passwordEncoder.encode("a12345678!"))
				.memberId("tester1")
				.roles("ROLE_MANAGER")
				.build();

		Member member2 = Member.builder()
				.password(passwordEncoder.encode("a12345678!"))
				.memberId("tester2")
				.roles("ROLE_MANAGER")
				.build();

		Member member3 = Member.builder()
				.password(passwordEncoder.encode("a12345678!"))
				.memberId("tester3")
				.roles("ROLE_MANAGER")
				.build();

		Member member4 = Member.builder()
				.password(passwordEncoder.encode("a12345678!"))
				.memberId("tester4")
				.roles("ROLE_MANAGER")
				.build();

		memberJpaRepository.save(member1);
		memberJpaRepository.save(member2);

		memberJpaRepository.delete(member1);

		List<Member> members = memberJpaRepository.findAll();

		for (Member member : members) {
			System.out.println("member = " + member);
		}

		memberJpaRepository.save(member3);
		memberJpaRepository.save(member4);

	}

}
