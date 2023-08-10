package com.example.appshopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import com.example.appshopping.Class_Properties.Cart;
import com.example.appshopping.DBCart;
import com.example.appshopping.InterFace.CartDao;
import com.example.appshopping.MainActivity;
import com.example.appshopping.R;
import com.example.appshopping.adapter.CartAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CartActivity extends AppCompatActivity {
    List<Cart> DsCart;
    Button btMua;
    CheckBox SelectAll;
    RecyclerView recyclerCart;
    ImageView bt_backCart;
    CartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        AnhXa();
        List<Cart> LCart = new ArrayList<>();
        SelectAll.setOnClickListener(view -> CheckSelect(LCart));
        btMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(CartActivity.this,MuaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("spMua", (Serializable) LCart);
                it.putExtras(bundle);
                startActivity(it);
            }
        });
        bt_backCart.setOnClickListener(v -> {
            onBackPressed();
        });
        getDataCart(LCart);
        new ItemTouchHelper(helper).attachToRecyclerView(recyclerCart);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void CheckSelect(List<Cart> LCart ) {
        if(SelectAll.isChecked()) {
            LCart.clear();
            CartAdapter.check = DsCart.size();
            for (int i = 0; i < DsCart.size(); i++) {
                LCart.add(0, DsCart.get(i));
            }
            cartAdapter.notifyDataSetChanged();
        }
        else
        {
            CartAdapter.check = 0;
            cartAdapter.notifyDataSetChanged();
            LCart.clear();
        }
    }

    private void AnhXa()
    {
        bt_backCart = findViewById(R.id.bt_back_cart);
        recyclerCart = findViewById(R.id.recycler_Cart);
        SelectAll = findViewById(R.id.SelectAllCart);
        btMua = findViewById(R.id.bt_Mua_Cart);
    }

    private void getDataCart(List<Cart> LCart)
    {
        CartAdapter.ProcessCheckBox click = new CartAdapter.ProcessCheckBox() {
            @Override
            public void clickCheckBox(boolean checked, int position, int ktr) {

               if(checked)
               {
                   LCart.add(0,DsCart.get(position));
               }
               else
               {
                   int id = DsCart.get(position).getIdcart();
                   for (int i = 0; i < LCart.size(); i++) {
                      if (LCart.get(i).getIdcart() == id)
                      {
                          LCart.remove(i);
                          break;
                      }
                   }
               }
               SelectAll.setChecked(ktr == DsCart.size());
            }
        };
        DsCart = DBCart.getInstance(this).cartDao().getAllCart(MainActivity.user.getId());
        cartAdapter = new CartAdapter(DsCart,this,click);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerCart.setLayoutManager(linearLayoutManager);
        recyclerCart.setAdapter(cartAdapter);
    }

  ItemTouchHelper.SimpleCallback helper = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT)
  {
      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
          return false;
      }

      @SuppressLint("NotifyDataSetChanged")
      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
          int position = viewHolder.getAdapterPosition();
          Cart cart = DsCart.get(position);
          DsCart.remove(position);
          CartDao bt = DBCart.getInstance(CartActivity.this).cartDao();
          bt.DeleteCart(cart);
          cartAdapter.notifyDataSetChanged();
          Snackbar.make(recyclerCart,"Đã xóa item",Snackbar.LENGTH_LONG)
                  .setAction("Khôi phục", new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                      DsCart.add(position,cart);
                      bt.addCart(cart);
                      cartAdapter.notifyDataSetChanged();
                      }
                  }).show();
      }

      @Override
      public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
          new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                  .addBackgroundColor(ContextCompat.getColor(CartActivity.this,R.color.Red))
                  .addActionIcon(R.drawable.ic_baseline_delete_24)
                  .addSwipeLeftLabel("Delete")
                  .setSwipeLeftLabelColor(ContextCompat.getColor(CartActivity.this,R.color.white))
                  .create()
                  .decorate();
          super.onChildDraw(c, recyclerView, viewHolder, dX,dY, actionState, isCurrentlyActive);
      }
  };

    @Override
    public void onBackPressed() {
        CartAdapter.check = 0;
        super.onBackPressed();
    }
}