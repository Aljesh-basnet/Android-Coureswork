package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private EditText input_email, input_password;
    private Button login_btn, createAccount_btn;
    private FirebaseAuth mAuth;
    ProgressBar progress;
    AppCompatCheckBox check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        input_email=findViewById(R.id.login_username);
        input_password=findViewById(R.id.login_password);
        login_btn=findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        progress=findViewById(R.id.progressBar2);
        createAccount_btn=findViewById(R.id.login_createAccount);
        check= findViewById(R.id.checkbox);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= input_email.getText().toString().trim();
                String password =input_password.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    input_email.setError("Email field is empty");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    input_password.setError("Password field is empty");
                    return;
                }

                progress.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Login success",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));

                        }
                        else{
                            Toast.makeText(Login.this,"Error ! "+ task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    input_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    input_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
            }
        });

    }
    public void CreateAccount(View view){
        Intent StartIntent= new Intent(Login.this,Signup.class);
        startActivity(StartIntent);
    }
}

