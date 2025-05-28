package org.openmrs.eip.dbsync.receiver.route;

import static org.openmrs.eip.Constants.HTTP_HEADER_AUTH;

import java.util.Base64;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ProcessDefinition;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "camel.springboot.xml-routes=classpath*:camel/"
        + ReceiverUpdateSearchIndexRouteTest.ROUTE_ID + ".xml")
@TestPropertySource(properties = "logging.level." + ReceiverUpdateSearchIndexRouteTest.ROUTE_ID + "=DEBUG")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReceiverUpdateSearchIndexRouteTest extends BaseReceiverRouteTest {
	
	protected static final String ROUTE_ID = "receiver-update-search-index";
	
	protected static final String URI = "direct:" + ROUTE_ID;
	
	protected static final String OPENMRS_USER = "user-1";
	
	protected static final String OPENMRS_PASS = "";
	
	public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
	
	@EndpointInject("mock:http")
	private MockEndpoint mockHttpEndpoint;
	
	@EndpointInject("mock:processor")
	private MockEndpoint mockProcessor;
	
	@BeforeEach
	public void setup() throws Exception {
		mockProcessor.reset();
		mockHttpEndpoint.reset();
		
		advise(ROUTE_ID, new AdviceWithRouteBuilder() {
			
			@Override
			public void configure() {
				weaveByType(ProcessDefinition.class).replace().to(mockProcessor);
				weaveByToUri("{{openmrs.baseUrl}}/ws/rest/v1/searchindexupdate").replace().to(mockHttpEndpoint);
			}
		});
		
	}
	
	@AfterEach
	public void tearDown() throws Exception {
		mockProcessor.assertIsSatisfied();
		mockHttpEndpoint.assertIsSatisfied();
	}
	
	@Test
	public void shouldRebuildTheSearchIndex() {
		final String auth = "Basic " + Base64.getEncoder().encodeToString((OPENMRS_USER + ":" + OPENMRS_PASS).getBytes());
		mockProcessor.expectedMessageCount(1);
		mockProcessor.whenAnyExchangeReceived(e -> {
			e.getIn().setBody(null);
		});
		
		mockHttpEndpoint.expectedMessageCount(1);
		mockHttpEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "POST");
		mockHttpEndpoint.expectedHeaderReceived(HTTP_HEADER_AUTH, auth);
		mockHttpEndpoint.expectedHeaderReceived(HTTP_HEADER_CONTENT_TYPE, "application/json");
		final String updateSearchBody = "{test}";
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(updateSearchBody);
		mockHttpEndpoint.expectedBodiesReceived(updateSearchBody);
		
		producerTemplate.send(URI, exchange);
	}
	
	@Test
	public void shouldUseTheOauthHeaderToAuthenticateIfItExists() {
		mockHttpEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "POST");
		mockProcessor.expectedMessageCount(1);
		final String oauthHeader = "Bearer oauth-token";
		mockProcessor.whenAnyExchangeReceived(e -> {
			e.getIn().setBody(oauthHeader);
		});
		
		mockHttpEndpoint.expectedMessageCount(1);
		mockHttpEndpoint.expectedHeaderReceived(HTTP_HEADER_AUTH, oauthHeader);
		mockHttpEndpoint.expectedHeaderReceived(HTTP_HEADER_CONTENT_TYPE, "application/json");
		final String updateSearchBody = "{test}";
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(updateSearchBody);
		mockHttpEndpoint.expectedBodiesReceived(updateSearchBody);
		
		producerTemplate.send(URI, exchange);
	}
	
}
