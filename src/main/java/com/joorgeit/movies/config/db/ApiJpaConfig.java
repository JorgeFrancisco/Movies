package com.joorgeit.movies.config.db;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "dbapiEntityManagerFactory", transactionManagerRef = "dbapiTransactionManager", basePackages = {
		"com.joorgeit.movies.repository.dbapi" })
public class ApiJpaConfig {

	@Primary
	@Bean(name = "dbapiDataSource")
	@ConfigurationProperties(prefix = "dbapi.spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().driverClassName(null).build();
	}

	@Primary
	@Bean(name = "dbapiEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("dbapiDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.joorgeit.movies.model.dbapi")
				.persistenceUnit("databasePersistenceUnit").build();
	}

	@Primary
	@Bean(name = "dbapiTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("dbapiEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
