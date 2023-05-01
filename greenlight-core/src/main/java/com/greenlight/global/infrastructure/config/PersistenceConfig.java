package com.greenlight.global.infrastructure.config;

import com.greenlight.global.infrastructure.persistence.MemberAdapterRepository;
import com.greenlight.global.infrastructure.persistence.jpa.MemberJpaRepository;
import com.greenlight.global.infrastructure.persistence.mybatis.MyBatisMemberRepository;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {

    private final SqlSessionTemplate sqlSessionTemplateGreenLight;
    private final MemberJpaRepository memberJpaRepository;

	public PersistenceConfig(SqlSessionTemplate sqlSessionTemplateGreenLight, MemberJpaRepository memberJpaRepository) {
		this.sqlSessionTemplateGreenLight = sqlSessionTemplateGreenLight;
		this.memberJpaRepository = memberJpaRepository;
	}

	@Bean
	public MyBatisMemberRepository myBatisMemberRepository() {
		return new MyBatisMemberRepository(sqlSessionTemplateGreenLight);
	}

	@Bean
	public MemberAdapterRepository memberAdapterRepository() {
		return new MemberAdapterRepository(memberJpaRepository);
	}

}
