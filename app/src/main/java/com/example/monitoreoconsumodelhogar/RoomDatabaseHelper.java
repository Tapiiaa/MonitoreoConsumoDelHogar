package com.example.monitoreoconsumodelhogar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RoomDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rooms.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_ROOMS = "rooms";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DEVICES = "devices";
    public static final String COLUMN_KWH = "kwh";

    public RoomDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de habitaciones
        String CREATE_ROOMS_TABLE = "CREATE TABLE " + TABLE_ROOMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DEVICES + " TEXT,"
                + COLUMN_KWH + " REAL"
                + ")";
        db.execSQL(CREATE_ROOMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
        onCreate(db);
    }

    // Método para insertar una nueva habitación en la base de datos
    public void addRoom(String name, String devices, double kwh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DEVICES, devices);
        values.put(COLUMN_KWH, kwh);

        db.insert(TABLE_ROOMS, null, values);
        db.close();
    }

    // Método para obtener todas las habitaciones
    public Cursor getAllRooms() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ROOMS, null);
    }
}
