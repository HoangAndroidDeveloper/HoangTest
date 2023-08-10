package com.example.appshopping.Class_Properties;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name, email, password, ngaysinh, avatar;
    private String state;
    public User()
    {

    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User(int id, String name, String email, String password, String ngaysinh, String avatar) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.ngaysinh = ngaysinh;
        this.avatar = avatar;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
