package com.example.samair.starcity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProductsAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<ArrayList<String>> list = new ArrayList<>();

    private Context context;
    StorageReference storageReference;

    public ProductsAdaper(Context context){
        this.context = context;
    }

    public void addProduct(ArrayList<ArrayList<String>> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.products_single, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((Holder) holder).tv_title.setText(list.get(position).get(0));
        ((Holder) holder).tv_model.setText(list.get(position).get(1));
        ((Holder) holder).tv_type.setText(list.get(position).get(2));

        // Reference to an image file in Cloud Storage

        storageReference = FirebaseStorage.getInstance().getReference().child("images/"+list.get(position).get(3)+"/"+list.get(position).get(4));

//        for (StorageReference reference: storageReference.get)
//        GlideApp.with(context /* context */)
//                .load(storageReference)
//                .into(((Holder) holder).iv);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv_title;
        TextView tv_type;
        TextView tv_model;
        CardView cardView;

        Holder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_model = itemView.findViewById(R.id.tv_model);
            cardView = itemView.findViewById(R.id.productCardView);
        }
    }
}
