package dictonary.mj.dastan;

import java.io.File;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Showbook extends Activity {
ImageView image;
String tablename;
String name;
String author;
TextView textview;
String Title;
String Author;
AlertDialog.Builder builder;
Button show;
Button remove;
ActionBar actionBar;
int page;
final Insertword datasource = new Insertword(this);
public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
final DataBaseHelper myDbHelper = new DataBaseHelper(this);
long position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showbook);
		 actionBar = getActionBar();
	        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F7D32")));
		image=(ImageView)findViewById(R.id.imageView1);
		textview=(TextView)findViewById(R.id.textView1);
		 Title= getResources().getString(R.string.title);
		 Author= getResources().getString(R.string.Author);
		 show=(Button)findViewById(R.id.show);
		 remove=(Button)findViewById(R.id.remove);
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
		     tablename= extras.getString("table");
		    
		}
		if(extras !=null){
			page=extras.getInt("shomare_dars");
		}
		try{
		myDbHelper.openDataBase();
		long imagesize=myDbHelper.counttable("images");
		for(long k=1;k<=imagesize;k++){
    		Epubbooks img=myDbHelper.getimage((int) k , "images");
    		if(img.getisbn().equals("cover")&&img.gettitle().equals(tablename)){
    			File file = new File(IM_PATH+img.getcreater());
    			if(file.exists()){      
    				Drawable test;
    				test=Drawable.createFromPath(IM_PATH+img.getcreater());
    				 image.setImageDrawable(test);
    		    
    			}
    			break;
    		}
    	}
		}catch (Throwable e){
			
		}
		try{
			myDbHelper.openDataBase();
			long tablesize=myDbHelper.counttable("epub");
			for(long h=1;h<=tablesize;h++){
				Epubbooks epub=myDbHelper.getimage((int) h , "epub");
				if(epub.getisbn().equals(tablename)){
					name=epub.gettitle();
					author=epub.getcreater();
					position=h;
				}
			}
			 textview.setText(Title+name+"\n"+Author+author);
			    textview.setTextSize(getResources().getDimension(R.dimen.textsize));
			}catch (Throwable e){
				
		}
		 show.setOnClickListener(new OnClickListener(){
	        	public void onClick(View arg0){
	        		Log.v("page",String.valueOf(page));
	        		 if(page==0){
	      	    	   
	      	       }else{
	      	       Intent mIntent=new Intent(Showbook.this,MainActivity3B.class);
	         		mIntent.putExtra("shomare_dars",page);
	         		mIntent.putExtra("table",tablename);
	         		
	         		startActivity(mIntent); 
	      	      }
	        	}});
		 remove.setOnClickListener(new OnClickListener(){
	        	public void onClick(View arg0){
	        		 AlertDialog alert = builder.create();
	        		    alert.show();
	        	}});
		 
		 builder = new AlertDialog.Builder(this);

		    builder.setTitle(getResources().getString(R.string.bookdelete));
		    builder.setMessage(getResources().getString(R.string.sure));

		    builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

		        public void onClick(DialogInterface dialog, int which) {
		            // Do nothing but close the dialog
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
		
	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.showbook, menu);
		
		return true;
	}*/
public void	bookdelete(String table){
	try{
		myDbHelper.openDataBase();
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
		datasource.deleterow("epub", position);
		long status_size=myDbHelper.counttable("epub_status");
		for(long b=1;b<=status_size;b++){
			Matns state=myDbHelper.getmatn((int) b, "epub_status") ;
			if(state._matn.equals(table)){
				datasource.updateMatn(b,"download_ready","epub_status");
				break;
			}
		}
		MainActivity.fa.finish();
		finish();
		 Intent intentm = new Intent(Showbook.this,MainActivity.class);
		    startActivity(intentm);
			}catch (Throwable e){
		}
			
		}
	}


