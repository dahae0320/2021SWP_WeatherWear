package com.example.a2021swp_weatherwear;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecommendActivity extends AppCompatActivity {

    private String strNick;
    ImageButton btnAddCloset;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        final Button favBtn = (Button) findViewById(R.id.favBtn);

        // 클릭시 선택된다.
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favBtn.setSelected(true);
            }
        });

        Intent intent = getIntent();
        strNick = intent.getStringExtra("name");

        TextView tv_name = findViewById(R.id.text_name);

        // name set
        tv_name.setText(strNick);

        btnAddCloset = findViewById(R.id.imageButton1);

        btnAddCloset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i1 = new Intent(RecommendActivity.this, SelectActivity.class);
                //이미지 상의 + 버튼을 누르면 SelectActivity 화면으로 이동한다.
                startActivity(i1);
            }
        });

    }

}