package com.example.appshopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appshopping.R;
import com.example.appshopping.activity.SelectLocationActivity;
import com.google.android.material.tabs.TabLayout;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.security.SecureRandom;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityHolder>
    implements StickyRecyclerHeadersAdapter<CityAdapter.HeaderHolder>
{
    List<SelectLocationActivity.City> DsCity;
    Context context;
    public  static  String nCity, nQuan, nXa; // lưu thông tin quận, huyện, xã
    TabLayout tabLayout;
    Location location; // dùng để xử lý sự kiện khi click vào item xã để quay trở về activity AddLocation
    public interface Location
    {
        public void back();
    }
    public static int checkTab = 0; // dùng để phân biệt đang chọn tab nào
@SuppressLint("NotifyDataSetChanged")
public void setData(List<SelectLocationActivity.City> Ds)
{
    DsCity = Ds;
    notifyDataSetChanged();
}

    public CityAdapter(List<SelectLocationActivity.City> dsCity, Context context, TabLayout tabLayout, Location location) {
        DsCity = dsCity;
        this.context = context;
        this.tabLayout = tabLayout;
        this.location = location;
    }

    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city,parent,false);
        return new CityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityHolder holder, @SuppressLint("RecyclerView") int position) {

     String name = DsCity.get(position).getName();
     holder.nameCity.setText(name);
     holder.nameCity.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             // kiểm tra xem đang nằm ở tab nào
             if(checkTab == 0) {
                 checkTab = 1;
                 nCity = name;
                 SelectLocationActivity.idC = position;
                 tabLayout.selectTab(tabLayout.getTabAt(1));
             }
             else
             if(checkTab == 1) {
                 SelectLocationActivity.idQ = DsCity.get(position).getId();
                 checkTab = 2;
                 nQuan = name;
                 tabLayout.selectTab(tabLayout.getTabAt(2));
             }
             else
             if(checkTab == 2)
             {
                checkTab = 0;
                nXa = name;
                location.back();

             }

         }
     });
    }

    @Override
    public long getHeaderId(int position) {

        return DsCity.get(position).getName().charAt(0);
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_location,parent,false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder headerHolder, int i) {
        String H = DsCity.get(i).getName().substring(0,1);
        headerHolder.header.setText(H);
        headerHolder.header.setBackgroundColor(randomColor());
    }

    @Override
    public int getItemCount() {
        if(DsCity != null)
            return DsCity.size();
        return 0;
    }

    public static class CityHolder extends RecyclerView.ViewHolder
    {
     TextView nameCity;
        public CityHolder(@NonNull View itemView) {
            super(itemView);
            nameCity = itemView.findViewById(R.id.nameCity_item);
        }
    }
    public static class HeaderHolder extends RecyclerView.ViewHolder {
        TextView header;
        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
        }
    }
   public int randomColor() // random màu nền của header
   {
      SecureRandom src = new SecureRandom();
      return Color.HSVToColor(150, new float[]{
              src.nextInt(359),1,1
      });

   }
}
