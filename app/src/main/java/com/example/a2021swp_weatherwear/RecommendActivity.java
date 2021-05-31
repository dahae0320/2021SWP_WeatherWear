
package com.example.a2021swp_weatherwear;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RecommendActivity extends AppCompatActivity {
    FloatingActionMenu fabMenu;
    FloatingActionButton fabCloset;
    FloatingActionButton fabLikelist;

    // 옷차림 추천 관련 변수들
    private Integer currentCel = 22; // 현재 기온 변수 (임의로 지정함)
    private FirebaseDatabase firebaseDatabase, firebaseDatabaseLike;
    private DatabaseReference databaseReference, databaseReferenceLike;
    private TextView txtOuter, txtTop, txtBottom;

    private String strNick;
    Document doc = null;
    long systemTime = System.currentTimeMillis();

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

    //최저 기온
    String weatherLow;
    //최고 기온
    String weatherHigh;

    // 현재 시스템 시간 구하기
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    // 출력 형태를 위한 formmater
    String dTime = formatter.format(systemTime);
    // format에 맞게 출력하기 위한 문자열 변환

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

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
                saveLikeGarment();
            }
        });

        Intent intent = getIntent();
        strNick = intent.getStringExtra("name");

        TextView tv_name = findViewById(R.id.text_name);

        // name set
        tv_name.setText(strNick);

        weather_text = findViewById(R.id.tv);
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

        new Thread(new Runnable() {
            @Override
            public void run() {

//                //현재날짜 각 시간별 기온 실수형 변환
//                double clock3;
//                double clock4;
//                double clock5;
//                double clock6;
//                double clock7;
//                double clock8;
//                double clock9;
//                double clock10;
//                double clock11;
//                double clock12;
//                double clock13;
//                double clock14;
//                double clock15;
//                double clock16;
//                double clock17;
//                double clock18;
//                double clock19;
//                double clock20;
//                double clock21;
//                double clock22;
//                double clock23;
//
//                //현재시간 기온 출력
//                String Wclock3;
//                String Wclock4;
//                String Wclock5;
//                String Wclock6;
//                String Wclock7;
//                String Wclock8;
//                String Wclock9;
//                String Wclock10;
//                String Wclock11;
//                String Wclock12;
//                String Wclock13;
//                String Wclock14;
//                String Wclock15;
//                String Wclock16;
//                String Wclock17;
//                String Wclock18;
//                String Wclock19;
//                String Wclock20;
//                String Wclock21;
//                String Wclock22;
//                String Wclock23;
//
//                //현재시간 기온 출력 변수
//                String getTime3am = "0300";
//                String getTime4am = "0400";
//                String getTime5am = "0500";
//                String getTime6am = "0600";
//                String getTime7am = "0700";
//                String getTime8am = "0800";
//                String getTime9am = "0900";
//                String getTime10am = "1000";
//                String getTime11am = "1100";
//                String getTime12pm = "1200";
//                String getTime1pm = "1300";
//                String getTime2pm = "1400";
//                String getTime3pm = "1500";
//                String getTime4pm = "1600";
//                String getTime5pm = "1700";
//                String getTime6pm = "1800";
//                String getTime7pm = "1900";
//                String getTime8pm = "2000";
//                String getTime9pm = "2100";
//                String getTime10pm = "2200";
//                String getTime11pm = "2300";
//
//                //api로 기온 불러오기
//                Wclock3 = WeatherApi.getWeatherData(getDate, getTime3am);
//                Wclock4 = WeatherApi.getWeatherData(getDate, getTime4am);
//                Wclock5 = WeatherApi.getWeatherData(getDate, getTime5am);
//                Wclock6 = WeatherApi.getWeatherData(getDate, getTime6am);
//                Wclock7 = WeatherApi.getWeatherData(getDate, getTime7am);
//                Wclock8 = WeatherApi.getWeatherData(getDate, getTime8am);
//                Wclock9 = WeatherApi.getWeatherData(getDate, getTime9am);
//                Wclock10 = WeatherApi.getWeatherData(getDate, getTime10am);
//                Wclock11 = WeatherApi.getWeatherData(getDate, getTime11am);
//                Wclock12 = WeatherApi.getWeatherData(getDate, getTime12pm);
//                Wclock13 = WeatherApi.getWeatherData(getDate, getTime1pm);
//                Wclock14 = WeatherApi.getWeatherData(getDate, getTime2pm);
//                Wclock15 = WeatherApi.getWeatherData(getDate, getTime3pm);
//                Wclock16 = WeatherApi.getWeatherData(getDate, getTime4pm);
//                Wclock17 = WeatherApi.getWeatherData(getDate, getTime5pm);
//                Wclock18 = WeatherApi.getWeatherData(getDate, getTime6pm);
//                Wclock19 = WeatherApi.getWeatherData(getDate, getTime7pm);
//                Wclock20 = WeatherApi.getWeatherData(getDate, getTime8pm);
//                Wclock21 = WeatherApi.getWeatherData(getDate, getTime9pm);
//                Wclock22 = WeatherApi.getWeatherData(getDate, getTime10pm);
//                Wclock23 = WeatherApi.getWeatherData(getDate, getTime11pm);
//
//                //문자형으로 불러온 기온을 실수형으로 변환
//                clock3 = Double.parseDouble(getTime3am);
//                clock4 = Double.parseDouble(getTime4am);
//                clock5 = Double.parseDouble(getTime5am);
//                clock6 = Double.parseDouble(getTime6am);
//                clock7 = Double.parseDouble(getTime7am);
//                clock8 = Double.parseDouble(getTime8am);
//                clock9 = Double.parseDouble(getTime9am);
//                clock10 = Double.parseDouble(getTime10am);
//                clock11 = Double.parseDouble(getTime11am);
//                clock12 = Double.parseDouble(getTime12pm);
//                clock13 = Double.parseDouble(getTime1pm);
//                clock14 = Double.parseDouble(getTime2pm);
//                clock15 = Double.parseDouble(getTime3pm);
//                clock16 = Double.parseDouble(getTime4pm);
//                clock17 = Double.parseDouble(getTime5pm);
//                clock18 = Double.parseDouble(getTime6pm);
//                clock19 = Double.parseDouble(getTime7pm);
//                clock20 = Double.parseDouble(getTime8pm);
//                clock21 = Double.parseDouble(getTime9pm);
//                clock22 = Double.parseDouble(getTime10pm);
//                clock23 = Double.parseDouble(getTime11pm);
//
//                //하루 전체 기온 평균
//                double temperatureAverage
//                        = (clock3 + clock4 + clock5 + clock6 + clock7 + clock8 + clock9 + clock10 + clock11 + clock12 + clock13 + clock14
//                        + clock15 + clock16 + clock17 + clock18 + clock19 + clock20 + clock21 + clock22 + clock23)/21;


                weather_data = WeatherApi.getWeatherData(getDate, getTime);
                weather_data1 = WeatherApi.getWeatherData(getDate, "1500");
                weather_data2 = WeatherApi.getWeatherData(getDate, "1700");
                weather_data3 = WeatherApi.getWeatherData(getDate, "1900");
                weather_data4 = WeatherApi.getWeatherData(getDate, "2100");

                weather_text1 = findViewById(R.id.txtBeforeCelsius1);
                weather_text2 = findViewById(R.id.txtBeforeCelsius2);
                weather_text3 = findViewById(R.id.txtBeforeCelsius3);
                weather_text4 = findViewById(R.id.txtBeforeCelsius4);
                //최고 최저 텍스트 출력 및 날씨 정보 출력



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Calendar calendar = Calendar.getInstance(); // 날짜 변수

                        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 시

                        // 현재 시각
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

                        //최저기온 정수형 변환
                        double low_number;
                        //최고기온 정수형 변환
                        double high_number;

                        String getTimeHigh = "0300";
                        String getTimeLow = "1400";

                        weatherLow = WeatherApi.getWeatherData(getDate, getTimeHigh);
                        weatherHigh = WeatherApi.getWeatherData(getDate, getTimeLow);

                        //문자형을 정수형으로 바꿔준다.
                        low_number = Double.parseDouble(getTimeHigh);
                        high_number = Double.parseDouble(getTimeLow);

                        if ((high_number - low_number) >= 20) {
                            weather_text5.setText("일교차가 큽니다.");
                        } else if((high_number - low_number) < 20){
                            weather_text5.setText(" ");
                        }
                        else
                        {
                            weather_text5.setText("데이터를 불러오지 못했습니다.");
                        }




                    }
                });

            }
        }).start();

        // 옷차림 추천
        recommendGarment(currentCel);

        // 옷차림 추천 새로고침
        final ImageButton btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommendGarment(currentCel);
            }
        });
    }



    // 좋아요 누를 시 옷차림 저장
    private void saveLikeGarment() {
        Random random = new Random();
        firebaseDatabaseLike = FirebaseDatabase.getInstance();
        // TODO : User2는 실제 사용자 데이터를 불러올 수 있도록 한다.
        databaseReferenceLike = firebaseDatabaseLike.getReference("User").child("User2").child("Like").child(String.valueOf(random.nextInt()));

        databaseReferenceLike.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtOuter = findViewById(R.id.textViewOuter);
                txtTop = findViewById(R.id.textViewTop);
                txtBottom = findViewById(R.id.textViewBottom);

                databaseReferenceLike.child("outer").setValue(txtOuter.getText().toString());
                databaseReferenceLike.child("top").setValue(txtTop.getText().toString());
                databaseReferenceLike.child("bottom").setValue(txtBottom.getText().toString());
                Toast.makeText(RecommendActivity.this, "좋아요 완료!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Like Error", String.valueOf(error)); // 에러문 출력
            }
        });
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
                    if ( arrayList.get(i) > currentCel ) {
                        current[0] = i - 1;
                        System.out.println(current[0]);
                        break;
                    }
                    else if ( arrayList.get(i) == currentCel ) {
                        current[0] = i;
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

                // 랜덤으로 옷 들고오기 (사용자 데이터 고려x)
                String strOuter = outer.get( random.nextInt(outer.size()) );
                String strTop = top.get( random.nextInt(top.size()) );
                String strBottom = bottom.get( random.nextInt(bottom.size()) );
                System.out.println(strOuter); System.out.println(strTop); System.out.println(strBottom);

                // 화면 출력
                txtOuter = findViewById(R.id.textViewOuter);
                txtTop = findViewById(R.id.textViewTop);
                txtBottom = findViewById(R.id.textViewBottom);
                txtOuter.setText(strOuter);
                txtTop.setText(strTop);
                txtBottom.setText(strBottom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("Recommend Error", String.valueOf(error)); // 에러문 출력
            }
        });
    }
}