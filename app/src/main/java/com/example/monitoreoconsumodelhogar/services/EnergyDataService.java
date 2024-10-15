package com.example.monitoreoconsumodelhogar.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EnergyDataService extends Service {

    private ExecutorService executorService;

    @Override
    public void onCreate() {
        super.onCreate();
        // Inicializar ExecutorService con un solo hilo para minimizar el impacto en el rendimiento
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Ejecutar tarea en segundo plano usando un hilo
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(2000); // Simular la recopilación de datos cada 2 segundos
                        float consumo = (float) (Math.random() * 200); // Simular consumo energético

                        // Enviar los datos a MainActivity mediante Broadcast
                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction("com.example.CONSUMO_ENERGETICO");
                        broadcastIntent.putExtra("consumo", consumo);
                        sendBroadcast(broadcastIntent);

                        Log.d("EnergyDataService", "Consumo de energía: " + consumo + " kWh");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return START_STICKY; // Mantener el servicio corriendo
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executorService.shutdown(); // Apagar el ExecutorService cuando el servicio se detenga
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // No se usa binding en este caso
    }
}
