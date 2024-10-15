package com.example.monitoreoconsumodelhogar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import java.text.BreakIterator;

public class EnergyDataReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        float consumo = intent.getFloatExtra("consumo", 0);
        BreakIterator energyDataView = null;
        energyDataView.setText("Consumo Energético: " + consumo + " kWh");

        BreakIterator recommendationsView = null;
        if (consumo > 100) {
            recommendationsView.setText("Alto consumo, apaga dispositivos innecesarios.");
        } else {
            recommendationsView.setText("Consumo bajo, sigue así.");
        }
    }
}
