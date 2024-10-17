package com.example.monitoreoconsumodelhogar.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.monitoreoconsumodelhogar.R;
import com.example.monitoreoconsumodelhogar.threads.ThreadManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SeeRoomActivity extends AppCompatActivity {

    private ThreadManager threadManager;
    private ExecutorService executorService;
    private List<String> rooms = new ArrayList<>();
    private ArrayAdapter<String> roomsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_room_layout);

    }
}
