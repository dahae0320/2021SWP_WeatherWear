package com.example.a2021swp_weatherwear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class RecommendActivity extends AppCompatActivity {

    ImageButton btnAddCloset;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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