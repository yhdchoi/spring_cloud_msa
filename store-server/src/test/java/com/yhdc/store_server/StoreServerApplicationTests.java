package com.yhdc.store_server;

import com.yhdc.store_server.transaction.object.StoreCreateRecord;
import com.yhdc.store_server.transaction.type.StoreStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import java.util.UUID;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StoreServerApplicationTests {

    @LocalServerPort
    private int port;

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @BeforeAll
    static void beforeAll() {
        mongoDBContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mongoDBContainer.stop();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void shouldCreateStore() {
        final String testSellerId = UUID.randomUUID().toString();
        final String testName = "Test Store";
        final String testDescription = "Test Description";
        final String testStatus = StoreStatus.ACTIVE.selection();

        StoreCreateRecord store = new StoreCreateRecord(
                testSellerId,
                testName,
                testDescription,
                testStatus
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(store)
                .when()
                .post("/store/create")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("sellerId", Matchers.equalTo(testSellerId))
                .body("name", Matchers.equalTo(testName))
                .body("description", Matchers.equalTo(testDescription))
                .body("status", Matchers.equalTo(testStatus));
    }

    @Test
    void shouldSearchStore() {

    }

    @Test
    void shouldUpdateStore() {

    }

    @Test
    void shouldDeleteStore() {

    }
}
