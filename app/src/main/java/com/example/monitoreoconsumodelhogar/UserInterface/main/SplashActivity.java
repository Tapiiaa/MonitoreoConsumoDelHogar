package com.example.monitoreoconsumodelhogar.UserInterface.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.monitoreoconsumodelhogar.R;
import com.example.monitoreoconsumodelhogar.UserInterface.auth.AccessActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Navegar a AccessActivity después de un retraso
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isUserAuthenticated()) {    //En el caso de que el usuario se haya autenticado anteriormente se redirige a MainActivity
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {    // Si el usuario no se ha autenticado anteriormente, se redirige a AccessActivity para que pueda iniciar sesión o registrarse.
                    Intent intent = new Intent(SplashActivity.this, AccessActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 1000); // 1 segundo de retraso
    }

    private boolean isUserAuthenticated(){
        //Almacenamos el estado de autenticacion del usuario
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isUserAuthenticated", false); // Si no se encuentra la clave, devuelve false
    }
}
