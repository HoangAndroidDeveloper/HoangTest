package com.example.appshopping.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appshopping.Class_Properties.MyLocation;
import com.example.appshopping.MainActivity;
import com.example.appshopping.R;
import com.example.appshopping.adapter.LocationAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DsLocationActivity extends AppCompatActivity {
    TextView bt_add;
    Button bt_selectLocation;
    RecyclerView rDsLocation;
    LocationAdapter adapter;
    public  static  List<MyLocation> DsLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_location);
        AnhXa();
        adapter = new LocationAdapter(DsLocation,this);
        rDsLocation.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rDsLocation.setAdapter(adapter);
        getLocation();
        bt_add.setOnClickListener(new View.OnClickListener() { // start activity
            @Override
            public void onClick(View view) {
                Intent it = new Intent(DsLocationActivity.this,AddLocationActivity.class);
                startActivity(it);
                Animatoo.INSTANCE.animateFade(DsLocationActivity.this);
            }
        });
        bt_selectLocation.setOnClickListener(new View.OnClickListener() // chọn địa chỉ nhận hàng
        {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Location",DsLocation.get(LocationAdapter.index));
                it.putExtras(bundle);
                setResult(Activity.RESULT_OK,it);
                finish();
            }
        });
    }

    private void AnhXa() {
        rDsLocation = findViewById(R.id.rDsLocation);
        bt_add = findViewById(R.id.bt_addLocation);
        bt_selectLocation = findViewById(R.id.bt_selectLocation);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void getLocation() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_loading_dialog);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        DsLocation = new ArrayList<>();

                DatabaseReference getLocation = FirebaseDatabase.getInstance()
                        .getReference("DeliveryAddress/" + MainActivity.user.getId());
                getLocation.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DsLocation.clear();
                        for(DataSnapshot sn : snapshot.getChildren()) {
                            DsLocation.add(sn.getValue(MyLocation.class));
                        }
                        dialog.dismiss();
                        adapter.setData(DsLocation);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
