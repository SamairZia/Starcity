package com.example.samair.starcity.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.GenericTransitionOptions;
import com.example.samair.starcity.Library.GlideApp;
import com.example.samair.starcity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class VerifyAuth extends AppCompatActivity {

    private String keyPin = null;
    private EditText textVerificationNumber;
    private ImageView imageVerify;
    private AppCompatButton btnNext;
    private int colorSuccess, colorOriginal;
    private Boolean imageVerifyHasChanged, btnColorHasChanged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_auth);
        textVerificationNumber = findViewById(R.id.et_verify);
        imageVerify = findViewById(R.id.image_verify);
        btnNext = findViewById(R.id.brnNextVerify);
        colorSuccess = ResourcesCompat.getColor(getResources(), R.color.colorBlueLogo, null);
        colorOriginal = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);

        imageVerifyHasChanged = false;
        btnColorHasChanged = false;


        textVerificationNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                final Boolean[] haveKey = { false };
                if(btnColorHasChanged){
                    btnNext.getBackground().setColorFilter(colorOriginal, PorterDuff.Mode.MULTIPLY);
                    btnNext.setEnabled(false);
                }

                if(textVerificationNumber.length()==12){

                    // Check if no view has focus:
                    View view = VerifyAuth.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }

                    imageVerifyHasChanged = true;
                    GlideApp.with(VerifyAuth.this)
                            .load(R.drawable.progressbar)
                            .into(imageVerify);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    Query queryVerification = reference.child("Verify");

                    queryVerification.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                                    String keyIsUsed = snapshot.child("used").getValue().toString();
                                    if(textVerificationNumber.getText().toString().equals(snapshot.child("pin").getValue().toString())){
                                        if(keyIsUsed.equals("0")){
                                            imageVerifyHasChanged = true;
                                            haveKey[0] = true;
                                            GlideApp.with(VerifyAuth.this)
                                                    .load(getResources().getDrawable(R.drawable.success))
                                                    .transition(GenericTransitionOptions.<Drawable>with(android.R.anim.fade_in))
                                                    .into(imageVerify);

                                            btnColorHasChanged = true;
                                            btnNext.getBackground().setColorFilter(colorSuccess, PorterDuff.Mode.MULTIPLY);
                                            btnNext.setEnabled(true);
                                            keyPin = snapshot.getKey();
                                        }
                                        else{
                                            haveKey[0] = true;
                                            Toast.makeText(VerifyAuth.this, "Code is used. Contact Admin.", Toast.LENGTH_LONG).show();
                                            imageVerifyHasChanged = false;
                                            GlideApp.with(VerifyAuth.this)
                                                    .load(getResources().getDrawable(R.drawable.warning))
                                                    .transition(GenericTransitionOptions.<Drawable>with(android.R.anim.fade_in))
                                                    .into(imageVerify);
                                        }
                                        break;
                                    }
                                }
                            }
                            if(!haveKey[0]){
                                imageVerifyHasChanged = true;
                                GlideApp.with(VerifyAuth.this)
                                        .load(getResources().getDrawable(R.drawable.error))
                                        .transition(GenericTransitionOptions.<Drawable>with(android.R.anim.fade_in))
                                        .into(imageVerify);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else if(textVerificationNumber.length()<12 && imageVerifyHasChanged){
                    imageVerifyHasChanged = false;
                    GlideApp.with(VerifyAuth.this)
                            .load(getResources().getDrawable(R.drawable.circle_outline))
                            .into(imageVerify);
                }
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignupPage();
                textVerificationNumber.setText("");
            }
        });
    }
    public void openSignupPage(){
        Intent intent = new Intent(VerifyAuth.this, Signup.class);
        intent.putExtra("keyPin", keyPin);
        startActivity(intent);
    }

}
