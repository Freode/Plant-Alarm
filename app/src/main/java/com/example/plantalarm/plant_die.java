package com.example.plantalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class plant_die extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_die);
    }

    public void plantSelectListener(View target){
        Intent intent = new Intent(getApplicationContext(), plant_select.class);
        startActivity(intent);
    }
}