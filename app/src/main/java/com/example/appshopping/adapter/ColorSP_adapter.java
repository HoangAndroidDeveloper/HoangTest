package com.example.appshopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appshopping.R;

import java.util.List;

public class ColorSP_adapter extends RecyclerView.Adapter<ColorSP_adapter.ColorHolder>
{
  List<String> DsImage, Name;
  Context context;
  sizeAdapter.sizeInterface iAdapter;
  public static int cCheck = -1;

    public ColorSP_adapter(List<String> dsImage, List<String> name, Context context, sizeAdapter.sizeInterface iAdapter) {
        DsImage = dsImage;
        Name = name;
        this.context = context;
        this.iAdapter = iAdapter;
    }

//    public interface I_adapter{
//      public void onClick(ColorSP_adapter ad, int position);
//  }
    @NonNull
    @Override
    public ColorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color_sp,parent,false);
        return new ColorHolder(view);
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onBindViewHolder(@NonNull ColorHolder holder, @SuppressLint("RecyclerView") int position) {
    Glide.with(context).load(DsImage.get(position)).into(holder.imageView);
    holder.NameSP.setText(Name.get(position));
        if(cCheck != position)
        {
            holder.NameSP.setTextColor(Color.BLACK);
            holder.ln.setBackgroundColor(context.getColor(R.color.colorSize));
            holder.NameSP.setBackgroundColor(context.getColor(R.color.colorSize));
            holder.imageView.setBackgroundColor(context.getColor(R.color.colorSize));
        }
        else {
            holder.NameSP.setTextColor(Color.RED);
            holder.NameSP.setBackgroundColor(Color.WHITE);
            holder.ln.setBackgroundColor(Color.RED);
            holder.imageView.setBackgroundColor(Color.WHITE);
        }
    holder.ln.setOnClickListener(v -> {
        if(cCheck == position)
        cCheck = -1;
        else
         cCheck = position;
        iAdapter.onClickColor(ColorSP_adapter.this,cCheck);
    });
    }

    @Override
    public int getItemCount() {
        if(Name != null)
         return Name.size();
        return 0;
    }

    public static class ColorHolder extends RecyclerView.ViewHolder {
        ImageView imageView ;
        TextView NameSP;
        LinearLayout ln;
        public ColorHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_category);
            NameSP = itemView.findViewById(R.id.NameSpBottom);
            ln = itemView.findViewById(R.id.stroke_category);
        }
    }
}
