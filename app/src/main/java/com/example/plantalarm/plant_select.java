package com.example.plantalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class plant_select extends AppCompatActivity {
    int imageIdx = 0;
    EditText ET_plantNicknameInput;
    Button B_makePlant;
    ImageView IV_selectImage;
    TextView TV_selectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_select);
        IV_selectImage = (ImageView) findViewById(R.id.imageView_select_image);
        TV_selectText = (TextView) findViewById(R.id.textView_selectPlantName);
    }
    public void plantMainListener(View target){
        Intent intent = new Intent(getApplicationContext(), plant_main.class);
        startActivity(intent);
    }

    public void onMakeBtnListener(View target){
        ET_plantNicknameInput = (EditText)findViewById(R.id.editTextText_plnat_text);
        String nickname = ET_plantNicknameInput.getText().toString();
        Plant.initPlantInfo(nickname, "-"); //닉네임 입력칸에 작성한 문자열 가져오기
        Plant.typeOfPlant = imageIdx;
        Intent intent = new Intent(getApplicationContext(), plant_main.class);
        startActivity(intent);
    }

    public void onNextBtnListener(View target){
        imageIdx = (imageIdx+1)%2;
        if(imageIdx ==1){
            IV_selectImage.setImageResource(R.drawable.plant2);
            TV_selectText.setText("-백량금-");
        }
        else{

            IV_selectImage.setImageResource(R.drawable.plant_image_tmp);
            TV_selectText.setText("-몬스테라-");
        }
    }
}
