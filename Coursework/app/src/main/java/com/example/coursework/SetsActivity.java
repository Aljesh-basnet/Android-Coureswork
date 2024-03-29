package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

public class SetsActivity extends AppCompatActivity {
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridView= findViewById(R.id.grid_view);

        GridAdapter adapter= new GridAdapter(getIntent().getStringExtra("title"), getIntent().getIntExtra("sets",0));//displays no of sets
        gridView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  //for back button
        if(item.getItemId() == android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
