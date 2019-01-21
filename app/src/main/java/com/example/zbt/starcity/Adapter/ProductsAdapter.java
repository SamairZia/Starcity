package com.example.zbt.starcity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.zbt.starcity.Library.GlideApp;
import com.example.zbt.starcity.Dashboard.Search_ProductDetail;
import com.example.zbt.starcity.R;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<ArrayList<String>> list = new ArrayList<>();

    private Context context;

    public ProductsAdapter(Context context){
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
                inflate(R.layout.recycler_view_product, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((Holder) holder).tv_title.setText(list.get(position).get(0));
        ((Holder) holder).tv_model.setText(list.get(position).get(1));
        ((Holder) holder).tv_type.setText(list.get(position).get(2));

        final String uID = list.get(position).get(3);
        final String productID = list.get(position).get(4);

        // Reference to an image file directly loading

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progressbar)
                .error(R.drawable.noimage);

        GlideApp.with(context /* context */)
                .load(list.get(position).get(5))
                .centerCrop()
                .thumbnail(GlideApp.with(context).load(R.drawable.progressbar))
                .into(((Holder) holder).iv);

        ((Holder) holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(context, Search_ProductDetail.class);
                myIntent.putExtra("uID", uID);
                myIntent.putExtra("productID", productID);
                context.startActivity(myIntent);
            }
        });

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
