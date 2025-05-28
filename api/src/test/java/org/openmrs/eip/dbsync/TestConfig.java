package org.openmrs.eip.dbsync;

import jakarta.jms.ConnectionFactory;
import javax.sql.DataSource;

import org.apache.camel.ProducerTemplate;
import org.mockito.Mockito;
import org.openmrs.eip.Constants;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@ComponentScan
public class TestConfig {
	
	@Bean(SyncConstants.OPENMRS_DATASOURCE_NAME)
	public DataSource getDataSource() {
		return DataSourceBuilder.create().url(BaseDbDrivenTest.mysqlContainer.getJdbcUrl())
		        .username(BaseDbDrivenTest.mysqlContainer.getUsername())
		        .password(BaseDbDrivenTest.mysqlContainer.getPassword()).build();
	}
	
	@Bean("activeMqConnFactory")
	public ConnectionFactory getConnectionFactory() {
		return Mockito.mock(ConnectionFactory.class);
	}
	
	@Bean("producerTemplate")
	public ProducerTemplate getProducerTemplate() {
		return Mockito.mock(ProducerTemplate.class);
	}
	
	@Bean(name = Constants.COMMON_PROP_SOURCE_BEAN_NAME)
	public PropertySource getCommonPropertySource() {
		return Mockito.mock(PropertySource.class);
	}
	
}
