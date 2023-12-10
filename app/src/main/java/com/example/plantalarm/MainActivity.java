package com.example.plantalarm;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    // 구역 범위
    public static final int AREASIZE = 7;
    // 초기 해충 설정 수
    public static final int INITINSECTS = 8;
    // 8방향
    public static final int DIRECTIONS = 8;
    // 8방향에 따라 더하는 Y와 X값
    public static final int[][] ADDDIRVALUE = {{-1, 0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
    // 타이머 시간
    public static final int TIMERVALUEMILLS = 300000;
    // 해충 모드 여부
    public static boolean bIsInsectMode = false;
    // 현재 남은 해충 수
    public static int remainInsectsNum;

    // 구역 정보 공간 할당
    Area areas[][] = new Area[AREASIZE][AREASIZE];
    // 랜덤 지원 객체
    Random rand = new Random();

    Button btnInsect;
    Button btnSearch;
    TextView tvRemainInsects;
    TextView tvTitle;
    TextView tvRemainTimer;
    CountDownTimer countDownTimer;
    // 타이머가 멈춘 시간
    long timeLeftInMillis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int buttonId;
        Button eachButton;

        // 해충 버튼
        btnInsect = (Button) findViewById(R.id.button_insect);
        // 탐색 버튼
        btnSearch = (Button) findViewById(R.id.button_search);
        // 남음 해충 수 표기
        tvRemainInsects = (TextView) findViewById(R.id.textView_remain_insects);
        // 제목 텍스트
        tvTitle = (TextView) findViewById(R.id.textView_titles);
        // 타이머 텍스트
        tvRemainTimer = (TextView) findViewById(R.id.textView_remain_timer);


        // 해충 모드 클릭
        btnInsect.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                btnIsPressed(btnInsect, false);
                btnIsPressed(btnSearch, true);
                bIsInsectMode = true;
            }
        });

        // 탐색 모드 클릭
        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                // 탐색 모드
                if(btnSearch.getText().equals("탐색"))
                {
                    btnIsPressed(btnInsect, true);
                    btnIsPressed(btnSearch, false);
                    bIsInsectMode = false;
                }
                // 재시작 모드
                else
                {
                    gameStart(areas, AREASIZE);
                }
            }
        });

        for(int i = 0; i < AREASIZE; i++)
        {
            for(int j = 0; j < AREASIZE; j++)
            {
                // 객체 할당
                areas[i][j] = new Area();

                // 버튼 ID를 형성해서 버튼 객체를 가져오기
                String areaId = "btn_area_" + String.valueOf(i) + String.valueOf(j);
                buttonId = getResources().getIdentifier(areaId, "id", getPackageName());
                eachButton = (Button) findViewById(buttonId);

                // 버튼에 행과 열 정보를 저장
                eachButton.setTag(new int[]{i,j});
                eachButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        // 저장된 정보 가져오기
                        int[] position = (int[]) view.getTag();

                        // 해충 모드로 구역 버튼 클릭
                        if(bIsInsectMode)
                        {
                            // 해충 표시가 안되어있으면, 해충 표시
                            if(areas[position[0]][position[1]].getButton().getText() == "")
                            {
                                areas[position[0]][position[1]].getButton().setText("X");
                                // 남은 해충이 1마리 이상일 때만 작동
                                if(remainInsectsNum != 0)
                                {
                                    remainInsectsNum--;
                                    tvRemainInsects.setText("남은 해충 수 : " + remainInsectsNum);
                                }

                            }
                            // 해충 표시가 되어있으면, 해충 표시 제거
                            else
                            {
                                areas[position[0]][position[1]].getButton().setText("");
                                // 남은 해충이 최대치보다 작을 경우에만 작동
                                if(remainInsectsNum != INITINSECTS)
                                {
                                    remainInsectsNum++;
                                }
                            }

                            // 남은 해충 수 텍스트 수정
                            tvRemainInsects.setText("남은 해충 수 : " + remainInsectsNum);

                            // 남은 해충이 0마리일 경우에는 스일 조건을 살펴봄
                            if(remainInsectsNum == 0)
                            {
                                // 승리 조건을 만족한 경우
                                if(gameCorrect(areas, AREASIZE))
                                {
                                    gameSuccessed(areas, AREASIZE);
                                }
                            }
                        }

                        // 탐색 모드로 구역 버튼 클릭
                        else {
                            // 해충 구역이라고 표시
                            if(areas[position[0]][position[1]].getButton().getText() == "X")
                            {
                                // 반응 없음
                            }
                            // 해충 구역을 표시 안했는데 해충 구역을 밟음
                            else if (areas[position[0]][position[1]].getIsInsectArea())
                            {
                                gameFailed(areas, AREASIZE);
                            }
                            // 해충 구역이 아님
                            else
                            {
                                progressAreaExamination(areas, position[0], position[1]);
                            }
                        }
                    }
                });
                // 해당 구역에 버튼 정보 추가
                areas[i][j].setButton(eachButton);
            }
        }

        // 게임 시작
        gameStart(areas, AREASIZE);
        startTimer(TIMERVALUEMILLS);
    }

    // 구역에 초기 벌레 위치 설정
    public void initInsectArea(Area _areas[][], int initInsectNum)
    {
        // int(Integer) 형으로 set 선언
        Set<Integer> insectPositions = new HashSet<>();

        // 10개가 될때까지 추가
        while(insectPositions.size() < initInsectNum)
        {
            insectPositions.add(rand.nextInt(49));
        }

        // 선택한 위치를 구역 정보에 대입
        int intEach, intY, intX;
        for(Integer each : insectPositions)
        {
            System.out.println(each);
            intEach = each.intValue();
            intY = intEach / 7;
            intX = intEach % 7;

            _areas[intY][intX].setIsInsectArea(true);
        }

    }

    // 해당 지점에 대한 검사 진행
    public void progressAreaExamination(Area[][] areas, int row, int col)
    {
        // 추가로 못 선택하도록 설정
        btnIsPressed(areas[row][col].getButton(), false);
        // 방문했다고 설정
        areas[row][col].setIsVisited(true);

        int tempX, tempY;
        // 해당 지점 주변에 벌레가 없는 경우
        // 주변 8방향 땅을 모두 검사한다.
        if(checkSurroundingInsects(areas, row, col))
        {
            // 8방향으로 이동해서 진행
            for(int i = 0; i < 8; i++)
            {
                tempY = row + ADDDIRVALUE[i][0];
                tempX = col + ADDDIRVALUE[i][1];

                // 경계 안에 있고 해당 지점을 방문하지 않은 경우
                if(checkIsItInBoundary(AREASIZE, tempY, tempX) && !areas[tempY][tempX].getIsVisited())
                {
                    progressAreaExamination(areas, tempY, tempX);
                }
            }
        }
    }

    // 주변에 벌레 확인
    public boolean checkSurroundingInsects(Area[][] areas, int row, int col)
    {
        int tempY, tempX;
        int insectNum = 0;
        for(int i = 0; i < 8; i++)
        {
            // 검사할 위치
            tempY = row + ADDDIRVALUE[i][0];
            tempX = col + ADDDIRVALUE[i][1];

            // 범위 안에 존재하고 벌레가 있다면, 주변 위치 벌레 수 1 증가
            if(checkIsItInBoundary(AREASIZE, tempY, tempX) && areas[tempY][tempX].getIsInsectArea())
            {
                insectNum++;
            }
        }

        // 벌레 수가 0마리가 아니면, 현재 버튼에 주변 벌레 수 표기
        if(insectNum != 0)
        {
            // 버튼에 주변 해충 수 출력
            areas[row][col].getButton().setText(Integer.toString(insectNum));
            return false;
        }
        else
        {
            return true;
        }
    }

    // 범위 안에 있는지 확인하는 함수
    public boolean checkIsItInBoundary(int size, int y, int x)
    {
        return (y >= 0 && x >= 0 && x < size && y < size);
    }

    // 해충찾기 게임 시작 ( 재시작도 가능 )
    public void gameStart(Area[][] areas, int size)
    {
        // 구역 정보 모두 초기화
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                areas[i][j].getButton().setText("");
                btnIsPressed(areas[i][j].getButton(), true);
                areas[i][j].setIsVisited(false);
                areas[i][j].setIsInsectArea(false);
            }
        }

        // 벌레 설정
        initInsectArea(areas, INITINSECTS);

        tvTitle.setText("토지에 숨은 해충을 모두 찾으세요!");
        btnSearch.setText("탐색");
        btnInsect.setVisibility(View.VISIBLE);

        // 탐색 버튼, 해충 버튼 초기화
        btnIsPressed(btnInsect, true);
        btnIsPressed(btnSearch, false);
        bIsInsectMode = false;
        remainInsectsNum = INITINSECTS;
        tvRemainInsects.setText("남은 해충 수 : " + remainInsectsNum);
    }

    // 게임 실패
    public void gameFailed(Area area[][], int size)
    {
        // 모든 구역 비활성화
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                area[i][j].getButton().setEnabled(false);
                // 해충 지역을 모두 보여줌
                if(area[i][j].getIsInsectArea())
                {
                    area[i][j].getButton().setText("X");
                }
            }
        }

        tvTitle.setText("해충을 밟았습니다! 다시 시작해주세요.");
        btnSearch.setText("재시작");
        btnIsPressed(btnSearch, true);
        btnInsect.setVisibility(View.INVISIBLE);
    }

    // 게임 성공
    public void gameSuccessed(Area area[][], int size)
    {
        // 모든 구역 비활성화
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                btnIsPressed(area[i][j].getButton(), false);
            }
        }

        stopTimer();
        tvTitle.setText("모든 해충을 찾았습니다!");
        btnSearch.setText("알람 끄기");
        btnIsPressed(btnSearch, true);
        btnInsect.setVisibility(View.INVISIBLE);

        // ==== 연경 ====
        btnSearch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(intent);
            }
        });
        // =============

        // ---------- 지안 -----------//
        AlarmReceiver.stopAlarmSound();
        //----------------------------//
    }

    // 게임을 끝낼 수 있는 조건과 일치하는지 확인
    public boolean gameCorrect(Area area[][], int size)
    {
        int correctNum = 0;

        // 해충 구역 표시 지점과 해충 지점이 모두 일치하는지 확인
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(area[i][j].getButton().getText().equals("X") && area[i][j].getIsInsectArea())
                {
                    correctNum++;
                }
            }
        }

        //
        return (correctNum == INITINSECTS);
    }

    // 버튼을 누르거나 누를 수 있을 때의 작동
    public void btnIsPressed(Button btn, boolean bIsPressed)
    {
        // 버튼을 한 번 눌러 더 이상 누를 수 없는 상태
        if(bIsPressed)
        {
            btn.setEnabled(true);
            btn.setBackgroundColor(Color.rgb(125,150,125));
        }
        // 버튼을 누를 수 있는 상태
        else
        {
            btn.setEnabled(false);
            btn.setBackgroundColor(Color.rgb(50,50,50));
        }
    }

    public void showInsectArea(Area area[][], int size)
    {
        // 모든 구역 비활성화
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                area[i][j].getButton().setEnabled(false);
                // 해충 지역을 모두 보여줌
                if(area[i][j].getIsInsectArea())
                {
                    area[i][j].getButton().setText("X");
                }
            }
        }
    }

    // 타이머 시작
    private void startTimer(long milliseconds) {
        // 1초 간격으로 카운트다운 진행
        countDownTimer = new CountDownTimer(milliseconds, 1000)
        {
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            // 타이머 종료
            public void onFinish() {
                //
                showInsectArea(areas, AREASIZE);
                tvTitle.setText("시간 안에 해충을 모두 찾지 못했습니다!");
                btnSearch.setText("알람 끄기");
                btnIsPressed(btnSearch, true);
                btnInsect.setVisibility(View.INVISIBLE);
                Plant.isDead = true;

                // ==== 연경 ====
                btnSearch.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                        startActivity(intent);
                    }
                });

            }
        }.start();
    }

    // 타이머 멈추기
    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    // 타이머 업데이트
    private void updateTimer() {
        int seconds = (int) (timeLeftInMillis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        // 시간 형식 맞추기
        String tmpFirst = "남은 시간 - ";
        String tmpSecond = ":";

        if(seconds < 10)
        {
            tmpSecond += "0";
        }

        if(minutes < 10)
        {
            tmpFirst += "0";
        }

        // 시간 출력
        tvRemainTimer.setText(tmpFirst + minutes + tmpSecond + seconds);
    }
}