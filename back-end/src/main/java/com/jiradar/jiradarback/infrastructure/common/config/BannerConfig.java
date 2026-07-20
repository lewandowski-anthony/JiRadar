package com.jiradar.jiradarback.infrastructure.common.config;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneId;

public class BannerConfig implements Banner {

	@Override
	public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {

		// --- Build & Spring Boot Context ---
		String appName = environment.getProperty("spring.application.name", "Jiradar Backend");
		String appVersion = environment.getProperty("application.version", "0.0.1-SNAPSHOT");
		String springVersion = environment.getProperty("spring-boot.version", "4.1.0");
		String activeProfiles = environment.getProperty("spring.profiles.active", "default");
		String serverPort = environment.getProperty("server.port", "8080");

		// --- Host & Environment Context ---
		String osName = System.getProperty("os.name");
		String osVersion = System.getProperty("os.version");
		String javaVersion = System.getProperty("java.version");
		String javaVendor = System.getProperty("java.vendor");
		String defaultTimezone = environment.getProperty("jiradar.app.default-timezone", ZoneId.systemDefault().toString());

		String ipAddress;
		String hostName;
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			ipAddress = localHost.getHostAddress();
			hostName = localHost.getHostName();
		} catch (UnknownHostException _) {
			ipAddress = "127.0.0.1";
			hostName = "localhost";
		}

		// --- Modules Context ---
		boolean isJiraEnabled = Boolean.parseBoolean(environment.getProperty("jiradar.issue-tracker.jira.config.enabled", "true"));
		String jiraUrl = environment.getProperty("jiradar.issue-tracker.jira.config.url", "http://localhost:8080");

		boolean isCacheEnabled = Boolean.parseBoolean(environment.getProperty("jiradar.cache.enabled", "true"));
		String cacheProvider = environment.getProperty("jiradar.cache.provider", "caffeine");
		String redisHost = environment.getProperty("spring.data.redis.host", "localhost");
		String redisPort = environment.getProperty("spring.data.redis.port", "6379");

		// --- AI Context ---
		String aiProvider = environment.getProperty("jiradar.ai.provider");
		boolean isAiEnabled = aiProvider != null && !aiProvider.isBlank();

		// --- LOGO PRINT ---
		out.println("\u001B[92m     ██╗██╗██████╗  █████╗ ██████╗  █████╗ ██████╗ ");
		out.println("     ██║██║██╔══██╗██╔══██╗██╔══██╗██╔══██╗██╔══██╗");
		out.println("     ██║██║██████╔╝███████║██║  ██║███████║██████╔╝");
		out.println("██╗  ██║██║██╔══██╗██╔══██║██║  ██║██╔══██║██╔══██╗");
		out.println("╚█████╔╝██║██║  ██║██║  ██║██████╔╝██║  ██║██║  ██║");
		out.println(" ╚════╝ ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝\u001B[0m");
		out.println("\u001B[90m ───  ENGINEERING PERFORMANCE & RADAR METRICS  ───\u001B[0m");
		out.println();

		// --- SECTION 1 : SYSTEM ARCHITECTURE ---
		out.println(" \u001B[95m[ System Landscape ]\u001B[0m");
		out.printf("  ├── \u001B[90mApplication Name :\u001B[0m \u001B[37m%s\u001B[0m%n", appName);
		out.printf("  ├── \u001B[90mRuntime Platform :\u001B[0m \u001B[37mJava %s (%s)\u001B[0m%n", javaVersion, javaVendor);
		out.printf("  ├── \u001B[90mFramework Platform :\u001B[0m \u001B[37mSpring-Boot %s\u001B[0m%n", springVersion);
		out.printf("  ├── \u001B[90mOperating System :\u001B[0m \u001B[37m%s (v%s)\u001B[0m%n", osName, osVersion);
		out.printf("  └── \u001B[90mTimezone Config  :\u001B[0m \u001B[33m%s\u001B[0m%n", defaultTimezone);
		out.println();

		// --- SECTION 2 : NETWORK & NETWORK BOUNDS ---
		out.println(" \u001B[96m[ Network Context ]\u001B[0m");
		out.printf("  ├── \u001B[90mLocal Hostname   :\u001B[0m \u001B[37m%s\u001B[0m%n", hostName);
		out.printf("  ├── \u001B[90mInternal IP      :\u001B[0m \u001B[37m%s\u001B[0m%n", ipAddress);
		out.printf("  └── \u001B[90mListening Port   :\u001B[0m \u001B[36m%s\u001B[0m%n", serverPort);
		out.println();

		// --- SECTION 3 : THIRD PARTY TRACKERS ---
		out.println(" \u001B[94m[ Tracker Providers ]\u001B[0m");
		if (isJiraEnabled) {
			out.println("  └── \u001B[90mJira Integration :\u001B[0m [\u001B[92mEnabled\u001B[0m]");
			out.printf("      └── \u001B[90mEndpoint URL :\u001B[0m \u001B[36m%s\u001B[0m%n", jiraUrl);
		} else {
			out.println("  └── \u001B[90mJira Integration :\u001B[0m [\u001B[91mDisabled\u001B[0m]");
		}
		out.println();

		// --- SECTION 4 : ARTIFICIAL INTELLIGENCE ---
		out.println(" \u001B[91m[ Artificial Intelligence ]\u001B[0m");
		if (isAiEnabled) {
			String normalizedProvider = aiProvider.toLowerCase();
			String modelName = environment.getProperty("jiradar.ai." + normalizedProvider + ".model-name", "N/A");

			out.printf("  └── \u001B[90mAI Engine        :\u001B[0m [\u001B[92mEnabled\u001B[0m] ── \u001B[90mProvider:\u001B[0m \u001B[33m%s\u001B[0m%n", normalizedProvider.toUpperCase());
			out.printf("      ├── \u001B[90mTarget Model :\u001B[0m \u001B[35m%s\u001B[0m%n", modelName);

			switch (normalizedProvider) {
			case "vertex" -> {
				String projectId = environment.getProperty("jiradar.ai.vertex.project-id", "N/A");
				String location = environment.getProperty("jiradar.ai.vertex.location", "europe-west1");
				out.printf("      └── \u001B[90mGCP Context  :\u001B[0m \u001B[36m%s\u001B[0m (%s)%n", projectId, location);
			}
			case "ollama" -> {
				String baseUrl = environment.getProperty("jiradar.ai.ollama.base-url", "http://localhost:11434");
				out.printf("      └── \u001B[90mHost URL     :\u001B[0m \u001B[36m%s\u001B[0m%n", baseUrl);
			}
			case "gemini" -> out.println("      └── \u001B[90mAuth Mode    :\u001B[0m \u001B[37mDirect Gemini API Key\u001B[0m");
			case "openai" -> out.println("      └── \u001B[90mAuth Mode    :\u001B[0m \u001B[37mOpenAI Bearer Token\u001B[0m");
			default -> out.println("      └── \u001B[90mCustom Engine:\u001B[0m \u001B[37mGeneric Provider Configured\u001B[0m");
			}
		} else {
			out.println("  └── \u001B[90mAI Engine        :\u001B[0m [\u001B[91mDisabled\u001B[0m] (No provider set via 'jiradar.ai.provider')");
		}
		out.println();

		// --- SECTION 5 : STORAGE & PERFORMANCE CACHE ---
		out.println(" \u001B[93m[ Storage & Performance Cache ]\u001B[0m");
		if (isCacheEnabled) {
			out.printf("  └── \u001B[90mCache Management :\u001B[0m [\u001B[92mEnabled\u001B[0m] ── \u001B[90mProvider:\u001B[0m \u001B[33m%s\u001B[0m%n", cacheProvider.toUpperCase());
			if ("redis".equalsIgnoreCase(cacheProvider)) {
				out.printf("      └── \u001B[90mRedis Remote :\u001B[0m \u001B[36m%s:%s\u001B[0m%n", redisHost, redisPort);
			} else {
				out.println("      └── \u001B[90mCaffeine     :\u001B[0m \u001B[37mIn-Memory Storage Topology\u001B[0m");
			}
		} else {
			out.println("  └── \u001B[90mCache Management :\u001B[0m [\u001B[91mDisabled\u001B[0m] (All data fetched live via Tracker APIs)");
		}
		out.println();

		// --- FOOTER META ---
		out.printf("\u001B[92m ✔\u001B[0m \u001B[37mJiRadar v%s\u001B[0m is operational using profile: \u001B[33m%s\u001B[0m%n", appVersion, activeProfiles);
		out.println("\u001B[90m ─────────────────────────────────────────────────────────\u001B[0m");
		out.println();
	}
}