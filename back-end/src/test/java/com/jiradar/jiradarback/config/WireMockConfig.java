package com.jiradar.jiradarback.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class WireMockConfig {

	private static final WireMockServer wireMockServer = new WireMockServer(
			WireMockConfiguration.wireMockConfig().port(8089)
	);

	@PostConstruct
	public void start() {
		if (!wireMockServer.isRunning()) {
			wireMockServer.start();
		}
	}

	@PreDestroy
	public void stop() {
		if (wireMockServer.isRunning()) {
			wireMockServer.stop();
		}
	}

	public WireMockServer getServer() {
		return wireMockServer;
	}
}