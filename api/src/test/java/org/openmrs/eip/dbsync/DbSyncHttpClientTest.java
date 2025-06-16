package org.openmrs.eip.dbsync;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.openmrs.eip.dbsync.DbSyncHttpClient.PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.openmrs.eip.EIPException;
import org.powermock.reflect.Whitebox;

public class DbSyncHttpClientTest {
	
	private static final String HOST = "127.0.0.1";
	
	protected static final String URL_PREFIX = "http://" + HOST + ":";
	
	protected static ClientAndServer mockServer;
	
	protected static MockServerClient mockServerClient;
	
	private static final String USER = "user";
	
	private static final String PASSWORD = "pass";
	
	private static final String AUTH = "Basic " + getEncoder().encodeToString((USER + ":" + PASSWORD).getBytes());
	
	private DbSyncHttpClient client;
	
	@BeforeAll
	public static void baseMockServerBackedBeforeAll() {
		mockServer = new ClientAndServer();
		mockServerClient = new MockServerClient(HOST, mockServer.getPort());
	}
	
	@BeforeEach
	public void setup() {
		client = new DbSyncHttpClient();
		Whitebox.setInternalState(client, "baseUrl", URL_PREFIX + mockServer.getPort());
		Whitebox.setInternalState(client, "username", USER);
		Whitebox.setInternalState(client, "password", PASSWORD.toCharArray());
	}
	
	@AfterEach
	public void tearDown() {
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
	
}
