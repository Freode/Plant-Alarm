package com.example.plantalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.plantalarm.MainActivity;

// 알람 리시버 클래스 생성
public class AlarmReceiver extends BroadcastReceiver {
    private static MediaPlayer mediaPlayer;
    int a;
  
    @Override
    public void onReceive(Context context, Intent intent){
        //알람이 울릴 때 실행되는 코드
        Toast.makeText(context, "알람이 울립니다!", Toast.LENGTH_LONG).show();


        // ----- 알람 소리 재생 -------------//
        mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.sound); // 여기서 mysound는 추가한 소리 파일 이름입니다.

        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true); // 소리 반복 재생 (소리 무한 반복)
            mediaPlayer.start(); // 소리 재생
        }
        // ----- 알람 소리 재생 -------------//

        // 해충 찾기 모드로 넘어간다.
         Intent newIntent = new Intent(context, MainActivity.class);
//         context.startActivity(newIntent); // 브로드캐스트 리시버에서 starttActivity()로 호출하는건 바람직핮 ㅣ않다.
        // 브로드캐스트 리시버는 주로 알람이나 푸시 알림과 같은 백그라운드 작업을 처리하는데 사용된다.
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 이 부분을 추가해주면 됨
        context.startActivity(newIntent);
    }

    // 알람 소리 중지 메소드
    public static void stopAlarmSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
