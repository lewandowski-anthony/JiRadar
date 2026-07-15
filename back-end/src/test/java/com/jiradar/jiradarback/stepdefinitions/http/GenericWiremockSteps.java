package com.jiradar.jiradarback.stepdefinitions.http;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.jiradar.jiradarback.config.WireMockConfig;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

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

		MappingBuilder mapping = getMappingBuilder(httpMethod, endpoint);
		wireMockConfig.getServer().stubFor(mapping.willReturn(responseBuilder));
	}

	@Given("^GET ([^\\s]+) responds with status (\\d+)$")
	public void mockExternalApiWithStatus(String endpoint, int statusCode) {
		ResponseDefinitionBuilder responseBuilder = aResponse()
				.withStatus(statusCode)
				.withHeader("Content-Type", "application/json");

		MappingBuilder mapping = getMappingBuilder("GET", endpoint);
		wireMockConfig.getServer().stubFor(mapping.willReturn(responseBuilder));
	}

	private MappingBuilder getMappingBuilder(String httpMethod, String endpoint) {
		MappingBuilder mapping;

		if (endpoint.contains("?")) {
			endpoint = endpoint.split("\\?")[0];
		}

		if ("POST".equalsIgnoreCase(httpMethod)) {
			mapping = post(urlPathEqualTo(endpoint));
		} else if ("PUT".equalsIgnoreCase(httpMethod)) {
			mapping = put(urlPathEqualTo(endpoint));
		} else if ("DELETE".equalsIgnoreCase(httpMethod)) {
			mapping = delete(urlPathEqualTo(endpoint));
		} else {
			mapping = get(urlPathEqualTo(endpoint));
		}
		return mapping;
	}
}