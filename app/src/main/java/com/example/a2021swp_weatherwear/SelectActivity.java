package com.example.a2021swp_weatherwear;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;


public class SelectActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    Context mContext;
    private long id;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private ViewPager2 mViewPager;
    private ViewPageAdaptor myPagerAdapter;
    private TabLayout tabLayout;
    private String[] titles = new String[]{"Outer", "Top", "Bottom"};
    String code;
    private BackPressHandler backPressHandler;
    String userNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        // 사용자 이름 받아옴.
        Intent intent = getIntent();
        userNick = intent.getStringExtra("strNick");

        mContext = SelectActivity.this;
        backPressHandler = new BackPressHandler(this);

        code = "";
        Log.e(TAG, code);

        // 각각의 Outer, Top, Bottom의 fragment 객체를 만든다.
        Fragment frag1 = new SelectOuterFragment().newInstance(code, "", userNick);
        Fragment frag2 = new SelectTopFragment().newInstance(code, "", userNick);
        Fragment frag3 = new SelectBottomFragment().newInstance(code, "", userNick);

        mViewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        // 각각의 fragment들을 하나의 ViewPageAdaptor 어댑터에 넣음.
        myPagerAdapter = new ViewPageAdaptor(this);
        myPagerAdapter.addFrag(frag1);
        myPagerAdapter.addFrag(frag2);
        myPagerAdapter.addFrag(frag3);

        // fragment 어댑터를 화면에 세팅함. 각각의 fragment가 select 화면에 보여짐.
        mViewPager.setAdapter(myPagerAdapter);

        // 탭 버튼을 디스플레이함.
        new TabLayoutMediator(tabLayout, mViewPager, (tab, position) -> tab.setText(titles[position])).attach();

        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        // 로그아웃 성공시 수행하는 지점
                        Intent i2 = new Intent(SelectActivity.this, MainActivity.class);
                        // 로그아웃되면 MainActivity 화면으로 이동
                        startActivity(i2);

                    }
                });
            }
        });
    }
}