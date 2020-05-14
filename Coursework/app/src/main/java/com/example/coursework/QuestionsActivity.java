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

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {
    private TextView question,noIndex;
    private LinearLayout optionsBox;
    private Button nextButton;
    private int count=0;
    private List<QuestionsModel> list;
    private int position=0;
    private  int score=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        question=findViewById(R.id.question);
        noIndex= findViewById(R.id.no_index);
        optionsBox=findViewById(R.id.option_box);
        nextButton=findViewById(R.id.next_btn);

         list= new ArrayList<>();
        list.add(new QuestionsModel("question1", "a","b","c","d","a"));
        list.add(new QuestionsModel("question2", "a","b","c","d","b"));
        list.add(new QuestionsModel("question3", "a","b","c","d","c"));
        list.add(new QuestionsModel("question4", "a","b","c","d","d"));
        list.add(new QuestionsModel("question5", "a","b","c","d","a"));
        list.add(new QuestionsModel("question6", "a","b","c","d","b"));
        list.add(new QuestionsModel("question7", "a","b","c","d","c"));
        list.add(new QuestionsModel("question8", "a","b","c","d","d"));

        for(int i=0;i<4;i++){
            optionsBox.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer((Button) v);
                }
            });
        }


        playAnime(question,0,list.get(position).getQuestion()); //code for first question

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

