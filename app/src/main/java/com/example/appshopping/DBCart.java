package com.example.appshopping;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appshopping.Class_Properties.Cart;
import com.example.appshopping.InterFace.CartDao;

@Database(entities = Cart.class, version = 1)
public abstract class DBCart extends RoomDatabase{

    private static final String DATABASENAME = "QLCart";
    public static DBCart instance;
    public static synchronized DBCart getInstance(Context context)
    {
       if(instance == null)
       {
           instance = Room.databaseBuilder(context, DBCart.class,DATABASENAME)
                   .allowMainThreadQueries()
                   .build();
       }
       return instance;
    }
public abstract CartDao cartDao();

}
