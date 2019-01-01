package com.example.samair.starcity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup extends AppCompatActivity {

    private Button btn_signup;
    private EditText email_signup, shopName_signup, contact_signup, repName_signup, password_signup, shopNimber_signup, confirmPassword_signup;
    private Spinner spinnerSelectFloor, spinnerSelectType;

    String floors[] = {"G" , "UB", "LB", "F", "S", "T", "FT"};
    String type[] = {"Wholesale" , "Retail"};
    String floor, typeString, newUserUID;

    ArrayAdapter<String> floorAdapter, typeAdapter;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btn_signup = findViewById(R.id.btn_signup);
        email_signup = findViewById(R.id.email_signup);
        shopName_signup = findViewById(R.id.shopName_signup);
        contact_signup = findViewById(R.id.contact_signup);
        repName_signup = findViewById(R.id.repName_signup);
        password_signup = findViewById(R.id.password_signup);
        shopNimber_signup = findViewById(R.id.shopNimber_signup);
        confirmPassword_signup = findViewById(R.id.confirmPassword_signup);

        spinnerSelectFloor = findViewById(R.id.spinnerSelectFloor);
        spinnerSelectType = findViewById(R.id.spinnerSelectType);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        floorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,floors);
        typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,type);

        spinnerSelectFloor.setAdapter(floorAdapter);
        spinnerSelectType.setAdapter(typeAdapter);

        spinnerSelectFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        floor = "G";
                        break;
                    case 1:
                        floor = "UB";
                        break;
                    case 2:
                        floor = "LB";
                        break;
                    case 3:
                        floor = "F";
                        break;
                    case 4:
                        floor = "S";
                        break;
                    case 5:
                        floor = "T";
                        break;
                    case 6:
                        floor = "FT";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSelectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        typeString = "Wholesale";
                        break;
                    case 1:
                        typeString = "Retail";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String uemail = email_signup.getText().toString().trim();
                final String ushopName = shopName_signup.getText().toString().trim();
                final String ucontact = contact_signup.getText().toString().trim();
                final String urepName = repName_signup.getText().toString().trim();
                final String ushopNo = shopNimber_signup.getText().toString().trim();
                final String upass = password_signup.getText().toString().trim();
                final String uconfirmPass = confirmPassword_signup.getText().toString().trim();
                final String ushopFloor = floor.toString().trim();
                final String ushopType = typeString.toString().trim();

                mAuth.createUserWithEmailAndPassword(uemail, upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            mAuth = FirebaseAuth.getInstance();
                            FirebaseUser make_user = mAuth.getCurrentUser();
                            newUserUID = make_user.getUid();
                            final HashMap<String, String> signupMap = new HashMap<String, String>();
                            signupMap.put("Email",uemail);
                            signupMap.put("Shop Name", ushopName);
                            signupMap.put("Contact Number",ucontact);
                            signupMap.put("Rep Name",urepName);
                            signupMap.put("Shop Number",ushopNo);
                            signupMap.put("Shop Floor",ushopFloor);
                            signupMap.put("Shop Type",ushopType);
                            signupMap.put("Password",upass);

                            mDatabaseReference.child(newUserUID).child("Shop Details").setValue(signupMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext() , "SignUp Successful" , Toast.LENGTH_LONG).show();
                                        finish();
                                        openShowProducts();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext() , "SignUp Failed" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }

    public void openShowProducts(){

        Intent intentShowProduct = new Intent(this, ShowProducts.class);
        startActivity(intentShowProduct);
    }
}
