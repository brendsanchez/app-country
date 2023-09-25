package com.dusk.country.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SuppressWarnings("unused")
public class SwaggerConfig {
    private static final String TITTLE = "Country API";

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.description}")
    private String appDescription;

    @Value("${app.contact.email}")
    private String appContactEmail;

    @Value("${app.contact.name}")
    private String appContactName;

    @Value("${app.contact.url}")
    private String appContactUrl;

    @Value("${app.server.url}")
    private String appServerUrl;

    @Value("${app.server.description}")
    private String appServerDescription;

    @Bean
    public OpenAPI openAPI() {
        var server = new Server()
                .url(appServerUrl)
                .description(appServerDescription);

        var contact = new Contact()
                .email(appContactEmail)
                .name(appContactName)
                .url(appContactUrl);

        var info = new Info()
                .title(TITTLE)
                .version(appVersion)
                .contact(contact)
                .description(appDescription)
                .termsOfService(appContactUrl);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}

