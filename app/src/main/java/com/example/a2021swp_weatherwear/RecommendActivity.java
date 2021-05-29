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
import java.net.URL;
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
import java.util.Date;


public class RecommendActivity extends AppCompatActivity {
    FloatingActionMenu fabMenu;
    FloatingActionButton fabCloset;
    FloatingActionButton fabLikelist;

    // 옷차림 추천 관련 변수들
    private double currentCel; // 현재 기온 변수
    private FirebaseDatabase firebaseDatabase, firebaseDatabaseLike;
    private DatabaseReference databaseReference, databaseReferenceLike;
    private TextView txtOuter, txtTop, txtBottom;

    private String strNick;

    long systemTime = System.currentTimeMillis();

    TextView timer;
    TextView time1;
    TextView time2;
    TextView time3;
    TextView time4;

    TextView weather_text;
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

                weather_data = WeatherApi.getWeatherData(getDate, getTime);
                currentCel = Double.parseDouble(weather_data);
                System.out.println(currentCel);

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
    private void recommendGarment(double currentCel) {
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