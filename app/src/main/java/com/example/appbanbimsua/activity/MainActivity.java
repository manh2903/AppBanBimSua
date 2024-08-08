package com.example.appbanbimsua.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanbimsua.R;

public class MainActivity extends AppCompatActivity {
    private Button btnLogout;
    private TextView textHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo UI
        btnLogout = findViewById(R.id.btn_logout);
        textHello = findViewById(R.id.text_hello);

        // Thiết lập listener cho nút đăng xuất
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogout();
            }
        });
    }

    private void handleLogout() {
        // Xóa trạng thái đăng nhập và thông tin người dùng
        clearUserInfo();
        saveLoginState(false);

        // Quay lại màn hình đăng nhập
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // Phương thức để xóa thông tin người dùng
    private void clearUserInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    // Phương thức để cập nhật trạng thái đăng nhập
    private void saveLoginState(boolean isLoggedIn) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }
}
