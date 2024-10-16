package com.example.monitoreoconsumodelhogar.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.monitoreoconsumodelhogar.R;

public class CreateRoomActivity extends AppCompatActivity {

    private double totalKWh = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        EditText roomTitle = findViewById(R.id.roomTitle);
        EditText roomDimensions = findViewById(R.id.roomDimensions);
        EditText roomColor = findViewById(R.id.roomColor);
        Button addLampButton = findViewById(R.id.addLampButton);
        Button addMultiplugButton = findViewById(R.id.addMultiplugButton);
        TextView kwhTextView = findViewById(R.id.kwhTextView);

        addLampButton.setOnClickListener(v -> {
            totalKWh += 0.5; // KWh fijo para una lámpara
            kwhTextView.setText("KWh: " + totalKWh);
        });

        addMultiplugButton.setOnClickListener(v -> {
            totalKWh += 1.2; // KWh fijo para un multienchufe
            kwhTextView.setText("KWh: " + totalKWh);
        });
    }
}
