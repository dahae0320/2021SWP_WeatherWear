package com.example.a2021swp_weatherwear;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
//import android.widget.Toolbar;

import java.util.ArrayList;

public class LikeActivity extends AppCompatActivity {

    RecyclerView mRecyclerView = null ;
    RecyclerLikeAdaptor mAdapter = null ;
    ArrayList<RecyclerLikeItem> mList = new ArrayList<RecyclerLikeItem>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        // toolbar 지정
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mRecyclerView = findViewById(R.id.recyclerViewLike);

        // 리사이클러뷰에 RecyclerLikeAdaptor 객체 지정.
        mAdapter = new RecyclerLikeAdaptor(mList) ;
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // 아이템 추가.
        addItem(getDrawable(R.drawable.hanger), getDrawable(R.drawable.hanger), getDrawable(R.drawable.hanger),
                "겉오옹", "상의이잉", "하의이");
        // 두 번째 아이템 추가.
        addItem(getDrawable(R.drawable.hanger), getDrawable(R.drawable.hanger), getDrawable(R.drawable.hanger),
                "겉오옹2", "상의이잉2", "하의이2");
        // 세 번째 아이템 추가.
        addItem(getDrawable(R.drawable.hanger), getDrawable(R.drawable.hanger), getDrawable(R.drawable.hanger),
                "겉오옹3", "상의이잉3", "하의이3");

        mAdapter.notifyDataSetChanged() ;
        
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