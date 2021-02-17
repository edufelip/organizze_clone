package com.example.organizze.model;

import com.example.organizze.config.FirebaseConfig;
import com.example.organizze.utils.Base64Custom;
import com.example.organizze.utils.DateUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Movement {

    private String data;
    private String category;
    private String description;
    private String type;
    private Double value;

    public Movement() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void saveMovement(String date){
        FirebaseAuth auth = FirebaseConfig.getFirebaseAuth();
        String userId = Base64Custom.encodeBase64(auth.getCurrentUser().getEmail());
        String monthYear = DateUtil.dateMonthAndYear(date);

        DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
        firebase.child("movement")
                .child(userId)
                .child(monthYear)
                .push()
                .setValue(this);
    }
}
