package com.ecommerce;

import com.ecommerce.api.handler.FileUploadProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileUploadProperties.class
})

public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
