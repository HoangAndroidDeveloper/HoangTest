package com.example.appshopping.Class_Properties;

import java.io.Serializable;

public class MyLocation implements Serializable {

     private String city, quan, xa, namekh, category;
     private String diachi;
     private String sdt;
     int iduser;
     private int lcdefault;

     public MyLocation() {
     }

     public MyLocation(String city, String quan, String xa, String namekh, String category, String diachi, String sdt, int iduser, int lcdefault) {
          this.city = city;
          this.quan = quan;
          this.xa = xa;
          this.namekh = namekh;
          this.category = category;
          this.diachi = diachi;
          this.sdt = sdt;
          this.iduser = iduser;
          this.lcdefault = lcdefault;
     }

     public String getCategory() {
          return category;
     }

     public void setCategory(String category) {
          this.category = category;
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

     public String getDiachi() {
          return diachi;
     }

     public void setDiachi(String diachi) {
          this.diachi = diachi;
     }

     public String getSdt() {
          return sdt;
     }

     public void setSdt(String sdt) {
          this.sdt = sdt;
     }


     public int getLcdefault() {
          return lcdefault;
     }

     public void setLcdefault(int lcdefault) {
          this.lcdefault = lcdefault;
     }

     public String getNamekh() {
          return namekh;
     }

     public void setNamekh(String namekh) {
          this.namekh = namekh;
     }

     public int getIduser() {
          return iduser;
     }

     public void setIduser(int iduser) {
          this.iduser = iduser;
     }
}
