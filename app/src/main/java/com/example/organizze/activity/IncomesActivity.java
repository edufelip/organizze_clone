package com.example.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.config.FirebaseConfig;
import com.example.organizze.model.Movement;
import com.example.organizze.model.User;
import com.example.organizze.utils.Base64Custom;
import com.example.organizze.utils.DateUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class IncomesActivity extends AppCompatActivity {
    private TextInputEditText dateField, categoryField, descriptionField;
    private EditText valueField;
    private FloatingActionButton saveBtn;
    private Movement movement;
    private Double totalIncome;

    private DatabaseReference firebaseRef = FirebaseConfig.getFirebaseDatabase();
    private FirebaseAuth auth = FirebaseConfig.getFirebaseAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomes);

        dateField = findViewById(R.id.editDate);
        categoryField = findViewById(R.id.editCategory);
        descriptionField = findViewById(R.id.editDescription);
        valueField = findViewById(R.id.editValue);
        saveBtn = findViewById(R.id.fabSave);

        dateField.setText(DateUtil.todayDate());
        recoverTotalIncome();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIncome();
            }
        });
    }

    public void saveIncome() {
        String date = dateField.getText().toString();
        Double value = Double.parseDouble(valueField.getText().toString());
        if(validateIncome()) {
            movement = new Movement();
            movement.setValue(value);
            movement.setCategory(categoryField.getText().toString());
            movement.setData(date);
            movement.setDescription(descriptionField.getText().toString());
            movement.setType("i");

            Double updatedIncome = totalIncome + value;
            updateIncome(updatedIncome);

            movement.saveMovement(date);
        }
    }

    public Boolean validateIncome() {
        String textValue = valueField.getText().toString();
        String textDate = dateField.getText().toString();
        String textCategory = categoryField.getText().toString();
        String textDescription = descriptionField.getText().toString();

        if(textValue.isEmpty()) {
            Toast.makeText(IncomesActivity.this,
                    "O valor não foi preenchido",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(textDate.isEmpty()) {
            Toast.makeText(IncomesActivity.this,
                    "A data não foi preenchida",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(textCategory.isEmpty()) {
            Toast.makeText(IncomesActivity.this,
                    "A categoria não foi preenchida",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(textDescription.isEmpty()) {
            Toast.makeText(IncomesActivity.this,
                    "A descrição não foi preenchida",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void recoverTotalIncome(){
        String userEmail = auth.getCurrentUser().getEmail();
        String userId = Base64Custom.encodeBase64(userEmail);
        DatabaseReference userRef = firebaseRef.child("users").child(userId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue( User.class );
                totalIncome = user.getTotalIncome();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateIncome(Double income) {
        String userEmail = auth.getCurrentUser().getEmail();
        String userId = Base64Custom.encodeBase64(userEmail);
        DatabaseReference userRef = firebaseRef.child("users").child(userId);

        userRef.child("totalIncome").setValue(income);

    }
}
