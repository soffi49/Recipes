package com.recipes.backend.common;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTestConfig
{

    private static final MySQLContainer<?> mysql;

    static
    {
        mysql = new MySQLContainer<>("mysql:5.7")
                .withDatabaseName("testDatabase")
                .withUsername("test")
                .withPassword("test")
                .withReuse(true);
        mysql.start();

        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(mysql, ""),"data/create-db.sql");
    }

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry)
    {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.username", mysql::getUsername);
    }

}
