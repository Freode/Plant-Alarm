package com.example.plantalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

// 알람 추가 또는 수정하는 페이지
public class AlarmSetActivity extends AppCompatActivity {
    Button btn_ok, btn_no, btn_timePicker;
    Switch switch_sound, switch_plant_mission;
    int g_hour, g_minute; // 전역 시간, 전역 분
    boolean g_sound_check, g_plant_mission_check;

    // 시간 결과 단어 배열
    String resultTime;

    // 알람 설정 관련 변수
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent; // PendingIntent은 나중에 실행될 인텐트를 나타내는 클래스

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alram_set);

        //-----------id 받기 ----------//
        btn_ok = (Button) findViewById(R.id.btn_ok); //'확인' 버튼
        btn_no = (Button) findViewById(R.id.btn_no); // '취소' 버튼
        btn_timePicker = (Button) findViewById(R.id.btn_timePicker); //'시간 설정' 버튼 (타임피커다이얼로그)
        Spinner spinner_repeat_count = (Spinner) findViewById(R.id.spinner_repeat_count);
        switch_sound = (Switch) findViewById(R.id.switch_sound); // 소리 스위치
        switch_plant_mission = (Switch) findViewById(R.id.switch_plant_mission); // 식물 스위치
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
                System.out.println(g_hour+" "+g_minute);

                setAlarm(g_hour, g_minute); // 알람AlarmActivity을 설정한다.
//                AlarmActivity alarmActivity = new AlarmActivity();
//                alarmActivity.addAlarm(g_hour,g_minute,g_sound_check,1,g_plant_mission_check);

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

        // --------------- 알람 설정하기 -------------//
        // 추가된 부분: 알람 매니저 및 펜딩인텐트 초기화
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // --------------스위치 ----------------------//
        switch_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // isChecked에는 현재 스위치의 클릭 상태가 전달됩니다.
                g_sound_check = isChecked;
            }
        });

        switch_plant_mission.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // isChecked에는 현재 스위치의 클릭 상태가 전달됩니다.
                g_plant_mission_check = isChecked; //상태 저장
            }
        });

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
                            g_hour = hourOfDay; // 전역 시간 저장
                            g_minute = minute; // 전역 분 저장
                            int hour; //시간
                            //오전 ~ 오후 판별
                            if (hourOfDay >= 12) {
                                amPm = "오후";
                                if (hourOfDay > 12) {
                                    hour = hourOfDay - 12;
                                } else {
                                    hour = hourOfDay;
                                }
                            } else {
                                amPm = "오전";
                                if (hourOfDay == 0) {
                                    hour = 12;
                                } else {
                                    hour = hourOfDay;
                                }
                            }

                            btn_timePicker.setText("시간 - "+amPm+" "+ hour +" : "+minute); // 시간 설정


                            resultTime= btn_timePicker.getText().toString(); // time을 resultTime에 저장

                            // setAlarm으로 알람 설정하기
//                            setAlarm(hourOfDay, minute);
                        }
                    }, mHour,mMinute,false);
            timePickerDialog1.show();

        }

    }


    //


    //
    //  -------------------------------------- 알람 설정 메소드 -------------------------------------- //
    private void setAlarm(int hourOfDay, int minute) {
        System.out.println("setAlarm:"+hourOfDay+" "+minute);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        long alarmTime = calendar.getTimeInMillis();

        // mDataList에 직접 데이터 추가
        AlarmActivity.mDataList.add(new MyData(999,hourOfDay, minute, "월,화", 1, g_sound_check ? 1 : 0, 1, g_plant_mission_check ? 1 : 0));


        // 추가된 부분: 알람 설정
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);

        // 알람 설정 메시지를 토스트 메시지로 표시
        String alarmMessage = "알람이 " + hourOfDay + "시 " + minute + "분에 설정되었습니다.";
        showToast(alarmMessage);
    }

    // -----------------------------------알람 삭제 메소드----------------------------------------- //
    private void cancelAlarm() {
        // 추가된 부분: 알람 취소
        alarmManager.cancel(pendingIntent);

        // 알람 취소 메시지를 토스트 메시지로 표시
        showToast("알람이 취소(삭제)되었습니다.");
    }

    //  ---------------------------------------------------------------------------------------- //

    private void showToast(String message) {
        // 토스트 메시지를 생성하여 화면에 표시
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
