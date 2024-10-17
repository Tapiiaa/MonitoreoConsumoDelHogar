package com.example.monitoreoconsumodelhogar.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.MergeCursor;

import com.example.monitoreoconsumodelhogar.R;
import com.example.monitoreoconsumodelhogar.database.RoomDatabaseHelper;

public class ViewRoomsActivity extends AppCompatActivity {

    private RoomDatabaseHelper dbHelper;
    private ListView roomsListView;
    private Button viewGraphButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooms);

        dbHelper = new RoomDatabaseHelper(this);
        roomsListView = findViewById(R.id.roomsListView);
        viewGraphButton = findViewById(R.id.viewGraphButton); // Botón "Ver gráfico"

        // Obtener todas las habitaciones y pasillos de la base de datos
        Cursor roomCursor = dbHelper.getAllRooms();
        Cursor hallCursor = dbHelper.getAllHalls();

        if ((roomCursor != null && roomCursor.getCount() > 0) || (hallCursor != null && hallCursor.getCount() > 0)) {
            // Combinar los cursores de habitaciones y pasillos
            MergeCursor mergeCursor = new MergeCursor(new Cursor[]{roomCursor, hallCursor});

            String[] from = {RoomDatabaseHelper.COLUMN_NAME, RoomDatabaseHelper.COLUMN_DEVICES, RoomDatabaseHelper.COLUMN_KWH};
            int[] to = {R.id.roomName, R.id.roomDevices, R.id.roomKwh};

            // Adaptador para habitaciones y pasillos combinados
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.room_item, mergeCursor, from, to, 0);
            roomsListView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No hay habitaciones o pasillos guardados", Toast.LENGTH_SHORT).show();
        }

        viewGraphButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewRoomsActivity.this, GraphActivity.class);
            startActivity(intent);
        });
    }
}
