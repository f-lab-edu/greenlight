package com.greenlight.global.infrastructure.config;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

public class DatabaseConfig {

	public static final String MYBATIS_BASE_PACKAGE = "com.greenlight.global.domain.repository";
	public static final String MYBATIS_TYPE_ALIASES_PACKAGE = "com.greenlight.global.domain.model, com.greenlight.global.application.processor";
	public static final String MYBATISCONFIG_PATH = "classpath:mapper/mybatis-config.xml";
	public static final String MYBATIS_MAPPER_LOCATIONS_PATH = "classpath:mapper/**/*Mapper.xml";

	protected void configureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws Exception {
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setVfs(SpringBootVFS.class);
		sessionFactoryBean.setTypeAliasesPackage(MYBATIS_TYPE_ALIASES_PACKAGE);
		sessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(MYBATISCONFIG_PATH));
		sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MYBATIS_MAPPER_LOCATIONS_PATH));
	}

}

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = DatabaseConfig.MYBATIS_BASE_PACKAGE
//        , sqlSessionFactoryRef = "greenLightSqlSessionFactory"
	, sqlSessionTemplateRef = "greenLightSqlSession"
	, annotationClass = MyBatisMapper.class)
class DatabaseMyBatisConfig extends DatabaseConfig {

	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean(name = "greenLightDataSourceProperties")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	@Bean(name = "greenLightDataSource")
	public HikariDataSource dataSource(@Qualifier("greenLightDataSourceProperties") DataSourceProperties properties) throws IllegalArgumentException {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	/**
	 * Mybatis SqlSession Settings
	 */
	@Bean(name = "greenLightSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("greenLightDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		configureSqlSessionFactory(sqlSessionFactory, dataSource);
		return sqlSessionFactory.getObject();
	}

	@Bean(name = "greenLightSqlSession")
	public SqlSessionTemplate sqlSession(@Qualifier("greenLightSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "greenLightTransactionManager")
	public DataSourceTransactionManager transactionManager(@Qualifier("greenLightDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
