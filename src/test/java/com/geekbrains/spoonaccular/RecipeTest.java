package com.geekbrains.spoonaccular;

import com.geekbrains.spoonacculare.DataIngredients;
import com.geekbrains.spoonacculare.DataIngredientsResponse;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import net.javacrumbs.jsonunit.JsonAssert;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class RecipeTest {
    private static final String API_KEY="0e236d5d78344b82a63393add595f905";


    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI=("https://api.spoonacular.com/recipes");
    }
    @Test
    @DisplayName("В 2.5 чашках содержится 500г сахара ")
    void testConvertAmounts() {
        String actually;
        actually = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .param("ingredientName","sugar")
                .param("sourceAmount", 2.5)
                .param("sourceUnit","cups")
                .param("targetUnit","gram")
                .expect()
                .log()
                .body()
                .body("targetAmount",is(500.0F) )
                .when()
                .get("convert")
                .body()
                .prettyPrint();
    }
    @Test
    @DisplayName("Cумма цен всех ингредиентов должна быть равна итоговой сумме")
    void testPriceBreakdownByIDEqualsTotalCost() {
        DataIngredientsResponse response = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .pathParam("id","1003464")
                .expect()
                .log()
                .body()
                .when()
                .get("{id}/priceBreakdownWidget.json")
                .as(DataIngredientsResponse.class);
        double sum=response.getIngredients()
                .stream()
                .mapToDouble(DataIngredients::getPrice)
                .reduce(0.0f,(e1,e2)-> e1 + e2);
        Assertions.assertEquals(sum,response.getTotalCost());
    }
    @Test
    @DisplayName("У продукта яичные белки кол-во только в штуках")
    void testPriceBreakdownByIDEquals()  {
        DataIngredientsResponse response = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .pathParam("id","1003464")
                .expect()
                .log()
                .body()
                .when()
                .get("{id}/priceBreakdownWidget.json")
                .as(DataIngredientsResponse.class);
        response.getIngredients()
                .stream()
                .filter(e->e.getName().equals("egg white"))
                .peek(e-> Assertions.assertEquals("",e.getAmount().getMetric().getUnit()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException());
    }

    @Test
    @DisplayName ("Проверка поиска упакованных продуктов питания")
    void testSearchGroceryProducts()throws IOException {
        String actually;
        actually = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .param("query", "Pizza")
                .param("number","2")
                .expect()
                .log()
                .body()
                .when()
                .get("/search")
                .prettyPrint();
        String expected=getRecourceAsString("testSearchGroceryProducts/expected.json");
        JsonAssert.assertJsonEquals(expected, actually, JsonAssert.when(Option.IGNORING_ARRAY_ORDER));
    }

        public String getRecourceAsString (String resource) throws IOException, NullPointerException {
            InputStream stream = getClass().getResourceAsStream(resource);
            ByteArrayOutputStream buffer=new ByteArrayOutputStream();
            int i;
            while (( i = stream.read()) != -1) {
                buffer.write((byte) i);
            }
            byte[] bytes=buffer.toByteArray();
            return new String(bytes, StandardCharsets.UTF_8);
        }

    @Test
    @DisplayName("Проверка анализ рецепта по составляющим")
    void testAnalyzeRecipeSearchQuery () {
        JsonPath actually;
        actually = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .queryParam("q", "salmon%+fusilly")
                .expect()
                .log()
                .body()
                .when()
                .get("/queries/analyze")
                .body()
                .jsonPath();
        assertThat(actually.get("ingredients[0].name"), equalTo("fusilli"));
    }
}