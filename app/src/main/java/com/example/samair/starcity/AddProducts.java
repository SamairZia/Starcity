package com.example.samair.starcity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

public class AddProducts extends AppCompatActivity {

    private Button btn_saveProduct, btn_chooseImage;
    private EditText name_addproduct, description_addproduct;
    private Spinner spinnerProductType, spinnerManufacturer;
    private String user_UID;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
    private ArrayList<String> productImagesArray;

    private ProgressDialog mProgressDialog;

    String productImage = "";
    String productType[] = {"New", "Used", "Refurbished"};
    String manufacturers[] = {"Samsung", "Apple", "LG", "Motorolla"};

    String productTypeString, manufacturer;

    ArrayAdapter<String> productTypeAdapter, manufacturerAdapter;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mDBref;
    private StorageReference mStorageRef;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        mProgressDialog = new ProgressDialog(AddProducts.this);

        btn_saveProduct = findViewById(R.id.btn_saveProduct);
        btn_chooseImage = findViewById(R.id.btn_chooseImage);
        name_addproduct = findViewById(R.id.name_addproduct);
        description_addproduct = findViewById(R.id.description_addproduct);

        imageView1 = findViewById(R.id.image_product1);
        imageView2 = findViewById(R.id.image_product2);
        imageView3 = findViewById(R.id.image_product3);
        imageView4 = findViewById(R.id.image_product4);
        imageView5 = findViewById(R.id.image_product5);

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

        mStorageRef = FirebaseStorage.getInstance().getReference();

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

                mProgressDialog.setMessage("Adding product...");
                mProgressDialog.show();

                mDBref.child(uidNode).child("Products").push().setValue(saveProductMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        String productKey = databaseReference.getKey();

                        for(int i=0; i<productImagesArray.size(); i++){
                            mProgressDialog.setMessage("Uploading images...");
                            Uri uri = Uri.fromFile(new File(productImagesArray.get(i)));
                            StorageReference storageReference = mStorageRef.child("images/" + uidNode + "/" + productKey + "/" +System.currentTimeMillis()/1000  + i +".jpg");

                            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
                                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                        }
                        name_addproduct.setText("");
                        description_addproduct.setText("");
                        Toast.makeText(getApplicationContext() , "Product Added Successfully" , Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                });


                        /*.addOnCompleteListener (new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task, DatabaseReference databaseReference) {
                        String productKey;
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext() , "Product Added Successfully" , Toast.LENGTH_LONG).show();
                            name_addproduct.setText("");
                            description_addproduct.setText("");
                            productKey = databaseReference.getKey();

                        }
                    }
                });*/
            }
        });

        btn_chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pix.start(AddProducts.this, 100, 5);
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("val", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        switch (requestCode) {
            case (100): {
                if (resultCode == Activity.RESULT_OK) {
                    productImagesArray = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                    for (int i=0; i<productImagesArray.size(); i++) {
                        String s = productImagesArray.get(i);
                        Log.e("valImage", " ->  " + s);
                        Uri uri = Uri.fromFile(new File(productImagesArray.get(i)));
                        try {
                            if(i+1 == 1){
                                imageView1.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
                                imageView1.setVisibility(View.VISIBLE);
                            }
                            else if(i+1 == 2){
                                imageView2.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
                                imageView2.setVisibility(View.VISIBLE);
                            }
                            else if(i+1 == 3){
                                imageView3.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
                                imageView3.setVisibility(View.VISIBLE);
                            }
                            else if(i+1 == 4){
                                imageView4.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
                                imageView4.setVisibility(View.VISIBLE);
                            }
                            else if(i+1 == 5){
                                imageView5.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
                                imageView5.setVisibility(View.VISIBLE);
                            }
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(AddProducts.this, 100, 5);
                } else {
                    Toast.makeText(AddProducts.this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }



}
