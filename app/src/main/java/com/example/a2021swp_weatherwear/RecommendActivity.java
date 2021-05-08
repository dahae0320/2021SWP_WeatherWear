
package com.example.a2021swp_weatherwear;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;




public class RecommendActivity extends AppCompatActivity
{
    FloatingActionMenu fabMenu;
    FloatingActionButton fabCloset;
    FloatingActionButton fabLikelist;

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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        // 플로팅 버튼
        fabMenu = findViewById(R.id.fabMenu);
        fabCloset = findViewById(R.id.fabCloset);
        fabLikelist = findViewById(R.id.fabLikelist);

        // 좋아요 버튼
        final Button favBtn = (Button) findViewById(R.id.favBtn);

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

        weather_text = (TextView) findViewById(R.id.txtBeforeCelsius1);
        weather_text = (TextView) findViewById(R.id.txtBeforeCelsius2);
        weather_text = (TextView) findViewById(R.id.txtBeforeCelsius3);
        weather_text = (TextView) findViewById(R.id.txtBeforeCelsius4);

        home_text = (TextView) findViewById(R.id.home);


        timer = (TextView) findViewById(R.id.textView2);

        Thread thread = new Thread()
        {

            @Override

            public void run()
            {

                while (!isInterrupted())
                {

                    runOnUiThread(new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            Calendar calendar = Calendar.getInstance(); // 날짜 변수

                            int hour = calendar.get(Calendar.HOUR_OF_DAY); // 시


                            if(hour <= 11)
                            timer.setText("현재 시각\n" + "오전" + hour + "시 ");

                            else if(hour >= 13)
                            timer.setText("현재 시각\n" + "오후" + (hour - 12) + "시 ");

                        }
                    });

                    try
                    {
                        Thread.sleep(1000); // 1000 ms = 1초
                    }
                    catch (InterruptedException e)
                    {
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

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}