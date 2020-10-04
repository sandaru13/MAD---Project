package com.example.buyingandselling.Model;

public class AddDetails {

    String name,contact,address,price,discription,imageId;

    public AddDetails() {
    }

    public AddDetails(String name, String contact, String address, String price, String discription, String imageId) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.price = price;
        this.discription = discription;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
