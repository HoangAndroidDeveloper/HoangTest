package com.example.appshopping.Class_Properties;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "Cart")
public class Cart implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int idcart;
    int idsp;
    int iduser;
    String imagecart;
    String namecart, size, color;
    int sl;
    String money;
    public Cart() {
    }

    public Cart(int idsp, int iduser, String imagecart, String namecart, String size, String color, int sl, String money) {
        this.idsp = idsp;
        this.iduser = iduser;
        this.imagecart = imagecart;
        this.namecart = namecart;
        this.size = size;
        this.color = color;
        this.sl = sl;
        this.money = money;
    }

    public int getIdcart() {
        return idcart;
    }

    public void setIdcart(int idcart) {
        this.idcart = idcart;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getImagecart() {
        return imagecart;
    }

    public void setImagecart(String imagecart) {
        this.imagecart = imagecart;
    }

    public String getNamecart() {
        return namecart;
    }

    public void setNamecart(String namecart) {
        this.namecart = namecart;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
