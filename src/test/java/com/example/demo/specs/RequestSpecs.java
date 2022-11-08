package com.example.demo.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class RequestSpecs {

    public static RequestSpecification spec =
            with()
                    .baseUri("http://localhost:8081")
                    .basePath("/")
                    .contentType(ContentType.JSON)
                    .log().uri()
                    .log().body();

    public static ResponseSpecification specCreated = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(LogDetail.ALL)
            .build();

    public static ResponseSpecification specSuccess = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.ALL)
            .build();
}
