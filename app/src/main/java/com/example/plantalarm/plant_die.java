package com.example.plantalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class plant_die extends AppCompatActivity {
    TextView TV_plantDie;
    TextView TV_dateLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_die);
        TV_plantDie = (TextView) findViewById(R.id.textView_plant_die);
        TV_dateLog = (TextView) findViewById(R.id.textView_date_log);
        TV_plantDie.setText(Plant.getPlantNickname()+" 사망...");
        TV_dateLog.setText("총 "+Plant.getServiveDate()+" 일 생존하였습니다.");
    }

    public void plantSelectListener(View target){
        Intent intent = new Intent(getApplicationContext(), plant_select.class);
        startActivity(intent);
    }
}
