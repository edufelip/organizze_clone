package com.example.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.config.FirebaseConfig;
import com.example.organizze.model.User;
import com.example.organizze.utils.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerName, registerEmail, registerPassword;
    private Button registerBtn;
    private FirebaseAuth auth;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nameText = registerName.getText().toString();
                String emailText = registerEmail.getText().toString();
                String passwordText = registerPassword.getText().toString();

                if(nameText.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,
                            "Preencha o nome",
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(emailText.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,
                            "Preencha o email",
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(passwordText.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,
                            "Preencha a senha",
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                user = new User("", nameText, emailText, passwordText);
                registerUser();
            }
        });
    }

    public void registerUser(){
        auth = FirebaseConfig.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    String userId = Base64Custom.encodeBase64(user.getEmail());
                    user.setUserId(userId);
                    user.saveUser();

                    Toast.makeText(RegisterActivity.this,
                            "Usuário cadastrado com sucesso",
                            Toast.LENGTH_SHORT)
                            .show(); 
                    finish();
                } else {
                    String exception = "";
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthWeakPasswordException e) {
                        exception = "Digite uma senha mais forte!";
                    } catch(FirebaseAuthInvalidCredentialsException e) {
                        exception = "Digite um e-mail válido";
                    } catch(FirebaseAuthUserCollisionException e) {
                        exception = "Esse e-mail já está cadastrado";
                    } catch(Exception e) {
                        exception = "Erro ao cadastrar o usuário" + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(RegisterActivity.this,
                            exception,
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
}
