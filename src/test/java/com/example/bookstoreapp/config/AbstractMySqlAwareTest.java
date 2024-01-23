package com.example.bookstoreapp.config;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractMySqlAwareTest {
    @Container
    @ServiceConnection
    private static final MySQLContainer<?> mySQLContainer =
            new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"))
                    .withCreateContainerCmdModifier(
                            cmd -> cmd.withName("book-store-test"
                                    + LocalTime.now().getNano())
                    )
                    .withReuse(true)
                    .withDatabaseName("test_booking");

    @BeforeAll
    public static void beforeAll() {
        mySQLContainer.start();
    }
}
