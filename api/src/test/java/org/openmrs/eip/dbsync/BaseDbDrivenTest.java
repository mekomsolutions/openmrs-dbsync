package org.openmrs.eip.dbsync;

import java.util.stream.Stream;

import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.boot.test.mock.mockito.ResetMocksTestExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@Transactional
@TestExecutionListeners({ DirtiesContextBeforeModesTestExecutionListener.class, MockitoTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        ResetMocksTestExecutionListener.class, DeleteDataTestExecutionListener.class, SqlScriptsTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@TestPropertySource(properties = "spring.jpa.properties.hibernate.hbm2ddl.auto=update")
@TestPropertySource(properties = "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect")
@TestPropertySource(properties = "camel.springboot.routes-collector-enabled=false")
@TestPropertySource(properties = "spring.liquibase.enabled=false")
public abstract class BaseDbDrivenTest {
	
	private static final Logger log = LoggerFactory.getLogger(BaseDbDrivenTest.class);
	
	protected static MySQLContainer mysqlContainer = new MySQLContainer("mysql:8.0.42");
	
	protected static Integer MYSQL_PORT;
	
	@Autowired
	protected ApplicationContext applicationContext;
	
	@Autowired
	protected CamelContext camelContext;
	
	@Autowired
	@Qualifier("openmrsDataSource")
	protected DataSource openmrsDataSource;
	
	@BeforeAll
	public static void startContainers() {
		mysqlContainer.withEnv("MYSQL_ROOT_PASSWORD", "test");
		mysqlContainer.withDatabaseName("openmrs");
		mysqlContainer.withUrlParam("useSSL", "false");
		Startables.deepStart(Stream.of(mysqlContainer)).join();
		MYSQL_PORT = mysqlContainer.getMappedPort(3306);
	}
	
}
