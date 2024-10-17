package com.example.monitoreoconsumodelhogar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RoomDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rooms_and_halls.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_ROOMS = "rooms";
    public static final String TABLE_HALLS = "halls";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DEVICES = "devices";
    public static final String COLUMN_KWH = "kwh";
    public static final String COLUMN_DIMENSIONS = "dimensions";
    public static final String COLUMN_NUMBER = "number";

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
                + COLUMN_KWH + " REAL,"
                + COLUMN_DIMENSIONS + " TEXT"
                + ")";
        db.execSQL(CREATE_ROOMS_TABLE);

        // Crear tabla de pasillos
        String CREATE_HALLS_TABLE = "CREATE TABLE " + TABLE_HALLS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NUMBER + " INTEGER,"
                + COLUMN_DEVICES + " TEXT,"
                + COLUMN_KWH + " REAL,"
                + COLUMN_DIMENSIONS + " TEXT"
                + ")";
        db.execSQL(CREATE_HALLS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HALLS);
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

    // Método para insertar un nuevo pasillo en la base de datos
    public void addHall(int number, String devices, double kwh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NUMBER, number);
        values.put(COLUMN_DEVICES, devices);
        values.put(COLUMN_KWH, kwh);

        db.insert(TABLE_HALLS, null, values);
        db.close();
    }

    // Método para obtener todas las habitaciones
    public Cursor getAllRooms() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ROOMS, null);
    }

    // Método para obtener todos los pasillos
    public Cursor getAllHalls() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HALLS, null);
    }
}
