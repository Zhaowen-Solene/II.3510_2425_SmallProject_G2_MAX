package com.Max.fitnesstracker;

import com.google.gson.annotations.SerializedName;

// Food.java
public class Food {
    @SerializedName("food_name")
    private String foodName;

    @SerializedName("nf_calories")
    private double nfCalories;

    @SerializedName("nf_protein")
    private double nfProtein;

    @SerializedName("nf_total_carbohydrate")
    private double nfTotalCarbohydrate;

    @SerializedName("nf_total_fat")
    private double nfTotalFat;

    // Getters
    public String getFoodName() { return foodName; }
    public double getNfCalories() { return nfCalories; }
    public double getNfProtein() { return nfProtein; }
    public double getNfTotalCarbohydrate() { return nfTotalCarbohydrate; }
    public double getNfTotalFat() { return nfTotalFat; }
}
