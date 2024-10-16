package com.example.monitoreoconsumodelhogar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView[] taskViews = new TextView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar TextViews
        taskViews[0] = findViewById(R.id.task_view_1);
        taskViews[1] = findViewById(R.id.task_view_2);
        taskViews[2] = findViewById(R.id.task_view_3);
        taskViews[3] = findViewById(R.id.task_view_4);

        // Registrar receptor con el atributo adecuado
        IntentFilter filter = new IntentFilter("com.example.CONSUMO_ENERGETICO");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(energyDataReceiver, filter, Context.RECEIVER_NOT_EXPORTED);  // Indica si es exportado o no
        }
    }

    private final BroadcastReceiver energyDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int taskId = intent.getIntExtra("task_id", -1);
            float consumo = intent.getFloatExtra("consumo", 0);

            if (taskId != -1) {
                taskViews[taskId % 4].setText("Hilo " + taskId + ": " + consumo + " kWh");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(energyDataReceiver);
    }
}
