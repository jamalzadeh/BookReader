package dictonary.mj.dastan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.SQLException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class Lookwords extends Activity {
	Context context=this;
	String customHtml;
	final DataBaseHelper myDbHelper = new DataBaseHelper(this);
	public static String AUTHORITY = "livio.pack.lang.en_US.DictionaryProvider"; 
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/dictionary"); 
	public static final Uri CONTENT_URI3 = Uri.parse("content://" + AUTHORITY + "/"+ SearchManager.SUGGEST_URI_PATH_QUERY);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lookwords);
		  try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		  long numbers=myDbHelper.counttable("words");
		  Log.v("table number",String.valueOf(numbers));
		  for(long i=1;i<=numbers;i++){
			String word=  myDbHelper.getmatn((int)i,"words")._matn;
			lookdict(word);
		  }
		  
		  
		  String uri = Environment.getExternalStorageDirectory()+"/html/";
          try {
              File myFile = new File(uri+"words.html");
              myFile.createNewFile();
               }catch (UnsupportedEncodingException e) 
   	   {
      		System.out.println(e.getMessage());
      	   }  
          catch (Exception e) {
              Toast.makeText(getBaseContext(), e.getMessage(),
                      Toast.LENGTH_SHORT).show();
          }
		
		  try {
				File fileDir = new File(uri+"word.txt");
		 
				Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileDir), "UTF8"));
		 
				out.append(customHtml);
				
		 
				out.flush();
				out.close();
		 
			    } 
			   catch (UnsupportedEncodingException e) 
			   {
				System.out.println(e.getMessage());
			   } 
			   catch (IOException e) 
			   {
				System.out.println(e.getMessage());
			    }
			   catch (Exception e)
			   {
				System.out.println(e.getMessage());
			   } 
		  Toast.makeText(context, "finished", Toast.LENGTH_SHORT).show();
	}
	 void lookdict(String word){
 		 if(word.equals("")){
 			 Log.v("lookdict","none");
 		 }else{
 			 Log.v("lookdict",word);
 		 }
 		Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, new String[] {word}, null); 
        if ((cursor != null) && cursor.moveToFirst()) { 
            int wIndex = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1); 
            int dIndex = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_2); 
           // Log.d("demo", "found word: "+ cursor.getString(wIndex)+", definition: "+ cursor.getString(dIndex));
             customHtml=customHtml+"<h2>"+cursor.getString(wIndex)+":"+"</h2>"+"<br>"+ cursor.getString(dIndex)+"<hr>";
          
           
            Log.v("if","called");
        } else { 
        	 Log.v("else","called");
        	
        		Toast.makeText(context, "word not found", Toast.LENGTH_SHORT).show();
        	Log.v("not found:",word);
        }}//end lookdict
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lookwords, menu);
		return true;
	}

}
