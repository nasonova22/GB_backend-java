package com.geekbrains.spoonacculare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amount {
        private Metric metric;
        private Us us;
}
