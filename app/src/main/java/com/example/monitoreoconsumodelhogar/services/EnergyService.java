package com.example.monitoreoconsumodelhogar.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.monitoreoconsumodelhogar.threads.EnergyTask;
import com.example.monitoreoconsumodelhogar.threads.ThreadManager;

public class EnergyService extends Service {
    private ThreadManager threadManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("EnergyService", "Servicio creado");
        threadManager = new ThreadManager(4); // Iniciar con 4 hilos de ejemplo
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Iniciar tareas en paralelo
        for (int i = 0; i < 4; i++) {
            threadManager.submitTask(new EnergyTask(i, this)); // Iniciar tarea por cada hilo
        }

        return START_STICKY; // Mantener el servicio corriendo
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        threadManager.shutdown(); // Apagar el pool de hilos
        Log.d("EnergyService", "Servicio destruido");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // No se utiliza binding en este caso
    }
}
