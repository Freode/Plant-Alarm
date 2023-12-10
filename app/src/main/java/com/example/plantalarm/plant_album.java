package com.example.plantalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class plant_album extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_album);
    }

    public void plantBackToMain(View target){
        Intent intent = new Intent(getApplicationContext(), plant_main.class);
        startActivity(intent);
    }

    public void plantSingleMemoryListener(View target){
        Intent intent = new Intent(getApplicationContext(), plant_single_memory.class);
        startActivity(intent);
    }
}