package com.example.monitoreoconsumodelhogar.activities;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.monitoreoconsumodelhogar.Enums.BathroomDevices;
import com.example.monitoreoconsumodelhogar.Enums.BedroomDevices;
import com.example.monitoreoconsumodelhogar.Enums.HallDevices;
import com.example.monitoreoconsumodelhogar.Enums.KitchenDevices;
import com.example.monitoreoconsumodelhogar.Enums.LivingroomDevices;
import com.example.monitoreoconsumodelhogar.R;
import com.example.monitoreoconsumodelhogar.RoomDatabaseHelper;
import com.example.monitoreoconsumodelhogar.threads.ThreadManager;
import com.example.monitoreoconsumodelhogar.threads.EnergyTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateRoomActivity extends AppCompatActivity {

    private double totalKWh = 0.0;
    private List<String> addedDevices = new ArrayList<>();
    private ArrayAdapter<String> devicesAdapter;
    private ThreadManager threadManager;
    private ExecutorService executorService;
    private RoomDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        threadManager = new ThreadManager(3);
        executorService = Executors.newFixedThreadPool(5);

        executorService.submit(() -> {
            try {
                Thread.sleep(500);
                runOnUiThread(() -> Log.d("CreateRoomActivity", "Recursos iniciales cargados"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        dbHelper = new RoomDatabaseHelper(this);  // Iniciar base de datos

        EditText roomTitle = findViewById(R.id.roomTitle);
        EditText roomDimensions = findViewById(R.id.roomDimensions);
        Button addMultiplugButton = findViewById(R.id.addMultiplugButton);
        Button addDeviceButton = findViewById(R.id.addDeviceButton);
        Button saveButton = findViewById(R.id.saveButton); // Botón para guardar la habitación
        Button backButton = findViewById(R.id.backButton); // Botón para volver a la MainActivity
        TextView kwhTextView = findViewById(R.id.kwhTextView);
        ListView devicesListView = findViewById(R.id.devicesListView);

        devicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, addedDevices);
        devicesListView.setAdapter(devicesAdapter);

        roomTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    roomTitle.setError("Este campo es obligatorio");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        addMultiplugButton.setOnClickListener(v -> {
            totalKWh += 1.2; // KWh fijo para un multienchufe
            addedDevices.add("Multienchufe");
            devicesAdapter.notifyDataSetChanged();

            if (addedDevices.size() >= 5) {
                addMultiplugButton.setEnabled(false);
            }
            kwhTextView.setText("KWh: " + totalKWh);

            threadManager.submitTask(new EnergyTask(addedDevices.size(), getApplicationContext()));
        });

        addDeviceButton.setOnClickListener(v -> showDeviceSelectionDialog(kwhTextView));

        // Guardar la habitación en la base de datos
        saveButton.setOnClickListener(v -> {
            String roomName = roomTitle.getText().toString();
            if (!roomName.isEmpty()) {
                String devices = String.join(", ", addedDevices);
                dbHelper.addRoom(roomName, devices, totalKWh);
                Toast.makeText(this, "Habitación guardada", Toast.LENGTH_SHORT).show();
            } else {
                roomTitle.setError("El nombre de la habitación es obligatorio");
            }
        });

        // Botón de volver a la MainActivity
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateRoomActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void showDeviceSelectionDialog(TextView kwhTextView) {
        // Crear un array con los nombres de los dispositivos
        KitchenDevices[] kitchenDevices = KitchenDevices.values();
        BedroomDevices[] bedroomDevices = BedroomDevices.values();
        HallDevices[] hallDevices = HallDevices.values();
        LivingroomDevices[] livingroomDevices = LivingroomDevices.values();
        BathroomDevices[] bathroomDevices = BathroomDevices.values();

        int totalDevices = kitchenDevices.length + bedroomDevices.length + hallDevices.length + livingroomDevices.length + bathroomDevices.length;
        String[] deviceNames = new String[totalDevices];
        int index = 0;

        for (KitchenDevices device : kitchenDevices) {
            deviceNames[index] = "Cocina - " + device.name();
            index++;
        }

        for (BedroomDevices device : bedroomDevices) {
            deviceNames[index] = "Dormitorio - " + device.name();
            index++;
        }

        for (HallDevices device : hallDevices) {
            deviceNames[index] = "Hall - " + device.name();
            index++;
        }

        for (LivingroomDevices device : livingroomDevices) {
            deviceNames[index] = "Livingroom - " + device.name();
            index++;
        }

        for (BathroomDevices device : bathroomDevices) {
            deviceNames[index] = "Baño - " + device.name();
            index++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona un dispositivo").setItems(deviceNames, (dialog, which) -> {
            double selectedKwh = 0.0;
            String selectedDeviceName = deviceNames[which];

            totalKWh += selectedKwh;
            addedDevices.add(selectedDeviceName);
            devicesAdapter.notifyDataSetChanged();
            kwhTextView.setText("KWh: " + totalKWh);
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

