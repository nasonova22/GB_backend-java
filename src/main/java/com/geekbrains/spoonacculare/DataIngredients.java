package com.geekbrains.spoonacculare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataIngredients {
        private String image;
        private String name;
        private double price;
        private Amount amount;
    }

