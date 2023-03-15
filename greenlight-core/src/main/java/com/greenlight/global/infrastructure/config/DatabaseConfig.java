package com.greenlight.global.infrastructure.config;

import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

public class DatabaseConfig {

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
@EnableJpaRepositories(
	basePackages = {"com.greenlight.global.domain.repository", "com.greenlight.global.infrastructure.persistence"},  // repository 관리할 패키지경로
	entityManagerFactoryRef = "jpaEntityManagerFactory",
	transactionManagerRef = "jpaTransactionManager")
class DatabaseMySQLConfig extends DatabaseConfig {

	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	@Bean(name = "dataSourceGreenLight")
	public DataSource dataSourceGreenLight(@Qualifier("dataSourceProperties") DataSourceProperties properties) throws IllegalArgumentException {
		return new LazyConnectionDataSourceProxy(properties.initializeDataSourceBuilder().type(HikariDataSource.class).build());
	}

	/**
	 * JAP setting
	 */
	@ConfigurationProperties(prefix = "spring.jpa")
	@Bean(name = "jpaProperties")
	public JpaProperties jpaProperties() {
		return new JpaProperties();
	}

	@Bean(name = "jpaEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(@Qualifier("dataSourceGreenLight") DataSource dataSource,
																		  @Qualifier("jpaProperties") JpaProperties jpaProperties) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		// Entity 패키지 경로
		em.setPackagesToScan("com.greenlight.global.domain.model.entity");
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		jpaProperties.setOpenInView(false);
		em.setJpaPropertyMap(jpaProperties.getProperties());
//		em.setJpaProperties(additionalProperties());
		em.setPersistenceUnitName("jpaEntityManagerFactory");
		return em;
	}

	@Bean(name = "jpaTransactionManager")
	public PlatformTransactionManager jpaTransactionManager(
		@Qualifier("jpaEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean(name = "jpaExceptionTranslation")
	public PersistenceExceptionTranslationPostProcessor jpaExceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	/**
	 * 수동 셋팅 파라미터 찾음 yml에 다 넣음
	 */
	@Deprecated
	public Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "validate");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		properties.setProperty("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
		properties.setProperty("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
		properties.setProperty("hibernate.format_sql", "true");
		properties.setProperty("hibernate.use_sql_comments", "true");
		return properties;
	}

	/**
	 * Mybatis SqlSession Settings
	 */
	@Bean(name = "sqlSessionFactoryGreenLight")
	public SqlSessionFactory sqlSessionFactoryGreenLight(@Qualifier("dataSourceGreenLight") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		configureSqlSessionFactory(sqlSessionFactory, dataSource);
		return sqlSessionFactory.getObject();
	}

	@Bean(name = "sqlSessionTemplateGreenLight")
	public SqlSessionTemplate sqlSessionTemplateGreenLight(@Qualifier("sqlSessionFactoryGreenLight") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "transactionManagerGreenLight")
	public DataSourceTransactionManager transactionManagerGreenLight(@Qualifier("dataSourceGreenLight") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	/* datasource diff
	@Bean
	public PlatformTransactionManager transactionManager(
		@Qualifier("jpaTransactionManager") PlatformTransactionManager jpaTransactionManager
		, @Qualifier("transactionManagerGreenLight") PlatformTransactionManager transactionManagerGreenLight) {

		//new ChainedTransactionManager(jpaTransactionManager, transactionManager11st); deprecated
		return new AbstractPlatformTransactionManager() {

			@Override
			protected Object doGetTransaction() throws TransactionException {
				return TransactionSynchronizationManager.getResourceMap();
			}

			@Override
			protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {
				TransactionSynchronizationManager.initSynchronization();
				for (PlatformTransactionManager tm : Arrays.asList(jpaTransactionManager, transactionManagerGreenLight)) {
					tm.getTransaction(definition);
				}
			}

			@Override
			protected void doCommit(DefaultTransactionStatus status) throws TransactionException {
				for (PlatformTransactionManager tm : Arrays.asList(jpaTransactionManager, transactionManagerGreenLight)) {
					tm.commit((TransactionStatus) status.getTransaction());
				}
			}

			@Override
			protected void doRollback(DefaultTransactionStatus status) throws TransactionException {
				for (PlatformTransactionManager tm : Arrays.asList(jpaTransactionManager, transactionManagerGreenLight)) {
					tm.rollback((TransactionStatus) status.getTransaction());
				}
			}

		};
	}
	*/
}

@Configuration
@EnableJpaAuditing
class DataBaseQueryDSLConfig {

	@PersistenceContext(unitName = "jpaEntityManagerFactory")
	private EntityManager em;

	@Bean(name = "jpaQueryFactory") /* 해당 부분은 이름 미지정 가능 */
	public JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(em);
	}

}
