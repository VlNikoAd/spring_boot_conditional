package ru.netology.springbootdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    private static final int DEV_PORT = 8080;
    private static final int PROD_PORT = 8081;

    static GenericContainer devApp = new GenericContainer("devapp").withExposedPorts(DEV_PORT);
    static GenericContainer prodApp = new GenericContainer("prodapp").withExposedPorts(PROD_PORT);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void contextLoadsDev() {
        final String expected = "Current profile is dev";
        String url = String.format("http://localhost:%d/profile",devApp.getMappedPort(DEV_PORT));
        ResponseEntity<String> forEntity = testRestTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(expected, forEntity.getBody());
    }

    @Test
    void contextLoadsProd() {
        final String expected = "Current profile is production";
        String url = String.format("http://localhost:%d/profile",prodApp.getMappedPort(PROD_PORT));
        ResponseEntity<String> forEntity = testRestTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(expected, forEntity.getBody());
    }
}
