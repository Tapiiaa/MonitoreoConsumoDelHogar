package com.example.monitoreoconsumodelhogar;

import android.app.IntentService;
import android.content.Intent;
import java.util.Random;

public class EnergyDataService extends IntentService {

    public EnergyDataService() {
        super("EnergyDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(2000); // Simular la recopilación de datos cada 2 segundos
                float consumo = random.nextFloat() * 200; // Simular consumo aleatorio

                // Enviar datos mediante difusión (Broadcast)
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("com.example.CONSUMO_ENERGETICO");
                broadcastIntent.putExtra("consumo", consumo);
                sendBroadcast(broadcastIntent);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
