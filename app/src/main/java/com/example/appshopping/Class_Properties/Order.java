package com.example.appshopping.Class_Properties;

public class Order extends Cart
{
    int  idorder;
    private String state, nameuser, sdt;
    private String city, quan, xa;

    public Order()
    {

    }

    public Order(int idorder, String state, String nameuser, String sdt, String city, String quan, String xa)
    {
        this.idorder = idorder;
        this.state = state;
        this.nameuser = nameuser;
        this.sdt = sdt;
        this.city = city;
        this.quan = quan;
        this.xa = xa;
    }

    public Order(int idorder,int idsp, int iduser, String imagecart, String namecart, String size, String color, int sl, String money, String state, String nameuser, String sdt, String city, String quan, String xa) {
        super(idsp, iduser, imagecart, namecart, size, color, sl, money);
        this.idorder = idorder;
        this.state = state;
        this.nameuser = nameuser;
        this.sdt = sdt;
        this.city = city;
        this.quan = quan;
        this.xa = xa;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getXa() {
        return xa;
    }

    public void setXa(String xa) {
        this.xa = xa;
    }

    public int getIdorder() {
        return idorder;
    }

    public void setIdorder(int idorder) {
        this.idorder = idorder;
    }
}
