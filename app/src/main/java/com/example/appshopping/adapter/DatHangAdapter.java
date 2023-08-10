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
import com.example.appshopping.Class_Properties.Cart;
import com.example.appshopping.Function_class;
import com.example.appshopping.R;

import java.util.List;

public class DatHangAdapter extends RecyclerView.Adapter<DatHangAdapter.DHHolder>{

    List<Cart> DsCart;
    Context context;
    public DatHangAdapter(List<Cart> dsCart, Context context) {
        DsCart = dsCart;
        this.context = context;
    }

    @NonNull
    @Override
    public DHHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mua,parent,false);
        return new DHHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DHHolder holder, int position) {
        Cart cart = DsCart.get(position);
        long money = Long.parseLong(cart.getMoney());
        int sl = cart.getSl();
        holder.tongSL.setText(holder.tongSL.getText().toString()
        +" ("+sl+" sản phẩm): ");
        holder.name.setText(cart.getNamecart());
        holder.category.setText(cart.getColor()+", "
                +cart.getSize());
        holder.moneySP.setText(Function_class.FormatMoney(money));
        Glide.with(context).load(cart.getImagecart()).centerCrop().into(holder.image);
        long sMoney = money * sl;
        holder.sumMoney.setText(Function_class.FormatMoney(sMoney));
    }

    @Override
    public int getItemCount() {
        if (DsCart != null)
         return DsCart.size();
        return 0;
    }

    public static class  DHHolder extends RecyclerView.ViewHolder {
        TextView tongSL, sumMoney, name, category, moneySP;
        ImageView image;
        public DHHolder(@NonNull View itemView) {
            super(itemView);
            tongSL = itemView.findViewById(R.id.soLuong_spMua);
            moneySP = itemView.findViewById(R.id.money_spMua);
            sumMoney = itemView.findViewById(R.id.sumMoneyMua);
            image = itemView.findViewById(R.id.image_Mua);
            name = itemView.findViewById(R.id.name_spMua);
            category = itemView.findViewById(R.id.category_spMua);
        }
    }
}
