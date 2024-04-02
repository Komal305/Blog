package com.blog.project.config;

import java.util.Arrays;
import java.util.List;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	
	Server local = new Server().url("http://localhost:3333").description("Local Development Server");
    Server test = new Server().url("http://10.10.20.3333").description("Test Development Server");
    Server dev = new Server().url("http://localhost/dev/:3333").description("Dev Development Server");
    Server staging = new Server().url("http://localhost/staging/:3333").description("Staging Development Server");
    List<Server> servers = Arrays.asList(local, test, dev, staging);
    
    
    @Bean
    GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(buildInfo())
                .build();
    }

    private OpenApiCustomizer buildInfo() {
        return openApi -> openApi.info(new Info()
        		.title("Blog Application Swagger-api")
        		.version("v1")
        		.description("user will be able to create blogs")
        		.contact(new Contact().name("Komal Rani").email("komalrani2861@gmail.com").url("https://www.example.com/contact"))
        		.termsOfService("https://www.example.com/terms")
        		.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
        		)
        		.servers(servers);
    }
    
}
