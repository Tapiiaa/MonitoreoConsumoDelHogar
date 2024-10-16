package com.example.monitoreoconsumodelhogar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.monitoreoconsumodelhogar.R;

public class AccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica de autenticación
                boolean isAuthenticated = authenticateUser();
                if (isAuthenticated) {
                    Intent intent = new Intent(AccessActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean authenticateUser() {
        // Implementa la lógica de autenticación aquí
        return true; // Simulación de autenticación exitosa
    }
}