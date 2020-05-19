package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {
    private TextView question;
    private LinearLayout optionsBox;
    private Button nextButton;
    private int count=0;
    private List<QuestionsModel> list;
    private int position=0;
    private  int score=0;
    private String category;
    private int sets;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        question=findViewById(R.id.question);
        TextView noIndex = findViewById(R.id.no_index);
        optionsBox=findViewById(R.id.option_box);
        nextButton=findViewById(R.id.next_btn);

        category=getIntent().getStringExtra("category");
        sets=getIntent().getIntExtra("sets",1);


        list=new ArrayList<>();
        myRef.child("SETS").child(category).child("questions").orderByChild("sets").equalTo(sets).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    list.add(dataSnapshot1.getValue(QuestionsModel.class));
                }
                if (list.size()>0){

                    for(int i=0;i<4;i++){
                        optionsBox.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkAnswer((Button) v);
                            }
                        });
                    }
                    playAnime(question,0,list.get(position).getQuestion()); //code for first question4
                    nextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nextButton.setEnabled(false);
                            nextButton.setAlpha(0.7f);
                            position++;
                            enableOption(true);
                            if(position ==list.size())

                            {
                                //score activity
                                return;
                            }
                            count=0;
                            playAnime(question,0,list.get(position).getQuestion());
                        }
                    });
                }
                else  {
                    finish();
                    Toast.makeText(QuestionsActivity.this, "No questions found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuestionsActivity.this,databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void playAnime(final View view, final int value, final String data){
//        view.animate().alpha(value).scaleX(value).setDuration(500).setStartDelay(100)
//        .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener()
         view.animate().alpha(value).scaleX(value).setDuration(500).setStartDelay(100)
        .setInterpolator(new AccelerateInterpolator()).setListener(new Animator.AnimatorListener()  {
            @Override
            public void onAnimationStart(Animator animation) {
                String option="";
                if(value==0 && count<4){
                    if(count==0){
                        option =list.get(position).getOptionA();
                    }
                    else if(count==1){
                        option =list.get(position).getOptionB();
                    }
                    else if(count==2){
                        option =list.get(position).getOptionC();
                    }
                    else if(count==3){
                        option =list.get(position).getOptionD();
                    }
                    playAnime(optionsBox.getChildAt(count),0, option); //view is invisible
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(value==0){
                    try {
                        ((TextView)view).setText(data);
//                        noIndex.setText(position+1+"/"+list.size());
                    }catch (ClassCastException ex){
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);  //view has memories of different options
                    playAnime(view,1,data);//view is visible
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });     //code for animation for displaying different questions and options when next button is clicked

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  //for back button
        if(item.getItemId() == android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);

    }
    private void checkAnswer(Button selected){
        enableOption(false);
        nextButton.setEnabled(true);
        nextButton.setAlpha(1);
        if (selected.getText().toString().equals(list.get(position).getCorrectAns())){
            //if answer is correct
            score++;
            selected.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7180AC")));

        }
        else{
            //if answer is incorrect
            selected.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F07167")));
            Button correctoption=(Button) optionsBox.findViewWithTag(list.get(position).getCorrectAns());
            correctoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7180AC")));
        }

    }
    private void enableOption(boolean enable){
        for(int i=0;i<4;i++){
            optionsBox.getChildAt(i).setEnabled(enable);
            if(enable){ //resetting the buttons to default
                optionsBox.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#56666B")));
            }
        }
    }

}

