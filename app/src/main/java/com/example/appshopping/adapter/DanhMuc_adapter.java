package com.example.appshopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appshopping.R;

public class DanhMuc_adapter extends RecyclerView.Adapter<DanhMuc_adapter.DMHolder>{
    String [] DsName,DsDM;
    Context context;

    public DanhMuc_adapter(String[] dsName, String[] dsDM, Context context) {
        DsName = dsName;
        DsDM = dsDM;
        this.context = context;
    }

    @NonNull
    @Override
    public DMHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danh_muc,parent,false);
        return new DMHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DMHolder holder, int position) {
     holder.name.setText(DsName [position]);
        Glide.with(context).load(DsDM [position]).centerCrop().into(holder.image);
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public int getItemCount() {
        if(DsDM != null)
        return DsDM.length;
        else
        return 0;
    }

    public class DMHolder extends RecyclerView.ViewHolder {
        ImageView image ;
        TextView name;
        public DMHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_danhmuc);
            name = itemView.findViewById(R.id.name_danhmuc);
        }
    }
}
