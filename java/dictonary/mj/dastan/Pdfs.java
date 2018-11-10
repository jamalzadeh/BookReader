package dictonary.mj.dastan;

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

public class Pdfs extends Fragment{
	 private static final String TAG = "ListViewActivity";
	    private PdfArrayAdapter pdfArrayAdapter;
		private ListView listView;
		
		 DataBaseHelper myDbHelper;
		Context context;
		String tablename;
		
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
		       
		        type=Typeface.createFromFile(IM_PATH+"Nazanin.ttf");
		        Book fg;
		        
		        listView = (ListView) rootView.findViewById(R.id.listView);
		        textview=(TextView) rootView.findViewById(R.id.textview);
		    	pdfArrayAdapter = new PdfArrayAdapter(context, R.layout.listview_row_pdf);
		    	myDbHelper = new DataBaseHelper(context);
		    	
		    	try {
					myDbHelper.openDataBase();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	listView.setAdapter(pdfArrayAdapter);
		    	if(myDbHelper.counttable("pdf")==0){

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
		                pdfArrayAdapter.add(book);
		                
		                }
		            
		           
		    	}
		    	myDbHelper.close();
		    	
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
		    	       if(pdfArrayAdapter.getItem(position).getid().equals("0")){
		    	    	   
		    	       }else{
		    	    	  
		    	    //   Intent mIntent=new Intent(context,BookPager.class);
		       	//	mIntent.putExtra("shomare_dars",page);
		       	//	mIntent.putExtra("table",tablename);
		    	   //  int ids= Integer.valueOf( bookArrayAdapter.getItem(position).getid());
		       		//mIntent.putExtra("position",position+1);
		       		//startActivity(mIntent); 
		       		
		       		
		       	 Epubbooks book=myDbHelper.getimage(position+1,"pdf");
		         tablename=book.getisbn();
		         Log.v("table name",tablename);
		         try{
		       	 page=Integer.valueOf(book.getTOC());
		          }catch (Throwable e){
		       	   
		          }
		         Intent mIntent;
		         if(page==1){
		   			 mIntent=new Intent(context,PdfReader.class);
		        		mIntent.putExtra("shomare_dars",1);
		        		mIntent.putExtra("path",tablename);
		        		mIntent.putExtra("number",position+1);
		        		startActivity(mIntent);    
		 	       }else{
		 	        mIntent=new Intent(context,PdfReader.class);
		    		mIntent.putExtra("shomare_dars",page);
		    		mIntent.putExtra("path",tablename);
		    		mIntent.putExtra("number",position+1);
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
			   
			  
			    long tablesize=myDbHelper.counttable("pdf");
			 //   Log.v("table size",String.valueOf(tablesize));
			    for(long i=1;i<=tablesize;i++){
			    try{	String[] fruit = new String[5];
			    fruit[3]=String.valueOf(i);
			  //  Log.v("fruit3",fruit[3]);
			    //	Log.v("i",String.valueOf(i));
			    	Epubbooks epubb=myDbHelper.getimage((int) i , "pdf");
			    	
			    	
			    	fruit[0]="";
			    	fruit[1] = epubb.gettitle();
			    	fruit[2] = epubb.getcreater();
			    	fruit[4]=epubb.getisbn();
			    	resultList.add(fruit);}catch (Throwable gh){}
			    }
			    return  resultList;
			}
}
