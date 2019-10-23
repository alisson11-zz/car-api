package com.fictiusclean.car.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableConfigurationProperties
@EnableJpaAuditing
@SpringBootApplication
@EntityScan(basePackages = "com.fictiusclean.car.api.entities")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run( Application.class, args );
	}

	@Bean
	public Docket api() {
		return new Docket( DocumentationType.SWAGGER_2 )
				.select()
				.apis( RequestHandlerSelectors.basePackage( "com.fictiusclean.car.api.controllers" ) )
				.paths( PathSelectors.any() )
				.build();
	}
}
