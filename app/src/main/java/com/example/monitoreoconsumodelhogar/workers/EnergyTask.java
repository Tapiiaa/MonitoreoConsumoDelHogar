package com.example.monitoreoconsumodelhogar.workers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class EnergyTask implements Runnable {
    private final int taskId;
    private final Context context;

    public EnergyTask(int taskId, Context context) {
        this.taskId = taskId;
        this.context = context;
    }

    @Override
    public void run() {
        // Simular la recopilación de datos de consumo energético
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000 * (taskId + 1)); // Simular procesamiento en cada hilo
                float consumo = (float) (Math.random() * 200); // Simular consumo aleatorio

                // Enviar un broadcast para actualizar la UI
                Intent intent = new Intent("com.example.CONSUMO_ENERGETICO");
                intent.putExtra("task_id", taskId);
                intent.putExtra("consumo", consumo);
                context.sendBroadcast(intent);

                // Mostrar el progreso en la terminal
                Log.d("EnergyTask", "Hilo " + taskId + " - Consumo: " + consumo + " kWh");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

