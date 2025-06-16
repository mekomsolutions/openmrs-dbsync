package org.openmrs.eip.dbsync;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;

import org.openmrs.eip.EIPException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * Sends http calls to the OpenMRS instance.
 */
@Component
public class OpenMrsHttpClient {
	
	protected static final String PATH = "/ws/rest/v1/";
	
	@Value("${openmrs.baseUrl}")
	private String baseUrl;
	
	@Value("${openmrs.username}")
	private String username;
	
	@Value("${openmrs.password}")
	private char[] password;
	
	private byte[] auth;
	
	private HttpClient client;
	
	public void initClientIfNecessary() {
		if (client == null) {
			client = HttpClient.newHttpClient();
		}
		
		if (auth == null) {
			final String userAndPass = username + ":" + new String(password);
			auth = Base64.getEncoder().encode(userAndPass.getBytes(UTF_8));
		}
	}
	
	public byte[] sendGetRequest(String resource) throws Exception {
		initClientIfNecessary();
		HttpRequest.Builder reqBuilder = HttpRequest.newBuilder();
		reqBuilder.GET().uri(URI.create(baseUrl + PATH + resource)).setHeader(HttpHeaders.AUTHORIZATION,
		    "Basic " + new String(auth, UTF_8));
		
		HttpResponse<byte[]> response = client.send(reqBuilder.build(), BodyHandlers.ofByteArray());
		if (response.statusCode() != HttpStatus.OK.value()) {
			throw new EIPException("Http GET request to OpenMRS failed with status code " + response.statusCode());
		}
		
		return response.body();
	}
	
	public void sendPostRequest(String resource, String body, int expectedStatusCode) throws Exception {
		initClientIfNecessary();
		HttpRequest.Builder reqBuilder = HttpRequest.newBuilder();
		reqBuilder.uri(URI.create(baseUrl + PATH + resource)).setHeader(HttpHeaders.AUTHORIZATION,
		    "Basic " + new String(auth, UTF_8));
		HttpRequest.BodyPublisher bodyPublisher;
		if (body != null) {
			reqBuilder.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			bodyPublisher = BodyPublishers.ofString(body, UTF_8);
		} else {
			bodyPublisher = BodyPublishers.noBody();
		}
		
		reqBuilder.POST(bodyPublisher);
		
		HttpResponse response = client.send(reqBuilder.build(), BodyHandlers.discarding());
		if (response.statusCode() != expectedStatusCode) {
			throw new EIPException("Http POST request to OpenMRS failed with status code " + response.statusCode());
		}
	}
	
}
