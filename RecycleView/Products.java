package com.example.mad_adzz;

public class Products {
    private String title;
    private String description;
    private String price;
    private String owner;

    public Products() {
    }

    public Products(String title, String description, String price, String owner) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getOwner() {
        return owner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
