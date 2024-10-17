package com.example.monitoreoconsumodelhogar;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

public class GraphActivity extends AppCompatActivity {

    private RoomDatabaseHelper dbHelper;
    private double[] kwhData;
    private String[] roomNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        dbHelper = new RoomDatabaseHelper(this);
        loadGraphData();

        LinearLayout graphLayout = findViewById(R.id.graphLayout);
        PieChartView pieChartView = new PieChartView(this, kwhData, roomNames);
        graphLayout.addView(pieChartView);
    }

    private void loadGraphData() {
        Cursor cursor = dbHelper.getAllRooms();
        int count = cursor.getCount();
        kwhData = new double[count];
        roomNames = new String[count];
        int index = 0;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                double kwh = cursor.getDouble(cursor.getColumnIndex(RoomDatabaseHelper.COLUMN_KWH));
                String roomName = cursor.getString(cursor.getColumnIndex(RoomDatabaseHelper.COLUMN_NAME));

                kwhData[index] = kwh;
                roomNames[index] = roomName;
                index++;
            } while (cursor.moveToNext());
        }
    }
}
