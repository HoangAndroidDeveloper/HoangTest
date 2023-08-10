package com.example.appshopping.Class_Properties;

import java.io.Serializable;
import java.util.List;

public class propertiesImage_SP implements Serializable {
    List<String>  image, nameimage,size;
    List<Long> SL;
    public propertiesImage_SP() {
    }

    public propertiesImage_SP(List<String> image, List<String> nameimage, List<String> size, List<Long> SL) {
        this.image = image;
        this.nameimage = nameimage;
        this.size = size;
        this.SL = SL;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public List<String> getNameimage() {
        return nameimage;
    }

    public void setNameimage(List<String> nameimage) {
        this.nameimage = nameimage;
    }

    public List<Long> getSL() {
        return SL;
    }

    public void setSL(List<Long> SL) {
        this.SL = SL;
    }
}
