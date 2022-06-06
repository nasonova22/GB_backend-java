package com.geekbrains.spoonaccular;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

public class SpoonaccularTest extends AbstractTest{

    private static final String API_KEY="f1775616df3b4f568340b4f58543809b";

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI=("https://api.spoonacular.com/");
        RestAssured.requestSpecification= new RequestSpecBuilder()
                .addQueryParam ("apiKey", API_KEY)
                .log(LogDetail.ALL)
                .build();
        RestAssured.responseSpecification= new ResponseSpecBuilder()
                .log(LogDetail.BODY)
                .build();
    }


}
