package com.Max.fitnesstracker;


// 添加必要的导入
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Interceptor;
import retrofit2.http.Body;
import retrofit2.http.POST;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;
import androidx.cardview.widget.CardView;
import java.io.IOException;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NutritionActivity extends BaseActivity {
    private EditText searchEditText;
    private Button searchButton;
    private CardView resultCard;
    private TextView foodNameText, caloriesText, proteinText, carbsText, fatText;
    private ProgressBar progressBar;

    private static final String NUTRITIONIX_APP_ID = "b017299b";
    private static final String NUTRITIONIX_APP_KEY = "f3c27400f2382045c9e746a073fdcd9a";

    private NutritionixApi nutritionixApi;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_nutrition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化视图
        initViews();
        // 初始化API
        initApi();
        // 设置点击事件
        setupClickListeners();
    }

    private void initViews() {
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        resultCard = findViewById(R.id.resultCard);
        foodNameText = findViewById(R.id.foodNameText);
        caloriesText = findViewById(R.id.caloriesText);
        proteinText = findViewById(R.id.proteinText);
        carbsText = findViewById(R.id.carbsText);
        fatText = findViewById(R.id.fatText);
        progressBar = findViewById(R.id.progressBar);
    }

    private void initApi() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("x-app-id", NUTRITIONIX_APP_ID)
                            .header("x-app-key", NUTRITIONIX_APP_KEY)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://trackapi.nutritionix.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        nutritionixApi = retrofit.create(NutritionixApi.class);
    }

    private void setupClickListeners() {
        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString();
            if (!query.isEmpty()) {
                searchFood(query);
            }
        });
    }

    private void searchFood(String query) {
        progressBar.setVisibility(View.VISIBLE);
        resultCard.setVisibility(View.GONE);

        NutritionixRequest request = new NutritionixRequest(query);

        // 添加日志
        Log.d("NutritionAPI", "Searching for: " + query);

        nutritionixApi.searchFood(request).enqueue(new retrofit2.Callback<NutritionixResponse>() {
            @Override
            public void onResponse(Call<NutritionixResponse> call, Response<NutritionixResponse> response) {
                progressBar.setVisibility(View.GONE);

                // 添加详细的响应信息
                if (!response.isSuccessful()) {
                    try {
                        Log.e("NutritionAPI", "Error Code: " + response.code());
                        Log.e("NutritionAPI", "Error Body: " + response.errorBody().string());
                        Toast.makeText(NutritionActivity.this,
                                "Error: " + response.code() + " - " + response.message(),
                                Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                if (response.body() != null && response.body().getFoods() != null
                        && !response.body().getFoods().isEmpty()) {
                    Food food = response.body().getFoods().get(0);
                    displayFoodInfo(food);
                } else {
                    Toast.makeText(NutritionActivity.this,
                            "Food not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NutritionixResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                // 添加详细的错误信息
                Log.e("NutritionAPI", "API Call Failed", t);
                Toast.makeText(NutritionActivity.this,
                        "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayFoodInfo(Food food) {
        resultCard.setVisibility(View.VISIBLE);
        foodNameText.setText(food.getFoodName());
        caloriesText.setText("Calories: " + food.getNfCalories() + " kcal");
        proteinText.setText("Protein: " + food.getNfProtein() + "g");
        carbsText.setText("Carbs: " + food.getNfTotalCarbohydrate() + "g");
        fatText.setText("Fat: " + food.getNfTotalFat() + "g");
    }
}




