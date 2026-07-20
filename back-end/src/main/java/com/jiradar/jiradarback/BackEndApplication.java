package com.jiradar.jiradarback;

import com.jiradar.jiradarback.infrastructure.common.config.BannerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class BackEndApplication {

	static void main(String[] args) {
		SpringApplication app = new SpringApplication(BackEndApplication.class);
		app.setBanner(new BannerConfig());
		app.run(args);
	}

}
