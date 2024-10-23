package com.Max.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SummaryActivity extends AppCompatActivity {
    private TextView summaryWorkoutType;
    private TextView summaryDuration;
    private TextView summaryCalories;
    private Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // 初始化视图
        summaryWorkoutType = findViewById(R.id.summary_workout_type);
        summaryDuration = findViewById(R.id.summary_duration);
        summaryCalories = findViewById(R.id.summary_calories);
        closeButton = findViewById(R.id.close_button);

        // 获取运动数据
        String workoutType = getIntent().getStringExtra("workoutType");
        long duration = getIntent().getLongExtra("duration", 0);

        // 计算并显示运动数据
        int minutes = (int) (duration / 60000);
        int seconds = (int) (duration / 1000) % 60;
        int calories = calculateCalories(workoutType, duration);

        // 设置文本
        summaryWorkoutType.setText("Workout Type: " + workoutType);
        summaryDuration.setText(String.format("Duration: %02d:%02d", minutes, seconds));
        summaryCalories.setText("Calories Burned: " + calories);

        // 设置关闭按钮点击事件
        closeButton.setOnClickListener(v -> {
            // 返回到TrackerActivity
            Intent intent = new Intent(SummaryActivity.this, TrackerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private int calculateCalories(String workoutType, long duration) {
        // 根据运动类型和时长计算卡路里
        // 这里使用简单的计算方法，您可以根据实际需求修改
        double minutes = duration / 60000.0;
        switch (workoutType.toLowerCase()) {
            case "run":
                return (int) (minutes * 11.5); // 假设跑步每分钟消耗11.5卡路里
            case "walk":
                return (int) (minutes * 5.0);  // 假设步行每分钟消耗5卡路里
            case "training":
                return (int) (minutes * 8.0);  // 假设训练每分钟消耗8卡路里
            case "yoga":
                return (int) (minutes * 4.0);  // 假设瑜伽每分钟消耗4卡路里
            default:
                return (int) (minutes * 6.0);  // 默认每分钟消耗6卡路里
        }
    }
}