package dictonary.mj.dastan;

import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WordArrayAdapter extends ArrayAdapter<Word>{
	private List<Word> wordList = new ArrayList<Word>();
	int color;
	 static class WordViewHolder {
	        TextView word;
	         }
	 public WordArrayAdapter(Context context, int textViewResourceId) {
	        super(context, textViewResourceId);
	        color=Color.parseColor("#3F7D32");
	    }
	 @Override
		public void add(Word object) {
			wordList.add(object);
			super.add(object);
		}

	    @Override
		public int getCount() {
			return this.wordList.size();
		}
	    @Override
	  		public Word getItem(int index) {
	  			return this.wordList.get(index);
	  		}
	    @Override
		public View getView(int position, View convertView, ViewGroup parent) {
	    	
			View row = convertView;
	        WordViewHolder viewHolder;
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.listview_row_2_layout, parent, false);
	            viewHolder = new WordViewHolder();
	           
	           
	            viewHolder.word = (TextView) row.findViewById(R.id.bookauthor);
	            row.setTag(viewHolder);
			} else {
	            viewHolder = (WordViewHolder)row.getTag();
	        }
			Word word = getItem(position);
		
			
			
	        
	       
	        viewHolder.word.setText(word.getText());
	        if(word.getMarked()){
	        	viewHolder.word.setBackgroundColor(color);
	        }
			return row;
		}
}
