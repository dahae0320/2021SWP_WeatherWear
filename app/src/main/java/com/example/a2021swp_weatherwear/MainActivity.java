package com.example.a2021swp_weatherwear;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.security.MessageDigest;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class MainActivity extends AppCompatActivity {

    private ISessionCallback mSessionCallback;
    TextView tv;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv);
        tv = findViewById(R.id.tv);

        //뷰에 애니메이션 적용
        Animation ani1 = AnimationUtils.loadAnimation(this,R.anim.bounce);
        iv.startAnimation(ani1);
        tv.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in));

        //애니메이션이 끝나면 로그인 버튼이 보임
        ani1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.loginBtn).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        mSessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                // 로그인 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        // 로그인 실패
                        Toast.makeText(MainActivity.this, "로그인 도중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        // 세션이 닫힘
                        Toast.makeText(MainActivity.this, "세션이 닫혔습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        // 로그인 성공
                        Intent intent = new Intent(MainActivity.this, RecommendActivity.class);
                        intent.putExtra("name", result.getKakaoAccount().getProfile().getNickname());

                        startActivity(intent);

                        Toast.makeText(MainActivity.this, "환영합니다!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {

            }
        };

        Session.getCurrentSession().addCallback(mSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

        getAppKeyHash();
    }


    /*카카오 로그인 시 필요한 해시키를 얻는 메소드*/
    private void getAppKeyHash(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("KeyHash", something);
            }
        }catch (Exception e) {
            Log.e("name not found", e.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(mSessionCallback);
    }

    public void onCustomToggleClick(View view) {
        Toast.makeText(this, "CustomToggle", Toast.LENGTH_SHORT).show();
    }
}