package com.example.appshopping.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appshopping.Class_Properties.Order;
import com.example.appshopping.MainActivity;
import com.example.appshopping.R;
import com.example.appshopping.adapter.OrderAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
public class OrderFragment extends Fragment
{
    public OrderFragment() {
    }
    View view ;
    TabLayout tabLayout;
    OrderAdapter orderAdapter ;
    List<Order> DsOrder, DsOrderCancel;
    RecyclerView rOrder;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_order,container, false);
        AnhXa();
        LinearLayoutManager ln = new LinearLayoutManager(view.getContext());
        tabLayout.addTab(tabLayout.newTab().setText("Chờ xác nhận"));
        tabLayout.addTab(tabLayout.newTab().setText("Chờ giao hàng"));
        tabLayout.addTab(tabLayout.newTab().setText("Đang giao hàng"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã giao"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã hủy"));
        rOrder.setLayoutManager(ln);
        orderAdapter = new OrderAdapter(DsOrder, view.getContext());
        rOrder.setAdapter(orderAdapter);
        getDsOrder();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition())
                {
                    case 0:
                        orderAdapter.setData(DsOrder);
                        break;
                    case 4:
                        orderAdapter.setData(DsOrderCancel);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
    private void AnhXa()
    {
        DsOrder = new ArrayList<>();
        tabLayout = view.findViewById(R.id.tabLayout_State_Order);
        rOrder = view.findViewById(R.id.rOrder);
    }
    private void getDsOrder()
    {
        DsOrderCancel = new ArrayList<>();
        DatabaseReference getOrder = FirebaseDatabase.getInstance()
                .getReference("DsOrder");
        Query query = getOrder.orderByChild("iduser")
                .equalTo(MainActivity.user.getId());

        query.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
              Order order = snapshot.getValue(Order.class);
              String state = order.getState();
              if(state.equals("Đang chờ xác nhận"))
               DsOrder.add(snapshot.getValue(Order.class));
              else
              if(state.equals("Đã được hủy bởi bạn"))
                  DsOrderCancel.add(order);
              orderAdapter.setData(DsOrder);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName)
            {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
