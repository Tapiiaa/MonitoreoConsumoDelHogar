package com.example.monitoreoconsumodelhogar.UserInterface.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.monitoreoconsumodelhogar.R;
import com.example.monitoreoconsumodelhogar.UserInterface.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccessActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button loginButton, signUpButton;
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        //Inicializamos Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);

        //Nos aseguramos de que no se puedan dejar en blanco.
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    usernameEditText.setError("Este campo es obligatorio");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    passwordEditText.setError("Este campo es obligatorio");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    loginUser(email, password);
                } else {
                    if (email.isEmpty()) {
                        usernameEditText.setError("El email es obligatorio");
                    }
                    if (password.isEmpty()) {
                        passwordEditText.setError("La contraseña es obligatoria");
                    }
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    signUp(email, password);
                } else {
                    if (email.isEmpty()) {
                        usernameEditText.setError("El email es obligatorio");
                    }
                    if (password.isEmpty()) {
                        passwordEditText.setError("La contraseña es obligatoria");
                    }
                }
            }
        });
    }

    private void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(AccessActivity.this, "Login exitoso: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        // Redirigir al usuario a MainActivity después del login
                        Intent intent = new Intent(AccessActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Cierra AccessActivity para que no se pueda volver con el botón atrás
                    } else {
                        Toast.makeText(AccessActivity.this, "Error en el login.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Usuario ya autenticado, redirigir a MainActivity
            Intent intent = new Intent(AccessActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(AccessActivity.this, "Registro exitoso: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AccessActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Cierra LoginActivity para que no se pueda volver con el botón atrás
                    } else {
                        Toast.makeText(AccessActivity.this, "Error en el registro.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}