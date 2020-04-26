package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText input_userName, input_password;
    private Button btn,btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        input_userName=findViewById(R.id.login_username);
        input_password=findViewById(R.id.login_password);
        btn=findViewById(R.id.login);

        btn1=findViewById(R.id.createAccount);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username= input_userName.getText().toString();
                String password= input_password.getText().toString();
                if (username.isEmpty()) {
                    Toast.makeText(Login.this, "UserName is empty", Toast.LENGTH_SHORT).show();

                } else if (password.isEmpty()) {
                    Toast.makeText(Login.this, "Password is empty", Toast.LENGTH_LONG).show();
                } else if (username.equals("admin") && password.equals("root")) {
                    startActivity(new Intent(Login.this, MainActivity.class));
                } else {
                    Toast.makeText(Login.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent= new Intent(getApplicationContext(), Signup.class);
                startActivity(startIntent);
            }
        });
    }
}
