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
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "camel.springboot.xml-routes=classpath*:camel/" + ReceiverClearDbCacheRouteTest.ROUTE_ID
        + ".xml")
@TestPropertySource(properties = "logging.level." + ReceiverClearDbCacheRouteTest.ROUTE_ID + "=DEBUG")
public class ReceiverClearDbCacheRouteTest extends BaseReceiverRouteTest {
	
	protected static final String ROUTE_ID = "receiver-clear-db-cache";
	
	protected static final String URI = "direct:" + ROUTE_ID;
	
	protected static final String OPENMRS_USER = "user-1";
	
	protected static final String OPENMRS_PASS = "";
	
	public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
	
	public static final String CLEAR_CACHE_ENDPOINT_ID = "clear-db-cache";
	
	public static final String MOCK_PROCESSOR_ENDPOINT = "mock://processor";
	
	public static final String MOCK_HTTP_ENDPOINT = "mock://http";
	
	@EndpointInject(MOCK_HTTP_ENDPOINT)
	private MockEndpoint mockHttpEndpoint;
	
	@EndpointInject(MOCK_PROCESSOR_ENDPOINT)
	private MockEndpoint mockProcessor;
	
	@BeforeEach
	public void setup() throws Exception {
		mockProcessor.reset();
		mockHttpEndpoint.reset();
		
		advise(ROUTE_ID, new AdviceWithRouteBuilder() {
			
			@Override
			public void configure() {
				weaveByType(ProcessDefinition.class).replace().to(mockProcessor);
				weaveById(CLEAR_CACHE_ENDPOINT_ID).replace().to(mockHttpEndpoint);
			}
		});
		
	}
	
	@AfterEach
	public void tearDown() throws Exception {
		try {
			mockProcessor.assertIsSatisfied();
			mockHttpEndpoint.assertIsSatisfied();
		}
		finally {
			//Drop the advice
			advise(ROUTE_ID, new AdviceWithRouteBuilder() {
				
				@Override
				public void configure() {
					weaveByToUri(MOCK_PROCESSOR_ENDPOINT).replace().process("oauthProcessor");
					weaveByToUri(MOCK_HTTP_ENDPOINT).replace().to("{{openmrs.baseUrl}}/ws/rest/v1/cleardbcache")
					        .id(CLEAR_CACHE_ENDPOINT_ID);
				}
			});
		}
	}
	
	@Test
	public void shouldClearDbCache() {
		final String auth = "Basic " + Base64.getEncoder().encodeToString((OPENMRS_USER + ":" + OPENMRS_PASS).getBytes());
		mockProcessor.expectedMessageCount(1);
		mockProcessor.whenAnyExchangeReceived(e -> {
			e.getIn().setBody(null);
		});
		
		mockHttpEndpoint.expectedMessageCount(1);
		mockHttpEndpoint.expectedHeaderReceived(Exchange.HTTP_METHOD, "POST");
		mockHttpEndpoint.expectedHeaderReceived(HTTP_HEADER_AUTH, auth);
		mockHttpEndpoint.expectedHeaderReceived(HTTP_HEADER_CONTENT_TYPE, "application/json");
		final String clearCacheBody = "{test}";
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(clearCacheBody);
		mockHttpEndpoint.expectedBodiesReceived(clearCacheBody);
		
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
		final String clearCacheBody = "{test}";
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(clearCacheBody);
		mockHttpEndpoint.expectedBodiesReceived(clearCacheBody);
		
		producerTemplate.send(URI, exchange);
	}
	
}
