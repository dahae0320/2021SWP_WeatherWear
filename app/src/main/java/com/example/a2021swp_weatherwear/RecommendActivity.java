package com.example.a2021swp_weatherwear;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecommendActivity extends AppCompatActivity {
    FloatingActionMenu fabMenu;
    FloatingActionButton fabCloset;
    FloatingActionButton fabLikelist;

    private String strNick;

    TextView timer;
    TextView time1;
    TextView time2;
    TextView time3;
    TextView time4;

    TextView weather_text;
    TextView weather_text1;
    TextView weather_text2;
    TextView weather_text3;
    TextView weather_text4;
    //최고 최저 기온 연관 알림, 날씨 알림
    TextView weather_text5;

    String weather_data;
    String weather_data1;
    String weather_data2;
    String weather_data3;
    String weather_data4;

    //최고 최저
    String weather_high;
    String weather_low;


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

        //api 데이터 텍스트 출력
        weather_text = findViewById(R.id.tv);
        weather_text1 = findViewById(R.id.txtBeforeCelsius1);
        weather_text2 = findViewById(R.id.txtBeforeCelsius2);
        weather_text3 = findViewById(R.id.txtBeforeCelsius3);
        weather_text4 = findViewById(R.id.txtBeforeCelsius4);
        //최고 최저 텍스트 출력 및 날씨 정보 출력
        weather_text5 = findViewById(R.id.textView1);


        timer = findViewById(R.id.textView2);
        time1 = findViewById(R.id.txtBeforeTime1);
        time2 = findViewById(R.id.txtBeforeTime2);
        time3 = findViewById(R.id.txtBeforeTime3);
        time4 = findViewById(R.id.txtBeforeTime4);


        String now = new DataLoader().DateLoader(-1);
        String getDate = now.substring(0, 8);

        int min = Integer.parseInt(now.substring(11, 13));
        String getTime;
        String getTime1;
        String getTime2;
        String getTime3;
        String getTime4;

        if (min >= 30) {
            Date mDate = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDate = new SimpleDateFormat("HH00");
            SimpleDateFormat simpleDate1 = new SimpleDateFormat("HH02");
            SimpleDateFormat simpleDate2 = new SimpleDateFormat("HH04");
            SimpleDateFormat simpleDate3 = new SimpleDateFormat("HH06");
            SimpleDateFormat simpleDate4 = new SimpleDateFormat("HH08");

            getTime = simpleDate.format(mDate);
            getTime1 = simpleDate1.format(mDate);
            getTime2 = simpleDate2.format(mDate);
            getTime3 = simpleDate3.format(mDate);
            getTime4 = simpleDate4.format(mDate);
        }
        else {
            getTime = now.substring(9, 11) + "00";
            getTime1 = now.substring(9, 11) + "02";
            getTime2 = now.substring(9, 11) + "04";
            getTime3 = now.substring(9, 11) + "06";
            getTime4 = now.substring(9, 11) + "08";

        }

        System.out.println(getDate);
        System.out.println(getTime);
        System.out.println(getTime1);
        System.out.println(getTime2);
        System.out.println(getTime3);
        System.out.println(getTime4);

        Thread thread = new Thread() {

            @Override
            public void run() {
                while (!isInterrupted()) {

                   weather_data = WeatherApi.getWeatherData(getDate, getTime);
                    weather_data1 = WeatherApi.getWeatherData(getDate, getTime1);
                    weather_data2 = WeatherApi.getWeatherData(getDate, getTime2);
                    weather_data3 = WeatherApi.getWeatherData(getDate, getTime3);
                    weather_data4 = WeatherApi.getWeatherData(getDate, getTime4);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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

                            weather_text.setText(weather_data + "°C");
                            weather_text1.setText(weather_data1 + "°C");
                            weather_text2.setText(weather_data2 + "°C");
                            weather_text3.setText(weather_data3 + "°C");
                            weather_text4.setText(weather_data4 + "°C");
                            //최고 최저 텍스트 출력 및 날씨 정보 출력

                            /*
                                String s = "";
                            Document doc;
                                NodeList nodeList = doc.getElementsByTagName("data");

                                for(int i = 0; i< nodeList.getLength(); i++) {

                                    //날씨 데이터를 추출
                                    s += "" + " ";

                                    Node node = nodeList.item(i); //data엘리먼트 노드
                                    Element fstElmnt = (Element) node;
                                    NodeList nameList = fstElmnt.getElementsByTagName("tmn");
                                    Element nameElement = (Element) nameList.item(0);
                                    nameList = nameElement.getChildNodes();


                                    NodeList timeList = fstElmnt.getElementsByTagName("tmEf");
                                    s += timeList.item(0).getChildNodes().item(0).getNodeValue() + ", ";


                                    s += "최저온도 = " + ((Node) nameList.item(0)).getNodeValue() + " ,";

                                    NodeList highList = fstElmnt.getElementsByTagName("tmx");
                                    //<wfKor>맑음</wfKor> =====> <wfKor> 태그의 첫번째 자식노드는 TextNode 이고 TextNode의 값은 맑음
                                    s += "최고온도 = " + highList.item(0).getChildNodes().item(0).getNodeValue() + " ,";

                                    NodeList websiteList = fstElmnt.getElementsByTagName("wf");
                                    //<wfKor>맑음</wfKor> =====> <wfKor> 태그의 첫번째 자식노드는 TextNode 이고 TextNode의 값은 맑음
                                    s += "날씨 = " + websiteList.item(0).getChildNodes().item(0).getNodeValue() + "\n";
                                }

                                weather_text5.setText(s);

                             */
                        }
                    });
                }
            }
        };
        thread.start();
    }
}