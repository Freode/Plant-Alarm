package com.example.plantalarm;

import static java.sql.Types.NULL;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class plant_album extends AppCompatActivity {

    plantDB helper;
    SQLiteDatabase db;
    LinearLayout LL_linearAlbum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_album);
        LL_linearAlbum = (LinearLayout) findViewById(R.id.LinearLayout_album);

        helper = new plantDB(this);
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM PlantMemoryList ", null);
        int _id_memory;
        String image_url;
        String nickname;
        int surviveDate;
        String message;
        String created;

        while(cursor.moveToNext()){
            _id_memory = cursor.getInt(0);
            image_url = cursor.getString(2);
            nickname = cursor.getString(3);
            surviveDate = cursor.getInt(4);
            message = cursor.getString(5);
            created = cursor.getString(6);

            makeImageView(_id_memory, image_url, nickname, surviveDate, message,created);

        }
    }

    public void makeImageView(int _id_memory, String image_url, String nickname, int surviveDate, String message, String created){

        LinearLayout totalLayout = new LinearLayout(this);
        totalLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout infoList = new LinearLayout(this);
        infoList.setOrientation(LinearLayout.VERTICAL);

        // 이미지 뷰 파일 경로로 이미지 추가
        ImageView IV_plantImage = new ImageView(this);
        Bitmap bitmap = BitmapFactory.decodeFile(image_url);
        IV_plantImage.setImageBitmap(bitmap);

        // 식물 기록 TextView 추가
        TextView TV_id_memory = new TextView(this);
        TV_id_memory.setText(_id_memory);
        TextView TV_nickname = new TextView(this);
        TV_id_memory.setText(nickname);
        TextView TV_surviveDate = new TextView(this);
        TV_id_memory.setText(surviveDate+"");
        TextView TV_meesage = new TextView(this);
        TV_id_memory.setText(message);
        TextView TV_created = new TextView(this);
        TV_id_memory.setText(created);

        infoList.addView(TV_id_memory);
        infoList.addView(TV_nickname);
        infoList.addView(TV_surviveDate);
        infoList.addView(TV_meesage);
        infoList.addView(TV_created);
        // 추억 한 칸 레이아웃 완성하기
        totalLayout.addView(IV_plantImage);
        totalLayout.addView(infoList);

        LL_linearAlbum.addView(totalLayout);

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