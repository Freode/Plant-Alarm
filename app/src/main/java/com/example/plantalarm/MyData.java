package com.example.plantalarm;

public class MyData {
    public String time; // 알람 시간
    public String[] week; //요일
    public boolean check; // 알람 실행 여부

    public MyData(String time, String[] week, boolean check){
        this.time = time;
        this.week = week;
        this.check = check;
    }
}
