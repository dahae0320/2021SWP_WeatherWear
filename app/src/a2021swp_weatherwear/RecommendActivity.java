
package com.example.a2021swp_weatherwear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectStreamField;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class RecommendActivity<OpenAPI> extends AppCompatActivity
{
    BufferedReader br;
    String Connect = "http://apis.data.go.kr/1360000/AsosHourlyInfoService/getWthrDataList"; //연결 URL
    String apikey = "sCyFo%2FDKOLW9l1EHk%2BlA0zwanD%2FpAjUbgIhgU5erOQKEcKoh6PUjl%2B%2FQ1WARH4AksQh3sl6FU%2Ff9buPEMK20Rw%3D%3D";//API 서비스키

    ImageButton btnAddCloset;
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
    OpenAPI dust;

    public static void main(String[] args) throws IOException {
        String add = null;
        URL url = new URL(add);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);


        home_text = (TextView) findViewById(R.id.home);

        btnAddCloset = findViewById(R.id.imageButton1);
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

                    btnAddCloset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i1 = new Intent(RecommendActivity.this, SelectActivity.class);
                            //이미지 상의 + 버튼을 누르면 SelectActivity 화면으로 이동한다.
                            startActivity(i1);
                        }
                    });

                }
            }
        };
        thread.start();
    }

}