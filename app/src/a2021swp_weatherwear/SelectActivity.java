package com.example.a2021swp_weatherwear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnTapOuter, btnTapTop, btnTapBottom;

    private final int FRAGMENTOUTER = 1;
    private final int FRAGMENTTOP = 2;
    private final int FRAGMENTBOTTOM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        // 탭 버튼에 대한 참조
        btnTapOuter = findViewById(R.id.btnOuter);
        btnTapTop = findViewById(R.id.btnTop);
        btnTapBottom = findViewById(R.id.btnBottom);

        // 탭 버튼 클릭 리스너 연결
        btnTapOuter.setOnClickListener(this);
        btnTapTop.setOnClickListener(this);
        btnTapBottom.setOnClickListener(this);

        // 기본 fragment 설정 == 처음 호출시 화면에 뜨는 fragment
        callFragment(FRAGMENTOUTER);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.btnOuter):
                callFragment(FRAGMENTOUTER);
                break;
            case (R.id.btnTop):
                callFragment(FRAGMENTTOP);
                break;
            case (R.id.btnBottom):
                callFragment(FRAGMENTBOTTOM);
                break;
        }
    }

    private void callFragment(int fragment_no) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (fragment_no) {
            case (1):   // outer 프래그먼트 호출
                SelectOuterFragment fragmentOuter = new SelectOuterFragment();
                transaction.replace(R.id.fragment_container, fragmentOuter);
                transaction.commit();
                break;
            case (2):   // top 프래그먼트 호출
                SelectTopFragment fragmentTop = new SelectTopFragment();
                transaction.replace(R.id.fragment_container, fragmentTop);
                transaction.commit();
                break;
            case (3):   // bottom 프래그먼트 호출
                SelectBottomFragment fragmentBottom = new SelectBottomFragment();
                transaction.replace(R.id.fragment_container, fragmentBottom);
                transaction.commit();
                break;
        }
    }
}