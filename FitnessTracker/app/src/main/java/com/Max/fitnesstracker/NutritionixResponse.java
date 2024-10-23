package com.Max.fitnesstracker;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// NutritionixResponse.java
public class NutritionixResponse {
    @SerializedName("foods")
    private List<Food> foods;

    public List<Food> getFoods() {
        return foods;
    }
}
