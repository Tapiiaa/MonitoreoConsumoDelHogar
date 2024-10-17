package com.example.monitoreoconsumodelhogar.UserInterface.graphs;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.monitoreoconsumodelhogar.view.PieChartView;
import com.example.monitoreoconsumodelhogar.R;
import com.example.monitoreoconsumodelhogar.data.database.RoomDatabaseHelper;

public class GraphActivity extends AppCompatActivity {

    private RoomDatabaseHelper dbHelper;
    private double[] kwhRoomData;
    private String[] roomNames;
    private double[] kwhHallData;
    private String[] hallNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        dbHelper = new RoomDatabaseHelper(this);
        loadGraphData();

        LinearLayout graphLayout = findViewById(R.id.graphLayout);

        // Crear gráfico para habitaciones
        PieChartView roomPieChartView = new PieChartView(this, kwhRoomData, roomNames);
        graphLayout.addView(roomPieChartView);

        // Crear gráfico para pasillos
        PieChartView hallPieChartView = new PieChartView(this, kwhHallData, hallNames);
        graphLayout.addView(hallPieChartView);
    }

    private void loadGraphData() {
        // Cargar datos de las habitaciones
        Cursor roomCursor = dbHelper.getAllRooms();
        int roomCount = roomCursor.getCount();
        kwhRoomData = new double[roomCount];
        roomNames = new String[roomCount];
        int roomIndex = 0;

        if (roomCursor != null && roomCursor.moveToFirst()) {
            do {
                double kwh = roomCursor.getDouble(roomCursor.getColumnIndex(RoomDatabaseHelper.COLUMN_KWH));
                String roomName = roomCursor.getString(roomCursor.getColumnIndex(RoomDatabaseHelper.COLUMN_NAME));

                kwhRoomData[roomIndex] = kwh;
                roomNames[roomIndex] = roomName;
                roomIndex++;
            } while (roomCursor.moveToNext());
        }

        // Cargar datos de los pasillos
        Cursor hallCursor = dbHelper.getAllHalls();
        int hallCount = hallCursor.getCount();
        kwhHallData = new double[hallCount];
        hallNames = new String[hallCount];
        int hallIndex = 0;

        if (hallCursor != null && hallCursor.moveToFirst()) {
            do {
                double kwh = hallCursor.getDouble(hallCursor.getColumnIndex(RoomDatabaseHelper.COLUMN_KWH));
                String hallName = "Pasillo " + hallCursor.getInt(hallCursor.getColumnIndex(RoomDatabaseHelper.COLUMN_NUMBER));

                kwhHallData[hallIndex] = kwh;
                hallNames[hallIndex] = hallName;
                hallIndex++;
            } while (hallCursor.moveToNext());
        }
    }
}
