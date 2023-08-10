package com.example.appshopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshopping.Class_Properties.MyLocation;
import com.example.appshopping.MainActivity;
import com.example.appshopping.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LHolder>
{
  List<MyLocation> DsLocation;
  Context context;
  public static int index = -1;

    public LocationAdapter(List<MyLocation> dsLocation, Context context) {
        DsLocation = dsLocation;
        this.context = context;
    }
 @SuppressLint("NotifyDataSetChanged")
 public void setData(List<MyLocation> Ds)
 {
     DsLocation = Ds;
     notifyDataSetChanged();
 }
    @NonNull
    @Override
    public LHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location, parent,false);
        return new LHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LHolder holder, @SuppressLint("RecyclerView") int position) {
        String city, quan, xa, DiaChi;
        city = DsLocation.get(position).getCity();
        quan = DsLocation.get(position).getQuan();
        xa   = DsLocation.get(position).getXa();
        DiaChi = DsLocation.get(position).getDiachi();
        String category = DsLocation.get(position).getCategory();
        holder.DiaChi.setText(city + ", " + quan +", " + xa + ", "+DiaChi);
        holder.sdt.setText(DsLocation.get(position).getSdt());
        holder.nameKH.setText(DsLocation.get(position).getNamekh());
        holder.radio.setChecked(index == position);
        holder.category.setText(category);
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMenu(view, position);
            }

        });
        holder.radio.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                index = position;
                notifyDataSetChanged();
            }
        });
    }

    private void ShowMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu_setting_location);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                DatabaseReference reference = FirebaseDatabase.getInstance()
                        .getReference("DeliveryAddress/" + MainActivity.user.getId());
                switch (menuItem.getItemId())
                {
                    case R.id.m_Delete:
                        DsLocation.remove(position);
                        reference.setValue(DsLocation);
                        return true;
                    case R.id.m_selectD:
                        int d = DsLocation.get(position).getLcdefault(); // dùng để kiểm tra xem đã là mặc định chưa
                        if(d != 1)
                        {
                            for (MyLocation location : DsLocation) {
                              if (location.getLcdefault() == 1) {
                                location.setLcdefault(0);
                                  break;
                              }
                            }
                            DsLocation.get(position).setLcdefault(1);
                            reference.setValue(DsLocation);
                        }
                       return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(DsLocation != null)
            return DsLocation.size();
        return 0;
    }

    public static class LHolder extends RecyclerView.ViewHolder
    {
       TextView DiaChi, sdt, nameKH, textDefault, category;
       ImageView iconDefault, menu;
       RadioButton radio;
        public LHolder(@NonNull View itemView) {
            super(itemView);
            DiaChi = itemView.findViewById(R.id.NameCity);
            nameKH = itemView.findViewById(R.id.nameKhachHang);
            sdt = itemView.findViewById(R.id.phoneNumber);
            radio = itemView.findViewById(R.id.radio_select);
            menu = itemView.findViewById(R.id.menu_location);
            iconDefault = itemView.findViewById(R.id.iconDefault);
            textDefault = itemView.findViewById(R.id.textDefault);
            category = itemView.findViewById(R.id.text_2);
        }
    }

}
