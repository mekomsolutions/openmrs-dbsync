package org.openmrs.eip.app.db.sync.sender;

import lombok.Builder;
import lombok.Data;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openmrs.eip.app.db.sync.DeleteDataTestExecutionListener;
import org.openmrs.eip.app.db.sync.sender.config.TestConfig;
import org.openmrs.eip.app.db.sync.camel.OpenmrsComponent;
import org.openmrs.eip.app.db.sync.utils.DateUtils;
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
import java.time.LocalDateTime;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = TestConfig.class)
@TestExecutionListeners({ DirtiesContextBeforeModesTestExecutionListener.class, MockitoTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        ResetMocksTestExecutionListener.class, DeleteDataTestExecutionListener.class, SqlScriptsTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@Sql("classpath:test_data_it.sql")
public abstract class OpenmrsExtractEndpointITest {

    @Autowired
    protected CamelContext camelContext;

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

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
        resultEndpoint.getExchanges().clear();
    }

    protected RoutesBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from(getUri())
                        .recipientList(simple("openmrs:extract?tableToSync=${body.getTableToSync()}"))
                        .split().jsonpathWriteAsString("$").streaming()
                        .to("log:json")
                        .to("mock:result");
            }
        };
    }

    @Data
    @Builder
    public static class CamelInitObect {
        private LocalDateTime lastSyncDate;
        private String tableToSync;

        public String getLastSyncDateAsString() {
            return DateUtils.dateToString(getLastSyncDate());
        }
    }
}
