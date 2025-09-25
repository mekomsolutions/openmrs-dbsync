package org.openmrs.eip.dbsync;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.openmrs.eip.dbsync.OpenMrsHttpClient.PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.ExchangeBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.openmrs.eip.EIPException;
import org.openmrs.eip.camel.OauthProcessor;
import org.powermock.reflect.Whitebox;

@ExtendWith(MockitoExtension.class)
public class OpenMrsHttpClientTest {
	
	private static final String HOST = "127.0.0.1";
	
	protected static final String URL_PREFIX = "http://" + HOST + ":";
	
	protected static ClientAndServer mockServer;
	
	protected static MockServerClient mockServerClient;
	
	private static final String USER = "user";
	
	private static final String PASSWORD = "pass";
	
	private static final String AUTH = "Basic " + getEncoder().encodeToString((USER + ":" + PASSWORD).getBytes());
	
	private MockedStatic<ExchangeBuilder> mockStaticExchangeBuilder;
	
	@Mock
	private ExchangeBuilder mockExchangeBuilder;
	
	@Mock
	private CamelContext mockCamelContext;
	
	@Mock
	private OauthProcessor mockOauthProcessor;
	
	@Mock
	private Exchange mockExchange;
	
	@Mock
	private Message mockMessage;
	
	private OpenMrsHttpClient client;
	
	@BeforeAll
	public static void baseMockServerBackedBeforeAll() {
		mockServer = new ClientAndServer();
		mockServerClient = new MockServerClient(HOST, mockServer.getPort());
	}
	
	@BeforeEach
	public void setup() throws Exception {
		mockStaticExchangeBuilder = Mockito.mockStatic(ExchangeBuilder.class);
		Mockito.when(ExchangeBuilder.anExchange(mockCamelContext)).thenReturn(mockExchangeBuilder);
		Mockito.when(mockExchangeBuilder.build()).thenReturn(mockExchange);
		Mockito.doAnswer(i -> {
			Exchange exchange = (Exchange) i.getArguments()[0];
			Mockito.when(exchange.getMessage()).thenReturn(mockMessage);
			return null;
		}).when(mockOauthProcessor).process(mockExchange);
		client = new OpenMrsHttpClient(mockOauthProcessor, mockCamelContext);
		Whitebox.setInternalState(client, "baseUrl", URL_PREFIX + mockServer.getPort());
		Whitebox.setInternalState(client, "username", USER);
		Whitebox.setInternalState(client, "password", PASSWORD.toCharArray());
	}
	
	@AfterEach
	public void tearDown() {
		mockStaticExchangeBuilder.close();
		mockServerClient.reset();
	}
	
	@AfterAll
	public static void baseMockServerBackedAfterAll() {
		mockServer.stop();
	}
	
	@Test
	public void sendGetRequest_shouldSendTheRequestToTheServer() throws Exception {
		final String resource = "person";
		final String json = "{}";
		mockServerClient.when(request().withPath(PATH + resource).withMethod("GET").withHeader(AUTHORIZATION, AUTH))
		        .respond(response().withStatusCode(OK.value()).withBody(json.getBytes(UTF_8)));
		
		byte[] data = client.sendGetRequest(resource);
		
		Assertions.assertArrayEquals(json.getBytes(UTF_8), data);
	}
	
	@Test
	public void sendGetRequest_shouldFailIfStatusCodeIsNot200() {
		final String resource = "person";
		mockServerClient.when(request().withPath(PATH + resource).withMethod("GET").withHeader(AUTHORIZATION, AUTH))
		        .respond(response().withStatusCode(NOT_FOUND.value()));
		
		EIPException e = assertThrows(EIPException.class, () -> client.sendGetRequest(resource));
		
		assertEquals("Http GET request to OpenMRS failed with status code " + NOT_FOUND.value(), e.getMessage());
	}
	
	@Test
	public void sendPostRequest_shouldSendTheRequestToTheServerWithPostMethod() throws Exception {
		final String resource = "person";
		final String json = "{}";
		mockServerClient
		        .when(request().withPath(PATH + resource).withMethod("POST").withHeader(AUTHORIZATION, AUTH)
		                .withContentType(MediaType.APPLICATION_JSON).withBody(json))
		        .respond(response().withStatusCode(NO_CONTENT.value()));
		
		client.sendPostRequest(resource, json, 204);
	}
	
	@Test
	public void sendPostRequest_shouldSendTheRequestToTheServerWithPostMethodAndBody() throws Exception {
		final String resource = "person";
		mockServerClient.when(request().withPath(PATH + resource).withMethod("POST").withHeader(AUTHORIZATION, AUTH))
		        .respond(response().withStatusCode(NO_CONTENT.value()));
		
		client.sendPostRequest(resource, null, 204);
	}
	
	@Test
	public void sendPostRequest_shouldFailIfStatusCodeIsDifferent() {
		final String resource = "person";
		mockServerClient.when(request().withPath(PATH + resource).withMethod("POST").withHeader(AUTHORIZATION, AUTH))
		        .respond(response().withStatusCode(NO_CONTENT.value()));
		
		EIPException e = assertThrows(EIPException.class, () -> client.sendPostRequest(resource, null, 200));
		
		assertEquals("Http POST request to OpenMRS failed with status code " + NO_CONTENT.value(), e.getMessage());
	}
	
	@Test
	public void shouldUseOauthToAuthenticateIfEnabled() throws Exception {
		final String token = "test-token";
		final String resource = "person";
		final String json = "{}";
		mockServerClient.when(request().withPath(PATH + resource).withMethod("GET").withHeader(AUTHORIZATION, token))
		        .respond(response().withStatusCode(OK.value()).withBody(json.getBytes(UTF_8)));
		Mockito.when(mockMessage.getBody(String.class)).thenReturn(token);
		
		byte[] data = client.sendGetRequest(resource);
		
		Assertions.assertArrayEquals(json.getBytes(UTF_8), data);
	}
	
}
