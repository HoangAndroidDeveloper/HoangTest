package com.example.appshopping;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.appshopping.Class_Properties.User;
import com.example.appshopping.fragment.HomeFragment;
import com.example.appshopping.fragment.ChatFragment;
import com.example.appshopping.fragment.MyFragment;
import com.example.appshopping.fragment.OrderFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity  {
public static int checkBack = 0;
public static User user;
MeowBottomNavigation meowBottomNavigation;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        String email = getIntent().getStringExtra("email");
        getUser(email);
        selectItemNavigation();
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.icon_order_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.baseline_chat_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_person));
        meowBottomNavigation.show(1,true);

    }

    private void selectItemNavigation() {
       meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
           @Override
           public Unit invoke(MeowBottomNavigation.Model model) {
               switch (model.getId())
               {
                   case 1:
                       loadFragment(new HomeFragment());
                       break;
                   case 2:
                       loadFragment(new OrderFragment());
                       break;
                   case 3: loadFragment(new ChatFragment());
                   break;
                   case  4:
                       loadFragment(new MyFragment());
                       break;
               }
               return null;

           }
       });
    }

    public void AnhXa()
{
    meowBottomNavigation = findViewById(R.id.navigation);
}

    @Override
    public void onBackPressed() {

        if(checkBack != 2) {
            Toast.makeText(this, "Nhấn 1 lần nữa để thoát", Toast.LENGTH_SHORT).show();
            checkBack ++;
        }
        else
         super.onBackPressed();

    }
    private void loadFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layoutReplace,fragment).commit();
    }
    private void getUser(String email) // lấy thông tin user đăng nhập để làm các chức năng khác
    {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("DsUser");

        if(email != null) {

            Query query = reference.orderByChild("email").equalTo(email);
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User u = snapshot.getValue(User.class);
                assert u != null;
                user = u;
                     loadFragment(new HomeFragment());
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
    }
}
