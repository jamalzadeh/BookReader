package dictonary.mj.dastan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milad on 8/19/2015.
 */
public class WordmAdapter extends RecyclerView.Adapter<WordmAdapter.wordViewHolder> {
    private List<Word> wordList = new ArrayList<Word>();
    Context context;
    Insertword datasource;
    DataBaseHelper myDbHelper;
int mark;
    public  class wordViewHolder extends RecyclerView.ViewHolder {
        ImageView star;
        TextView wordtext;
        TextView wordmean;

        public wordViewHolder(View v) {
            super(v);
            star = (ImageView) v.findViewById(R.id.star);
            wordtext = (TextView) v.findViewById(R.id.wordtext);
wordmean=(TextView) v.findViewById(R.id.wordmean);
        }
    }
    public void add(Word object) {
        wordList.add(object);
    }
    public WordmAdapter(List<Word> wordList) {
        this.wordList=wordList;

    }
    @Override
    public int getItemCount() {
        return wordList.size();
    }
    @Override
    public wordViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        // create a new view
context=parent.getContext();
        myDbHelper = new DataBaseHelper(context);
        datasource=new Insertword(context);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wordlist_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        wordViewHolder vh = new wordViewHolder( v);
        return vh;
    }
    @Override
    public void onBindViewHolder(final wordViewHolder holder, final int position) {
      final Word ci= wordList.get(position);
        Log.v("se size",String.valueOf(wordList.size()));
        Log.v("ci",ci.getText());
        holder.wordtext.setText(ci.getText());
        holder.wordmean.setText(ci.getMean());
        mark=0;
        if(ci.getMarked()){
            holder.star.setImageResource(R.drawable.starpressed);
            mark=1;
        }
        holder.wordmean.setOnClickListener((View.OnClickListener) new OnClickListener2(position));
        holder.wordtext.setOnClickListener((View.OnClickListener) new OnClickListener2(position));
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mark==0){
                    mark=1;
                    Log.v("inner mark",String.valueOf(mark));
                    holder.star.setImageResource(R.drawable.starpressed);
                    datasource.open();

                    datasource.updatemark(position,mark);
                    datasource.close();
                }
                else{
                    holder.star.setImageResource(R.drawable.normalstar);
                    mark=0;
                    datasource.open();

                    datasource.updatemark(position,mark);
                    datasource.close();
                }
            }
        });
    }
    private class OnClickListener2 implements View.OnClickListener {
        Intent dbintent;
        private int mPosition;

        private OnClickListener2(int position) {
            mPosition = position;
        }


        @Override
        public void onClick(View v) {
            dbintent = new Intent(context, WordDetailActivity.class);
            String ids=String.valueOf(mPosition);
            Log.v("first id",ids);
            dbintent.putExtra("id", ids);

            context.startActivity(dbintent);
        }
    }
}
