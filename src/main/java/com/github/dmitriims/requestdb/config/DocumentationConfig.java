package com.github.dmitriims.requestdb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DocumentationConfig {

    @Bean
    public OpenAPI openAPI() {
        Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("http://localhost:8080");

        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new Info()
                .title("Request API")
                .description(
                        "Описание методов работы с запросами, тэгами и папками.")
                .version("1.0")
                .contact(new Contact().name("Dmitrii Soia").
                        url("https://github.com/DmitriiMS").email("tdmitrii.soia@protonmail.ch")));;
        openAPI.setServers(List.of(localServer));

        return openAPI;
    }

}