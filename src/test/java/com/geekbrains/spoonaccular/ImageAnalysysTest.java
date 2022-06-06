package com.geekbrains.spoonaccular;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.ClassUtils.getName;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class ImageAnalysysTest extends SpoonaccularTest{
    @Test
    void  testDonatAnalysys () {
        given()
                .multiPart (getFile("coffee.jpeg"))
                .expect()
                .body("category", is ("coffee"))
                .body("probability",greaterThan (0.8f))
                .when()
                .post("food/images/classify");
    }
}
