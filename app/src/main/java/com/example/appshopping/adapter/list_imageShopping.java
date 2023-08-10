package com.example.appshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appshopping.Class_Properties.propertiesImage_SP;
import com.example.appshopping.R;

import java.util.List;

public class list_imageShopping extends RecyclerView.Adapter<list_imageShopping.shopHolder>{

    List<String> list_image;
    Context context;

    public list_imageShopping(List<String> list_image, Context context) {
        this.list_image = list_image;
        this.context = context;
    }

    @NonNull
    @Override
    public shopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imgae_shop,parent,false);
        return new shopHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull shopHolder holder, int position) {
        Glide.with(context).load(list_image.get(position)).centerCrop().into(holder.imageView);
        holder.sumImage.setText((position+1)+"/"+list_image.size());
    }

    @Override
    public int getItemCount() {
        if (list_image != null)
        return list_image.size();
        else
            return 0;
    }

    public class shopHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView sumImage;
        public shopHolder(@NonNull View itemView) {
            super(itemView);
            sumImage = itemView.findViewById(R.id.Sum_image);
            imageView = itemView.findViewById(R.id.item_image_shop);
        }
    }
}
