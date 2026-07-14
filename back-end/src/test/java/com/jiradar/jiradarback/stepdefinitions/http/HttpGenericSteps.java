package com.jiradar.jiradarback.stepdefinitions.http;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class HttpGenericSteps {

	private final TestRestTemplate restTemplate;

	private ResponseEntity<String> lastResponse;

	@When("I send a GET request to {string}")
	public void iSendAGetRequestTo(String path) {
		this.lastResponse = restTemplate.getForEntity(path, String.class);
	}

	@Then("the HTTP response status should be {int}")
	public void theHttpResponseStatusShouldBe(int statusCode) {
		assertNotNull(lastResponse, "No response recorded from a previous step!");
		assertEquals(HttpStatus.valueOf(statusCode), lastResponse.getStatusCode());
	}

	@Then("the response body should contain {string}")
	public void theResponseBodyShouldContain(String expectedContent) {
		assertNotNull(lastResponse);
		String body = lastResponse.getBody();
		assertNotNull(body, "Response body is null!");
		assertTrue(body.contains(expectedContent),
				String.format("Expected text '%s' was not found in the body: %s", expectedContent, body));
	}
}