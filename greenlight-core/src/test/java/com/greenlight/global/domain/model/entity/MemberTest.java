package com.greenlight.global.domain.model.entity;

import java.util.List;
import com.greenlight.global.infrastructure.persistence.jpa.MemberJpaRepository;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

}
