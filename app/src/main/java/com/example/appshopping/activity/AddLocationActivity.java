package com.example.appshopping.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appshopping.Class_Properties.MyLocation;
import com.example.appshopping.MainActivity;
import com.example.appshopping.R;
import com.example.appshopping.adapter.CityAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddLocationActivity extends AppCompatActivity
{
EditText  Phone, DiaChi, inputName;
public static MyLocation location; // địa chỉ
TextView countName, selectCity, selectQuan, selectXa;
private static final int  rCode = 10;
Button btXacNhan;
LinearLayout homeL, officeL;
List<MyLocation> lLocation;
int categoryLocation = 1;
CheckBox bt_LDefault;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
          lLocation = DsLocationActivity.DsLocation;
         if(lLocation == null)
           lLocation = new ArrayList<>();
        AnhXa();

         // mặc định là địa chỉ  nhà
        btXacNhan.setOnClickListener(new View.OnClickListener() { // Xác nhận thêm địa chỉ mới
            @Override
            public void onClick(View view) {
                String city = selectCity.getText().toString();
                String quan = selectQuan.getText().toString();
                String xa = selectXa.getText().toString();
                String diachi = DiaChi.getText().toString();
                String name = inputName.getText().toString();
                String sdt = Phone.getText().toString();
                if(sdt.isEmpty())
                    Phone.setError("Số điện thoại rỗng");
                if(name.isEmpty())
                    inputName.setError("Tên không được để trống");
                if(diachi.isEmpty())
                    DiaChi.setError("Địa chỉ trống");
                if(city.isEmpty())
                    selectCity.setError("Thành phố trống");
                if(quan.isEmpty())
                    selectQuan.setError("Quận, huyện trống");
                if(xa.isEmpty()) {
                    selectXa.setError("Phường, xã trống");
                    return;
                }
                if(!city.isEmpty() && !quan.isEmpty() && !xa.isEmpty() && !diachi.isEmpty()
                && !name.isEmpty() && !sdt.isEmpty())
                {
                    // check xem có địa chỉ có rỗng k
                    putAddress();
                }

            }
        });
        homeL.setBackgroundColor(getColor(R.color.Red));
        homeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeL.setBackgroundColor(getColor(R.color.Blue));
                officeL.setBackgroundColor(Color.BLACK);
                categoryLocation = 1;
            }
        });
        officeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                officeL.setBackgroundColor(getColor(R.color.Blue));
                homeL.setBackgroundColor(Color.BLACK);
                categoryLocation = 2;
            }
        });
        selectCity.setOnClickListener(new View.OnClickListener() { // start activity
            @Override
            public void onClick(View view) {
                Intent it = new Intent(AddLocationActivity.this, SelectLocationActivity.class);
                startActivityForResult(it,rCode);
                Animatoo.INSTANCE.animateFade(AddLocationActivity.this);
            }
        });
        inputName.addTextChangedListener(new TextWatcher() { // count length của text
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               countName.setText(charSequence.length()+"/50");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void AnhXa() {
        inputName = findViewById(R.id.input_name);
        countName = findViewById(R.id.count_name);
        selectCity = findViewById(R.id.selectCity);
        selectQuan = findViewById(R.id.SelectQuan);
        selectXa = findViewById(R.id.SelectXa);
        btXacNhan = findViewById(R.id.bt_xacNhan);
        location = new MyLocation();
        DiaChi = findViewById(R.id.inputLocation);
        Phone = findViewById(R.id.input_PhoneNumber);
        homeL = findViewById(R.id.HomeL);
        officeL = findViewById(R.id.OfficeLocation);
        bt_LDefault = findViewById(R.id.bt_LocationDefault);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Animatoo.INSTANCE.animateFade(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // set Dịa chỉ khi quay về từ activity kia
        if(requestCode == rCode && resultCode == Activity.RESULT_OK)
        {
            selectCity.setText(CityAdapter.nCity);
            selectXa.setText(CityAdapter.nXa);
            selectQuan.setText(CityAdapter.nQuan);

        }
    }
    private void putAddress()  // put địa chỉ lên firebase
    {
        String city, quan, xa, diachi, phone, name;
        city = selectCity.getText().toString();
        quan = selectQuan.getText().toString();
        xa = selectXa.getText().toString();
        diachi = DiaChi.getText().toString();
        phone = Phone.getText().toString();
        name = inputName.getText().toString();
        int idUser = MainActivity.user.getId();
        String category;
        if(categoryLocation == 1)
        category = "Nhà";
        else {
            category = "Công ty";
        }
        int cDefault;
        if(bt_LDefault.isChecked())
            cDefault = 1;
        else {
            cDefault = 0;
        }
        location = new MyLocation(city, quan,xa,name,category,diachi,phone,idUser,cDefault);
        lLocation.add(location);
        DatabaseReference putLocation = FirebaseDatabase.getInstance()
                .getReference("DeliveryAddress/"+MainActivity.user.getId());
        putLocation.setValue(lLocation);
        onBackPressed();
    }
}