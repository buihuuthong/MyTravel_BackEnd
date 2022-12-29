package com.BuiHuuThong.MyTravel.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;

@Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "MyTravel",
		description = "Ứng dụng đặt tour du lịch",
		version = "0.0.1-SNAPSHOT"
	),
	externalDocs = @ExternalDocumentation(
		description = "GitHub repository",
		url = "https://github.com/"
	)
)
@SecurityScheme(
	name = "FirebaseToken",
	type = SecuritySchemeType.HTTP,
	bearerFormat = "FirebaseToken",
	scheme = "bearer"
)
public class SpringdocConfig {
	@Bean
	public GroupedOpenApi allApis() {
		return GroupedOpenApi.builder()
			.group("all")
			.displayName("All APIs")
			.pathsToMatch("/api/**")
			.build();
	}

	@Bean
	public GroupedOpenApi publicApis() {
		return GroupedOpenApi.builder()
			.group("public")
			.displayName("Public APIs")
			.pathsToMatch("/api/**")
			.addOpenApiMethodFilter(method -> !method.isAnnotationPresent(PreAuthorize.class))
			.build();
	}

	@Bean
	public GroupedOpenApi deprecatedApis() {
		return GroupedOpenApi.builder()
			.group("deprecated")
			.displayName("Deprecated APIs")
			.pathsToMatch("/api/**")
			.addOpenApiMethodFilter(method -> {
				var operationAnnotation = method.getDeclaredAnnotation(Operation.class);
				return operationAnnotation != null && operationAnnotation.deprecated();
			})
			.build();
	}
}
