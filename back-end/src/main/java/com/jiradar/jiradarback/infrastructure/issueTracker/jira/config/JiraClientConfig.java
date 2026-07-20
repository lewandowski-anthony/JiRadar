package com.jiradar.jiradarback.infrastructure.issueTracker.jira.config;

import com.jiradar.jiradarback.infrastructure.issueTracker.jira.JiraServiceClient;
import com.jiradar.jiradarback.infrastructure.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

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
		WebClient webClient = WebClient.builder()
				.baseUrl(jiraProperties.getUrl())
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, "en")
				.defaultHeader("X-Force-Accept-Language", "en")
				.filter(bearerTokenFilter())
				.build();

		return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient))
				.build();
	}

	private ExchangeFilterFunction bearerTokenFilter() {
		return (request, next) -> {
			String userToken = UserContext.getAuthorization();

			if (userToken != null && !userToken.isBlank()) {
				ClientRequest authenticatedRequest = ClientRequest.from(request)
						.headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, userToken))
						.build();
				return next.exchange(authenticatedRequest);
			}
			return next.exchange(request);
		};
	}
}