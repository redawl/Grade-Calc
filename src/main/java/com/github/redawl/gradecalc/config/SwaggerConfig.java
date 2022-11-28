package com.github.redawl.gradecalc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private final String contactName;
    private final String contactEmail;
    private final String contactUrl;

    public SwaggerConfig(
            @Value("${swagger.contactName}") String contactName,
            @Value("${swagger.contactEmail}") String contactEmail,
            @Value("${swagger.contactUrl}") String contactUrl) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactUrl = contactUrl;
    }

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Grade Calc API")
                        .description("Grade Calculator App API")
                        .version("v1")
                        .contact(new Contact().name(contactName).email(contactEmail).url(contactUrl))
                );
    }
}
