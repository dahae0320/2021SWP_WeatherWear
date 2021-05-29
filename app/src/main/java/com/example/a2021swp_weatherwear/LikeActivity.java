package com.example.a2021swp_weatherwear;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
//import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LikeActivity extends AppCompatActivity {

    RecyclerView mRecyclerView = null ;
    RecyclerLikeAdaptor mAdapter = null ;
    ArrayList<RecyclerLikeItem> mList = new ArrayList<RecyclerLikeItem>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userNick;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        // 사용자 이름 받아옴
        Intent intent = getIntent();
        userNick = intent.getStringExtra("strNick");

        // toolbar 지정
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mRecyclerView = findViewById(R.id.recyclerViewLike);

        // 리사이클러뷰에 RecyclerLikeAdaptor 객체 지정.
        mAdapter = new RecyclerLikeAdaptor(mList) ;
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<RecyclerLikeItem> list = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = firebaseDatabase.getReference("User"); // DB 테이블 연결

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                list.clear(); // 기존 배열리스트가 존재하지않게 초기화
                // TODO : User2는 실제 사용자 이름으로 넣을
                for (DataSnapshot snapshot : dataSnapshot.child(userNick).child("Like").getChildren()) { // 반복문으로 데이터 list 를 추출해냄
                    addItem(getDrawable(R.drawable.hanger), getDrawable(R.drawable.hanger), getDrawable(R.drawable.hanger),
                            snapshot.child("outer").getValue().toString(),
                            snapshot.child("top").getValue().toString(),
                            snapshot.child("bottom").getValue().toString());
                }
                mAdapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침해야 반영이 됨
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("FragLike", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });


        
    }

    public void addItem(Drawable iconOuter, Drawable iconTop, Drawable iconBottom, String strOuter, String strTop, String strBottom) {
        RecyclerLikeItem item = new RecyclerLikeItem();

        item.setIconLikeOuter(iconOuter);
        item.setIconLikeTop(iconTop);
        item.setIconLikeBottom(iconBottom);
        item.setStrLikeOuter(strOuter);
        item.setStrLikeTop(strTop);
        item.setStrLikeBottom(strBottom);

        mList.add(item);
    }
}