package com.example.appshopping.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appshopping.Class_Properties.Cart;
import com.example.appshopping.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder>{

    List<Cart> DsCart;
    Context context;
    public static int check = 0;
    ProcessCheckBox click ;
    public CartAdapter(List<Cart> dsCart, Context context, ProcessCheckBox click) {
        DsCart = dsCart;
        this.context = context;
        this.click = click;
    }
    public interface ProcessCheckBox
    {
        public void clickCheckBox(boolean checked, int position, int ktr);
    }
    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new CartHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, @SuppressLint("RecyclerView") int position) {
     Glide.with(context).load(DsCart.get(position).getImagecart()).into(holder.imageCart);
     holder.NameCart.setText(DsCart.get(position).getNamecart());
     holder.SLCart.setText(String.valueOf(DsCart.get(position).getSl()));
     Locale locale = new Locale("vi","VN");
     NumberFormat number = NumberFormat.getNumberInstance(locale);
     long money = Long.parseLong(DsCart.get(position).getMoney());
     holder.moneyCart.setText(number.format(money)+"đ");
     holder.sizeCart.setText(DsCart.get(position).getColor()+", "+DsCart.get(position).getSize());
//     if(holder.checkBoxCart.isChecked()== false) thay đổi giá trị mặc định thành true
//     Toast.makeText(context,position+"",Toast.LENGTH_SHORT).show();  các pisiotion đc lm mới
     holder.checkBoxCart.setChecked(check == DsCart.size());
     holder.checkBoxCart.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(holder.checkBoxCart.isChecked())
             ++ check;
             else
             {
             -- check;
             }
             click.clickCheckBox(holder.checkBoxCart.isChecked(),position, check);
         }
     });

    }

    @Override
    public int getItemCount() {
        if(DsCart != null)
        {
            return DsCart.size();
        }
        return 0;
    }

    public static class CartHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxCart;
        ImageView imageCart;
        TextView NameCart, sizeCart, moneyCart, addSLCart, SLCart, GiamSLCart;
        public CartHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxCart = itemView.findViewById(R.id.checkboxCart);
            imageCart = itemView.findViewById(R.id.ImageCart);
            NameCart = itemView.findViewById(R.id.NameCart);
            sizeCart = itemView.findViewById(R.id.sizeCart);
            moneyCart = itemView.findViewById(R.id.moneyCart);
            addSLCart = itemView.findViewById(R.id.addSLCart);
            GiamSLCart = itemView.findViewById(R.id.GiamSLCart);
            SLCart = itemView.findViewById(R.id.SLCart);
        }
    }
}
