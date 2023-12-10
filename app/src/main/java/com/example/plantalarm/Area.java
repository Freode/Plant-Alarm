package com.example.plantalarm;


import android.widget.Button;

public class Area {
    // 현재 구역 방문 여부

    // 현재 구역 버튼
    private Button btn;
    private boolean bIsVisited = false;
    // 현재 구역에 해충이 있는지 여부
    private boolean bIsInsectArea = false;
    // 현재 구역 Y 번호
    private int numY;
    // 현재 구역 X 번호
    private int numX;

    // 생성자
    public Area()
    {
        this.btn = null;
        bIsInsectArea = false;
        bIsVisited = false;
    }

    public Area(Button btn, boolean bIsVisited, boolean bIsInsectArea)
    {
        this.btn = btn;
        this.bIsVisited = bIsVisited;
        this.bIsInsectArea = bIsInsectArea;
    }

    // 해당 구역 버튼 가져오기
    public Button getButton() { return btn; }
    // 해당 구역 방문 여부 확인
    public boolean getIsVisited()
    {
        return bIsVisited;
    }

    // 해당 구역에 해충이 있는지 정보 확인
    public boolean getIsInsectArea()
    {
        return bIsInsectArea;
    }

    // 해당 구역 버튼 설정하기
    public void setButton(Button btn) {this.btn = btn;}
    // 해당 구역 방문 여부 설정
    public void setIsVisited(boolean bIsVisited)
    {
        this.bIsVisited = bIsVisited;
    }

    // 해충 구역인지 설정
    public void setIsInsectArea(boolean bIsInsectArea)
    {
        this.bIsInsectArea = bIsInsectArea;
    }

}
