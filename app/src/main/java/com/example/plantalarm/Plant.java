package com.example.plantalarm;

public class Plant {
    private static int surviveDate =0;
    private static int growingDate=0;
    private static String plantNickname;
    private static String plantImagePath;
    public static int growthState;
    public static int typeOfPlant;
    public static boolean isMissionActive;


    public static int getServiveDate(){  // 생존 일자 가져오기
        return surviveDate;
    }

    public static int getGrowingDate(){  // 성장 일자 가져오기
        return growingDate;
    }
    public static String getPlantNickname(){return plantNickname;}

    public static void initPlantInfo(String plantNickname, String plantImagePath){
        Plant.surviveDate = 0;
        Plant.growingDate =0;
        Plant.plantNickname = plantNickname;
        Plant.plantImagePath = plantImagePath;
    }
    public static void addGrowing(int date){  // 알람 잘 일어난 경우: 성장 일자와 생존 일자 각각 +date
        surviveDate += date;
        growingDate += date;
    }

    public static void rest(){    // 알람을 하루 놓쳤을 경우: 성장 일자를 초기화
        growingDate = 0;
    }
    
    public static void saveMemory(){    // 앨범에 추억 저장: 데이터베이스 활용
        
    }
}
