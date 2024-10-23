package com.Max.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// TrackerActivity.java
public class TrackerActivity extends BaseActivity {
    private RadioGroup workoutTypeGroup;
    private Button startButton;
    private String selectedWorkoutType = "General";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_tracker;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);  // BaseActivity会调用setContentView

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化视图
        workoutTypeGroup = findViewById(R.id.workout_type_group);
        startButton = findViewById(R.id.start_button);

        // 设置标题
        TextView titleText = findViewById(R.id.title_text);
        titleText.setText("Workout Type");

        // 设置RadioGroup监听器
        workoutTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = findViewById(checkedId);
            selectedWorkoutType = selectedButton.getText().toString();
        });

        // 设置开始按钮点击监听器
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(TrackerActivity.this, WorkoutActivity.class);
            intent.putExtra("workoutType", selectedWorkoutType);
            startActivity(intent);
        });
    }
}

