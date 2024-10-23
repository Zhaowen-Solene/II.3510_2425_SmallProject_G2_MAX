package com.Max.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class WorkoutActivity extends AppCompatActivity {
    private TextView workoutTypeText;
    private TextView timerText;
    private Button pauseButton;
    private Button endButton;

    private Handler timerHandler = new Handler();
    private long startTime = 0L;
    private long elapsedTime = 0L;
    private boolean isTimerRunning = false;
    private String workoutType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        // 初始化视图
        workoutTypeText = findViewById(R.id.workout_type_text);
        timerText = findViewById(R.id.timer_text);
        pauseButton = findViewById(R.id.pause_button);
        endButton = findViewById(R.id.end_button);

        // 获取运动类型
        workoutType = getIntent().getStringExtra("workoutType");
        workoutTypeText.setText(workoutType);

        // 开始计时
        startTimer();

        // 设置暂停按钮点击事件
        pauseButton.setOnClickListener(v -> {
            if (isTimerRunning) {
                pauseTimer();
                pauseButton.setText("RESUME");
            } else {
                resumeTimer();
                pauseButton.setText("PAUSE");
            }
        });

        // 设置结束按钮点击事件
        endButton.setOnClickListener(v -> {
            stopTimer();
            Intent intent = new Intent(WorkoutActivity.this, SummaryActivity.class);
            intent.putExtra("workoutType", workoutType);
            intent.putExtra("duration", elapsedTime);
            startActivity(intent);
            finish();
        });
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        isTimerRunning = true;
        timerHandler.post(timerRunnable);
    }

    private void pauseTimer() {
        isTimerRunning = false;
        timerHandler.removeCallbacks(timerRunnable);
    }

    private void resumeTimer() {
        startTime = System.currentTimeMillis() - elapsedTime;
        isTimerRunning = true;
        timerHandler.post(timerRunnable);
    }

    private void stopTimer() {
        isTimerRunning = false;
        timerHandler.removeCallbacks(timerRunnable);
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateTimerText(elapsedTime);
            if (isTimerRunning) {
                timerHandler.postDelayed(this, 10);
            }
        }
    };

    private void updateTimerText(long elapsedMillis) {
        int minutes = (int) (elapsedMillis / 60000);
        int seconds = (int) (elapsedMillis / 1000) % 60;
        timerText.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerHandler.removeCallbacks(timerRunnable);
    }
}
