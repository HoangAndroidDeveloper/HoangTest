package com.example.appshopping.Class_Properties;
import java.io.Serializable;

public class SP implements Serializable {
    int id;
    propertiesImage_SP listimage;
    String name, category, money;
    String star;
    long daban;
    String giamgia;

    public SP() {
    }

    public SP(int id, propertiesImage_SP listimage, String name, String category, String money, String star, long daban, String giamgia) {
        this.id = id;
        this.listimage = listimage;
        this.name  = name;
        this.category = category;
        this.money = money;
        this.star = star;
        this.daban = daban;
        this.giamgia = giamgia;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMoney()
    {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public long getDaban() {
        return daban;
    }

    public void setDaban(long daban) {
        this.daban = daban;
    }

    public String getGiamgia() {
        return giamgia;
    }

    public void setGiamgia(String giamgia) {
        this.giamgia = giamgia;
    }

    public propertiesImage_SP getListimage() {
        return listimage;
    }

    public void setListimage(propertiesImage_SP listimage) {
        this.listimage = listimage;
    }

}
