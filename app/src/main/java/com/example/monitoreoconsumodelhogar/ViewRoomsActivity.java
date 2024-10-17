package com.example.monitoreoconsumodelhogar;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ViewRoomsActivity extends AppCompatActivity {

    private RoomDatabaseHelper dbHelper;
    private ListView roomsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooms);

        dbHelper = new RoomDatabaseHelper(this);
        roomsListView = findViewById(R.id.roomsListView);

        // Obtener todas las habitaciones y pasillos de la base de datos
        Cursor cursor = dbHelper.getAllRooms();

        // Verificar si el cursor tiene datos
        if (cursor != null && cursor.getCount() > 0) {
            // Definir las columnas de la base de datos a usar
            String[] from = {RoomDatabaseHelper.COLUMN_NAME, RoomDatabaseHelper.COLUMN_DEVICES, RoomDatabaseHelper.COLUMN_KWH};
            // Definir los IDs de las vistas donde se mostrarán los datos
            int[] to = {R.id.roomName, R.id.roomDevices, R.id.roomKwh};

            // Crear un SimpleCursorAdapter para vincular los datos de la base de datos con las vistas
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.room_item, cursor, from, to, 0);
            roomsListView.setAdapter(adapter);
        } else {
            // Si no hay datos, mostrar un mensaje
            Toast.makeText(this, "No hay habitaciones o pasillos guardados", Toast.LENGTH_SHORT).show();
        }
    }
}

