package com.example.monitoreoconsumodelhogar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView energyDataView;
    private TextView recommendationsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        energyDataView = findViewById(R.id.energy_data);
        recommendationsView = findViewById(R.id.recommendations);

        // Iniciar el servicio en segundo plano
        Intent intent = new Intent(this, EnergyDataService.class);
        startService(intent);

        // Registrar receptor de difusión para recibir los datos
        registerReceiver(energyDataReceiver, new IntentFilter("com.example.CONSUMO_ENERGETICO"));
    }

    // Receptor de difusión para obtener datos de consumo
    private final BroadcastReceiver energyDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            float consumo = intent.getFloatExtra("consumo", 0);
            energyDataView.setText("Consumo Energético: " + consumo + " kWh");

            // Mostrar recomendaciones simples
            if (consumo > 100) {
                recommendationsView.setText("Alto consumo, apaga dispositivos innecesarios.");
            } else {
                recommendationsView.setText("Consumo bajo, sigue así.");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(energyDataReceiver);
    }
}
