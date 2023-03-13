package com.greenlight.global.infrastructure.config;

import com.greenlight.global.domain.repository.member.MemberRepository;
import com.greenlight.global.infrastructure.persistence.member.MyBatisMemberRepository;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {

    private final SqlSessionTemplate sqlSessionTemplateGreenLight;

    public PersistenceConfig(SqlSessionTemplate sqlSessionTemplateGreenLight) {
        this.sqlSessionTemplateGreenLight = sqlSessionTemplateGreenLight;
    }

    @Bean
    public MemberRepository myBatisMemberRepository() {
        return new MyBatisMemberRepository(sqlSessionTemplateGreenLight);
    }

}
