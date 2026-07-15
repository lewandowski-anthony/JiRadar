package com.jiradar.jiradarback.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class WireMockConfig {

	private final WireMockServer wireMockServer = new WireMockServer(8089);

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