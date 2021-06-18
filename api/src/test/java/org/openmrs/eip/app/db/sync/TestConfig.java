package org.openmrs.eip.app.db.sync;

import static org.openmrs.eip.app.db.sync.SyncConstants.OPENMRS_DATASOURCE_NAME;

import java.sql.SQLException;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class TestConfig {
	
	@Bean(OPENMRS_DATASOURCE_NAME)
	public DataSource getDataSource() {
		return DataSourceBuilder.create().url(BaseDbTest.mysqlContainer.getJdbcUrl())
		        .username(BaseDbTest.mysqlContainer.getUsername()).password(BaseDbTest.mysqlContainer.getPassword()).build();
	}
	
	@Bean("activeMqConnFactory")
	public ConnectionFactory getConnectionFactory() {
		return Mockito.mock(ConnectionFactory.class);
	}
	
	@Bean("CamelContext")
	public CamelContext getCamelContext() {
		return Mockito.mock(CamelContext.class);
	}
	
}
