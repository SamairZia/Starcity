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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddProducts extends AppCompatActivity {

    private Button btn_saveProduct;
    private EditText name_addproduct, description_addproduct;
    private Spinner spinnerProductType, spinnerManufacturer;
    private String user_UID;

    String productType[] = {"New", "Used", "Refurbished"};
    String manufacturers[] = {"Samsung", "Apple", "LG", "Motorolla"};

    String productTypeString, manufacturer;

    ArrayAdapter<String> productTypeAdapter, manufacturerAdapter;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mDBref;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        btn_saveProduct = findViewById(R.id.btn_saveProduct);
        name_addproduct = findViewById(R.id.name_addproduct);
        description_addproduct = findViewById(R.id.description_addproduct);

        spinnerProductType = findViewById(R.id.spinnerProductType);
        spinnerManufacturer = findViewById(R.id.spinnerManufacturer);

        mAuth = FirebaseAuth.getInstance();
        mDBref =  FirebaseDatabase.getInstance().getReference().child("Users");

        productTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,productType);
        manufacturerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,manufacturers);

        spinnerProductType.setAdapter(productTypeAdapter);
        spinnerManufacturer.setAdapter(manufacturerAdapter);

        spinnerProductType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        productTypeString = "New";
                        break;
                    case 1:
                        productTypeString = "Used";
                        break;
                    case 2:
                        productTypeString = "Refurbished";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerManufacturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        manufacturer = "Samsung";
                        break;
                    case 1:
                        manufacturer = "Apple";
                        break;
                    case 2:
                        manufacturer = "LG";
                        break;
                    case 3:
                        manufacturer = "Motorolla";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FirebaseUser new_user = mAuth.getCurrentUser();
        user_UID = new_user.getUid();

        final String uidNode = user_UID;

        btn_saveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pname = name_addproduct.getText().toString().trim();
                final String pdescription = description_addproduct.getText().toString().trim();
                final String ptype = productTypeString.toString().trim();
                final String pmanufacturer = manufacturer.toString().trim();

                HashMap<String, String> saveProductMap = new HashMap<>();
                saveProductMap.put("Product Name", pname);
                saveProductMap.put("Product Description", pdescription);
                saveProductMap.put("Product Type", ptype);
                saveProductMap.put("Product Manufacturer", pmanufacturer);

                mDBref.child(uidNode).child("Products").push().setValue(saveProductMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext() , "Product Added Successfully" , Toast.LENGTH_LONG).show();
                            backToShowProduct();
                        }
                    }
                });
            }
        });
    }

    public void backToShowProduct(){
        Intent intentBackToShow = new Intent(this, ShowProducts.class);
        startActivity(intentBackToShow);
    }

}
