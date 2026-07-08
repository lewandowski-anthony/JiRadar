package com.jiradar.jiradarback.infrastructure.jira.config;

import com.jiradar.jiradarback.infrastructure.jira.JiraServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Base64;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JiraProperties.class)
public class JiraClientConfig {

	private final JiraProperties jiraProperties;

	@Bean
	public JiraServiceClient jiraServiceClient(HttpServiceProxyFactory jiraServiceProxyFactory) {
		return jiraServiceProxyFactory.createClient(JiraServiceClient.class);
	}

	@Bean
	public HttpServiceProxyFactory jiraServiceProxyFactory() {
		String credentials = jiraProperties.getUser() + ":" + jiraProperties.getToken();
		String base64Auth = Base64.getEncoder().encodeToString(credentials.getBytes());

		WebClient webClient = WebClient.builder()
				.baseUrl(jiraProperties.getUrl())
				.defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + base64Auth)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, "en")
				.defaultHeader("X-Force-Accept-Language", "en")
				.build();

		return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient))
				.build();
	}
}