package com.example.plantalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class plant_main extends AppCompatActivity {
    TextView TV_growingDate;
    TextView TV_serviveDate;
    TextView TV_plantNickname;
    ImageView IV_plantImage;

    // 데이터베이스 부분
    plantDB helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_main);
        TV_growingDate = (TextView)findViewById(R.id.textView_plant_growing_date);
        TV_serviveDate = (TextView)findViewById(R.id.TextView_plant_servive_date);
        TV_plantNickname = (TextView) findViewById(R.id.TextView_plant_nickname);
        IV_plantImage = (ImageView) findViewById(R.id.imageView_plant_image);

        updatePlantInfoText();
        TV_plantNickname.setText(Plant.getPlantNickname());
        IV_plantImage.setImageResource(R.drawable.plant0_0);

        // 데이터베이스 부분
        helper = new plantDB(this);
        db = helper.getWritableDatabase();

        helper.initPlantLevelImage();



        // 데이터베이스 부분
        helper = new plantDB(this);
        db = helper.getWritableDatabase();

        helper.initPlantLevelImage();



    }

    public void onBtnMemoryListener(View target){
        db = helper.getReadableDatabase();

        String[] selectionArgs = {String.valueOf(Plant.typeOfPlant), String.valueOf(Plant.growthState)};
        Cursor res = db.rawQuery("SELECT image_url FROM PlantLevelImage WHERE _id_plant=? AND level=?", selectionArgs);
        String filePath = "C:/study/3/3-2/Mobile/MobileTerm/Plant-Alarm/app/src/main/res/drawable/plant"+Plant.typeOfPlant+"_"+Plant.growthState+"";
        if(res.moveToFirst()){
            int idx = res.getColumnIndex("image_url");
            if(idx != -1){
//            String filePath = res.getString(idx);

            helper.insertPlantMemory(Plant.typeOfPlant, filePath, "추억 메시지", 123);}
        }
        res.close();
        db.close();
    }
    public void plantDieListener(View target){
        Intent intent = new Intent(getApplicationContext(), plant_die.class);
        startActivity(intent);
    }

    public void plantAlbumListener(View target){
        Intent intent = new Intent(getApplicationContext(), plant_album.class);
        startActivity(intent);

    }

    public void onBtnGrowingListener(View target){
        Plant.addGrowing(1);
        updatePlantInfoText();
    }

    public void onBtnRestListener(View target){
        Plant.rest();
        updatePlantInfoText();
    }

    public void onBtnDieListener(View target){

        Intent intent = new Intent(getApplicationContext(), plant_die.class);
        startActivity(intent);
    }

    public void updatePlantInfoText(){
        String growingDate_str = "성장 "+Integer.toString(Plant.getGrowingDate())+"일 째";
        String survivieDate_str = "생존 "+Integer.toString(Plant.getServiveDate())+"일 째";
        TV_growingDate.setText(growingDate_str);
        TV_serviveDate.setText(survivieDate_str);


        if(Plant.getServiveDate()>3){
            IV_plantImage.setImageResource(R.drawable.plant0_1);
            Plant.growthState = 1;
        }
        if(Plant.getServiveDate()>6){
            IV_plantImage.setImageResource(R.drawable.plant0_2);
            Plant.growthState = 2;
        }
         if(Plant.getServiveDate()>9){

            IV_plantImage.setImageResource(R.drawable.plant0_3);
             Plant.growthState = 3;
        }
    }

    public void onBtnInsectListener(View target){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onBtnAlarmListener(View target){

        Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
        startActivity(intent);
    }


}