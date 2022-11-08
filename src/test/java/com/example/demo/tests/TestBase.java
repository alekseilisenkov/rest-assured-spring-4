package com.example.demo.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static com.example.demo.specs.RequestSpecs.spec;
import static com.example.demo.specs.RequestSpecs.specCreated;
import static io.restassured.RestAssured.given;
//2
public class TestBase {

    private String dataAdd = "{ \"student_name\": \"Ginny Weasley\", \"student_house\": \"Gryffindor\" }";

    public String getDataAdd() {
        return dataAdd;
    }

    protected void createStudent(String name, String house) {
        String data = "{ \"student_name\":\"" + name + "\", \"student_house\":\"" + house + "\"}";

        given().spec(spec)
                .body(data)
                .when()
                .post("student")
                .then()
                .spec(specCreated);
    }

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8081";
    }
}
