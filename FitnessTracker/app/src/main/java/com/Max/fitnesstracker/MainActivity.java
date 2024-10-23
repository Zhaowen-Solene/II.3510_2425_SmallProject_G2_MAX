package com.Max.fitnesstracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends BaseActivity {
    private TextView dateText;
    private TextView weatherText;
    private TextInputEditText currentWeightInput;
    private TextInputEditText targetWeightInput;
    private TextView progressText;
    private ProgressBar weightProgressBar;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize views and setup functionality
        initViews();
        setupPreferences();
        updateUI();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    private void initViews() {
        dateText = findViewById(R.id.dateText);
        weatherText = findViewById(R.id.weatherText);
        currentWeightInput = findViewById(R.id.currentWeightInput);
        targetWeightInput = findViewById(R.id.targetWeightInput);
        progressText = findViewById(R.id.progressText);
        weightProgressBar = findViewById(R.id.weightProgressBar);
        Button updateButton = findViewById(R.id.updateButton);

        updateButton.setOnClickListener(v -> updateWeight());
    }

    private void setupPreferences() {
        preferences = getSharedPreferences("fitness_prefs", MODE_PRIVATE);

        // Load saved weights
        float savedCurrent = preferences.getFloat("current_weight", 0);
        float savedTarget = preferences.getFloat("target_weight", 0);

        if (savedCurrent > 0) {
            currentWeightInput.setText(String.valueOf(savedCurrent));
        }
        if (savedTarget > 0) {
            targetWeightInput.setText(String.valueOf(savedTarget));
        }

        if (savedCurrent > 0 && savedTarget > 0) {
            updateWeightProgress(savedCurrent, savedTarget);
        }
    }

    private void updateWeight() {
        String currentWeightStr = currentWeightInput.getText().toString();
        String targetWeightStr = targetWeightInput.getText().toString();

        if (TextUtils.isEmpty(currentWeightStr) || TextUtils.isEmpty(targetWeightStr)) {
            Toast.makeText(this, "Please enter both weights", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float currentWeight = Float.parseFloat(currentWeightStr);
            float targetWeight = Float.parseFloat(targetWeightStr);

            if (currentWeight <= 0 || targetWeight <= 0) {
                Toast.makeText(this, "Weights must be greater than 0", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save weights
            preferences.edit()
                    .putFloat("current_weight", currentWeight)
                    .putFloat("target_weight", targetWeight)
                    .putFloat("start_weight", preferences.getFloat("start_weight", currentWeight))
                    .apply();

            updateWeightProgress(currentWeight, targetWeight);
            Toast.makeText(this, "Weight goal updated", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid weights", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateWeightProgress(float currentWeight, float targetWeight) {
        float startWeight = preferences.getFloat("start_weight", currentWeight);

        // Calculate progress
        float totalToLose = startWeight - targetWeight;
        float lost = startWeight - currentWeight;
        int progress = totalToLose > 0 ? (int)((lost / totalToLose) * 100) : 0;
        progress = Math.min(100, Math.max(0, progress));

        // Update UI
        weightProgressBar.setProgress(progress);
        progressText.setText(String.format(Locale.US,
                "Progress: %d%% (%.1f kg lost)",
                progress, lost));
    }

    private void updateUI() {
        updateDate();
        updateWeather();
    }

    private void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        dateText.setText(sdf.format(new Date()));
    }

    private void updateWeather() {
        // Mock weather data
        weatherText.setText("25°C Sunny");
    }
}