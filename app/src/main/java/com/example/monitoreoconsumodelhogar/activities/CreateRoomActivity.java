package com.example.monitoreoconsumodelhogar.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.monitoreoconsumodelhogar.Enums.BathroomDevices;
import com.example.monitoreoconsumodelhogar.Enums.BedroomDevices;
import com.example.monitoreoconsumodelhogar.Enums.HallDevices;
import com.example.monitoreoconsumodelhogar.Enums.KitchenDevices;
import com.example.monitoreoconsumodelhogar.Enums.LivingroomDevices;
import com.example.monitoreoconsumodelhogar.R;

import java.util.ArrayList;
import java.util.List;

public class CreateRoomActivity extends AppCompatActivity {

    private double totalKWh = 0.0;
    private List<String> addedDevices = new ArrayList<>();
    private ArrayAdapter<String> devicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        EditText roomTitle = findViewById(R.id.roomTitle);
        EditText roomDimensions = findViewById(R.id.roomDimensions);
        Button addMultiplugButton = findViewById(R.id.addMultiplugButton);
        Button addDeviceButton = findViewById(R.id.addDeviceButton);
        TextView kwhTextView = findViewById(R.id.kwhTextView);
        ListView devicesListView = findViewById(R.id.devicesListView);

        devicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, addedDevices);
        devicesListView.setAdapter(devicesAdapter);

        //Asegurarse de que el título de la habitación no esté vacío.
        roomTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    roomTitle.setError("Este campo es obligatorio");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        addMultiplugButton.setOnClickListener(v -> {
            totalKWh += 1.2; // KWh fijo para un multienchufe
            kwhTextView.setText("KWh: " + totalKWh);
        });

        addDeviceButton.setOnClickListener(v -> {
            showDeviceSelectionDialog(kwhTextView);
        });
    }

    private void showDeviceSelectionDialog(TextView kwhTextView){

        //Crear un array con los nombres de los dispositivos de las habitaciones.
        KitchenDevices[] kitchenDevices = KitchenDevices.values();
        BedroomDevices[] bedroomDevices = BedroomDevices.values();
        HallDevices[] hallDevices = HallDevices.values();
        LivingroomDevices[] livingroomDevices = LivingroomDevices.values();
        BathroomDevices[] bathroomDevices = BathroomDevices.values();

        //Crear un array con los nombres de los dispositivos de las habitaciones.
        int totalDevices = kitchenDevices.length + bedroomDevices.length + hallDevices.length + livingroomDevices.length + bathroomDevices.length;
        String[] deviceNames = new String[totalDevices];
        int index = 0;

        //Recorremos los arrays de los dispositivos y los añadimos al array de nombres de dispositivos.
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

           if (which < kitchenDevices.length){
               selectedKwh = kitchenDevices[which].getKwh();
               selectedDeviceName = "Cocina - " + kitchenDevices[which].name();
           } else if (which < kitchenDevices.length + livingroomDevices.length) {
               selectedKwh = livingroomDevices[which - kitchenDevices.length].getKwh();
               selectedDeviceName = "Livingroom - " + livingroomDevices[which - kitchenDevices.length].name();
           } else if (which < kitchenDevices.length + livingroomDevices.length + bedroomDevices.length) {
               selectedKwh = bedroomDevices[which - kitchenDevices.length - livingroomDevices.length].getKwh();
               selectedDeviceName = "Dormitorio - " + bedroomDevices[which - kitchenDevices.length - livingroomDevices.length].name();
           } else if(which < kitchenDevices.length + livingroomDevices.length + bedroomDevices.length + hallDevices.length){
               selectedKwh = hallDevices[which - kitchenDevices.length - livingroomDevices.length - bedroomDevices.length].getKwh();
               selectedDeviceName = "Hall - " + hallDevices[which - kitchenDevices.length - livingroomDevices.length - bedroomDevices.length].name();
           }

           //Al seleccionar un dispositivo, se añade al array de dispositivos seleccionados y se actualiza el TextView de KWh.
           totalKWh += selectedKwh;
           addedDevices.add(selectedDeviceName);
           devicesAdapter.notifyDataSetChanged();
           kwhTextView.setText("KWh: " + totalKWh);
        });

        builder.create().show();
    }

}
