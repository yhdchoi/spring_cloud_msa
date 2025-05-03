package com.yhdc.account_service;

import com.yhdc.account_service.object.UserCreateRecord;
import com.yhdc.account_service.object.UserPutRecord;
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
import org.testcontainers.containers.MariaDBContainer;

import java.util.HashMap;
import java.util.Map;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountServiceApplicationTests {

    String testUserId;

    @LocalServerPort
    private int port;

    @ServiceConnection
    static MariaDBContainer mariaDBContainer = new MariaDBContainer("mariadb:latest");

    @BeforeAll
    static void beforeAll() {
        mariaDBContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mariaDBContainer.stop();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port + "/account";
    }

    @Test
    void shouldCreateAccount() {
        UserCreateRecord account = new UserCreateRecord("testuser",
                "fiorano1q2w",
                "Daniel",
                "Choi",
                "63 Fitzwilliam blvd.",
                "yhdc@yhdc.com",
                "010-2233-4234",
                "MANAGER");

        JsonPath jsonPath = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(account)
                .when()
                .post("/register")
                .then()
                .statusCode(201)
                .body("userId", Matchers.notNullValue())
                .extract()
                .jsonPath();

        testUserId = jsonPath.getString("userId");
    }


    @Test
    void shouldGetAccount() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .param("userId", testUserId)
                .when()
                .get("/detail")
                .then()
                .statusCode(200)
                .body("userId", Matchers.notNullValue())
                .extract();
    }


    @Test
    void shouldSearchAccount() {
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("keyword", "Daniel");
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
    void shouldUpdateAccount() {
        UserPutRecord userPutRecord = new UserPutRecord(testUserId,
                "updateuser",
                "Yeong",
                "Choi",
                "yhd@gmail.com",
                "01022337263");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userPutRecord)
                .when()
                .put("/update")
                .then()
                .statusCode(200)
                .body("username", Matchers.equalTo("updateuser"));
    }


    @Test
    void shouldUpdatePassword() {


    }


    @Test
    void shouldDeleteAccount() {

    }

}
