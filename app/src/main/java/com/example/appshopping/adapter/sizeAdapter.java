package com.example.appshopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshopping.R;

import java.util.List;

public class sizeAdapter extends RecyclerView.Adapter<sizeAdapter.SizeHolder>{

    List<String> DsSize;
    Context context;
    sizeInterface SInterface;
    public static int nClick = -1;
    public interface sizeInterface{
        public void onClick(sizeAdapter ad,int position);
        public void onClickColor(ColorSP_adapter ad, int position);
    }

    public sizeAdapter(List<String> dsSize, Context context, sizeInterface SInterface) {
        DsSize = dsSize;
        this.context = context;
        this.SInterface = SInterface;
    }

    @NonNull
    @Override
    public SizeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_size,parent,false);
        return new SizeHolder(view);
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onBindViewHolder(@NonNull SizeHolder holder, @SuppressLint("RecyclerView") int position) {
     holder.sizeSP.setText(DsSize.get(position));
     if(nClick == position) {
         holder.sizeSP.setTextColor(context.getColor(R.color.Red));
         holder.faFrameLayout.setBackgroundColor(Color.RED);
         holder.sizeSP.setBackgroundColor(Color.WHITE);
     }
     else {
         holder.faFrameLayout.setBackgroundColor(context.getColor(R.color.colorSize));
         holder.sizeSP.setTextColor(Color.BLACK);
         holder.sizeSP.setBackgroundColor(context.getColor(R.color.colorSize));
     }
     holder.sizeSP.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             if(SInterface != null) {
                 if (nClick == position) {
                     nClick = -1;
                     SInterface.onClick( sizeAdapter.this,-1);

                 }
                 else {
                     nClick = position;
                     SInterface.onClick( sizeAdapter.this,position);

                 }
             }
         }
     });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (DsSize != null)
         return DsSize.size();
        return 0;

    }

    public class SizeHolder extends RecyclerView.ViewHolder
    {
        FrameLayout faFrameLayout;
        TextView sizeSP;
        public SizeHolder(@NonNull View itemView) {
            super(itemView);
            sizeSP = itemView.findViewById(R.id.text_size);
            faFrameLayout = itemView.findViewById(R.id.stroke_size);
        }
    }
}
