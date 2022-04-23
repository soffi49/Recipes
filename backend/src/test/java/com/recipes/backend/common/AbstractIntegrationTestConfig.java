package com.recipes.backend.common;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;


@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTestConfig
{

    private static final MySQLContainer<?> mysql;

    static
    {
        mysql = new MySQLContainer<>("mysql:5.7")
                .withDatabaseName("testDatabase")
                .withUsername("root")
                .withPassword("")
                .withReuse(true);
        mysql.start();

        JdbcDatabaseDelegate jdbcDatabaseDelegate = new JdbcDatabaseDelegate(mysql, "");
        ScriptUtils.runInitScript(jdbcDatabaseDelegate,"data/create-db.sql");
        ScriptUtils.runInitScript(jdbcDatabaseDelegate,"data/truncate-db.sql");
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, "data/insert-data.sql");
    }

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry)
    {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.username", mysql::getUsername);
    }

}
