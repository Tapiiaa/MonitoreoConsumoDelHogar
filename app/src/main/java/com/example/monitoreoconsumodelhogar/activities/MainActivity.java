package com.example.monitoreoconsumodelhogar.activities;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.monitoreoconsumodelhogar.R;
import com.example.monitoreoconsumodelhogar.ViewRoomsActivity;

public class MainActivity extends AppCompatActivity {

    private Button createRoomButton;
    private Button createHallButton;
    private Button viewRoomsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createRoomButton = findViewById(R.id.createRoomButton);
        createHallButton = findViewById(R.id.createHallButton);
        viewRoomsButton = findViewById(R.id.viewRoomsButton);

        createRoomButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateRoomActivity.class);
            startActivity(intent);
        });

        createHallButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateHallActivity.class);
            startActivity(intent);
        });

        viewRoomsButton.setOnClickListener(v -> {
            // Redirigir a la actividad ViewRoomsActivity
            Intent intent = new Intent(MainActivity.this, ViewRoomsActivity.class);
            startActivity(intent);
        });
    }
}
