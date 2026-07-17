package com.jiradar.jiradarback;

import com.jiradar.jiradarback.infrastructure.common.config.BannerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class BackEndApplication {

	static void main(String[] args) {
		SpringApplication app = new SpringApplication(BackEndApplication.class);
		app.setBanner(new BannerConfig());
		app.run(args);
	}

}
