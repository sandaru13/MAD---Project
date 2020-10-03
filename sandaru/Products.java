package com.example.mad_adzz;


import android.net.Uri;

public class Products {
    private String title;
    private String description;
    private float price;
    private String owner;
    private Uri img;
    private long phone;

    public Products() {
    }

    public Products(String title, String description, float price, String owner,Uri img,long phone) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.owner = owner;
        this.img = img;
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        String pr = String.valueOf(price);
        return pr;
    }

    public String getOwner() {
        return owner;
    }

    public Uri getImg() {
        return img;
    }

    public String getPhone() {
        String ph = String.valueOf(phone);
        return ph;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setImg(Uri img) {
        this.img = img;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
