package com.example.a2021swp_weatherwear;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecommendActivity extends AppCompatActivity {
    FloatingActionMenu fabMenu;
    FloatingActionButton fabCloset;
    FloatingActionButton fabLikelist;
    TextView weatherTemp;

    // 현재 날짜
    private String year, month, day;

    // 옷차림 추천 관련 변수들
    private int currentCel = 22; // 현재 기온 변수 (임의로 지정함)
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String strNick;
    long systemTime = System.currentTimeMillis();

    TextView timer;

    TextView weather_text;
    TextView home_text;
    String weather_data;
    String home_data;

    // 현재 시스템 시간 구하기
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    // 출력 형태를 위한 formmater
    String dTime = formatter.format(systemTime);
    // format에 맞게 출력하기 위한 문자열 변환

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        // 동네관측 api
        final apiTest apiTest = new apiTest();

        // 플로팅 버튼
        fabMenu = findViewById(R.id.fabMenu);
        fabCloset = findViewById(R.id.fabCloset);
        fabLikelist = findViewById(R.id.fabLikelist);

        fabCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(RecommendActivity.this, SelectActivity.class);
                //이미지 상의 의류 추가 버튼을 누르면 SelectActivity 화면으로 이동한다.
                startActivity(i1);
            }
        });

        fabLikelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(RecommendActivity.this, LikeActivity.class);
                //이미지 상의 좋아요 확인 버튼을 누르면 SelectActivity 화면으로 이동한다.
                startActivity(i3);
            }
        });

        // 좋아요 버튼
        final Button favBtn = findViewById(R.id.favBtn);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favBtn.setSelected(true);
            }
        });

        Intent intent = getIntent();
        strNick = intent.getStringExtra("name");

        TextView tv_name = findViewById(R.id.text_name);

        // name set
        tv_name.setText(strNick);

        home_text = findViewById(R.id.home);
        timer = findViewById(R.id.textView2);

        Thread thread = new Thread() {

            @Override
            public void run() {
                while (!isInterrupted()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            JSONObject r1 = apiTest.s1;
                            JSONObject r2 = apiTest.s2;
                            JSONObject r3 = apiTest.s3;
                            JSONObject r4 = apiTest.s4;

                            Calendar calendar = Calendar.getInstance(); // 날짜 변수
                            year = String.valueOf(calendar.get(Calendar.YEAR));
                            month = 0 + String.valueOf(calendar.get(Calendar.MONTH) + 1);
                            day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                            int hour = calendar.get(Calendar.HOUR_OF_DAY); // 시

                            System.out.println(year + month + day);

                            if (hour <= 11) {
                                timer.setText("현재 시각\n" + "오전" + hour + "시 ");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius1);
                                weatherTemp.setText(r1 + "'C");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius2);
                                weatherTemp.setText(r2 + "'C");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius3);
                                weatherTemp.setText(r3 + "'C");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius4);
                                weatherTemp.setText(r4 + "'C");

                            } else if (hour >= 13) {
                                timer.setText("현재 시각\n" + "오후" + (hour - 12) + "시 ");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius1);
                                weatherTemp.setText(r1 + "'C");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius2);
                                weatherTemp.setText(r2 + "'C");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius3);
                                weatherTemp.setText(r3 + "'C");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius4);
                                weatherTemp.setText(r4 + "'C");
                            }
                        }
                    });

                    try {
                        apiTest.func(year, month, day);
                        Thread.sleep(1000); // 1000 ms = 1초
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
//        thread.start();

        recommendGarment(currentCel);
    }

    // 옷차림 추천 메소드
    private void recommendGarment(int currentCel) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        ArrayList<String> outer = new ArrayList<>();
        ArrayList<String> top = new ArrayList<>();
        ArrayList<String> bottom = new ArrayList<>();
        Random random = new Random();
        final int[] current = new int[1];

        firebaseDatabase = FirebaseDatabase.getInstance();  // 파이어베이스 DB 연동
        databaseReference = firebaseDatabase.getReference("GarmentTemperature");   // DB 데이블 연결

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                int i = 0;

                // 현재 기온에 맞는 추천 옷차림 테이블 찾기
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    arrayList.add(Integer.valueOf(dataSnapshot.getKey()));
                    if ( arrayList.get(i) >= currentCel ) {
                        current[0] = i - 1;
                        System.out.println(current[0]);
                        break;
                    }
                    i++;
                }

                // 현재 기온에 맞는 추천 옷차림을 따로 배열에 할당
                for(DataSnapshot dataSnapshot : snapshot.child(String.valueOf(arrayList.get(current[0]))).child("Outer").getChildren() ) {
//                    System.out.println(dataSnapshot.getValue());
                    outer.add((String) dataSnapshot.getValue());
                }
                for(DataSnapshot dataSnapshot : snapshot.child(String.valueOf(arrayList.get(current[0]))).child("Top").getChildren() ) {
//                    System.out.println(dataSnapshot.getValue());
                    top.add((String) dataSnapshot.getValue());
                }
                for(DataSnapshot dataSnapshot : snapshot.child(String.valueOf(arrayList.get(current[0]))).child("Bottom").getChildren() ) {
//                    System.out.println(dataSnapshot.getValue());
                    bottom.add((String) dataSnapshot.getValue());
                }

                System.out.println( outer.get( random.nextInt(outer.size()) ) );
                System.out.println( top.get( random.nextInt(top.size()) ) );
                System.out.println( bottom.get( random.nextInt(bottom.size()) ) );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}