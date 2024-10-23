package com.Max.fitnesstracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Max.fitnesstracker.databinding.ActivitySignupBinding;

import org.jetbrains.annotations.Nullable;


public class SignUpActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private static final int PICK_IMAGE = 1;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setupClickListeners();
    }

    private void setupClickListeners() {
        // chose avator
        binding.selectImageBtn.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), PICK_IMAGE);
        });


        binding.signUpButton.setOnClickListener(view -> {
            if (validateInputs()) {
                saveUserInfo();
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
            }
        });

        setupInputValidation();
    }

    private void setupInputValidation() {

        binding.nicknameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 2) {
                    binding.nicknameInput.setError("Nickname must be at least 2 characters");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // listening height
        binding.heightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    int height = Integer.parseInt(s.toString());
                    if (height < 50 || height > 250) {
                        binding.heightInput.setError("Please enter a valid height (50-250 cm)");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // 体重输入监听
        binding.weightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    int weight = Integer.parseInt(s.toString());
                    if (weight < 20 || weight > 200) {
                        binding.weightInput.setError("Please enter a valid weight (20-200 kg)");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            binding.profileImage.setImageURI(selectedImageUri);
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // valide nickname
        String nickname = binding.nicknameInput.getText().toString().trim();
        if (nickname.isEmpty() || nickname.length() < 2) {
            binding.nicknameInput.setError("Please enter a valid nickname");
            isValid = false;
        }

        // valid hight
        String height = binding.heightInput.getText().toString().trim();
        if (height.isEmpty()) {
            binding.heightInput.setError("Please enter your height");
            isValid = false;
        } else {
            int heightValue = Integer.parseInt(height);
            if (heightValue < 50 || heightValue > 250) {
                binding.heightInput.setError("Please enter a valid height (50-250 cm)");
                isValid = false;
            }
        }

        // 验证体重
        String weight = binding.weightInput.getText().toString().trim();
        if (weight.isEmpty()) {
            binding.weightInput.setError("Please enter your weight");
            isValid = false;
        } else {
            int weightValue = Integer.parseInt(weight);
            if (weightValue < 20 || weightValue > 200) {
                binding.weightInput.setError("Please enter a valid weight (20-200 kg)");
                isValid = false;
            }
        }

        return isValid;
    }

    private void saveUserInfo() {
        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("username", binding.usernameInput.getText().toString().trim());
        editor.putString("password", binding.passwordInput.getText().toString());
        editor.putString("nickname", binding.nicknameInput.getText().toString().trim());
        editor.putString("height", binding.heightInput.getText().toString().trim());
        editor.putString("weight", binding.weightInput.getText().toString().trim());
        editor.putString("motto", binding.mottoInput.getText().toString().trim());

        if (selectedImageUri != null) {
            editor.putString("profileImage", selectedImageUri.toString());
        }

        editor.apply();

        Toast.makeText(this, "Registration successful! Please login.", Toast.LENGTH_SHORT).show();

        // SignUP
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish(); // finish
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null; // 避免内存泄漏
    }
}