package com.example.appshopping.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.appshopping.Animation_Cart;
import com.example.appshopping.Class_Properties.Cart;
import com.example.appshopping.Class_Properties.SP;
import com.example.appshopping.Class_Properties.User;
import com.example.appshopping.Class_Properties.propertiesImage_SP;
import com.example.appshopping.DBCart;
import com.example.appshopping.Function_class;
import com.example.appshopping.MainActivity;
import com.example.appshopping.R;
import com.example.appshopping.adapter.ColorSP_adapter;
import com.example.appshopping.adapter.list_imageShopping;
import com.example.appshopping.adapter.sizeAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.willy.ratingbar.ScaleRatingBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShoppingActivity extends AppCompatActivity {

    ScaleRatingBar ratingBar;
    ViewPager2 viewPager2;
    TextView descriptionSp, spDaBan, star, bt_mua, moneySP;
    public static int checkS = -1, checkC = -1;
    ImageView bt_addCart,  imageAnimation ;
    CardView btGioHang;
    ColorSP_adapter CAdapter;
    sizeAdapter SAdapter;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        AnhXa();
        Bundle bundle = getIntent().getExtras();
        SP sp = (SP) bundle.get("SP");
        OpenAndCloseBottomSh(sp);
        moneySP.setText(Function_class.FormatMoney(Long.parseLong(sp.getMoney())));
        star.setText(sp.getStar());
        descriptionSp.setText(sp.getName());
        propertiesImage_SP LImage = sp.getListimage();
       if(sp.getDaban() >= 1000)
       {
           int num = String.valueOf(sp.getDaban()).length() / 3;
           float daBan = (float) (sp.getDaban()/Math.pow(1000,num));
           spDaBan.setText("Đã bán "+daBan+"K");
       }
       else {
           spDaBan.setText("Đã bán"+sp.getDaban()+"");
       }
        list_imageShopping ad = new list_imageShopping(LImage.getImage(),this);
        viewPager2.setAdapter(ad);

        btGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ShoppingActivity.this,CartActivity.class);
                startActivity(it);
            }
        });
    }

    private void AnhXa() {
        moneySP = findViewById(R.id.shopping_money);
        ratingBar = findViewById(R.id.rating_Start);
        viewPager2 = findViewById(R.id.List_image_SP);
        descriptionSp = findViewById(R.id.description_sp);
        spDaBan = findViewById(R.id.sp_DaBan);
        star = findViewById(R.id.star);
        bt_mua = findViewById(R.id.bt_mua);
        bt_addCart = findViewById(R.id.bt_addCart);
        btGioHang = findViewById(R.id.bt_gioHang_shop);
        user = MainActivity.user;
    }
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    private void OpenAndCloseBottomSh(SP sp) {

        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_cart,null);
        ImageView image = view.findViewById(R.id.imageSP_bottomSheet);
        imageAnimation = view.findViewById(R.id.image_animation);
        TextView addSP = view.findViewById(R.id.addSL);
        TextView SL = view.findViewById(R.id.SL);
        TextView GiamSL = view.findViewById(R.id.Giam);
        GiamSL.setEnabled(false);
        addSP.setOnClickListener(v -> {
            if(!GiamSL.isEnabled()) {
                GiamSL.setEnabled(true);
                GiamSL.setTextColor(Color.BLACK);
            }
            int sl = Integer.parseInt(SL.getText().toString())+1;
            SL.setText(sl+"");
        });
        GiamSL.setOnClickListener(v -> {
            int sl = Integer.parseInt(SL.getText().toString()) - 1;
            SL.setText(sl + "");
            if(SL.getText().toString().equals("1"))
            {
                GiamSL.setTextColor(getColor(R.color.color_bt));
                GiamSL.setEnabled(false);
            }
            else {
                if(!GiamSL.isEnabled())
                 GiamSL.setEnabled(true);
                GiamSL.setTextColor(Color.BLACK);
            }
        });
        AppCompatButton bMua = view.findViewById(R.id.bt_mua_sheet);
        Glide.with(this).load(sp.getListimage().getImage().get(0)).into(image);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bt_addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               bMua.setText("Thêm vào giỏ hàng");
                bottomSheetDialog.show();
                bottomSheetDialog.setDismissWithAnimation(true);
            }
        });
        bt_mua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bMua.setText("Mua ngay");
                bottomSheetDialog.show();
            }
        });
        ImageView bt_close = view.findViewById(R.id.bt_close_sheet);
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

      setSize(view,sp,bMua);
        setColorAndNameSP(view,sp,image,bMua);
        bMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bMua.getText().toString().equals("Thêm vào giỏ hàng") && checkS != -1 && checkC != -1)
                {
                    imageAnimation.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(ShoppingActivity.this,R.anim.animation_add_cart);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                               imageAnimation.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    imageAnimation.startAnimation(animation);
                    addAndMua(view, sp, Integer.parseInt(SL.getText().toString()));
                }
                else
                if(bMua.getText().toString().equals("Mua ngay") && checkS != -1 && checkC != -1)
                {
                  setCartActivityMua(Integer.parseInt(SL.getText().toString()),sp);
                }
            }
        });
    }
    private void setSize(View view, SP sp, Button bMua)
    {

     RecyclerView rSize = view.findViewById(R.id.ds_size);
     GridLayoutManager G = new GridLayoutManager(this,4);
    SAdapter = null;
        sizeAdapter.sizeInterface  SInterface = new sizeAdapter.sizeInterface() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick( sizeAdapter ad, int position) {
                checkS = position;
                ad.notifyDataSetChanged();
                if( checkC > -1 && checkS >-1) {
                    bMua.setTextColor(Color.WHITE);
                    bMua.setBackgroundColor(Color.RED);
                }
                else
                {
                    bMua.setTextColor(getColor(R.color.color_textMua));
                    bMua.setBackgroundColor(getColor(R.color.color_bt));
                }
             }

            @Override
            public void onClickColor(ColorSP_adapter ad, int position) {

            }
        };
        SAdapter = new sizeAdapter(sp.getListimage().getSize(),this,SInterface);
        rSize.setAdapter(SAdapter);
        rSize.setLayoutManager(G);

    }
    public void setColorAndNameSP(View view, SP sp, ImageView image, Button bMua)
    {
        sizeAdapter.sizeInterface interFace = new sizeAdapter.sizeInterface() {
            @Override
            public void onClick(sizeAdapter ad, int position) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClickColor(ColorSP_adapter ad, int position) {
                checkC = position;
                if(position >-1)
                 Glide.with(ShoppingActivity.this).load(sp.getListimage().getImage().get(position)).into(image);
                ad.notifyDataSetChanged();
                if(checkC >-1 && checkS >-1) {
                    bMua.setTextColor(Color.WHITE);
                    bMua.setBackgroundColor(Color.RED);
                }
                else
                {
                    bMua.setTextColor(getColor(R.color.color_textMua));
                    bMua.setBackgroundColor(getColor(R.color.color_bt));
                }
            }
        };

        RecyclerView rName = view.findViewById(R.id.colorSP);
        List<String> LImage = sp.getListimage().getImage();
        List<String> LName = sp.getListimage().getNameimage();
        CAdapter = new ColorSP_adapter(LImage,LName,this,interFace);
        GridLayoutManager manager = new GridLayoutManager(this,3);
        rName.setLayoutManager(manager);
        rName.setAdapter(CAdapter);
    }

    public void addAndMua(View view, SP sp, int SL)
    {
         int id = sp.getId();
         String image = sp.getListimage().getImage().get(checkC);
         String name = sp.getName();
         String size = sp.getListimage().getSize().get(checkS);
         String color = sp.getListimage().getNameimage().get(checkC);
         String money = sp.getMoney();
         Cart cart = DBCart.getInstance(this).cartDao().checkCart(name, color,size);
         if(cart == null) {
             cart = new Cart(id,user.getId(),image,name,size,color,SL,money);
             DBCart.getInstance(this).cartDao().insertCar(cart);
         }
         else
         {
             cart.setSl(cart.getSl()+SL);
             DBCart.getInstance(this).cartDao().updateCart(cart);
         }
    }
    public void setCartActivityMua(int SL, SP sp)
    {
        Intent it = new Intent(ShoppingActivity.this,MuaActivity.class);
        String image = sp.getListimage().getImage().get(checkC);
        String name = sp.getName();
        String size = sp.getListimage().getSize().get(checkS);
        String color = sp.getListimage().getNameimage().get(checkC);
        String money = sp.getMoney();
        List<Cart> LCart = new ArrayList<>();
        Cart cart = new Cart(sp.getId(),user.getId(),image, name, size, color, SL, money);
        LCart.add(cart);
        Bundle bundle = new Bundle();
        bundle.putSerializable("spMua", (Serializable) LCart);
        it.putExtras(bundle);
        startActivity(it);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBackPressed() {
        ColorSP_adapter.cCheck = -1;
        sizeAdapter.nClick = -1;
        super.onBackPressed();

    }
}