package com.example.varchas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etPhone;
    Button btnSendOtp;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhone = findViewById(R.id.etPhone);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        tvRegister = findViewById(R.id.tvRegister);

        btnSendOtp.setOnClickListener(v -> {

            String phone = etPhone.getText().toString().trim();

            if(phone.length() != 10){
                Toast.makeText(this,"Enter valid phone number",Toast.LENGTH_SHORT).show();
                return;
            }

            // For demo OTP = 123456
            Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
            intent.putExtra("phone", phone);
            startActivity(intent);

        });

        tvRegister.setOnClickListener(v -> {

            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);

        });
    }
}