package dictonary.mj.dastan;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

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


public class PdfArrayAdapter extends ArrayAdapter<Fruit> {
	private List<Fruit> bookList = new ArrayList<Fruit>();
	public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	Context contexta;
	 Insertword datasource;
	 DataBaseHelper myDbHelper;
	
	int Position;
	String tablename;
	 ArrayList<Epubbooks> bookq=new ArrayList<Epubbooks>();
    static class BookViewHolder {
        ImageView bookImg;
        TextView bookName;
        TextView bookauthor;
        
        String Table;
        
    }

    public PdfArrayAdapter(Context context, int textViewResourceId) {
    	
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
    	Log.v("small position",String.valueOf(position));
    	
    	Position=position+1;
    	Log.v("Big Position",String.valueOf(Position));
		View row = convertView;
         BookViewHolder viewHolder;
		if (row == null) {
			contexta=this.getContext();
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listview_row_pdf, parent, false);
            viewHolder = new BookViewHolder();
            viewHolder.bookImg = (ImageView) row.findViewById(R.id.bookImg);
            viewHolder.bookName = (TextView) row.findViewById(R.id.bookName);
            viewHolder.bookauthor = (TextView) row.findViewById(R.id.bookauthor);
            
            row.setTag(viewHolder);
		} else {
            viewHolder = (BookViewHolder)row.getTag();
        }
		Fruit fruit = getItem(position);
		Log.v("View","called");
		tablename=fruit.getTableName();
		viewHolder.Table=tablename;
		
	
		File file = new File(IM_PATH+fruit.getFruitImg());
		if(file.exists()){      
			Drawable test;
			test=Drawable.createFromPath(IM_PATH+fruit.getFruitImg());
			
	     viewHolder.bookImg.setImageDrawable(test);
	     viewHolder.bookImg.getLayoutParams().width = 80;
	     viewHolder.bookImg.getLayoutParams().height = 120;
	     viewHolder.bookImg.setAdjustViewBounds(true);
	    
		}else{
			
			 viewHolder.bookImg.setImageResource(R.drawable.epub2);
			 viewHolder.bookImg.getLayoutParams().width = 80;
		     viewHolder.bookImg.getLayoutParams().height = 120;
		     viewHolder.bookImg.setAdjustViewBounds(true);
		}
		myDbHelper = new DataBaseHelper(contexta);
         datasource=new Insertword(contexta);
		// Do something else.
		 
		    Log.v("set button",String.valueOf(position));
		 		 		    
		        viewHolder.bookName.setText(fruit.getTableName());
      
        viewHolder.bookauthor.setText(fruit.getCalories());
        
		return row;
	}

    public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
   public void updatetable(int pos){
  long size=myDbHelper.counttable("epub");
 
  
  for(long i=1;i<=size;i++){
	
	  
	if(i!=pos){
		Epubbooks bookd=myDbHelper.getimage((int) i,"epub"); 
		if(bookd!=null){
		bookq.add(bookd);
		
		}
	}
  }
  datasource.deletetable("epub");
  
  datasource.createepubtable();
  if(bookq!=null){
  for(int i=0;i<bookq.size();i++){
	  Epubbooks a=bookq.get(i);
	//  Log.v("new table name",a.getisbn());
	  datasource.createepubbooks("epub",a.getisbn(),a.gettitle(),a.getcreater(),a.getTOC());
  }
  }
}

}
