package com.example.coursework;

import android.content.Intent;
import android.nfc.TagLostException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder>{
    private  List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent, false);
        return new Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

            String title=categoryModelList.get(position).getName();//getting name and sets from modelclass object
            int sets=categoryModelList.get(position).getSets();

            holder.setData(title,sets);//start new activity
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView title;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title); //mapping category_item text view to title

        }
        private void setData(final String title, final int sets){

            this.title.setText(title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent setIntent = new Intent(itemView.getContext(), SetsActivity.class);
                    setIntent.putExtra("title", title);
                    setIntent.putExtra("sets", sets);
                    itemView.getContext().startActivity(setIntent);
                }
            });
        }
    }
}