package com.geekbrains.spoonacculare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataIngredientsResponse {
    List <DataIngredients> ingredients;
    double totalCost;
    double totalCostPerServing;
}
