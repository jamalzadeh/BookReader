package dictonary.mj.dastan;

import java.io.File;
import java.sql.SQLException;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class DownloadActivity extends Activity implements DownloadFragment.TaskCallbacks{
	private static final String TAG_TASK_FRAGMENT = "download_fragment";
	String title,size,url,status,path;
	public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	private DownloadFragment mTaskFragment;
		  private ProgressBar mProgress;
		  Button install;
		  ImageView image;
		  DataBaseHelper myDbHelper;
		  OnClickListener stopL;
		  OnClickListener downloadL;
		  TextView textv;
		  Context context;
		  Drawable test;
		  Epubbooks epubb;
		  int id=0;
		  Typeface type;
		  int width,height;
		  private static String EPUB_PATH = "/data/data/dictonary.mj.dastan/epubs/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		  Bundle extras = getIntent().getExtras();
		
		   textv=(TextView) findViewById(R.id.textview);
		   type=Typeface.createFromFile(IM_PATH+"Nazanin.ttf");
		   textv.setTypeface(type);
		   image=(ImageView) findViewById(R.id.imageView1);
		    context=this;
	        mProgress = (ProgressBar) findViewById(R.id.progressBar1);
	        mProgress.setMax(100);
	        ActionBar actionBar = getActionBar(); 
	        actionBar.hide();
	       install=(Button) findViewById(R.id.install);
	       install.setEnabled(false);
	       
	       install.setVisibility(View.GONE);
	       myDbHelper = new DataBaseHelper(context);
	       try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	        if (extras != null) {
			     title= extras.getString("title");
			    size=extras.getString("size");
			    url=extras.getString("url");
			    path=extras.getString("path");
			}
	        try{
	        	id=extras.getInt("id");
	        }catch (Throwable h){
	        	id=0;
	        }
	        status=myDbHelper.getmatn(id,"epub_status")._matn;
	        myDbHelper.close();
			  if(status.equals("download_complete")){
				  install.setEnabled(true);
				  install.setVisibility(View.VISIBLE);
				  textv.setText( getResources().getString(R.string.download_complete));
			  }
			
	        Display display = getWindowManager().getDefaultDisplay(); 
	        width = display.getWidth();  // deprecated
	        height = display.getHeight();  // deprecated	
	    			File file = new File(IM_PATH+path);
	    			if(file.exists()){      
	    				
	    				test=Drawable.createFromPath(IM_PATH+path);
	    				if(test.getIntrinsicHeight()<(int)(0.4*height)){
	    					test=resize(test);
	    				}
	    		     image.setImageDrawable(test);
	    		     
	    		     Log.v("ba aks","ba aks");
	    			}
	    			
			

			FragmentManager fm = getFragmentManager();
		    mTaskFragment = (DownloadFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);
			    // If the Fragment is non-null, then it is currently being
			  
		    if (mTaskFragment == null) {
			      mTaskFragment = new DownloadFragment();
			      mTaskFragment.settitle(title);
			      mTaskFragment.setid(id);
			      mTaskFragment.setsize(size);
			      mTaskFragment.setURL(url);
			     
			      fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
			    }
		    install.setTypeface(type);
		    install.setOnClickListener(new OnClickListener(){
	        	public void onClick(View arg0){
	        		
	        		 PackageManager m = context.getPackageManager();
				        String s = context.getPackageName();
				        try {
				            PackageInfo p = m.getPackageInfo(s, 0);
				            s = p.applicationInfo.dataDir;
				        } catch (NameNotFoundException e) {
				            Log.w("yourtag", "Error Package name not found ", e);
				        }
				        Log.v("data direction",s);
	        	//	Intent mIntent=new Intent(context,Epubsecond.class);
	        		Intent mIntent=new Intent(context,JpubInstaller.class);
	        		mIntent.putExtra("path",s+"/epubs/"+title+".jpub" );
	        		mIntent.putExtra("id", id);
	        		finish();
	        		BookListActivity.fa.finish();
	        		startActivity(mIntent); 
	        	}});
		  
		  
		   
	}
	 private Drawable resize(Drawable imag) {
		    Bitmap b = ((BitmapDrawable)imag).getBitmap();
		    int iheight= imag.getIntrinsicHeight();
			int iwidth=imag.getIntrinsicWidth();
			int newheight=(int)(height*0.45);
			int newwidth=(int)(newheight*((double)((double)iwidth/(double)iheight)));
				
    	Log.v("newimage size","width="+newwidth+" height="+newheight);
		    Bitmap bitmapResized = Bitmap.createScaledBitmap(b, newwidth, newheight, false);
		    return new BitmapDrawable(getResources(), bitmapResized);
		}
	@Override
	public void onBackPressed() {
	    // Do Here what ever you want do on back press;
		try{
		mTaskFragment.cancell();
		mTaskFragment.stopdownload();
		}catch (Throwable e){}
		 
		 try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		status=myDbHelper.getmatn(id,"epub_status")._matn;
		myDbHelper.close();
		Log.v("statusafter",status);
			if(status.equals("download_complete")){
			BookListActivity.fa.finish();
			 Intent intentm = new Intent(context,BookListActivity.class);
			    startActivity(intentm);
			}else{
				 Toast.makeText(context, getResources().getString(R.string.download_cancelled), Toast.LENGTH_SHORT).show();
				
				
		    }
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.download, menu);
		return true;
	}

	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgressUpdate(int percent) {
		// TODO Auto-generated method stub
		mProgress.setProgress(percent);
		textv.setText(getResources().getString(R.string.downloading)+Integer.toString(percent)+"%");
	}

	@Override
	public void onCancelled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostExecute() {
		// TODO Auto-generated method stub
		 try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  status=myDbHelper.getmatn(id,"epub_status")._matn;
		  myDbHelper.close();
		  Log.v("status",status);
		 if(status.equals("download_ready")){
			 Toast.makeText(context, getResources().getString(R.string.download_failed), Toast.LENGTH_SHORT).show();
			 finish();
		 }
		 if(status.equals("download_complete")){
			 install.setEnabled(true);
			 install.setVisibility(View.VISIBLE);
			 mProgress.setProgress(100);
			 textv.setText( getResources().getString(R.string.download_complete));
			// Toast.makeText(context, getResources().getString(R.string.download_complete), Toast.LENGTH_SHORT).show();
		 }

		/*BookListActivity.fa.finish();
		finish();
		 Intent intentm = new Intent(context,BookListActivity.class);
		    startActivity(intentm);*/
		
	}

}
