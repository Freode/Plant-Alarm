package com.example.plantalarm;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class plant_single_memory extends AppCompatActivity {
    TextView TV_nickname;
    TextView TV_surviveDate;
    TextView TV_createdDate;
    TextView TV_message;
    ImageView IV_plantImage;
    plantDB helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_single_memory);
        TV_nickname = (TextView) findViewById(R.id.textView_single_memory);
        TV_surviveDate=(TextView) findViewById(R.id.textView_surviveDate_log);
        TV_createdDate=(TextView) findViewById(R.id.textView_createdDate_log);
        TV_message=(TextView) findViewById(R.id.textView_messageLog);
        IV_plantImage = (ImageView) findViewById(R.id.imageView_plantLog);

        helper = new plantDB(this);
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PlantMemoryList WHERE _id_memory="+PlantLog._id_plant+"", null);
        if(cursor.moveToFirst()){
            int imageID = cursor.getInt(2);
            String nickname = cursor.getString(3);
            String surviveDate = cursor.getString(4);
            String message = cursor.getString(5);
            String created = cursor.getString(6);

            TV_nickname.setText(nickname+"와의 추억");
            TV_surviveDate.setText("생존 "+surviveDate+" 일 째");
            TV_message.setText("한 마디: \""+message+"\"");
            TV_createdDate.setText("기록일: "+created);
            IV_plantImage.setImageResource(imageID);
        }
        db.close();
    }

    public void plantBackToAlbum(View target){
        Intent intent = new Intent(getApplicationContext(), plant_album.class);
        startActivity(intent);
    }

    public void onBtnDeleteListener(View target){
        helper = new plantDB(this);
        db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM PlantMemoryList WHERE _id_memory = "+PlantLog._id_plant+"");
        db.close();
        Toast.makeText(plant_single_memory.this, "추억이 삭제되었습니다!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), plant_album.class);
        startActivity(intent);
    }
}