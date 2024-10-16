package com.example.monitoreoconsumodelhogar.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.monitoreoconsumodelhogar.R;

public class CreateHallActivity extends AppCompatActivity {

    private double totalKWh = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hall);

        EditText hallDimensions = findViewById(R.id.hallDimensions);
        EditText hallColor = findViewById(R.id.hallColor);
        Button addLightButton = findViewById(R.id.addLightButton);
        Button addSensorButton = findViewById(R.id.addSensorButton);
        TextView kwhHallTextView = findViewById(R.id.kwhHallTextView);

        addLightButton.setOnClickListener(v -> {
            totalKWh += 0.4; // KWh fijo para una luz
            kwhHallTextView.setText("KWh: " + totalKWh);
        });

        addSensorButton.setOnClickListener(v -> {
            totalKWh += 0.7; // KWh fijo para un sensor
            kwhHallTextView.setText("KWh: " + totalKWh);
        });
    }
}
