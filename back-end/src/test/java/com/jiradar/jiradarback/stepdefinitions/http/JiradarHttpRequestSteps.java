package com.jiradar.jiradarback.stepdefinitions.http;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class JiradarHttpRequestSteps {

	private final WebTestClient webTestClient;

	private EntityExchangeResult<String> lastResponse;

	@When("I send a GET request to {string}")
	public void iSendAGetRequestTo(String path) {
		this.lastResponse = webTestClient.get()
				.uri(path)
				.exchange()
				.expectBody(String.class)
				.returnResult();
	}

	@Then("the HTTP response status should be {int}")
	public void theHttpResponseStatusShouldBe(int statusCode) {
		assertNotNull(lastResponse, "No response recorded from a previous step!");
		assertEquals(HttpStatus.valueOf(statusCode), lastResponse.getStatus());
	}

	@Then("the response body should contain {string}")
	public void theResponseBodyShouldContain(String expectedContent) {
		assertNotNull(lastResponse);
		String body = lastResponse.getResponseBody();
		assertNotNull(body, "Response body is null!");
		assertTrue(body.contains(expectedContent),
				String.format("Expected text '%s' was not found in the body: %s", expectedContent, body));
	}

	@Then("the response body contains:")
	public void theResponseBodyShouldMatchJson(String expectedJson) throws Exception {
		assertNotNull(lastResponse);
		String actualJson = lastResponse.getResponseBody();
		assertNotNull(actualJson);
		JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);
	}
}