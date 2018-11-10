package dictonary.mj.dastan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dictonary.mj.dastan.BookArrayAdapter.BookViewHolder;

public class BookArrayAdapter2 extends ArrayAdapter<Fruit> {
	  //  private static final String TAG = "BookArrayAdapter";
		private List<Fruit> bookList = new ArrayList<Fruit>();
		public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	    static class BookViewHolder {
	        ImageView bookImg;
	        TextView bookName;
	        TextView bookauthor;
	    }

	    public BookArrayAdapter2(Context context, int textViewResourceId) {
	        super(context, textViewResourceId);
	    }

		@Override
		public void add(Fruit object) {
			bookList.add(object);
			super.add(object);
		}

	    @Override
		public int getCount() {
			return this.bookList.size();
		}

	    @Override
		public Fruit getItem(int index) {
			return this.bookList.get(index);
		}

	    @Override
		public View getView(int position, View convertView, ViewGroup parent) {
	    	
			View row = convertView;
	        BookViewHolder viewHolder;
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.listview_row_2_layout, parent, false);
	            viewHolder = new BookViewHolder();
	           // viewHolder.bookImg = (ImageView) row.findViewById(R.id.bookImg);
	           
	            viewHolder.bookauthor = (TextView) row.findViewById(R.id.bookauthor);
	            row.setTag(viewHolder);
			} else {
	            viewHolder = (BookViewHolder)row.getTag();
	        }
			Fruit fruit = getItem(position);
		/*	File file = new File(IM_PATH+fruit.getFruitImg());
			if(file.exists()){      
				Drawable test;
				test=Drawable.createFromPath(IM_PATH+fruit.getFruitImg());
				
		     viewHolder.bookImg.setImageDrawable(test);
		     viewHolder.bookImg.getLayoutParams().width = 80;
		     viewHolder.bookImg.getLayoutParams().height = 120;
		     viewHolder.bookImg.setAdjustViewBounds(true);
		     Log.v("ba aks","ba aks");
			}else{
				Log.v("bedun aks","bedun aks");
				 viewHolder.bookImg.setImageResource(R.drawable.epub2);
				 viewHolder.bookImg.getLayoutParams().width = 80;
			     viewHolder.bookImg.getLayoutParams().height = 120;
			     viewHolder.bookImg.setAdjustViewBounds(true);
			}*/
			
			
	        
	       
	        viewHolder.bookauthor.setText(fruit.getCalories());
	        Log.v("book author",fruit.getCalories());
			return row;
		}

	    public Bitmap decodeToBitmap(byte[] decodedByte) {
			return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
		}
	}

