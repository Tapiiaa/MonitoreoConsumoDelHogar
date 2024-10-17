package com.example.monitoreoconsumodelhogar.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.monitoreoconsumodelhogar.R;
import com.example.monitoreoconsumodelhogar.threads.ThreadManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SeeRoomActivity extends AppCompatActivity {

    private ThreadManager threadManager;
    private ExecutorService executorService;
    private List<String> rooms = new ArrayList<>();
    private ArrayAdapter<String> roomsAdapter;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_room_layout);

        threadManager = new ThreadManager(3);
        executorService = Executors.newFixedThreadPool(5);

        ListView roomsListView = findViewById(R.id.roomsListView);

        roomsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rooms);
        roomsListView.setAdapter(roomsAdapter);

        // Inicializar la referencia a la base de datos Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("rooms");

        executorService.submit(() -> {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    rooms.clear(); // Limpiar la lista antes de agregar nuevos datos
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String room = snapshot.getValue(String.class);
                        if (room != null) {
                            rooms.add(room);
                        }
                    }
                    // Actualizar la interfaz de usuario en el hilo principal
                    runOnUiThread(() -> roomsAdapter.notifyDataSetChanged());
                    Log.d("SeeRoomActivity", "Habitaciones cargadas correctamente desde Firebase");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("SeeRoomActivity", "Error al cargar los datos de Firebase: " + databaseError.getMessage());
                }
            });
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Apagar los hilos de manera controlada al destruir la actividad
        if (threadManager != null) {
            threadManager.shutdown();
        }
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
