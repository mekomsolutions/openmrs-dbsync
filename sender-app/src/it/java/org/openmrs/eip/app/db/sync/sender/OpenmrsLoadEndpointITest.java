package org.openmrs.eip.app.db.sync.sender;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openmrs.eip.app.db.sync.DeleteDataTestExecutionListener;
import org.openmrs.eip.app.db.sync.sender.config.TestConfig;
import org.openmrs.eip.app.db.sync.camel.OpenmrsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.boot.test.mock.mockito.ResetMocksTestExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.security.Security;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = TestConfig.class)
@TestExecutionListeners({ DirtiesContextBeforeModesTestExecutionListener.class, MockitoTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        ResetMocksTestExecutionListener.class, DeleteDataTestExecutionListener.class, SqlScriptsTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@Sql("classpath:test_data_it.sql")
public abstract class OpenmrsLoadEndpointITest {

    @Autowired
    protected CamelContext camelContext;

    @Produce(property = "uri")
    protected ProducerTemplate template;

    @Autowired
    private ApplicationContext applicationContext;

    public String getUri() {
        return "direct:start" + getClass().getSimpleName();
    }

    @Before
    public void init() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        camelContext.addComponent("openmrs", new OpenmrsComponent(camelContext, applicationContext));
        camelContext.addRoutes(createRouteBuilder());
    }

    @After
    public void teardown() {
        camelContext.removeComponent("openmrs");
    }

    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from(getUri()).to("openmrs:load");
            }
        };
    }
}
