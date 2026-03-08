package com.example.varchas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OtpActivity extends AppCompatActivity {

    EditText etOtp;
    Button btnVerify;

    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        etOtp = findViewById(R.id.etOtp);
        btnVerify = findViewById(R.id.btnVerify);

        phone = getIntent().getStringExtra("phone");

        btnVerify.setOnClickListener(v -> {

            String otp = etOtp.getText().toString().trim();

            // DEMO OTP
            if(otp.equals("123456")){

                Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent);
                finish();

            }else{
                Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show();
            }

        });

    }
}