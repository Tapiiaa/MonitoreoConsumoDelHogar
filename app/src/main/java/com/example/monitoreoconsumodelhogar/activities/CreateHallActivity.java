package com.example.monitoreoconsumodelhogar.activities;

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

        //Inicializamos el threadManager con 3 hilos y el executorService con un pool de 5 hilos
        threadManager = new ThreadManager(3);
        executorService = Executors.newFixedThreadPool(5);
        dbHelper = new RoomDatabaseHelper(this); //Iniciamos base de datos.

        EditText hallDimensions = findViewById(R.id.hallDimensions);
        EditText hallNumberEditText = findViewById(R.id.hallNumber); // Campo para identificar el pasillo.
        Button addLightButton = findViewById(R.id.addLightButton);
        Button addDeviceButton = findViewById(R.id.addDeviceButton);
        Button saveButton = findViewById(R.id.saveButton);
        Button backButton = findViewById(R.id.backButton);
        TextView kwhHallTextView = findViewById(R.id.kwhHallTextView);
        ListView devicesListView = findViewById(R.id.devicesListView);

        devicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, addedDevices);
        devicesListView.setAdapter(devicesAdapter);

        addLightButton.setOnClickListener(v -> {
            totalKWh += 0.4; // KWh fijo para una luz
            addedDevices.add("Luz");
            runOnUiThread(() -> {
                devicesAdapter.notifyDataSetChanged();
                kwhHallTextView.setText("KWh: " + totalKWh);
            });
        });

        //Mostramos el dialogo para seleccionar un dispositivo.
        addDeviceButton.setOnClickListener(v -> executorService.submit(() -> runOnUiThread(() -> showDeviceSelectionDialog(kwhHallTextView))));

        // Guardar la habitación en la base de datos
        saveButton.setOnClickListener(v -> {
            String hallNumberText = hallNumberEditText.getText().toString().trim();
            String hallDimensionsText = hallDimensions.getText().toString().trim();

            if (!hallNumberText.isEmpty()) {
                try {
                    int hallNumber = Integer.parseInt(hallNumberText);
                    String devices = String.join(", ", addedDevices);
                    dbHelper.addHall(hallNumber, devices, totalKWh);
                    Toast.makeText(this, "Pasillo guardado", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    hallNumberEditText.setError("El número del pasillo debe ser un valor numérico");
                }
            } else {
                if (hallNumberText.isEmpty()) {
                    hallNumberEditText.setError("El número del pasillo es obligatorio");
                }
                if (hallDimensionsText.isEmpty()) {
                    hallDimensions.setError("Las dimensiones del pasillo son obligatorias");
                }
            }
        });

        // Botón de volver a la MainActivity
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateHallActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }


    private void showDeviceSelectionDialog(TextView kwhHallTextView) {
        // Crear un array con los nombres de los dispositivos disponibles para el pasillo
        HallDevices[] hallDevices = HallDevices.values();
        String[] deviceNames = new String[hallDevices.length];
        for (int i = 0; i < hallDevices.length; i++) {
            deviceNames[i] = hallDevices[i].name();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona un dispositivo").setItems(deviceNames, (dialog, which) -> {
            executorService.submit(() -> {
                double selectedKwh = hallDevices[which].getKwh();
                String selectedDeviceName = hallDevices[which].name();

                // Al seleccionar un dispositivo, se añade al array de dispositivos seleccionados y se actualiza el TextView de KWh.
                totalKWh += selectedKwh;
                addedDevices.add(selectedDeviceName);

                runOnUiThread(() -> {
                    devicesAdapter.notifyDataSetChanged();
                    kwhHallTextView.setText("KWh: " + totalKWh);
                });
                threadManager.submitTask(new EnergyTask(addedDevices.size(), getApplicationContext()));
            });
        });

        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Apagar los hilos de manera controlada al destruir la actividad
        if (threadManager != null) {
            threadManager.shutdown();
        }
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
