package com.example.samair.starcity.User;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.samair.starcity.R;

public class ShowProducts extends AppCompatActivity {

    private Button btn_openAddproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        btn_openAddproduct = findViewById(R.id.btn_openAddproduct);

        btn_openAddproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });

    }
     public void addProduct(){
         Intent intentAddProduct = new Intent(this, AddProducts.class);
         startActivity(intentAddProduct);
     }
}
