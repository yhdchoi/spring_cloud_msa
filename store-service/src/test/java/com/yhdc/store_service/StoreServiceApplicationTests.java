package com.yhdc.store_service;

import com.yhdc.store_service.transaction.object.StoreCreateRecord;
import com.yhdc.store_service.transaction.object.StorePatchRecord;
import com.yhdc.store_service.transaction.object.StorePutRecord;
import com.yhdc.store_service.transaction.type.StoreStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StoreServiceApplicationTests {

    // TEST VARIABLES
    final String testSellerId = UUID.randomUUID().toString();
    String storeId;

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
        RestAssured.baseURI = "http://localhost:" + port + "/store";
    }


    @Test
    void shouldCreateStore() {
        final String testName = "Test Store";
        final String testDescription = "Test Description";
        final String testStatus = StoreStatus.ACTIVE.selection();

        StoreCreateRecord store = new StoreCreateRecord(
                testSellerId,
                testName,
                testDescription,
                testStatus
        );

        JsonPath jsonPath = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(store)
                .when()
                .post("/create")
                .then()
                .statusCode(201)
                .body("storeId", Matchers.notNullValue())
                .body("sellerId", Matchers.equalTo(testSellerId))
                .body("name", Matchers.equalTo(testName))
                .body("description", Matchers.equalTo(testDescription))
                .body("status", Matchers.equalTo(testStatus))
                .extract()
                .jsonPath();

        storeId = jsonPath.getString("storeId");
    }


    @Test
    void shouldGetStore() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParams("sellerId", testSellerId)
                .when()
                .get("/detail")
                .then()
                .statusCode(200)
                .body("storeId", Matchers.equalTo(storeId));
    }

    @Test
    void shouldSearchStore() {
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("keyword", "Test");
        searchParams.put("pageNo", "0");
        searchParams.put("pageSize", "10");
        searchParams.put("pageBy", "ASC");
        searchParams.put("pageOrder", "createdAt");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParams(searchParams)
                .when()
                .get("/search")
                .then()
                .statusCode(200);
    }


    @Test
    void shouldUpdateStore() {
        final String updateTestName = "Test Put Store";

        StorePutRecord storePutRecord = new StorePutRecord(testSellerId, updateTestName);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(storePutRecord)
                .when()
                .put("/update")
                .then()
                .statusCode(200)
                .body("name", Matchers.equalTo(updateTestName));
    }


    @Test
    void shouldPatchStore() {
        final String updateTestName = "Test Put Store";

        StorePatchRecord storePatchRecord = new StorePatchRecord(testSellerId, storeId, "INACTIVE");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(storePatchRecord)
                .when()
                .patch("/patch")
                .then()
                .statusCode(200)
                .body("status", Matchers.equalTo("INACTIVE"));
    }


    @Test
    void shouldDeleteStore() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParams("storeId", storeId)
                .when()
                .delete("/delete")
                .then()
                .statusCode(200);
    }
}
