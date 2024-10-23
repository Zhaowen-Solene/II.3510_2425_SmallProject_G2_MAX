package com.Max.fitnesstracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.Max.fitnesstracker.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 设置全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // 登录按钮点击事件
        binding.loginButton.setOnClickListener(view -> {
            if (validateLogin()) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish(); // 结束登录界面
            }
        });

        // 注册链接点击事件
        binding.signUpLink.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            // 不要finish()，让用户可以返回登录界面
        });
    }

    private boolean validateLogin() {
        String username = binding.loginUsernameInput.getText().toString().trim();
        String password = binding.loginPasswordInput.getText().toString().trim();

        if (username.isEmpty()) {
            binding.loginUsernameInput.setError("Please enter username");
            return false;
        }

        if (password.isEmpty()) {
            binding.loginPasswordInput.setError("Please enter password");
            return false;
        }

        // 从SharedPreferences获取保存的用户信息
        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String savedUsername = prefs.getString("username", "");
        String savedPassword = prefs.getString("password", "");

        // 验证用户名和密码
        if (username.equals(savedUsername) && password.equals(savedPassword)) {
            return true;
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}