package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Signup extends AppCompatActivity {
    EditText input_username, input_password, input_ConfirmPassword,input_email;
    Button btn_login, btn_register;
    ProgressBar progress;
    FirebaseAuth fAuth;
    AppCompatCheckBox check1, check2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        input_username=findViewById(R.id.signup_username);
        input_password=findViewById(R.id.signup_password);
        input_email=findViewById(R.id.signup_email);
        input_ConfirmPassword=findViewById(R.id.signup_confirmPassword);
        btn_login=findViewById(R.id.signup_login);
        btn_register=findViewById(R.id.signup_createAccount);
        progress=findViewById(R.id.progressBar);
        check1=findViewById(R.id.checkbox1);
        check2=findViewById(R.id.checkbox2);

        fAuth= FirebaseAuth.getInstance();

//        if(fAuth.getCurrentUser()!= null){
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username= input_username.getText().toString().trim();
                String email= input_email.getText().toString().trim();
                String password =input_password.getText().toString().trim();
                String confirmPassword =input_ConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)){
                    input_username.setError("Username field is empty");
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    input_email.setError("Email field is empty");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    input_password.setError("Password field is empty");
                    return;
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    input_ConfirmPassword.setError("Password field is empty");
                    return;
                }
                progress.setVisibility(View.VISIBLE);
                //registration of user

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Signup.this,"User created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                        else{
                            Toast.makeText(Signup.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                });



            }
        });
        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    input_ConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    input_ConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
            }
        });

    }
    public void Login(View view){
        Intent startIntent = new Intent(Signup.this,Login.class);
        startActivity(startIntent);
    }

}
