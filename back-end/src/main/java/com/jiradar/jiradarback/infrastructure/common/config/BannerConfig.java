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
		} catch (UnknownHostException e) {
			ipAddress = "127.0.0.1";
			hostName = "localhost";
		}

		// --- Modules Context ---
		boolean isJiraEnabled = Boolean.parseBoolean(environment.getProperty("issue-tracker.jira.config.enabled", "true"));
		String jiraUrl = environment.getProperty("issue-tracker.jira.config.url", "http://localhost:8080");

		boolean isCacheEnabled = Boolean.parseBoolean(environment.getProperty("cache.enabled", "true"));
		String cacheProvider = environment.getProperty("cache.provider", "caffeine");
		String redisHost = environment.getProperty("spring.data.redis.host", "localhost");
		String redisPort = environment.getProperty("spring.data.redis.port", "6379");

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
		out.printf("  ├── \u001B[90mApplication Name :\u001B[0m \u001B[37m%s\u001B[0m\n", appName);
		out.printf("  ├── \u001B[90mRuntime Platform :\u001B[0m \u001B[37mJava %s (%s)\u001B[0m\n", javaVersion, javaVendor);
		out.printf("  ├── \u001B[90mOperating System :\u001B[0m \u001B[37m%s (v%s)\u001B[0m\n", osName, osVersion);
		out.printf("  └── \u001B[90mTimezone Config  :\u001B[0m \u001B[33m%s\u001B[0m\n", defaultTimezone);
		out.println();

		// --- SECTION 2 : NETWORK & NETWORK BOUNDS ---
		out.println(" \u001B[96m[ Network Context ]\u001B[0m");
		out.printf("  ├── \u001B[90mLocal Hostname   :\u001B[0m \u001B[37m%s\u001B[0m\n", hostName);
		out.printf("  ├── \u001B[90mInternal IP      :\u001B[0m \u001B[37m%s\u001B[0m\n", ipAddress);
		out.printf("  └── \u001B[90mListening Port   :\u001B[0m \u001B[36m%s\u001B[0m\n", serverPort);
		out.println();

		// --- SECTION 3 : THIRD PARTY TRACKERS ---
		out.println(" \u001B[94m[ Tracker Providers ]\u001B[0m");
		if (isJiraEnabled) {
			out.println("  └── \u001B[90mJira Integration :\u001B[0m [\u001B[92mEnabled\u001B[0m]");
			out.printf("      └── \u001B[90mEndpoint URL :\u001B[0m \u001B[36m%s\u001B[0m\n", jiraUrl);
		} else {
			out.println("  └── \u001B[90mJira Integration :\u001B[0m [\u001B[91mDisabled\u001B[0m]");
		}
		out.println();

		// --- SECTION 4 : STORAGE & PERFORMANCE CACHE ---
		out.println(" \u001B[93m[ Storage & Performance Cache ]\u001B[0m");
		if (isCacheEnabled) {
			out.printf("  └── \u001B[90mCache Management :\u001B[0m [\u001B[92mEnabled\u001B[0m] ── \u001B[90mProvider:\u001B[0m \u001B[33m%s\u001B[0m\n", cacheProvider.toUpperCase());
			if ("redis".equalsIgnoreCase(cacheProvider)) {
				out.printf("      └── \u001B[90mRedis Remote :\u001B[0m \u001B[36m%s:%s\u001B[0m\n", redisHost, redisPort);
			} else {
				out.println("      └── \u001B[90mCaffeine     :\u001B[0m \u001B[37mIn-Memory Storage Topology\u001B[0m");
			}
		} else {
			out.println("  └── \u001B[90mCache Management :\u001B[0m [\u001B[91mDisabled\u001B[0m] (All data fetched live via Tracker APIs)");
		}
		out.println();

		// --- FOOTER META ---
		out.printf("\u001B[92m ✔\u001B[0m \u001B[37mJiRadar v%s\u001B[0m is operational using profile: \u001B[33m%s\u001B[0m\n", appVersion, activeProfiles);
		out.println("\u001B[90m ─────────────────────────────────────────────────────────\u001B[0m");
		out.println();
	}
}