package com.example.appshopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appshopping.MainActivity;
import com.example.appshopping.R;
import com.example.appshopping.adapter.CityAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectLocationActivity extends AppCompatActivity {
TabLayout tabLayout;
RecyclerView rCity;
List<City> DsCity, DsQuan, DsXa;
CityAdapter adapter;
LinearLayout bt_getCurrentLocation;
public static class City
{
    int id;
    String name;
    int id2;

    public City() {
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public City(int id, String name, int id2) {
        this.id = id;
        this.name = name;
        this.id2 = id2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
    public static int idC = -1, idQ = -1, idX = -1; // dùng biến này để kiểm tra xem những quận thuộc tỉnh đã chọn
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        AnhXa();
        createData();
        getCity();
        tabLayout.addTab(tabLayout.newTab().setText("Thành Phố"));
        tabLayout.addTab(tabLayout.newTab().setText("Quận/ Huyện"));
        tabLayout.addTab(tabLayout.newTab().setText("Phường/ Xã"));
        CityAdapter.Location location = new CityAdapter.Location() {
            @Override
            public void back() {
               onBackPressed();
            }
        };
         adapter = new CityAdapter(DsCity, this,tabLayout, location);
        rCity.setLayoutManager(new LinearLayoutManager(this));
        rCity.setAdapter(adapter);
        tabLayout.setSelected(false);
        rCity.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int i = tab.getPosition();
                    if(i <= CityAdapter.checkTab)
                    {
                        CityAdapter.checkTab = tab.getPosition();
                        setData(i);
                    }
                    else {
                        tabLayout.selectTab(tabLayout.getTabAt(CityAdapter.checkTab));
                        setData(CityAdapter.checkTab);
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            // nhấn vào bt get Current Location
        bt_getCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                TedPermission.create().setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        getMyLocation();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }
                }).setPermissions(Manifest.permission.ACCESS_FINE_LOCATION).check();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getMyLocation()
    {
        FusedLocationProviderClient CliProviderClient = LocationServices
                .getFusedLocationProviderClient(this);
        CliProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
               if(location != null)
               {
                   Geocoder geocoder = new Geocoder(SelectLocationActivity.this);
                   try {
                       List<Address> addresses = geocoder.getFromLocation(location.getLatitude()
                       ,location.getLongitude(),1);
                       String s = addresses.get(0).getAddressLine(0);
                       String xa [] = s.split(",");
                       CityAdapter.nCity = addresses.get(0).getAdminArea();
                       CityAdapter.nQuan = addresses.get(0).getSubAdminArea();
                       CityAdapter.nXa = xa[1];
                       onBackPressed();

                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               }
            }
        });
    }

    private void setData(int position) {
        List<City> list = new ArrayList<>();
        switch (position)
        {
            case 0:
                adapter.setData(DsCity);
                break;
            case 1:
                for(City city : DsQuan)
                {
                    if(city.id2 == idC)
                    {
                        list.add(city);
                    }

                }
                adapter.setData(list);
                break;
            case 2:
                for(City city : DsXa)
                {
                    if(city.id2 == idQ)
                    {
                        list.add(city);
                    }
                }
                adapter.setData(list);
                break;
        }
    }

    private void AnhXa() {
        tabLayout = findViewById(R.id.tab_City);
        rCity = findViewById(R.id.rDsCity);
        bt_getCurrentLocation = findViewById(R.id.getMyLocation);
    }

    @Override
    public void onBackPressed() {

        setResult(Activity.RESULT_OK, new Intent());
        finish();
        super.onBackPressed();
        Animatoo.INSTANCE.animateFade(this);
    }
    private void createData()
    {
        DsCity = new ArrayList<>();
        String [] l1  = getResources().getStringArray(R.array.DsCity);

         for(int i = 0;i<l1.length;i++)
         {
            City city = new City(i,l1[i],0);
            DsCity.add(city);
         }


    }
    private void getCity()
    {
        DsQuan = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("DsLocation/DsQuan");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn : snapshot.getChildren())
                {
                    DsQuan.add(sn.getValue(City.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DsXa = new ArrayList<>();
      DatabaseReference reference1 = FirebaseDatabase.getInstance()
              .getReference("DsLocation/DsXa");
      reference1.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              for(DataSnapshot sn : snapshot.getChildren())
              {
                  DsXa.add(sn.getValue(City.class));
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });
    }

}