package com.reshmah.incident;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IncidentApiTest {

    private static final String ADMIN_USER = "admin1";
    private static final String ADMIN_PASS = "admin123";
    private static final String VIEWER_USER = "viewer1";
    private static final String VIEWER_PASS = "viewer123";

    @Value("${local.server.port}")
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    private String loginAs(String user, String pass) {
        return given()
                .contentType("application/json")
                .body(Map.of("userName", user, "password", pass))
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", not(emptyOrNullString()))
                .extract().path("token");
    }

    @Test
    void listIncidents_withoutToken_isRejected() {
        given()
                .when()
                .get("/api/incidents")
                .then()
                .statusCode(anyOf(is(401), is(403)));
    }

    @Test
    void login_withValidCredentials_returnsToken() {
        loginAs(ADMIN_USER, ADMIN_PASS);
    }

    @Test
    void login_withWrongPassword_isRejected() {
        given()
                .contentType("application/json")
                .body(Map.of("userName", ADMIN_USER, "password", "wrong"))
                .when()
                .post("/auth/login")
                .then()
                .statusCode(anyOf(is(401), is(403)));
    }

    @Test
    void createIncident_asAdmin_thenFetchIt() {
        String token = loginAs(ADMIN_USER, ADMIN_PASS);

        Integer id = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(Map.of(
                        "title", "Payment gateway timeout",
                        "description", "Checkout calls to the payment gateway are timing out",
                        "affectedService", "payments"))
                .when()
                .post("/api/incidents")
                .then()
                .statusCode(201)
                .body("title", equalTo("Payment gateway timeout"))
                .body("id", notNullValue())
                .extract().path("id");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/incidents/{id}", id)
                .then()
                .statusCode(200)
                .body("affectedService", equalTo("payments"));
    }
    @Test
    void createIncident_withBlankTitle_isRejected() {
        String token = loginAs(ADMIN_USER, ADMIN_PASS);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(Map.of("title", "", "description", "x"))
                .when()
                .post("/api/incidents")
                .then()
                .statusCode(400);
    }
    @Test
    void createIncident_asViewer_isForbidden() {
        String token = loginAs(VIEWER_USER, VIEWER_PASS);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(Map.of("title", "t", "description", "d"))
                .when()
                .post("/api/incidents")
                .then()
                .statusCode(403);
    }

    @Test
    void listIncidents_asViewer_isAllowed() {
        String token = loginAs(VIEWER_USER, VIEWER_PASS);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/incidents")
                .then()
                .statusCode(200);
    }
}