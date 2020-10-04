package com.exercise.ace;

import android.net.Uri;

public class Ad {

    private String Title;
    private String Description;
    private String Price;
    private String Phone;
    private String Category;
    private String img;

    public Ad() {

    }

    public Ad(String Title, String Description, String Price,String img,String Phone) {
        this.Title = Title;
        this.Description = Description;
        this.Price = Price;
        this.img = img;
        this.Phone = Phone;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String  getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
