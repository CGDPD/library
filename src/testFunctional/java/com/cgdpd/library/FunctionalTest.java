package com.cgdpd.library;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
public class FunctionalTest {

    private static final PostgreSQLContainer<?> postgres = SingletonPostgreSQLContainer.getInstance();

    @Autowired
    private Flyway flyway;

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    public void setUp() {
        flyway.migrate();
    }

    @AfterEach
    public void tearDown() {
        flyway.clean();
    }

    private static class SingletonPostgreSQLContainer {

        private static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse(
              "postgres:latest");
        private static PostgreSQLContainer<?> container;

        private SingletonPostgreSQLContainer() {}

        public static synchronized PostgreSQLContainer<?> getInstance() {
            if (container == null) {
                container = new PostgreSQLContainer<>(POSTGRES_IMAGE);
                container.start();
            }
            return container;
        }
    }
}
