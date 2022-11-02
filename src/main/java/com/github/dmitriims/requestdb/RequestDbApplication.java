package com.github.dmitriims.requestdb;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Objects;

@SpringBootApplication
@RequiredArgsConstructor
public class RequestDbApplication implements CommandLineRunner {

    private final Environment environment;
    private final MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RequestDbApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (Objects.equals(environment.getProperty("user-settings.drop-on-restart"), "true")) {
            mongoTemplate.getDb().drop();
        }
    }

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}
