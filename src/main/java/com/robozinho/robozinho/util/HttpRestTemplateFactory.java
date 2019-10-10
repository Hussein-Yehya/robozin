package com.robozinho.robozinho.util;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpRestTemplateFactory {

	private int timeout = 10;
	
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(this.timeout);
		return clientHttpRequestFactory;
	}
	
	public RestTemplate getRestTemplate() {
		return new RestTemplate(this.getClientHttpRequestFactory());
	}

	
}