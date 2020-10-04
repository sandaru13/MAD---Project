package com.example.buyingandselling.MemberDetails;

public class Members {
    String name;
    String email;
    String id;
    String phone;
    String age;
    String gender;
    String password;
    String feedback;

    public Members() {
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Members(String feedback) {
        this.feedback = feedback;
    }

    public Members(String name, String email, String id, String phone, String age, String gender, String password) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.password = password;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
