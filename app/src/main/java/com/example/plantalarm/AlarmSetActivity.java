package com.example.plantalarm;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

// 알람 추가 또는 수정하는 페이지
public class AlarmSetActivity extends AppCompatActivity {
    Button btn_ok, btn_no, btn_timePicker;

    // 시간 결과 단어 배열
    String resultTime;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alram_set);

        //-----------id 받기 ----------//
        btn_ok = (Button) findViewById(R.id.btn_ok); //'확인' 버튼
        btn_no = (Button) findViewById(R.id.btn_no); // '취소' 버튼
        btn_timePicker = (Button) findViewById(R.id.btn_timePicker); //'시간 설정' 버튼 (타임피커다이얼로그)
        Spinner spinner_repeat_count = (Spinner) findViewById(R.id.spinner_repeat_count);
        //-----------------------------//

        // ----------------- 반복 횟수 스피너 -------------------------//
        //1. 리소스로부터 ArrayAdapter를 생성한다. (스피너 초기화)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.alarm_repeat_count, android.R.layout.simple_spinner_item);
        //2. 사용자가 클릭하여서 항목이 오픈될때에 항목을 표시하는 뷰의 모양을 정의한다. (스피너 초기화)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_repeat_count.setAdapter(adapter); // (스피너 초기화)

        //3. 무명클래스 생성. 스피터에서 항목이 선택되었을때 애플케이션에 알려주는 콜백 메소드를 제공한다.
        spinner_repeat_count.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            // 4. onItemSelected() 메소드는 어댑터 뷰의 항목이 선택되는 경우 호출된다.
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id){ //pos : 선택된 항목 인덱스이다.
                Toast.makeText(parent.getContext(),
                        "선택된 알람 반복 횟수는 " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            // 5. onNothingSelected() 메소드는 어댑터 뷰에서 선택이 사라지는 경우 호출된다.
            public void onNothingSelected(AdapterView<?> arg0){}
        });

        // ----------------------------------------------------------//


        // =========== 제일 아래 버튼 ===========//
        // '확인' 버튼 클릭시, 현재 액티비티 종료
        btn_ok.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish(); //이벤트 리스너에서 버튼이 클릭되면 현재의 액티비티가 종료됨.
            }
        });

        // '취소' 버튼 클릭시, 현재 액티비티 종료
        btn_no.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish(); //이벤트 리스너에서 버튼이 클릭되면 현재의 액티비티가 종료됨.
            }
        });
        // =================================//
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_timePicker){ // 시간 설정 버튼 클릭시
            // 타임피커다이얼로그가 뜨면서 시간 설정이 가능하다.
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY); //시
            int mMinute = c.get(Calendar.MINUTE); //분

            TimePickerDialog timePickerDialog1 = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener(){
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                            String amPm; //오전, 오후 나타내기
                            int hour; //시간
                            //오전 ~ 오후 판별
                            if (hourOfDay >= 12){
                                amPm = "오후";
                                hour = hourOfDay - 12;
                            }else{
                                amPm="오전";
                                hour = hourOfDay;
                            }

                            btn_timePicker.setText("시간 - "+amPm+" "+ hour +" : "+minute); // 시간 설정

                            resultTime= btn_timePicker.getText().toString(); // time을 resultTime에 저장

                        }
                    }, mHour,mMinute,false);
            timePickerDialog1.show();

        }

    }
}
