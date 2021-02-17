package com.example.organizze.model;

import com.example.organizze.config.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private Double totalIncome = 0.00;
    private Double totalExpenditure = 0.00;


    public User(String userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    @Exclude
    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
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

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(Double totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    public void saveUser() {
        DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
        firebase.child("users").child(this.userId).setValue(this);
    }
}
