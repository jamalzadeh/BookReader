package dictonary.mj.dastan;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
 
public class TopRatedFragment extends Fragment {
	 private static final String TAG = "ListViewActivity";
	    private BookArrayAdapter bookArrayAdapter;
		private ListView listView;
		
		 DataBaseHelper myDbHelper;
		Context context;
		String tablename;
		Insertword datasource;
		TextView textview;
		Typeface type;
		int Position;
		AlertDialog.Builder builder;
		ArrayList<Epubbooks> bookq=new ArrayList<Epubbooks>();
		 public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);
        context=getActivity();
        datasource=new Insertword(context);
        type=Typeface.createFromFile(IM_PATH+"Nazanin.ttf");
        Book fg;
        
        listView = (ListView) rootView.findViewById(R.id.listView);
        textview=(TextView) rootView.findViewById(R.id.textview);
    	bookArrayAdapter = new BookArrayAdapter(context, R.layout.listview_row_layout);
    	myDbHelper = new DataBaseHelper(context);
    	
    	try {
			myDbHelper.openDataBase();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	listView.setAdapter(bookArrayAdapter);
    	if(myDbHelper.counttable("epub")==0){

          //  Fruit book = new Fruit("b",getResources().getString(R.string.noBooks),"","0");
           // bookArrayAdapter.add(book);
    		Log.v("0","0");
    		textview.setVisibility(View.VISIBLE);
    		textview.setTypeface(type);
    		textview.setText(getResources().getString(R.string.noBooks));
    	}else{
    		Log.v("hello","1");
            List<String[]> bookList = readData();
            for(String[] bookData:bookList ) {
            	Log.v("hello","2");
                String bookImg = bookData[0];
                Log.v("bookImg",bookImg);
                String bookauthor = bookData[1];
                String bookName = bookData[2];
                String id=bookData[3];
                String ta=bookData[4];
                Log.v("fruit id",id);
                Fruit book = new Fruit(bookImg,bookName,bookauthor,ta,id);
                bookArrayAdapter.add(book);
                
                }
            
           
    	}
    	myDbHelper.close();
    	
    	builder = new AlertDialog.Builder(context);

	    builder.setTitle(getResources().getString(R.string.bookdelete));
	    builder.setMessage(getResources().getString(R.string.sure));

	    builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

	        public void onClick(DialogInterface dialog, int which) {
	            // Do nothing but close the dialog
	        	Log.v("deleted table",tablename);
	        	bookdelete(tablename);
	            dialog.dismiss();
	        }

	    });
	    builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            // Do nothing
	            dialog.dismiss();
	        }
	    });
    	 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    	      @Override
    	      public void onItemClick(AdapterView<?> parent, final View view,
    	          int position, long id) {
    	     Log.v("position",Integer.toString(position));
    	       
    	       int page=0;
    	     /*  try{
    	    	  long size= myDbHelper.counttable("last_page");
    	    	  for(long j=1;j<=size;j++){
    	    		  Log.v("tablename1",myDbHelper.getContact((int) j,"last_page")._1name);
    	    		  if(myDbHelper.getContact((int) j,"last_page")._1name.equals(tablename)){
    	    			  page=Integer.valueOf(myDbHelper.getContact((int) j,"last_page")._2name);
    	    			  Log.v("found","first");
    	    			  break;
    	    		  }
    	    	  }
    	       }catch (Throwable e){
    	    	   
    	       }*/
    	       Position=position;
    	      // Log.v("position",Integer.toString(position));
    	       //Log.v("array size=",String.valueOf(bookArrayAdapter.getCount()));
    	       if(bookArrayAdapter.getItem(position).getid().equals("0")){
    	    	   
    	       }else{
    	    	  
    	    //   Intent mIntent=new Intent(context,BookPager.class);
       	//	mIntent.putExtra("shomare_dars",page);
       	//	mIntent.putExtra("table",tablename);
    	   //  int ids= Integer.valueOf( bookArrayAdapter.getItem(position).getid());
       		//mIntent.putExtra("position",position+1);
       		//startActivity(mIntent); 
       		
       		
       	 Epubbooks book=myDbHelper.getimage(position+1,"epub");
         tablename=book.getisbn();
         try{
       	  long size= myDbHelper.counttable("last_page");
       	  for(long j=1;j<=size;j++){
       		//  Log.v("tablename1",myDbHelper.getContact((int) j,"last_page")._1name);
       		  if(myDbHelper.getContact((int) j,"last_page")._1name.equals(tablename)){
       			  page=Integer.valueOf(myDbHelper.getContact((int) j,"last_page")._2name);
       			 // Log.v("found","first");
       			  break;
       		  }
       	  }
          }catch (Throwable e){
       	   
          }
         Intent mIntent;
         if(page==0){
   			 mIntent=new Intent(context,MainActivity3B.class);
        		mIntent.putExtra("shomare_dars",1);
        		mIntent.putExtra("table",tablename);
        		
        		startActivity(mIntent);    
 	       }else{
 	        mIntent=new Intent(context,MainActivity3B.class);
    		mIntent.putExtra("shomare_dars",page);
    		mIntent.putExtra("table",tablename);
    		
    		startActivity(mIntent); 
 	      }
    	      }}

    	    });
    	 return rootView;
        
    }
public List<String[]> readData(){
	List<String[]> resultList = new ArrayList<String[]>();
    try {
		myDbHelper.openDataBase();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   
    long imagesize=myDbHelper.counttable("images");
    long tablesize=myDbHelper.counttable("epub");
 //   Log.v("table size",String.valueOf(tablesize));
    for(long i=1;i<=tablesize;i++){
    try{	String[] fruit = new String[5];
    fruit[3]=String.valueOf(i);
  //  Log.v("fruit3",fruit[3]);
    //	Log.v("i",String.valueOf(i));
    	Epubbooks epubb=myDbHelper.getimage((int) i , "epub");
    	
    	for(long k=1;k<=imagesize;k++){
    		Epubbooks img=myDbHelper.getimage((int) k , "images");
    		//Log.v("read isbn=cover",img.getisbn());
    	//	Log.v("read title=",img.gettitle().substring(img.gettitle().lastIndexOf("/")+1,img.gettitle().lastIndexOf(".")));
    		//Log.v("read isbn",epubb.getisbn());
    		if(img.getisbn().equals("cover")&&img.gettitle().substring(img.gettitle().lastIndexOf("/")+1,img.gettitle().lastIndexOf(".")).equals(epubb.getisbn())){
    			fruit[0]=img.getcreater();
    			
    			break;
    		}
    	}
    	
    	fruit[1] = epubb.gettitle();
    	fruit[2] = epubb.getcreater();
    	fruit[4]=epubb.getisbn();
    	resultList.add(fruit);}catch (Throwable gh){}
    }
    return  resultList;
}
public void Remove(int position){
	Position=position+1;
	 tablename="table_" +Integer.toString(position+1);
	 AlertDialog alert = builder.create();
	    alert.show();
}
int MYCODE=1000;
public void	bookdelete(String table){
	
	try {
		myDbHelper.openDataBase();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	long imagesize=myDbHelper.counttable("images");
	for(long f=1;f<=imagesize;f++){
		Epubbooks imgr=myDbHelper.getimage((int) f, "images");
		if(imgr.getisbn().equals(tablename)){
			File file = new File(IM_PATH+imgr.getcreater());
			boolean deleted = file.delete();
		}//end if
	}//end first for
	datasource.open();
	datasource.deletetable(tablename);
//	Log.v("last position",String.valueOf(Position+1));
	updatetable(Position+1);
//	datasource.deleterow("epub", position);
	long status_size=myDbHelper.counttable("epub_status");
	Log.v("status_size",String.valueOf(status_size));
	for(long b=1;b<=status_size;b++){
		Matns state=myDbHelper.getmatn((int) b, "epub_status") ;
		if(state._matn.equals(table)){
			datasource.updateMatn(b,"download_ready","epub_status");
			break;
		}
	}
	myDbHelper.close();
	datasource.close();
	MainActivity.fa.finish();
	
	 Intent intentm = new Intent(context,MainActivity.class);
	    startActivity(intentm);
		
		
	}
public void updatetable(int pos){
	  long size=myDbHelper.counttable("epub");
	 Log.v("update position",String.valueOf(Position+1));
	  
	  for(long i=1;i<=size;i++){
		  Log.v("update i",String.valueOf(i));
		  
		if(i!=pos){
			Epubbooks bookd=myDbHelper.getimage((int) i,"epub"); 
			Log.v("update title",bookd.gettitle());
			Log.v("update creater",bookd.getcreater());
			Log.v("update isbn",bookd.getisbn());
			if(bookd!=null){
			bookq.add(bookd);
			Log.v("book","added");
			}
		}
	  }
	  datasource.deletetable("epub");
	  
	  datasource.createepubtable();
	  if(bookq!=null){
	  for(int i=0;i<bookq.size();i++){
		  Epubbooks a=bookq.get(i);
		  datasource.createepubbooks("epub",a.getisbn(),a.gettitle(),a.getcreater(),a.getcreater());
	  }
	  }
}
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    // Result OK.d.
    if (requestCode == MYCODE) {
        // do something good
    	if(myDbHelper.counttable("epub_online")==0){

            Fruit book = new Fruit("b","empty","directory","");
            bookArrayAdapter.add(book);
    	}else{
            List<String[]> bookList = readData();
            for(String[] bookData:bookList ) {
                String bookImg = bookData[0];
                String bookName = bookData[1];
                String bookauthor = bookData[2];
                
                Fruit book = new Fruit(bookImg,bookName,bookauthor,"");
                bookArrayAdapter.add(book);
            }
    	}
    }
}

}
