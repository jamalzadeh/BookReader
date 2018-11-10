package dictonary.mj.dastan;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class BookArrayAdapter extends ArrayAdapter<Fruit> {
  //  private static final String TAG = "BookArrayAdapter";
	private List<Fruit> bookList = new ArrayList<Fruit>();
	public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	Context contexta;
	 Insertword datasource;
	 DataBaseHelper myDbHelper;
	AlertDialog.Builder builder;
	int Position;
	String tablename;
	 ArrayList<Epubbooks> bookq=new ArrayList<Epubbooks>();
    static class BookViewHolder {
        ImageView bookImg;
        TextView bookName;
        TextView bookauthor;
        ImageView more;
        String Table;
        
    }

    public BookArrayAdapter(Context context, int textViewResourceId) {
    	
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
			row = inflater.inflate(R.layout.listview_row_layout, parent, false);
            viewHolder = new BookViewHolder();
            viewHolder.bookImg = (ImageView) row.findViewById(R.id.bookImg);
            viewHolder.bookName = (TextView) row.findViewById(R.id.bookName);
            viewHolder.bookauthor = (TextView) row.findViewById(R.id.bookauthor);
            viewHolder.more=(ImageView) row.findViewById(R.id.more);
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
		 builder = new AlertDialog.Builder(contexta);

		    builder.setTitle(contexta.getResources().getString(R.string.bookdelete));
		    builder.setMessage(contexta.getResources().getString(R.string.sure));
		    Log.v("set button",String.valueOf(position));
		  //  builder.setPositiveButton(contexta.getResources().getString(R.string.yes),new OnClickListener2(position));
		  /*  builder.setPositiveButton(contexta.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

		        public void onClick(DialogInterface dialog, int which) {
		            // Do nothing but close the dialog
		        	int gh=position;
		        	Log.v("clicked tablename",viewHolder.Table);
		        	bookdelete(viewHolder.Table);
		            dialog.dismiss();
		        }

		    });*/
		    builder.setNegativeButton(contexta.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		            // Do nothing
		            dialog.dismiss();
		        }
		    });
		    
		viewHolder.more.setOnClickListener(new OnClickListener() {

           @Override
            public void onClick(View v) {
            


              

                    PopupMenu popup = new PopupMenu(contexta, v);
                    popup.getMenuInflater().inflate(R.menu.popup_menu,
                            popup.getMenu());
                    popup.show();
                    
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                            case R.id.remove:

                            	 
                            	 AlertDialog alert = builder.create();
                       		    alert.show();
                            

                                break;
                            

                            default:
                                break;
                            }

                            return true;
                        }
                    });

                    

              



            }
        });
        viewHolder.bookName.setText(fruit.getFruitName());
      
        viewHolder.bookauthor.setText(fruit.getCalories());
        viewHolder.more.setOnClickListener((OnClickListener) new OnClickListener2(position));
		return row;
	}

    public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
    public void	bookdelete(String table,int Lposition){
		Log.v("deleted book",table);
		try {
			myDbHelper.openDataBase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long imagesize=myDbHelper.counttable("images");
		for(long f=1;f<=imagesize;f++){
			Epubbooks imgr=myDbHelper.getimage((int) f, "images");
					
			if(imgr.gettitle().equals(table)){
				File file = new File(IM_PATH+imgr.getcreater());
				boolean deleted = file.delete();
			}//end if
		}//end first for
		datasource.open();
		
		datasource.deletetable(table);
		
		updatetable(Lposition);
	//	datasource.deleterow("epub", position);
		long status_size=myDbHelper.counttable("epub_status");
		
		for(long b=1;b<=status_size;b++){
			Matns state=myDbHelper.getmatn((int) b, "epub_status") ;
			if(state._matn.equals(table)){
				datasource.updateMatn(b,"download_ready","epub_status");
				break;
			}
		}
		MainActivity.fa.finish();
		
		 Intent intentm = new Intent(contexta,MainActivity.class);
		    contexta.startActivity(intentm);
			
			
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
private class OnClickListener2 implements OnClickListener {

    private int mPosition;

    private OnClickListener2(int position){
        mPosition = position;
    }

   
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 PopupMenu popup = new PopupMenu(contexta, v);
         popup.getMenuInflater().inflate(R.menu.popup_menu,
                 popup.getMenu());
         popup.show();
         
         popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem item) {

                 switch (item.getItemId()) {
                 case R.id.remove:
                	 AlertDialog.Builder builder2;
                	 builder2 = new AlertDialog.Builder(contexta);

         		    builder2.setTitle(contexta.getResources().getString(R.string.bookdelete));
         		    builder2.setMessage(contexta.getResources().getString(R.string.sure));
         		   builder2.setPositiveButton(contexta.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

       		        public void onClick(DialogInterface dialog, int which) {
       		            // Do nothing but close the dialog
       		        	int gh=mPosition;
       		        	Log.v("mPosition",String.valueOf(mPosition));
       		 		Fruit fruit = getItem(mPosition);
       		 		
       		 		String innername=fruit.getTableName();
       		 		Log.v("clicked tablename",innername);

       		        	bookdelete(innername,mPosition+1);
       		            dialog.dismiss();
       		        }

       		    });
       		    builder2.setNegativeButton(contexta.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

       		        @Override
       		        public void onClick(DialogInterface dialog, int which) {
       		            // Do nothing
       		            dialog.dismiss();
       		        }
       		    });
       		    

                 	 AlertDialog alert = builder2.create();
            		    alert.show();
                 

                     break;
                 

                 default:
                     break;
                 }

                 return true;
             }
         });

         

				
    //	bookdelete(innername);
      //  arg0.dismiss();
        
	}   
}
}

