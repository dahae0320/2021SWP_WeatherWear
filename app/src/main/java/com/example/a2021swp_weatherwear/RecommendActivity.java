package com.example.a2021swp_weatherwear;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecommendActivity extends AppCompatActivity {
    FloatingActionMenu fabMenu;
    FloatingActionButton fabCloset;
    FloatingActionButton fabLikelist;

    private String year, month, day;

    private String strNick;

    TextView timer;
    TextView time1;
    TextView time2;
    TextView time3;
    TextView time4;


    TextView weather_text;
    String weather_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

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

        Intent intent = getIntent();
        strNick = intent.getStringExtra("name");

        TextView tv_name = findViewById(R.id.text_name);

        // name set
        tv_name.setText(strNick);

        weather_text = findViewById(R.id.tv);
        timer = findViewById(R.id.textView2);
        time1 = findViewById(R.id.txtBeforeTime1);
        time2 = findViewById(R.id.txtBeforeTime2);
        time3 = findViewById(R.id.txtBeforeTime3);
        time4 = findViewById(R.id.txtBeforeTime4);

        String now = new DataLoader().DateLoader(-1);
        String getDate = now.substring(0, 8);

        int min = Integer.parseInt(now.substring(11, 13));
        String getTime;

        if (min >= 30) {
            Date mDate = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDate = new SimpleDateFormat("HH00");
            getTime = simpleDate.format(mDate);
        }
        else {
            getTime = now.substring(9, 11) + "00";
        }

        System.out.println(getDate);
        System.out.println(getTime);

        Thread thread = new Thread() {

            @Override
            public void run() {
                while (!isInterrupted()) {
                    Calendar calendar = Calendar.getInstance(); // 날짜 변수
                    int hour = calendar.get(Calendar.HOUR_OF_DAY); // 시

                    if (hour == 12) {
                        timer.setText("현재 시각\n" + "오후 12시 ");
                    } else if (hour == 0) {
                        timer.setText("현재 시각\n" + "오전 12시 ");
                    } else if (hour >= 13) {
                        timer.setText("현재 시각\n" + "오후 " + (hour - 12) + "시 ");
                    } else {
                        timer.setText("현재 시각\n" + "오전 " + hour + "시 ");
                    }
                    // 2시간뒤 시각
                    if (hour <= 9) {
                        time1.setText("오전 " + (hour + 2) + "시");
                    } else if (hour == 10) {
                        time1.setText("오후 " + 12 + "시");
                    } else if (hour == 22) {
                        time1.setText("오전 " + 12 + "시");
                    } else if (hour >= 23) {
                        time1.setText("오전 " + (hour - 22) + "시");
                    } else {
                        time1.setText("오후 " + (hour - 10) + "시");
                    }
                    // 4시간 뒤 시간
                    if (hour <= 7) {
                        time2.setText("오전 " + (hour + 4) + "시");
                    } else if (hour == 8) {
                        time2.setText("오후 " + 12 + "시");
                    } else if (hour == 20) {
                        time2.setText("오전 " + 12 + "시");
                    } else if (hour >= 21) {
                        time2.setText("오전 " + (hour - 20) + "시");
                    } else {
                        time2.setText("오후 " + (hour - 8) + "시");
                    }

                    //6시간 뒤 시각
                    if (hour <= 5) {
                        time3.setText("오전 " + (hour + 6) + "시");
                    } else if (hour == 6) {
                        time3.setText("오후 " + 12 + "시");
                    } else if (hour == 18) {
                        time3.setText("오전 " + 12 + "시");
                    } else if (hour >= 19) {
                        time3.setText("오전 " + (hour - 18) + "시");
                    } else {
                        time3.setText("오후 " + (hour - 6) + "시");
                    }

                    // 8시간 뒤 시각
                    if (hour <= 3) {
                        time4.setText("오전 " + (hour + 8) + "시");
                    } else if (hour == 4) {
                        time4.setText("오후 " + 12 + "시");
                    } else if (hour == 16) {
                        time4.setText("오전 " + 12 + "시");
                    } else if (hour >= 17) {
                        time4.setText("오전 " + (hour - 16) + "시");
                    } else {
                        time4.setText("오후 " + (hour - 4) + "시");
                    }

                    weather_data = getWeatherXmlData(getDate, getTime);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            weather_text.setText(weather_data + "°C");
                        }
                    });
                }
            }
        };
        thread.start();
    }

    String getWeatherXmlData(String getDate, String getTime){
        StringBuffer buffer = new StringBuffer();

        String endPoint = "http://apis.data.go.kr/1360000/VilageFcstInfoService/";
        String serviceKey = "sCyFo%2FDKOLW9l1EHk%2BlA0zwanD%2FpAjUbgIhgU5erOQKEcKoh6PUjl%2B%2FQ1WARH4AksQh3sl6FU%2Ff9buPEMK20Rw%3D%3D";
        String pageNo = "1";
        String numOfRows = "10";
        String baseDate = getDate; // 원하는 날짜
        String baseTime = getTime;
        String nx = "81"; // 위경도임.
        String ny = "75";

        String queryUrl = endPoint + "getUltraSrtNcst?serviceKey=" + serviceKey
                + "&pageNo=" + pageNo
                + "&numOfRows=" + numOfRows
                + "&base_date=" + baseDate
                + "&base_time=" + baseTime
                + "&nx=" + nx
                + "&ny=" + ny;

        try {
            URL url = new URL(queryUrl);

            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String t;
            int i = 0;
            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        t = xpp.getName();

                        if (t.equals("item")) ;
                        else if (t.equals("obsrValue")) {
                            i++;
                            //기온
                            if (i == 4) {
                                buffer.append("");
                                xpp.next();
                                buffer.append(xpp.getText());
                                System.out.println(xpp.getText());
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        t = xpp.getName(); //태그 이름 얻어오기
                        if (t.equals("item")) ;
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {

        }
        return buffer.toString();
    }
}