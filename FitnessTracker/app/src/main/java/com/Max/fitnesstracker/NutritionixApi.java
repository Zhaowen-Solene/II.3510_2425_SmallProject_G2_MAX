package com.Max.fitnesstracker;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

// NutritionixApi.java
public interface NutritionixApi {
    @POST("v2/natural/nutrients")
    Call<NutritionixResponse> searchFood(@Body NutritionixRequest request);
}
