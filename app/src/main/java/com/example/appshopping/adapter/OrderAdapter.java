package com.example.appshopping.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appshopping.Class_Properties.Order;
import com.example.appshopping.Function_class;
import com.example.appshopping.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OderHolder> {
    List<Order> DsOrder;
    Context context;

    public OrderAdapter(List<Order> dsOrder, Context context) {
        DsOrder = dsOrder;
        this.context = context;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Order> dsOrder)
    {
        DsOrder = dsOrder;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent
                , false);
        return new OderHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OderHolder holder, int position)
    {
        Order order = DsOrder.get(position);
        int sl = order.getSl();
        String state = order.getState();
        long money = Long.parseLong(order.getMoney());
        long sumMoney = money * sl + 20000;
        String size = order.getSize();
        String color = order.getColor();
        Glide.with(context).load(order.getImagecart()).into(holder.image);
        holder.nameOrder.setText(order.getNamecart());
        holder.ColorAndSize.setText(size +", "+ color);
        holder.money1.setText(Function_class.FormatMoney(money) +"đ");
        holder.moneyTong.setText(Function_class.FormatMoney(sumMoney)+"đ");
        holder.SL.setText(sl+" sản phẩm");
        holder.stateOrder.setText(order.getState());
        if(state.equals("Đã được hủy bởi bạn"))
        {
            holder.bt_cancel.setText("Chi tiết hủy đơn");
        }
        else
        {
            holder.bt_cancel.setText("Hủy đơn");
        }
        holder.bt_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                order.setState("Đã hủy");
                DatabaseReference editOrder = FirebaseDatabase.getInstance()
                        .getReference("DsOrder/"+order.getIdorder());
                editOrder.setValue(order);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        if(DsOrder != null)
            return DsOrder.size();
        return 0;
    }

    public static class OderHolder extends RecyclerView.ViewHolder {
       ImageView image;
       TextView nameOrder, ColorAndSize, SL, money1, moneyTong, stateOrder;
       Button bt_cancel;
        public OderHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_order);
            nameOrder = itemView.findViewById(R.id.name_order);
            ColorAndSize = itemView.findViewById(R.id.colorAndSize);
            SL = itemView.findViewById(R.id.SLOrder);
            money1 = itemView.findViewById(R.id.money1);
            moneyTong = itemView.findViewById(R.id.SumMoneyOrder);
            bt_cancel = itemView.findViewById(R.id.bt_Cancel);
            stateOrder = itemView.findViewById(R.id.state_order);
        }
    }
}
