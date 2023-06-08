package org.openmrs.eip.dbsync.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.openmrs.eip.Constants;
import org.openmrs.eip.dbsync.SyncConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "openmrsEntityManager", transactionManagerRef = "openmrsTransactionManager", basePackages = {
        "org.openmrs.eip.dbsync.repository" })
public class OpenmrsEntityManagerConfig {
	
	private static final Logger log = LoggerFactory.getLogger(OpenmrsEntityManagerConfig.class);
	
	@Primary
	@Bean(name = "openmrsEntityManager")
	@DependsOn(Constants.COMMON_PROP_SOURCE_BEAN_NAME)
	public LocalContainerEntityManagerFactoryBean entityManager(final EntityManagerFactoryBuilder builder,
	                                                            @Qualifier(SyncConstants.OPENMRS_DATASOURCE_NAME) final DataSource dataSource) {
		Map<String, String> properties=new HashMap<>();
		properties.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
		return builder.dataSource(dataSource).packages("org.openmrs.eip.dbsync.entity").persistenceUnit("openmrs").properties(properties).build();
	}
	
	@Primary
	@Bean(name = "openmrsTransactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("openmrsEntityManager") final EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
}
