package com.example.monitoreoconsumodelhogar.threads;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class EnergyDataWorker extends Worker {

    public EnergyDataWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Simular la recopilación de datos en segundo plano
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000); // Simular la recopilación de datos cada segundo
                float consumo = (float) (Math.random() * 200); // Simular consumo aleatorio

                // Enviar un broadcast para actualizar la UI
                Intent intent = new Intent("com.example.CONSUMO_ENERGETICO");
                intent.putExtra("task_id", i);
                intent.putExtra("consumo", consumo);
                getApplicationContext().sendBroadcast(intent);

                // Mostrar en la terminal el progreso
                Log.d("EnergyDataWorker", "Consumo: " + consumo + " kWh");
            } catch (InterruptedException e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        return Result.success();
    }
}
