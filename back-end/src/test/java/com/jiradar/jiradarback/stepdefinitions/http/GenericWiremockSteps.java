package com.jiradar.jiradarback.stepdefinitions.http;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.jiradar.jiradarback.config.CucumberSpringConfiguration;
import com.jiradar.jiradarback.config.WireMockConfig;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.http.HttpMethod;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RequiredArgsConstructor
public class GenericWiremockSteps {

	private final WireMockConfig wireMockConfig;

    @Given("^(GET|POST|PUT|DELETE) ([^\\s]+) responds with:$")
    public void mockExternalApi(String httpMethod, String endpoint, String jsonResponse) {
		ResponseDefinitionBuilder responseBuilder = aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody(jsonResponse);

		MappingBuilder mapping;
		if ("POST".equalsIgnoreCase(httpMethod)) {
			mapping = post(urlEqualTo(endpoint));
		} else if ("PUT".equalsIgnoreCase(httpMethod)) {
			mapping = put(urlEqualTo(endpoint));
		} else if ("DELETE".equalsIgnoreCase(httpMethod)) {
			mapping = delete(urlEqualTo(endpoint));
		} else {
			mapping = get(urlEqualTo(endpoint));
		}

		wireMockConfig.getServer().stubFor(mapping.willReturn(responseBuilder));
    }
}