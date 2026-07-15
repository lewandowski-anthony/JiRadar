package com.jiradar.jiradarback.controller.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class SnakeCaseApplicationConfiguration {

	@Bean
	public OncePerRequestFilter snakeCaseConverterFilter() {
		return new OncePerRequestFilter() {

			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
				final Map<String, String[]> parameters = new ConcurrentHashMap<>();

				for (String param : request.getParameterMap().keySet()) {
					String camelCaseParam = snakeToCamel(param);

					parameters.put(camelCaseParam, request.getParameterValues(param));
					parameters.put(param, request.getParameterValues(param));
				}

				filterChain.doFilter(new HttpServletRequestWrapper(request) {

					@Override
					public String getParameter(String name) {
						return parameters.containsKey(name) ? parameters.get(name)[0] : null;
					}

					@Override
					public Enumeration<String> getParameterNames() {
						return Collections.enumeration(parameters.keySet());
					}

					@Override
					public String[] getParameterValues(String name) {
						return parameters.get(name);
					}

					@Override
					public Map<String, String[]> getParameterMap() {
						return parameters;
					}
				}, response);
			}
		};
	}

	public static String snakeToCamel(String stringToConvert) {
		while (stringToConvert.contains("_")) {
			stringToConvert = stringToConvert
					.replaceFirst(
							"_[a-z]",
							String.valueOf(
									Character.toUpperCase(
											stringToConvert.charAt(
													stringToConvert.indexOf("_") + 1))));
		}

		return stringToConvert;
	}
}