package com.example.monitoreoconsumodelhogar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.example.monitoreoconsumodelhogar.services.EnergyDataService;

public class MainActivity extends AppCompatActivity {

    private TextView energyDataView;
    private TextView recommendationsView;
    private EnergyDataReceiver energyDataReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        energyDataView = findViewById(R.id.energy_data);
        recommendationsView = findViewById(R.id.recommendations);

        // Registrar receptor
        energyDataReceiver = new EnergyDataReceiver();
        IntentFilter filter = new IntentFilter("com.example.CONSUMO_ENERGETICO");
        registerReceiver(energyDataReceiver, filter, Context.RECEIVER_NOT_EXPORTED);

        // Iniciar el servicio en segundo plano
        Intent intent = new Intent(this, EnergyDataService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(energyDataReceiver); // Desregistrar el receptor al destruir la actividad
    }

    // Clase interna del BroadcastReceiver
    public class EnergyDataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            float consumo = intent.getFloatExtra("consumo", 0);
            energyDataView.setText("Consumo Energético: " + consumo + " kWh");

            if (consumo > 100) {
                recommendationsView.setText("Alto consumo, apaga dispositivos innecesarios.");
            } else {
                recommendationsView.setText("Consumo bajo, sigue así.");
            }
        }
    }
}
