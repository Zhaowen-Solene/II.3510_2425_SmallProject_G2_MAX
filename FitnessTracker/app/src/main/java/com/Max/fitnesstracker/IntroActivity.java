package com.Max.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.Max.fitnesstracker.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {
    ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 设置全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Let's go 按钮点击事件 - 跳转到登录界面
        binding.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }
        });



        // 添加动画效果
        binding.startBtn.setAlpha(0f);


        binding.startBtn.animate().alpha(1f).setDuration(1000).setStartDelay(1000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}