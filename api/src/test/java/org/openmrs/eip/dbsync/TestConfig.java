package org.openmrs.eip.dbsync;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

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
	
	@Bean("CamelContext")
	public CamelContext getCamelContext() {
		return Mockito.mock(CamelContext.class);
	}
	
	@Bean("producerTemplate")
	public ProducerTemplate getProducerTemplate() {
		return Mockito.mock(ProducerTemplate.class);
	}
	
}
