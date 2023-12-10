package com.example.plantalarm;

public class MyData {
    public int _id_alarm; //알람 id
    public String time; // 알람 시간
    public String week; //요일
    public int hour;    // 시간
    public int minutes; //분
    public int alarm_check; //알람 활성화 여부
    public int sound_check; // 알림음 설정


    public int plant_mission_check; // 알람 미션 체크 여부
    public int repeat_count; //알람 반복 횟수

    public String[] allWeek = {"월", "화", "수", "목","금","토","일"};

    public boolean check; // 알람 실행 여부

    public MyData(String time, String week, boolean check){
        this.time = time;
        this.week = week;
        this.check = check;
    }

    public MyData( int _id_alarm,int hour, int minutes, String week, int alarm_check, int sound_check, int repeat_count, int plant_mission_check ){
        this._id_alarm = _id_alarm; // 알람 id
        this.hour = hour; // 시
        this.minutes = minutes; // 분
        this.week = week; // 요일
        this.alarm_check = alarm_check; //알람 활성화 여부
        this.sound_check = sound_check; // 알림음 설정
        this.repeat_count = repeat_count; // 반복 횟수 설정
        this.plant_mission_check = plant_mission_check; // 알람 미션 체크 여부

    }
}
