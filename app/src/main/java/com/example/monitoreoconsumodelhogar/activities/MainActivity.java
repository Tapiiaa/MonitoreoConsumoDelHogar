package com.example.monitoreoconsumodelhogar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.monitoreoconsumodelhogar.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Asegúrate de que este nombre coincide con tu archivo XML

        // Vincular los botones correctamente con los IDs definidos en el XML
        Button createRoomButton = findViewById(R.id.createRoomButton);
        Button createHallButton = findViewById(R.id.createHallButton);

        createRoomButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateRoomActivity.class);
            startActivity(intent);
        });

        createHallButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateHallActivity.class);
            startActivity(intent);
        });
    }
}
