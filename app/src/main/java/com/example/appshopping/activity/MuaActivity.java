package com.example.appshopping.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appshopping.Class_Properties.Cart;
import com.example.appshopping.Class_Properties.MyLocation;
import com.example.appshopping.Class_Properties.Order;
import com.example.appshopping.Class_Properties.User;
import com.example.appshopping.DBCart;
import com.example.appshopping.Function_class;
import com.example.appshopping.Helper.AppInfo;
import com.example.appshopping.Helper.CreateOrder;
import com.example.appshopping.InterFace.CartDao;
import com.example.appshopping.MainActivity;
import com.example.appshopping.R;
import com.example.appshopping.adapter.DatHangAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class MuaActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "Channel 1";
    RadioButton tra_sau, Zalopay;
    TextView sumMoneySP, sumMoney2, bt_addLocation, locationDefault;
    RecyclerView rDsDatHang;
    DatHangAdapter adapter;
    Button btDatHang;
    ConstraintLayout layout;
    LinearLayout linearLayout, linearLayout1;
    MyLocation myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua);
        AnhXa();
        createIDChanel();
        getDeliveryAddress();
            Bundle bundle = getIntent().getExtras();
            List<Cart> sp = (List<Cart>) bundle.getSerializable("spMua"); // Ds cart mà activity shopping gửi qua
            if (sp != null)
            {
               sumMoney(sp);
                adapter = new DatHangAdapter(sp, this);
                LinearLayoutManager ln = new LinearLayoutManager(this);
                rDsDatHang.setLayoutManager(ln);
                rDsDatHang.setAdapter(adapter);
            }
            btDatHang.setOnClickListener(view -> {
                assert sp != null;
                {
                    CartDao deleteCart = DBCart.getInstance(this).cartDao();
                    for(Cart cart : sp)
                    {
                        deleteCart.DeleteCart(cart);// xóa sản phẩm trong giỏ hàng sau khi đặt đơn
                    }
                    if (Zalopay.isChecked())
                        DatHang(sp);
                    else {
                        putOder(sp);
                    }
                }

            });
            linearLayout1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(MuaActivity.this, DsLocationActivity.class);
                    startActivityForResult(it, 0);
                }
            });
            locationDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent it = new Intent(MuaActivity.this, DsLocationActivity.class);
                    startActivityForResult(it, 0);
                }
            });
        }

    private void DatHang(List<Cart> sp) // thanh toán bằng zalo pay
    {

        StrictMode.ThreadPolicy policy = new
        StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2553, Environment.SANDBOX);
        CreateOrder orderApi = new CreateOrder();
        try {
            String amount = sumMoney(sp);
            JSONObject data = orderApi.createOrder(amount);
            String code = data.getString("return_code");
            if(code.equals("1")) {
                String token = data.getString("zp_trans_token");
                // demozapdk là: sau khi thanh toán xong thì trở về app của mình
                ZaloPaySDK.getInstance().payOrder(MuaActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                        Toast.makeText(MuaActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        putOder(sp);
                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
                        Toast.makeText(MuaActivity.this, "Thanh toán bị hủy", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                        Toast.makeText(MuaActivity.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                    }

                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongViewCast")
        private void AnhXa ()
    {
            rDsDatHang = findViewById(R.id.Ds);
            sumMoneySP = findViewById(R.id.sumMoneyMua_3);
            sumMoney2 = findViewById(R.id.sumMoneyMua_2);
            btDatHang = findViewById(R.id.bt_DatHang);
            layout = findViewById(R.id.layout);
            bt_addLocation = findViewById(R.id.bt_startActivityLocation);
            linearLayout = findViewById(R.id.LnLayout);
            locationDefault = findViewById(R.id.Location_default);
            linearLayout1 = findViewById(R.id.LnLayout1);
            tra_sau = findViewById(R.id.tra_sau);
            Zalopay = findViewById(R.id.zaloPay);
        }

        @SuppressLint("SetTextI18n")
        private String sumMoney (List < Cart > sp) // tính tổng tiền hàng
        {

            long sum = 0;
            for (int i = 0; i < sp.size(); i++) {

                long tongMoney = Long.parseLong(sp.get(i).getMoney()) * sp.get(i).getSl();
                sum = sum + tongMoney;
            }
            sumMoney2.setText(Function_class.FormatMoney(sum));
            sum = sum + 20000;
            sumMoneySP.setText(Function_class.FormatMoney(sum));
            return  String.valueOf(sum);
        }

        private void pushNotification (List < Cart > sp) throws InterruptedException { // push thông báo


                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.facebook);
                    @SuppressLint("RestrictedApi") NotificationCompat.Builder notification = new NotificationCompat.Builder(MuaActivity.this, CHANNEL_ID)
                            .setContentTitle("Đặt hàng thành công")
                            .setContentText("Nhấn vào đây để kiểm tra tình trạng đơn hàng")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText("Nhấn vào đây để kiểm tra tình trạng đơn hàng"))
                            .setLargeIcon(bitmap);

                    if (ActivityCompat.checkSelfPermission(MuaActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    NotificationManagerCompat.from(MuaActivity.this).notify(Function_class.createID(), notification.build());
                }


        private void createIDChanel () // tạo chanel id cho notification
        {

            CharSequence name = "Thông báo đặt hàng";
            String description = "Đơn hàng";
            int importance = NotificationManager.IMPORTANCE_MAX;
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        private void getDeliveryAddress () // lấy địa chỉ nhận hàng
        {
            User user = MainActivity.user;
            check(false);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DeliveryAddress/" + user.getId());
            Query query = reference.orderByChild("lcdefault").equalTo(1);
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName)
                {
                    myLocation = snapshot.getValue(MyLocation.class);
                    check(true);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
@SuppressLint("SetTextI18n")
private void check(boolean c)
{
    if(c)
    {
        String name = myLocation.getNamekh();
        String sdt = myLocation.getSdt();
        String diachi = myLocation.getDiachi();
        String city = myLocation.getCity() + ", " + myLocation.getQuan() + ", " + myLocation.getXa();
        locationDefault.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout1.setVisibility(View.GONE);
        locationDefault.setText(name+" | "+"(+84)"+sdt+"\n"+diachi+"\n"+city);
        return;
    }

    linearLayout1.setVisibility(View.VISIBLE);
    linearLayout.setVisibility(View.GONE);
    locationDefault.setVisibility(View.GONE);

}

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == Activity.RESULT_OK)
        {
            assert data != null;
            myLocation = (MyLocation) data.getExtras().getSerializable("Location");
            String name = myLocation.getNamekh();
            String sdt = myLocation.getSdt();
            String diachi = myLocation.getDiachi();
            String city = myLocation.getCity() + ", " + myLocation.getQuan() + ", " + myLocation.getXa();
            locationDefault.setText(name+" | "+"(+84)"+sdt+"\n"+diachi+"\n"+city);
        }
    }
    private void putOder(List<Cart> DsCart)
    {
        for(Cart cart : DsCart)
        {
            int  idSP = cart.getIdsp();
            int idUser = cart.getIduser();
            String imageCart = cart.getImagecart();
            String nameCart  = cart.getNamecart()
                    , size = cart.getSize()
                    , color = cart.getColor();
            int SL = cart.getSl();
            String money = cart.getMoney();
            String nameUser = MainActivity.user.getName()
                    , sdt = myLocation.getSdt();
            String city = myLocation.getCity();
            String quan = myLocation.getQuan();
            String xa = myLocation.getXa();
            Order order = new Order(Function_class.createID(),idSP, idUser,imageCart,nameCart, size, color
                    , SL, money,"Đang chờ xác nhận", nameUser, sdt,city, quan, xa);
            DatabaseReference pOrder = FirebaseDatabase.getInstance()
                    .getReference("DsOrder/"+order.getIdorder());
            pOrder.setValue(order);

        }

        Dialog dialog = new Dialog(MuaActivity.this);
        dialog.setContentView(R.layout.custom_loading_dialog);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Handler handler = new Handler();
        dialog.show();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                try {
                    pushNotification(DsCart);
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
                dialog.dismiss();
                onBackPressed();
            }
        },3000);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
       ZaloPaySDK.getInstance().onResult(intent);
    }
}