package io.swagger.petstore.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.swagger.petstore.util.PropertiesLoader;
import org.apache.http.HttpStatus;

import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class BaseApiClient {

    protected final Properties properties;

    public BaseApiClient() {
        this.properties = PropertiesLoader.loadProperties();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = properties.getProperty("base.url");
    }

    public <R> R post(String path, Object body, Class<R> responseClass) {
        return getRequestSpecification()
                .body(body)
                .when()
                .post(path)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(responseClass);
    }

    public <R> R put(String path, Object body, Class<R> responseClass) {
        return getRequestSpecification()
                .body(body)
                .when()
                .put(path)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(responseClass);
    }

    public Response get(String path) {
        return getRequestSpecification()
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public Response get(String path, Map<String, ?> parameterNameValuePairs) {
        return getRequestSpecification()
                .queryParams(parameterNameValuePairs)
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public Response delete(String path) {
        return getRequestSpecification()
                .when()
                .delete(path)
                .then()
                .extract()
                .response();
    }

    protected static RequestSpecification getRequestSpecification() {
        return given()
                .filter(new AllureRestAssured())
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
}
