package com.example.theahostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theahostel.create.CreateAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button login;
    TextView create;
    private FirebaseAuth mAuth;
    EditText Email,Password;
    public String email;
    public String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.button);
        create = findViewById(R.id.textView2);
        mAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.txt_email);
        Password = findViewById(R.id.txt_pass);

        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();



        login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signIn();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Create = new Intent(LoginActivity.this, CreateAccount.class);
                startActivity(Create);
                finish();
            }
        });


    }

    public void signIn(){
        final String email = Email.getText().toString().trim();
        final String password = Password.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            Email.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(password)){
            Password.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(email)&&TextUtils.isEmpty(password)){
            Email.setError("Required");
            Password.setError("Required");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent loginIntent = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(loginIntent);
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Wrong email or password",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

}
