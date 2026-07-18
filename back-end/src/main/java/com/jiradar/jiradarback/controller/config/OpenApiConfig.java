package com.jiradar.jiradarback.controller.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.Map;

@Configuration
public class OpenApiConfig {

	static {
		SpringDocUtils.getConfig().addRequestWrapperToIgnore(com.jiradar.jiradarback.core.service.AbstractIssueTrackerService.class);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().version("1.0.0"));
	}

	@Bean
	public OpenApiCustomizer dynamicPropertyResolver(MessageSource messageSource, LocaleResolver localeResolver, HttpServletRequest request) {
		return openAPI -> {
			Locale locale = localeResolver.resolveLocale(request);

			if (openAPI.getInfo() == null) {
				openAPI.setInfo(new Info().version("1.0.0"));
			}
			openAPI.getInfo().setTitle(messageSource.getMessage("openapi.info.title", null, "Jiradar Backend API", locale));
			openAPI.getInfo().setDescription(messageSource.getMessage("openapi.info.description", null, "", locale));
			processTags(openAPI, messageSource, locale);
			processPaths(openAPI, messageSource, locale);
			processComponents(openAPI, messageSource, locale);
		};
	}

	private void processTags(OpenAPI openAPI, MessageSource messageSource, Locale locale) {
		if (openAPI.getTags() != null) {
			openAPI.getTags().forEach(tag -> tag.setDescription(resolve(tag.getDescription(), messageSource, locale)));
		}
	}

	private void processPaths(OpenAPI openAPI, MessageSource messageSource, Locale locale) {
		if (openAPI.getPaths() != null) {
			openAPI.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
				operation.setSummary(resolve(operation.getSummary(), messageSource, locale));
				operation.setDescription(resolve(operation.getDescription(), messageSource, locale));
				if (operation.getParameters() != null) {
					operation.getParameters().forEach(p -> p.setDescription(resolve(p.getDescription(), messageSource, locale)));
				}
			}));
		}
	}

	private void processComponents(OpenAPI openAPI, MessageSource messageSource, Locale locale) {
		if (openAPI.getComponents() != null && openAPI.getComponents().getSchemas() != null) {
			openAPI.getComponents().getSchemas().values().forEach(schema -> {
				schema.setDescription(resolve(schema.getDescription(), messageSource, locale));
				Map<String, Schema> properties = schema.getProperties();
				if (properties != null) {
					properties.values().forEach(prop -> prop.setDescription(resolve(prop.getDescription(), messageSource, locale)));
				}
			});
		}
	}

	private String resolve(String value, MessageSource messageSource, Locale locale) {
		if (value == null) return null;
		if (value.startsWith("${") && value.endsWith("}")) {
			String key = value.substring(2, value.length() - 1);
			return messageSource.getMessage(key, new Object[0], value, locale);
		}
		return messageSource.getMessage(value, new Object[0], value, locale);
	}
}