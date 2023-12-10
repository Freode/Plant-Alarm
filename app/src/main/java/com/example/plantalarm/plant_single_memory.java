package com.example.plantalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class plant_single_memory extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_single_memory);
    }

    public void plantBackToAlbum(View target){
        Intent intent = new Intent(getApplicationContext(), plant_album.class);
        startActivity(intent);
    }
}