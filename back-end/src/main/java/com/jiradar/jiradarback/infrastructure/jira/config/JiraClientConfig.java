package com.jiradar.jiradarback.infrastructure.jira.config;

import com.jiradar.jiradarback.infrastructure.jira.JiraServiceClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Base64;

@Getter
@Setter
@Configuration
@ConfigurationProperties("jira.config")
public class JiraClientConfig {

	private String user;
	private String url;
	private String token;

	@Bean
	public JiraServiceClient jiraServiceClient(HttpServiceProxyFactory factory) {
		return factory.createClient(JiraServiceClient.class);
	}

	@Bean
	public HttpServiceProxyFactory jiraServiceProxyFactory() {
		String base64Auth = Base64.getEncoder().encodeToString((user + ":" + token).getBytes());

		WebClient webClient = WebClient.builder()
				.baseUrl(url)
				.defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + base64Auth)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, "en")
				.defaultHeader("X-Force-Accept-Language", "en")
				.build();

		return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient))
				.build();
	}
}
