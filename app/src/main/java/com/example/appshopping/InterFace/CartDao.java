package com.example.appshopping.InterFace;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.appshopping.Class_Properties.Cart;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    public void insertCar(Cart cart);
    @Query("Select * From Cart Where iduser = :id ")
    public List<Cart> getAllCart(int id);
    @Query("Select * From Cart Where namecart = :name and color = :color and size = :size")
    public Cart checkCart(String name, String color, String size);
    @Update
    public void  updateCart(Cart cart);
    @Delete
    public void DeleteCart(Cart cart);
    @Insert
    public void addCart(Cart cart);
}
