package com.example.a2021swp_weatherwear;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class SelectActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    Context mContext;

    private ViewPager2 mViewPager;
    private ViewPageAdaptor myPagerAdapter;
    private TabLayout tabLayout;

    private String[] titles = new String[]{"Outer", "Top", "Bottom"};

    String code;
    private BackPressHandler backPressHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        mContext = SelectActivity.this;
        backPressHandler = new BackPressHandler(this);

        //code = getIntent().getExtras().getString("code"); // 다른 Activity에서 값을 넘겨 받았을 때
        code = "";
        Log.e(TAG, code);

        Fragment frag1 = new SelectOuterFragment().newInstance(code,"");
        Fragment frag2 = new SelectTopFragment().newInstance(code,"");
        Fragment frag3 = new SelectBottomFragment().newInstance(code,"");

        mViewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        myPagerAdapter = new ViewPageAdaptor(this);
        myPagerAdapter.addFrag(frag1);
        myPagerAdapter.addFrag(frag2);
        myPagerAdapter.addFrag(frag3);

        mViewPager.setAdapter(myPagerAdapter);

        //displaying tabs
        new TabLayoutMediator(tabLayout, mViewPager, (tab, position) -> tab.setText(titles[position])).attach();
    }

}