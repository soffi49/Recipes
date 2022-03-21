package com.recipes.backend.common;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractIntegrationTestConfig
{

    private static final MySQLContainer<?> mysql;

    static {
        mysql = new MySQLContainer<>("mysql:5.7")
                .withDatabaseName("testDatabase")
                .withUsername("root")
                .withPassword("")
                .withReuse(true);

        mysql.start();
    }

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.username", mysql::getUsername);
    }

}