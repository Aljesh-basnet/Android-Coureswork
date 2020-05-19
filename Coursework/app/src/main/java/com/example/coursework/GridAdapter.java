package com.example.coursework;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GridAdapter extends BaseAdapter {
private  int sets =0;
private String category;
    public GridAdapter(String category,int sets)
    {
        this.category=category;
        this.sets = sets;
    }

    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;


        if(convertView==null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item,parent,false);
        }
        else {
            view=convertView;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questionIntent = new Intent(parent.getContext(),QuestionsActivity.class);//for generating questions
                questionIntent.putExtra("category",category);
                questionIntent.putExtra("sets",position+1);
                parent.getContext().startActivity(questionIntent);

            }
        });

        ((TextView) view.findViewById(R.id.set_textview)).setText(String.valueOf(position+1)); //casting view to textView
        //position starts with 0 but sets starts with 1
        return view;
    }
}
