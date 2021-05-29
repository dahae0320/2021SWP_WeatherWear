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
//        System.out.println("select에서 사용자 이름 : " + userNick);

        mContext = SelectActivity.this;
        backPressHandler = new BackPressHandler(this);

//        code = getIntent().getExtras().getString("code"); // 다른 Activity에서 값을 넘겨 받았을 때
        code = "";
        Log.e(TAG, code);

        Fragment frag1 = new SelectOuterFragment().newInstance(code, "", userNick);
        Fragment frag2 = new SelectTopFragment().newInstance(code, "", userNick);
        Fragment frag3 = new SelectBottomFragment().newInstance(code, "", userNick);

        mViewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        myPagerAdapter = new ViewPageAdaptor(this);
        myPagerAdapter.addFrag(frag1);
        myPagerAdapter.addFrag(frag2);
        myPagerAdapter.addFrag(frag3);

        mViewPager.setAdapter(myPagerAdapter);

        //displaying tabs
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