package org.openmrs.eip.dbsync.config;

import javax.sql.DataSource;

import org.openmrs.eip.Constants;
import org.openmrs.eip.dbsync.AppUtils;
import org.openmrs.eip.dbsync.OpenMrsHttpClient;
import org.openmrs.eip.dbsync.SyncConstants;
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

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "openmrsEntityManager", transactionManagerRef = "openmrsTransactionManager", basePackages = {
        "org.openmrs.eip.dbsync.repository" })
public class OpenmrsEntityManagerConfig {
	
	@Primary
	@Bean(name = "openmrsEntityManager")
	@DependsOn(Constants.COMMON_PROP_SOURCE_BEAN_NAME)
	public LocalContainerEntityManagerFactoryBean entityManager(final EntityManagerFactoryBuilder builder,
	                                                            @Qualifier(SyncConstants.OPENMRS_DATASOURCE_NAME) final DataSource dataSource,
	                                                            OpenMrsHttpClient httpClient)
	    throws Exception {
		AppUtils.adjustJpaMappings(httpClient);
		return builder.dataSource(dataSource).packages("org.openmrs.eip.dbsync.entity").persistenceUnit("openmrs").build();
	}
	
	@Primary
	@Bean(name = "openmrsTransactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("openmrsEntityManager") final EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
}
