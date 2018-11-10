package dictonary.mj.dastan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
 
public class GamesFragment extends Fragment {
	DataBaseHelper myDbHelper;
	Context context;
	private ListView listView;
	private BookArrayAdapter bookArrayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_games, container, false);
        context=getActivity();
        myDbHelper = new DataBaseHelper(context);
        listView = (ListView) rootView.findViewById(R.id.listView);
    	bookArrayAdapter = new BookArrayAdapter(context, R.layout.listview_row_layout);
    	myDbHelper = new DataBaseHelper(context);
    	try {
			myDbHelper.createDataBase();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			
		}
    	try {
			myDbHelper.openDataBase();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	listView.setAdapter(bookArrayAdapter);
    	if(myDbHelper.counttable("epub")==0){

            Fruit book = new Fruit("b",getResources().getString(R.string.noBooks),"","");
            bookArrayAdapter.add(book);
    	}else{
            List<String[]> bookList = readData();
            for(String[] bookData:bookList ) {
                String bookImg = bookData[0];
                String bookauthor = bookData[1];
                String bookName = bookData[2];
                
                Fruit book = new Fruit(bookImg,bookName,bookauthor,"");
                bookArrayAdapter.add(book);
                }
            
           
    	}
    	 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    	      @Override
    	      public void onItemClick(AdapterView<?> parent, final View view,
    	          int position, long id) {
    	       Log.v("position",Integer.toString(position));
    	       String tablename="table_";
    	      tablename="table_" +Integer.toString(position+1);
    	       int page=0;
    	       try{
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
    	    	   
    	       }
    	       Log.v("page",Integer.toString(page));
    	       Log.v("table",tablename);
    	       if(page==0){
    	    	   
    	       }else{
    	    	  
    	       Intent mIntent=new Intent(context,Showbook.class);
       		mIntent.putExtra("shomare_dars",page);
       		mIntent.putExtra("table",tablename);
       		
       		startActivity(mIntent); 
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
        long tablesize=myDbHelper.counttable("epub_online");
        for(long i=1;i<=tablesize;i++){
        	String[] fruit = new String[3];
        	Epubbooks epubb=myDbHelper.getimage((int) i , "epub_online");
        	String tname=epubb.getisbn();
        	for(long k=1;k<=imagesize;k++){
        		Epubbooks img=myDbHelper.getimage((int) k , "images");
        		if(img.getisbn().equals("cover")&&img.gettitle().equals(epubb.getisbn())){
        			fruit[0]=img.getcreater();
        			
        			break;
        		}
        	}
        	
        	fruit[1] = epubb.gettitle();
        	fruit[2] = epubb.getcreater();
        	resultList.add(fruit);
        }
        return  resultList;
    }
    int MYCODE=1000;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result OK.d.
        if (requestCode == MYCODE) {
            // do something good
        	if(myDbHelper.counttable("epub")==0){

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