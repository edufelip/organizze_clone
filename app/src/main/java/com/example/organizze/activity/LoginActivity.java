package com.example.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.config.FirebaseConfig;
import com.example.organizze.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button loginBtn;
    private User user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = loginEmail.getText().toString();
                String passwordText = loginPassword.getText().toString();
                if(emailText.isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            "Preencha o email",
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(passwordText.isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            "Preencha a senha",
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                user = new User();
                user.setEmail(emailText);
                user.setPassword(passwordText);
                validateLogin();
            }
        });
    }

    public void validateLogin() {
        auth = FirebaseConfig.getFirebaseAuth();
        auth.signInWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    openMainMenu();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Erro ao validar o login!",
                            Toast.LENGTH_SHORT)
                            .show();
                    String exception = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        exception = "O usuário inserido não está cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "A senha está incorreta";
                    } catch (Exception e) {
                        exception = "Erro ao cadastrar o usuário" + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openMainMenu(){
        startActivity(new Intent(this, LoggedMainActivity.class));
        finish();
    }
}
