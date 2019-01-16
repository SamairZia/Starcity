package com.example.samair.starcity.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samair.starcity.R;

import java.util.ArrayList;

public class StoreFinderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ArrayList<String>> list = new ArrayList<>();
    private Context context;

    public StoreFinderAdapter(Context context){
        this.context = context;
    }

    public void addStore(ArrayList<ArrayList<String>> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recycler_view_store_finder, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

//        ((Holder) holder).tv_alphabetStore.setText(list.get(position).get(0));

        ((Holder) holder).tv_shopNameStore.setText(list.get(position).get(1));
        ((Holder) holder).tv_shopRepNameStore.setText(list.get(position).get(2));
        ((Holder) holder).tv_shopNumberStore.setText(list.get(position).get(3));
        ((Holder) holder).tv_shopFloorStore.setText(list.get(position).get(4));
        ((Holder) holder).tv_shopEmailStore.setText(list.get(position).get(5));
        ((Holder) holder).tv_shopContactStore.setText(list.get(position).get(6));
        ((Holder) holder).tv_shopTypeStore.setText(list.get(position).get(7));

        Log.d("TAGShop", list.get(position).get(1));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView tv_shopNameStore;
        TextView tv_shopRepNameStore;
        TextView tv_shopFloorStore;
        TextView tv_shopNumberStore;
        TextView tv_shopEmailStore;
        TextView tv_shopContactStore;
        TextView tv_shopTypeStore;

        Holder(View itemView) {
            super(itemView);

            tv_shopNameStore = itemView.findViewById(R.id.tv_shopNameStore);
            tv_shopRepNameStore = itemView.findViewById(R.id.tv_shopRepNameStore);
            tv_shopNumberStore = itemView.findViewById(R.id.tv_shopNumberStore);
            tv_shopFloorStore = itemView.findViewById(R.id.tv_shopFloorStore);
            tv_shopEmailStore = itemView.findViewById(R.id.tv_shopEmailStore);
            tv_shopContactStore = itemView.findViewById(R.id.tv_shopContactStore);
            tv_shopTypeStore = itemView.findViewById(R.id.tv_shopTypeStore);

        }
    }
}
