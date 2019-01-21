package com.example.zbt.starcity.Dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.request.RequestOptions;

import com.example.zbt.starcity.Library.GlideApp;
import com.example.zbt.starcity.Library.MyGestureDetector;
import com.example.zbt.starcity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Search_ProductDetail extends AppCompatActivity {


    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        final TextView tv_productTitle = findViewById(R.id.tv_productTitle);
        final TextView tv_productBrand = findViewById(R.id.tv_productBrand);
        final TextView tv_productType = findViewById(R.id.tv_productType);
        final TextView productDescription = findViewById(R.id.productDescription);
        final TextView shopName = findViewById(R.id.tv_shopName);
        final TextView tv_shopRepName = findViewById(R.id.tv_shopRepName);
        final TextView tv_shopNumber = findViewById(R.id.tv_shopNumber);
        final TextView tv_shopFloor = findViewById(R.id.tv_shopFloor);
        final TextView tv_shopContact = findViewById(R.id.tv_shopContact);
        final TextView tv_shopEmail = findViewById(R.id.tv_shopEmail);
        final TextView tv_shopType = findViewById(R.id.tv_shopType);

        final RelativeLayout loadingScreen = findViewById(R.id.progressProductDetail);
//        final RelativeLayout loadingImages = findViewById(R.id.progressProductImages);

        final ViewFlipper viewFlipper = findViewById(R.id.viewFlipperProductImages);

        Intent mIntent = getIntent();
        String uID = mIntent.getStringExtra("uID");
        String productID = mIntent.getStringExtra("productID");

        final GestureDetector gestureDetector = new GestureDetector(new MyGestureDetector(viewFlipper, this));

        viewFlipper.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return false;
                } else {
                    return true;
                }
            }
        });


        reference = FirebaseDatabase.getInstance().getReference();

        Query queryUser = reference.child("Users/"+uID+"/Shop Details");
        Query queryProduct = reference.child("Users/"+uID+"/Products/"+productID);

        queryUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if(dataSnapshot.child("Shop Name").exists())
                        shopName.setText(dataSnapshot.child("Shop Name").getValue().toString());
                    else
                        shopName.setText("No Name");
                    if(dataSnapshot.child("Rep Name").exists())
                        tv_shopRepName.setText(dataSnapshot.child("Rep Name").getValue().toString());
                    else
                        tv_shopRepName.setText("No Representative Found");
                    if(dataSnapshot.child("Shop Number").exists())
                        tv_shopNumber.setText(dataSnapshot.child("Shop Number").getValue().toString());
                    else
                        tv_shopNumber.setText("-");
                    if(dataSnapshot.child("Shop Floor").exists())
                        tv_shopFloor.setText(dataSnapshot.child("Shop Floor").getValue().toString());
                    else
                        tv_shopFloor.setText("-");
                    if(dataSnapshot.child("Contact Number").exists())
                        tv_shopContact.setText(dataSnapshot.child("Contact Number").getValue().toString());
                    else
                        tv_shopContact.setText("-");
                    if(dataSnapshot.child("Email").exists())
                        tv_shopEmail.setText(dataSnapshot.child("Email").getValue().toString());
                    else
                        tv_shopEmail.setText("-");
                    if(dataSnapshot.child("Shop Type").exists())
                        tv_shopType.setText(dataSnapshot.child("Shop Type").getValue().toString());
                    else
                        tv_shopType.setText("-");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        queryProduct.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if(dataSnapshot.child("Product Name").exists())
                        tv_productTitle.setText(dataSnapshot.child("Product Name").getValue().toString());
                    else
                        tv_productTitle.setText("No Title");
                    if(dataSnapshot.child("Product Manufacturer").exists())
                        tv_productBrand.setText(dataSnapshot.child("Product Manufacturer").getValue().toString());
                    else
                        tv_productBrand.setText("No Brand");
                    if(dataSnapshot.child("Product Type").exists())
                        tv_productType.setText(dataSnapshot.child("Product Type").getValue().toString());
                    else
                        tv_productType.setText("No Type");
                    if(dataSnapshot.child("Product Description").exists())
                        productDescription.setText(dataSnapshot.child("Product Description").getValue().toString());
                    else{
                        productDescription.setText("No Description");
                        productDescription.setTypeface(null, Typeface.ITALIC);
                    }
                    loadingScreen.setVisibility(View.GONE);

                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.noimage)
                            .error(R.drawable.noimage);

                    if(dataSnapshot.child("imagesUrl").exists()) {
                        for (DataSnapshot imageUrl : dataSnapshot.child("imagesUrl").getChildren()) {
                            ImageView image = new ImageView(getApplicationContext());

                            GlideApp.with(Search_ProductDetail.this)
                                    .load(imageUrl.getValue().toString())
                                    .centerCrop()
                                    .thumbnail(GlideApp.with(Search_ProductDetail.this).load(R.drawable.progressbar))
                                    .into(image);
                            viewFlipper.addView(image);

                        }

                    }else {
                        ImageView image = new ImageView(getApplicationContext());
                        GlideApp.with(Search_ProductDetail.this)
                                .load("https://firebasestorage.googleapis.com/v0/b/starcity-4d6e6.appspot.com/o/No_Image.png?alt=media&token=a99878fd-807c-4982-a156-7d118f2f7732")                                .apply(options)
                                .into(image);
                        viewFlipper.addView(image);
                    }
//                    loadingImages.setVisibility(View.GONE);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
