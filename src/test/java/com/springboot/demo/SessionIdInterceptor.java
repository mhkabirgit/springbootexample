package com.springboot.demo;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class SessionIdInterceptor implements ClientHttpRequestInterceptor {

	private String sessionId;
	
	public SessionIdInterceptor(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		HttpHeaders headers = request.getHeaders();
		headers.add("Set-Cookie", "JSESSIONID=" + sessionId);
        return execution.execute(request, body);
	}
}
