package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    private TextView score,total;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score=findViewById(R.id.score);
        total=findViewById(R.id.total);
        btn=findViewById(R.id.done);

        score.setText(String.valueOf(getIntent().getIntExtra("score",0)));
        total.setText("Out of "+ String.valueOf(getIntent().getIntExtra("total",0)));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ScoreActivity.this,CategoriesActivity.class);
                startActivity(intent);
            }
        });
    }
}
