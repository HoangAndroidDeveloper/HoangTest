package com.example.appshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appshopping.R;


public class slide_image_Home extends RecyclerView.Adapter<slide_image_Home.slideHolder>{
    String [] Ds_item;
    Context context;
    public slide_image_Home(String[] ds_item,Context context) {
        Ds_item = ds_item;
        this.context = context;
    }

    @NonNull
    @Override
    public slideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide_home,parent,false);
        return new slideHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull slideHolder holder, int position) {
        Glide.with(context).load(Ds_item[position]).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return Ds_item.length;
    }

    public static class slideHolder extends RecyclerView.ViewHolder
{
ImageView image;
    public slideHolder(@NonNull View itemView) {

        super(itemView);
        image = itemView.findViewById(R.id.item_image);

    }
}
}
