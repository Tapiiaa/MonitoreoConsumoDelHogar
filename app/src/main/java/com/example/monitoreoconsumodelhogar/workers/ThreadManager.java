package com.example.monitoreoconsumodelhogar.workers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    private ExecutorService executorService;

    public ThreadManager(int threadCount) {
        // Crear un pool de hilos con el número de hilos especificado
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    public void submitTask(Runnable task) {
        executorService.submit(task); // Enviar una tarea al pool de hilos
    }

    public void shutdown() {
        // Apagar los hilos de manera controlada
        executorService.shutdown();
    }
}

