
package com.example.a2021swp_weatherwear;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class RecommendActivity extends AppCompatActivity {
    FloatingActionMenu fabMenu;
    FloatingActionButton fabCloset;
    FloatingActionButton fabLikelist;
    TextView weatherTemp;

    private String year, month, day;

    private String strNick;
    long systemTime = System.currentTimeMillis();
    // 현재 시스템 시간 구하기
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    // 출력 형태를 위한 formmater
    String dTime = formatter.format(systemTime);
    // format에 맞게 출력하기 위한 문자열 변환

    TextView timer;

    TextView weather_text;
    TextView home_text;
    String weather_data;
    String home_data;

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
                            month = 0+String.valueOf(calendar.get(Calendar.MONTH)+1);
                            day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                            int hour = calendar.get(Calendar.HOUR_OF_DAY); // 시

                            System.out.println(year + month + day);

                            if (hour <= 11) {
                                timer.setText("현재 시각\n" + "오전" + hour + "시 ");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius1);
                                weatherTemp.setText( r1 + "'C");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius2);
                                weatherTemp.setText( r2 + "'C");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius3);
                                weatherTemp.setText( r3 + "'C");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius4);
                                weatherTemp.setText( r4 + "'C");

                            }
                            else if (hour >= 13) {
                                timer.setText("현재 시각\n" + "오후" + (hour - 12) + "시 ");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius1);
                                weatherTemp.setText( r1 + "'C");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius2);
                                weatherTemp.setText( r2 + "'C");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius3);
                                weatherTemp.setText( r3 + "'C");
                                weatherTemp = findViewById(R.id.txtBeforeCelsius4);
                                weatherTemp.setText( r4 + "'C");
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
                }
            }
        };
        thread.start();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}