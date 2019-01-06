package com.example.samair.starcity.Dashboard;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.samair.starcity.Adapter.ProductsAdapter;
import com.example.samair.starcity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private EditText editSearch;
    private ProductsAdapter productsAdapter;

    private DatabaseReference reference;

    private ArrayList<String> productInfo;
    private ArrayList<ArrayList<String>> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productsAdapter = new ProductsAdapter(this);

        editSearch = findViewById(R.id.et_search);
        ImageButton btn_search = findViewById(R.id.btn_search);
        reference = FirebaseDatabase.getInstance().getReference();
        recyclerView.setAdapter(productsAdapter);



        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String searchValue = editSearch.getText().toString().toLowerCase();
                recyclerView.removeAllViews();
                Query query = reference.child("Users");
                productList = new ArrayList<>();
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            for(DataSnapshot val : dataSnapshot.getChildren()){
                                if(val.child("Products").exists()){
                                    for (DataSnapshot product : val.child("Products").getChildren()){
                                        String productManufacturer = product.child("Product Manufacturer").getValue().toString().toLowerCase();
                                        String productName = product.child("Product Name").getValue().toString().toLowerCase();
                                        if( productManufacturer.contains(searchValue) || productName.contains(searchValue)){
                                            productInfo = new ArrayList<>();
                                            productInfo.add(product.child("Product Name").getValue().toString());
                                            productInfo.add(product.child("Product Manufacturer").getValue().toString());
                                            productInfo.add(product.child("Product Type").getValue().toString());
                                            if(product.child("imagesUrl").exists()) {
                                                for (DataSnapshot imageUrl : product.child("imagesUrl").getChildren()) {
                                                    productInfo.add(imageUrl.getValue().toString());
                                                }
                                            }else {
                                                productInfo.add("https://firebasestorage.googleapis.com/v0/b/starcity-4d6e6.appspot.com/o/No_Image.png?alt=media&token=a99878fd-807c-4982-a156-7d118f2f7732");
                                            }
                                            productList.add(productInfo);
                                        }
                                    }
                                }
                            }
                            if(productList.isEmpty())
                                Toast.makeText(getApplicationContext(), "No Product Found", Toast.LENGTH_LONG).show();
                            else
                                productsAdapter.addProduct(productList);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
