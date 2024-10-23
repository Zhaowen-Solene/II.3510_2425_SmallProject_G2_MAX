package com.Max.fitnesstracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends BaseActivity {
    private EditText editTextHeight, editTextWeight;
    private TextView textViewBMI, profileNickname, profileUsername;
    private SharedPreferences prefs;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化视图
        initViews();
        // 加载用户信息
        loadUserInfo();
        // 设置输入监听
        setupTextChangeListeners();
    }

    private void initViews() {
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        textViewBMI = findViewById(R.id.textViewBMI);
        profileNickname = findViewById(R.id.profileNickname);
        profileUsername = findViewById(R.id.profileUsername);
    }

    private void loadUserInfo() {
        prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);

        // 设置昵称和用户名
        String nickname = prefs.getString("nickname", "");
        String username = prefs.getString("username", "");
        profileNickname.setText(nickname);
        profileUsername.setText(username);

        // 设置保存的身高体重
        String savedHeight = prefs.getString("height", "");
        String savedWeight = prefs.getString("weight", "");

        editTextHeight.setText(savedHeight);
        editTextWeight.setText(savedWeight);

        // 如果有保存的数据，计算初始BMI
        if (!savedHeight.isEmpty() && !savedWeight.isEmpty()) {
            calculateBMI();
        }
    }

    private void setupTextChangeListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                calculateBMI();
                saveUserData();
            }
        };

        // 为身高体重输入框添加监听器
        editTextHeight.addTextChangedListener(textWatcher);
        editTextWeight.addTextChangedListener(textWatcher);
    }

    private void calculateBMI() {
        String heightStr = editTextHeight.getText().toString();
        String weightStr = editTextWeight.getText().toString();

        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            textViewBMI.setText("--");
            return;
        }

        try {
            float height = Float.parseFloat(heightStr) / 100; // 转换为米
            float weight = Float.parseFloat(weightStr);

            if (height <= 0 || weight <= 0) {
                textViewBMI.setText("--");
                return;
            }

            // 计算BMI
            float bmi = weight / (height * height);

            // 显示BMI（保留一位小数）
            textViewBMI.setText(String.format("%.1f", bmi));

            // 设置BMI分类和颜色
            String category = getBMICategory(bmi);
            textViewBMI.append(" (" + category + ")");

        } catch (NumberFormatException e) {
            textViewBMI.setText("--");
        }
    }

    private String getBMICategory(float bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 24.9) {
            return "Normal weight";
        } else if (bmi < 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    private void saveUserData() {
        String height = editTextHeight.getText().toString();
        String weight = editTextWeight.getText().toString();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("height", height);
        editor.putString("weight", weight);
        editor.apply();
    }
}