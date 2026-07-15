package com.jiradar.jiradarback.stepdefinitions.http;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.jiradar.jiradarback.config.WireMockConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

@Slf4j
@RequiredArgsConstructor
public class GlobalHooks {

	private final ApplicationContext applicationContext;

	@Before(order = 0)
	@After(order = 0)
	public void resetWireMockGlobally() {
		try {
			WireMockConfig wireMockConfig = applicationContext.getBean(WireMockConfig.class);
			WireMockServer server = wireMockConfig.getServer();

			if (server != null && server.isRunning()) {
				server.resetAll();
				log.info("--- Successfully reset WireMockGlobally ---");
			}
		} catch (Exception e) {
			log.error("Impossible to reset WireMockGlobally : {}", e.getMessage());
		}
	}
}