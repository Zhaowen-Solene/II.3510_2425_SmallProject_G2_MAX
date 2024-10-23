package com.Max.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected LinearLayout navHome, navVideo, navNutrition, navTracker, navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        // 初始化底部导航栏
        initializeNavigation();
    }

    // 子类需要实现这个方法来提供各自的布局
    protected abstract int getLayoutResourceId();

    private void initializeNavigation() {
        navHome = findViewById(R.id.nav_home);
        navVideo = findViewById(R.id.nav_video);
        navNutrition= findViewById(R.id.nav_nutrition);
        navTracker = findViewById(R.id.nav_tracker);
        navProfile = findViewById(R.id.nav_profile);

        // 设置点击事件
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                // 使用getClass()来判断当前Activity类型
                if (!getClass().equals(MainActivity.class)) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
            });
        }

        if (navVideo != null) {
            navVideo.setOnClickListener(v -> {
                if (!getClass().equals(VideoActivity.class)) {
                    startActivity(new Intent(this, VideoActivity.class));
                    finish();
                }
            });
        }

        if (navNutrition != null) {
            navNutrition.setOnClickListener(v -> {
                if (!getClass().equals(NutritionActivity.class)) {
                    startActivity(new Intent(this, NutritionActivity.class));
                    finish();
                }
            });
        }

        if (navTracker != null) {
            navTracker.setOnClickListener(v -> {
                if (!getClass().equals(TrackerActivity.class)) {
                    startActivity(new Intent(this, TrackerActivity.class));
                    finish();
                }
            });
        }

        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                if (!getClass().equals(ProfileActivity.class)) {
                    startActivity(new Intent(this, ProfileActivity.class));
                    finish();
                }
            });
        }

        // 设置当前选中的导航项
        updateNavigationSelection();
    }

    private void updateNavigationSelection() {
        // 默认所有图标半透明
        if (navHome != null) navHome.setAlpha(0.5f);
        if (navVideo != null) navVideo.setAlpha(0.5f);
        if (navNutrition != null) navNutrition.setAlpha(0.5f);
        if (navTracker != null) navTracker.setAlpha(0.5f);
        if (navProfile != null) navProfile.setAlpha(0.5f);

        // 使用getClass()来判断当前Activity类型
        if (getClass().equals(MainActivity.class)) {
            navHome.setAlpha(1.0f);
        } else if (getClass().equals(VideoActivity.class)) {
            navVideo.setAlpha(1.0f);
        } else if (getClass().equals(NutritionActivity.class)) {
            navNutrition.setAlpha(1.0f);
        } else if (getClass().equals(TrackerActivity.class)) {
            navTracker.setAlpha(1.0f);
        } else if (getClass().equals(ProfileActivity.class)) {
            navProfile.setAlpha(1.0f);
        }
    }
}