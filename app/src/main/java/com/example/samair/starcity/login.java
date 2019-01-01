package com.example.samair.starcity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity {

    private Button btn_login;
    private EditText email_login, password_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
    }
}
