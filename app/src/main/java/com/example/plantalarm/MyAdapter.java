package com.example.plantalarm;

import static androidx.core.content.res.ComplexColorCompat.inflate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyAdapter extends BaseAdapter {
    private Context ctx;
    private MyData[] data;

    public MyAdapter(Context ctx, MyData[] data){
        this.ctx = ctx; //컨텍스트
        this.data = data; //데이터 받아 저장
    }

    @Override
    public int getCount(){
        return data.length; //데이터의 전체 개수 리턴
    }

    @Override
    public Object getItem(int i){
        return data.length; //데이터의 전체 개수 리턴
    }

    @Override
    public long getItemId(int i){
        return i;   // i번째 항목의 식별자 리턴
    }

    // getView() 메소드의 두번째 인자 view가 null이면,
    // 뷰 객체를 만든다. LayoutInflater 객체를 준비하고,
    // infate 메소드로 커스텀 레이아웃을 읽어 들인다.
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        if (view == null) { //view가 null이면, 뷰 객체를 만든다.
            LayoutInflater inflater = LayoutInflater.from(ctx);
            view = inflater.inflate(R.layout.listview_item, viewGroup, false);
            // 읽어들일 레이아웃의 리소스 iD, 생성할 뷰의 부모 뷰, 생성한 뷰를 부모 뷰에 붙일 지 결정, 어뎁터 코드는 항상 false
        }

        // 시간, 요일, 알람 여부 설정
        // 시간
        TextView timeText = (TextView) view.findViewById(R.id.timeText);
        timeText.setText(data[i].time);

        // 요일 설정
        TextView weekText = (TextView) view.findViewById(R.id.weekText);
        String resultWeek ="";

        for (int j = 0; j< data[i].week.length; j++){
            resultWeek+=data[i].week[j];
        }
        weekText.setText(resultWeek);

        // 알람 여부 설정
        Switch alramCheck = (Switch) view.findViewById(R.id.alramCheck);
        alramCheck.setChecked(data[i].check);

        return view;



    }

}
