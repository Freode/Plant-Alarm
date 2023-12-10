package com.example.plantalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class plantDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mycontacts.db";
    private static final int DATABASE_VERSION = 3;

    public plantDB(Context context){ super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    public void onCreate(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS Plant");
        db.execSQL("CREATE TABLE Plant (" +
                "_id_plant INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name TEXT," +
                "type TEXT," +
                "level INTEGER," +
                "image_url TEXT," +
                "survive_date INTEGER," +
                "growth_date INTEGER," +
                "created TIMESTAMP," +
                "updated TIMESTAMP" +
                ");");

        db.execSQL("DROP TABLE IF EXISTS PlantLevelImage");
        db.execSQL("CREATE TABLE PlantLevelImage (" +
                "_id_plant INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "type INTEGER NOT NULL," +
                "level INTEGER NOT NULL," +
                "image_url TEXT " +
                ");");

        db.execSQL("DROP TABLE IF EXISTS PlantMemoryList");
        db.execSQL("CREATE TABLE PlantMemoryList (" +
                "_id_memory INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "_id_plant BIGINT NOT NULL," +
                "image_url TEXT," +
                "message TEXT," +
                "created TIMESTAMP" +
                ");");

        db.execSQL("DROP TABLE IF EXISTS Alarm");
        db.execSQL("CREATE TABLE Alarm (" +
                "_id_alarm INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "hour INTEGER," +
                "minutes INTEGER," +
                "week TEXT," +
                "alarm_success INTEGER," +
                "alarm_check INTEGER," +
                "sound_check INTEGER," +
                "plant_mission_check INTEGER," +
                "repeat_count INTEGER," +
                "repeat_count_index INTEGER," +
                "all_minutes INTEGER," +
                "created TIMESTAMP," +
                "updated TIMESTAMP" +
                ");");



    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS PlantLevelImage");
        db.execSQL("DROP TABLE IF EXISTS Plant");
        db.execSQL("DROP TABLE IF EXISTS Alarm");
        db.execSQL("DROP TABLE IF EXISTS PlantMemoryList");
        onCreate(db);
    }

    // 특정 테이블에 데이터 추가하는 메서드
    public long insertPlant(String name, String type, int level, String imageUrl, long surviveDate, long growthDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("type", type);
        values.put("level", level);
        values.put("image_url", imageUrl);
        values.put("survive_date", surviveDate);
        values.put("growth_date", growthDate);

        long newRowId = db.insert("Plant", null, values);
        db.close();
        return newRowId;
    }

    public long insertAlarm(int hour, int minutes, String week, int alarmSuccess, int alarmCheck,
                            int soundCheck, int plantMissionCheck, int repeatCount, int repeatCountIndex, long allMinutes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hour", hour);
        values.put("minutes", minutes);
        values.put("week", week);
        values.put("alarm_success", alarmSuccess);
        values.put("alarm_check", alarmCheck);
        values.put("sound_check", soundCheck);
        values.put("plant_mission_check", plantMissionCheck);
        values.put("repeat_count", repeatCount);
        values.put("repeat_count_index", repeatCountIndex);
        values.put("all_minutes", allMinutes);

        long newRowId = db.insert("Alarm", null, values);
        db.close();
        return newRowId;
    }

    public long insertPlantMemory(long plantId, String imageUrl, String message, long created) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id_plant", plantId);
        values.put("image_url", imageUrl);
        values.put("message", message);
        values.put("created", created);

        long newRowId = db.insert("PlantMemoryList", null, values);
        db.close();
        return newRowId;
    }

    public long insertPlantLevelImage(long type, int level, String imageUrl, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("type", type);
        values.put("level", level);
        values.put("image_url", imageUrl);

        return db.insert("PlantLevelImage", null, values);
    }


    public void initPlantLevelImage() {
        String imagePath = "C:/study/3/3-2/Mobile/MobileTerm/Plant-Alarm/app/src/main/res/drawable/";

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            // 여러 개의 insert 작업 수행
            insertPlantLevelImage(0, 0, imagePath + "plant1_0.jpg", db);
            insertPlantLevelImage(0, 1, imagePath + "plant_baby.jpg", db);
            insertPlantLevelImage(0, 2, imagePath + "plant_image_tmp.jpg", db);
            insertPlantLevelImage(0, 3, imagePath + "plant_all_grown.jpg", db);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

    }
}
