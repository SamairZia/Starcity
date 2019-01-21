package com.example.zbt.starcity.Dashboard;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.zbt.starcity.Adapter.StoreFinderAdapter;
import com.example.zbt.starcity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StoreFinder extends AppCompatActivity {

    ArrayList<String> storeDetailList;
    ArrayList<ArrayList<String>> storeList;
    StoreFinderAdapter storeFinderAdapter;
    RecyclerView recyclerView;
//    TextView tv_alphabetStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_finder);

//        tv_alphabetStore = findViewById(R.id.tv_alphabetStore);

        recyclerView = findViewById(R.id.recyclerViewStoreFinder);
        storeFinderAdapter = new StoreFinderAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(storeFinderAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query queryUser = reference.child("Users").orderByChild("Shop Details/Shop Name");

        storeList = new ArrayList<>();
        queryUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot user: dataSnapshot.getChildren()){
                        DataSnapshot shopInfo = user.child("Shop Details");
                        storeDetailList = new ArrayList<>();
                        if(shopInfo.child("Shop Name").exists()){
                            String alpha[] = shopInfo.child("Shop Name").getValue().toString().toUpperCase().split("(?!^)");
                            Log.d("TAGPop", alpha[0]);
                            storeDetailList.add((alpha[0]));
//                            tv_alphabetStore.setText(alpha[0]);
                            storeDetailList.add(shopInfo.child("Shop Name").getValue().toString());
                        }
                        else
                            storeDetailList.add("No Name");
                        if(shopInfo.child("Rep Name").exists())
                            storeDetailList.add(shopInfo.child("Rep Name").getValue().toString());
                        else
                            storeDetailList.add("No Representative Found");
                        if(shopInfo.child("Shop Number").exists())
                            storeDetailList.add(shopInfo.child("Shop Number").getValue().toString());
                        else
                            storeDetailList.add("-");
                        if(shopInfo.child("Shop Floor").exists())
                            storeDetailList.add(shopInfo.child("Shop Floor").getValue().toString());
                        else
                            storeDetailList.add("-");
                        if(shopInfo.child("Contact Number").exists())
                            storeDetailList.add(shopInfo.child("Contact Number").getValue().toString());
                        else
                            storeDetailList.add("-");
                        if(shopInfo.child("Email").exists())
                            storeDetailList.add(shopInfo.child("Email").getValue().toString());
                        else
                            storeDetailList.add("-");
                        if(shopInfo.child("Shop Type").exists())
                            storeDetailList.add(shopInfo.child("Shop Type").getValue().toString());
                        else
                            storeDetailList.add("-");

                        storeList.add(storeDetailList);
                    }
                    if(storeList.isEmpty())
                        Toast.makeText(getApplicationContext(), "No Store Found", Toast.LENGTH_LONG).show();
                    else{
                        Collections.sort(storeList, new Comparator<ArrayList<String>>() {
                            @Override
                            public int compare(ArrayList<String> a, ArrayList<String> b) {
                                return a.get(1).toLowerCase().compareTo(b.get(1).toLowerCase());
                            }
                        });

                        storeFinderAdapter.addStore(storeList);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
