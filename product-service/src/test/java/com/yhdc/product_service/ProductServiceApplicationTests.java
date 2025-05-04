package com.yhdc.product_service;

import com.yhdc.product_service.transaction.object.ProductCreateRecord;
import com.yhdc.product_service.transaction.object.ProductPutRecord;
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
class ProductServiceApplicationTests {

    String testUserId = UUID.randomUUID().toString();
    String testStoreId = UUID.randomUUID().toString();
    String testSku = UUID.randomUUID().toString();
    String testProductId;

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
    void shouldCreateProduct() {
        ProductCreateRecord productCreateRecord = new ProductCreateRecord(
                testUserId,
                testStoreId,
                "Test Product",
                "Testing product",
                "12000",
                "ACTIVE",
                "20",
                testSku
        );

        JsonPath jsonPath = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(productCreateRecord)
                .when()
                .post("/create")
                .then()
                .statusCode(201)
                .body("productId", Matchers.notNullValue())
                .extract()
                .jsonPath();

        testProductId = jsonPath.getString("productId");
    }


    @Test
    void shouldGetProduct() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParams("productId", testProductId)
                .when()
                .get("/detail")
                .then()
                .statusCode(200)
                .body("productId", Matchers.equalTo(testProductId));
    }


    @Test
    void shouldGetStoreProduct() {
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("storeId", testStoreId);
        searchParams.put("pageNo", "0");
        searchParams.put("pageSize", "10");
        searchParams.put("pageBy", "ASC");
        searchParams.put("pageOrder", "createdAt");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParams(searchParams)
                .when()
                .get("/list/store")
                .then()
                .statusCode(200);
    }


    @Test
    void shouldSearchStoreProduct() {
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("storeId", testStoreId);
        searchParams.put("keyword", "Test");
        searchParams.put("pageNo", "0");
        searchParams.put("pageSize", "10");
        searchParams.put("pageBy", "ASC");
        searchParams.put("pageOrder", "createdAt");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParams(searchParams)
                .when()
                .get("/search/store")
                .then()
                .statusCode(200);
    }


    @Test
    void shouldSearchAllProduct() {
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
    void shouldUpdateProduct() {
        ProductPutRecord productPutRecord = new ProductPutRecord(testProductId,
                "Update test product",
                "Product update test",
                "39400",
                "ACTIVE",
                testSku
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(productPutRecord)
                .when()
                .put("/update")
                .then()
                .statusCode(200)
                .body("name", Matchers.equalTo("Update test product"));
    }


    @Test
    void shouldDeleteProduct() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParams("productId", testProductId)
                .when()
                .delete("/delete")
                .then()
                .statusCode(200);
    }

}
