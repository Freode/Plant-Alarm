package com.example.plantalarm;

import static java.sql.Types.NULL;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
        int image_url;
        String nickname;
        int surviveDate;
        String message;
        String created;

        while(cursor.moveToNext()){
            _id_memory = cursor.getInt(0);
            image_url = cursor.getInt(2);
            nickname = cursor.getString(3);
            surviveDate = cursor.getInt(4);
            message = cursor.getString(5);
            created = cursor.getString(6);

            makeImageView(_id_memory, image_url, nickname, surviveDate, message,created);

        }
    }

    public void makeImageView(int _id_memory, int imageID, String nickname, int surviveDate, String message, String created){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // width
                LinearLayout.LayoutParams.WRAP_CONTENT // height
        );

        LinearLayout.LayoutParams IV_layoutParams = new LinearLayout.LayoutParams(
                300,300
        );

        LinearLayout totalLayout = new LinearLayout(this);
        totalLayout.setOrientation(LinearLayout.HORIZONTAL);
        totalLayout.setLayoutParams(layoutParams);
        totalLayout.setPadding(10, 10, 10, 10);

        LinearLayout infoList = new LinearLayout(this);
        infoList.setOrientation(LinearLayout.VERTICAL);
        infoList.setLayoutParams(layoutParams);
        infoList.setPadding(10, 10, 10, 10);

        // 이미지 뷰 파일 경로로 이미지 추가
        ImageView IV_plantImage = new ImageView(this);


        IV_plantImage.setImageResource(imageID);

//        IV_plantImage.setImageResource(R.drawable.plant0_2);
        IV_plantImage.setLayoutParams(layoutParams);

//        IV_plantImage.setPadding(10, 10, 10, 10);

        // 식물 기록 TextView 추가
        TextView TV_id_memory = new TextView(this);
        TV_id_memory.setText(_id_memory+"");
        TV_id_memory.setLayoutParams(layoutParams);
        TextView TV_nickname = new TextView(this);
        TV_nickname.setText("이름: "+nickname);
        TV_nickname.setLayoutParams(layoutParams);
        TextView TV_surviveDate = new TextView(this);
        TV_surviveDate.setText("성장한 지 "+surviveDate+" 일 째");
        TV_surviveDate.setLayoutParams(layoutParams);
        TextView TV_meesage = new TextView(this);
        TV_meesage.setText("한 마디: "+message);
        TV_meesage.setLayoutParams(layoutParams);
        TextView TV_created = new TextView(this);
        TV_created.setText("기록 일자: "+created);
        TV_created.setLayoutParams(layoutParams);
        TV_id_memory.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                int id = Integer.parseInt(((TextView)view).getText().toString());
                PlantLog._id_plant = id;
                Intent intent = new Intent(plant_album.this, plant_single_memory.class);
                startActivity(intent);
            }
        });

        infoList.addView(TV_id_memory);
        infoList.addView(TV_nickname);
        infoList.addView(TV_surviveDate);
        infoList.addView(TV_meesage);
        infoList.addView(TV_created);
        // 추억 한 칸 레이아웃 완성하기
        totalLayout.addView(IV_plantImage, IV_layoutParams );
        totalLayout.addView(infoList);

        infoList.setBackgroundColor(Color.WHITE);

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