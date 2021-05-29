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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;
import java.util.Random;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Document;

import java.util.Date;


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

    TextView weatherTemp;

    // 옷차림 추천 관련 변수들
    private int currentCel = 22; // 현재 기온 변수 (임의로 지정함)
    private FirebaseDatabase firebaseDatabase, firebaseDatabaseLike, firebaseDatabaseUser;
    private DatabaseReference databaseReference, databaseReferenceLike, databaseReferenceUser;
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

    String weather_data;


    // 현재 시스템 시간 구하기
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    // 출력 형태를 위한 formmater
    String dTime = formatter.format(systemTime);
    // format에 맞게 출력하기 위한 문자열 변환

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        // 로그인한 사용자의 이름 화면 출력
        Intent intent = getIntent();
        strNick = intent.getStringExtra("name");
        TextView tv_name = findViewById(R.id.text_name);
        // name set
        tv_name.setText(strNick);

        // 플로팅 버튼
        fabMenu = findViewById(R.id.fabMenu);
        fabCloset = findViewById(R.id.fabCloset);
        fabLikelist = findViewById(R.id.fabLikelist);
        fabCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(RecommendActivity.this, SelectActivity.class);
                i1.putExtra("strNick", strNick);
                //이미지 상의 의류 추가 버튼을 누르면 SelectActivity 화면으로 이동한다.
                startActivity(i1);
            }
        });
        fabLikelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(RecommendActivity.this, LikeActivity.class);
                i3.putExtra("strNick", strNick);
                //이미지 상의 좋아요 확인 버튼을 누르면 SelectActivity 화면으로 이동한다.
                startActivity(i3);
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

        // 좋아요 버튼
        final Button favBtn = findViewById(R.id.favBtn);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favBtn.setSelected(true);
                saveLikeGarment(strNick);
            }
        });

        // 시간 및 기온 출력
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

//         if (min >= 30) {
//             Date mDate = new Date(System.currentTimeMillis());
//             SimpleDateFormat simpleDate = new SimpleDateFormat("HH00");
//             getTime = simpleDate.format(mDate);
//         }
//         else {
//             getTime = now.substring(9, 11) + "00";
        }

        System.out.println(getDate);
        System.out.println(getTime);

        System.out.println(getTime1);
        System.out.println(getTime2);
        System.out.println(getTime3);
        System.out.println(getTime4);


        //최저기온 정수형 변환
        float low_number;
        //최고기온 정수형 변환
        float high_number;

        weatherLow =  WeatherApi.getWeatherData(getDate, "HH03");
        weatherHigh =  WeatherApi.getWeatherData(getDate, "HH14");

        //문자형을 정수형으로 바꿔준다.
        low_number = Float.parseFloat(weatherLow);
        high_number = Float.parseFloat(weatherHigh);

//        if((high_number - low_number) >= 10)
//        {
//            weather_text5.setText("일교차가 큽니다.");
//        }
//        else
//        {
//            weather_text5.setText(" ");
//        }

        Log.i("low_number", String.valueOf(low_number));

        new Thread(new Runnable() {
            @Override
            public void run() {

                weather_data = WeatherApi.getWeatherData(getDate, getTime);
                weather_data1 = WeatherApi.getWeatherData(getDate, getTime1);
                weather_data2 = WeatherApi.getWeatherData(getDate, getTime2);
                weather_data3 = WeatherApi.getWeatherData(getDate, getTime3);
                weather_data4 = WeatherApi.getWeatherData(getDate, getTime4);

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
        };
        thread.start();

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
    private void saveLikeGarment(String strNick) {
        Random random = new Random();
        firebaseDatabaseLike = FirebaseDatabase.getInstance();
        // TODO : User2는 실제 사용자 데이터를 불러올 수 있도록 한다. 확인 작업!
        databaseReferenceLike = firebaseDatabaseLike.getReference("User").child(strNick).child("Like").child(String.valueOf(random.nextInt()));

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

        ArrayList<String> userOuter = new ArrayList<>();
        ArrayList<String> userTop = new ArrayList<>();
        ArrayList<String> userBottom = new ArrayList<>();

        Random random = new Random();
        final int[] current = new int[1];

        // 사용자 데이터 들고오기
        getUserGarment(strNick, userOuter, "Outer");
        getUserGarment(strNick, userTop,"Top");
        getUserGarment(strNick, userBottom, "Bottom");
        outer.clear(); top.clear(); bottom.clear();

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

                // 현재 기온에 맞는 추천 옷차림을 따로 배열에 할당, 사용자가 가지지 않은 옷이면 배열에 저장되지 않음
                for(DataSnapshot dataSnapshot : snapshot.child(String.valueOf(arrayList.get(current[0]))).child("Outer").getChildren() ) {
//                    System.out.println(dataSnapshot.getValue());
                    if (userOuter.contains(dataSnapshot.getValue().toString())) {
                        outer.add((String) dataSnapshot.getValue());
                    }
                }
                for(DataSnapshot dataSnapshot : snapshot.child(String.valueOf(arrayList.get(current[0]))).child("Top").getChildren() ) {
//                    System.out.println(dataSnapshot.getValue());
                    if (userTop.contains(dataSnapshot.getValue().toString())) {
                        top.add((String) dataSnapshot.getValue());
                    }
                }
                for(DataSnapshot dataSnapshot : snapshot.child(String.valueOf(arrayList.get(current[0]))).child("Bottom").getChildren() ) {
//                    System.out.println(dataSnapshot.getValue());
                    if (userBottom.contains(dataSnapshot.getValue().toString())) {
                        bottom.add((String) dataSnapshot.getValue());
                    }
                }

                if (outer.isEmpty()) {
                    outer.add("저장된 옷이 없음");
                }
                if (top.isEmpty()) {
                    top.add("저장된 옷이 없음");
                }
                if (bottom.isEmpty()) {
                    bottom.add("저장된 옷이 없음");
                }

                // 랜덤으로 옷 들고오기 (사용자 데이터 고려함)
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

    // 사용자 옷 데이터 불러오는 메소드
    private void getUserGarment(String nick, ArrayList list, String type) {

        firebaseDatabaseUser = FirebaseDatabase.getInstance();
        databaseReferenceUser = firebaseDatabaseUser.getReference("User");

        list.clear();
        databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.child(nick).child(type).getChildren()) {
                    list.add(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("Recommend Error", String.valueOf(error)); // 에러문 출력
            }
        });
    }

}