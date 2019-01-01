package com.example.samair.starcity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private Button btn_login;
    private EditText email_login, password_login;
    private FirebaseAuth authLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);

        authLogin = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uemail_login = email_login.getText().toString().trim();
                final String upass_login = password_login.getText().toString().trim();

                authLogin.signInWithEmailAndPassword(uemail_login, upass_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext() , "Login Successfull" ,Toast.LENGTH_LONG).show();

                            finish();
                            openShowproduct();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    public void openShowproduct(){
        Intent intentShowProduct = new Intent(this, ShowProducts.class);
        intentShowProduct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentShowProduct);
    }
}
