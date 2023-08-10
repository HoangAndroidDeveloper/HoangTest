package com.example.appshopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appshopping.Class_Properties.SP;
import com.example.appshopping.Function_class;
import com.example.appshopping.R;
import com.example.appshopping.activity.ShoppingActivity;

import java.util.List;


public class SP_adapter extends RecyclerView.Adapter<SP_adapter.SpHolder> {

    Context context;
    List<SP> DsSP;

@SuppressLint("NotifyDataSetChanged")
public void  setData(List<SP> Ds)
{
    DsSP = Ds;
    notifyDataSetChanged();
}

    public SP_adapter(Context context, List<SP> dsSP) {
        this.context = context;
        DsSP = dsSP;
    }

    @NonNull
    @Override
    public SpHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sp,parent,false);
        return new SpHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(DsSP.get(position).getListimage().getImage().get(0)).centerCrop().into(holder.image_sp);
        holder.NameSp.setText(DsSP.get(position).getName());
        holder.countStar.setText(DsSP.get(position).getStar());
        long money = Long.parseLong(DsSP.get(position).getMoney());
        holder.money.setText(Function_class.FormatMoney(money));
        holder.click_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(context, ShoppingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SP",DsSP.get(position));
                it.putExtras(bundle);
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(DsSP.size() > 0)
        return DsSP.size();
        else
            return 0;
    }

    public class SpHolder extends RecyclerView.ViewHolder {
        ImageView image_sp;
        TextView NameSp, money, countStar;
        FrameLayout click_sp;
        public SpHolder(@NonNull View itemView) {
            super(itemView);
            image_sp = itemView.findViewById(R.id.image_sp);
            NameSp = itemView.findViewById(R.id.Name_sp);
            money = itemView.findViewById(R.id.Money_sp);
            countStar = itemView.findViewById(R.id.count_start);
            click_sp = itemView.findViewById(R.id.layout_click_sp);
        }
    }
}
