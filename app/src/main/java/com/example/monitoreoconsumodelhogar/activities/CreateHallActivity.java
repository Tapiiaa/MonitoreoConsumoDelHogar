package com.example.monitoreoconsumodelhogar.activities;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.monitoreoconsumodelhogar.Enums.HallDevices;
import com.example.monitoreoconsumodelhogar.R;
import com.example.monitoreoconsumodelhogar.RoomDatabaseHelper;
import com.example.monitoreoconsumodelhogar.threads.EnergyTask;
import com.example.monitoreoconsumodelhogar.threads.ThreadManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateHallActivity extends AppCompatActivity {

    private double totalKWh = 0.0;
    private ThreadManager threadManager;
    private ExecutorService executorService;
    private List<String> addedDevices = new ArrayList<>();
    private ArrayAdapter<String> devicesAdapter;
    private RoomDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hall);

        threadManager = new ThreadManager(3);
        executorService = Executors.newFixedThreadPool(5);

        dbHelper = new RoomDatabaseHelper(this);  // Iniciar base de datos

        EditText hallDimensions = findViewById(R.id.hallDimensions);
        EditText hallNumber = findViewById(R.id.hallNumber); // Campo para identificar el pasillo.
        Button addLightButton = findViewById(R.id.addLightButton);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button addDeviceButton = findViewById(R.id.addDeviceButton);
        Button saveButton = findViewById(R.id.saveButton);  // Botón para guardar el pasillo
        Button backButton = findViewById(R.id.backButton);  // Botón para volver a la MainActivity
        TextView kwhHallTextView = findViewById(R.id.kwhHallTextView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ListView devicesListView = findViewById(R.id.devicesListView);

        devicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, addedDevices);
        devicesListView.setAdapter(devicesAdapter);

        addLightButton.setOnClickListener(v -> {
            totalKWh += 0.4;
            addedDevices.add("Luz");
            devicesAdapter.notifyDataSetChanged();
            kwhHallTextView.setText("KWh: " + totalKWh);
        });

        addDeviceButton.setOnClickListener(v -> showDeviceSelectionDialog(kwhHallTextView));

        // Guardar el pasillo en la base de datos
        saveButton.setOnClickListener(v -> {
            String hallName = "Pasillo " + hallNumber.getText().toString();
            if (!hallName.isEmpty()) {
                String devices = String.join(", ", addedDevices);
                dbHelper.addRoom(hallName, devices, totalKWh);
                Toast.makeText(this, "Pasillo guardado", Toast.LENGTH_SHORT).show();
            } else {
                hallNumber.setError("El número del pasillo es obligatorio");
            }
        });

        // Botón de volver a la MainActivity
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateHallActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void showDeviceSelectionDialog(TextView kwhHallTextView) {
        HallDevices[] hallDevices = HallDevices.values();
        String[] deviceNames = new String[hallDevices.length];
        for (int i = 0; i < hallDevices.length; i++) {
            deviceNames[i] = hallDevices[i].name();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona un dispositivo").setItems(deviceNames, (dialog, which) -> {
            double selectedKwh = hallDevices[which].getKwh();
            String selectedDeviceName = hallDevices[which].name();

            totalKWh += selectedKwh;
            addedDevices.add(selectedDeviceName);
            devicesAdapter.notifyDataSetChanged();
            kwhHallTextView.setText("KWh: " + totalKWh);
        });

        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (threadManager != null) {
            threadManager.shutdown();
        }
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
