package com.example.varchas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etPhone;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if(name.isEmpty() || phone.length()!=10){
                Toast.makeText(this,"Enter valid details",Toast.LENGTH_SHORT).show();
                return;
            }

            // Pass phone to OTP screen
            Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
            intent.putExtra("phone", phone);
            intent.putExtra("name", name);
            startActivity(intent);

        });
    }
}