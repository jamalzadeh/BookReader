package dictonary.mj.dastan;

import java.io.FileInputStream;
import java.io.InputStream;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Epubsecond extends Activity implements TaskFragment.TaskCallbacks{
	private static final String TAG_TASK_FRAGMENT = "task_fragment";
String path;
public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	  private TaskFragment mTaskFragment;
	  private ProgressBar mProgress;
	  int width,height;
	  Typeface type;
	        ImageView image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_epubsecond);
		
	        Bundle extras = getIntent().getExtras();
	   
        mProgress = (ProgressBar) findViewById(R.id.progressBar1);
        mProgress.setMax(100);
        image=(ImageView) findViewById(R.id.imageView1);
       // ActionBar actionBar = getActionBar();
       // actionBar.hide();
       int id=0;
       type=Typeface.createFromFile(IM_PATH+"Nazanin.ttf");
       TextView textv=(TextView) findViewById(R.id.textview);
       TextView text2=(TextView) findViewById(R.id.textview2);
       text2.setTypeface(type);
       textv.setTypeface(type);
        if (extras != null) {
		     path= extras.getString("path");
		    
		}
        try{
        	id=extras.getInt("id");
        }catch (Throwable h){
        	id=0;
        }
        Display display = getWindowManager().getDefaultDisplay(); 
        width = display.getWidth();  // deprecated
        height = display.getHeight();  // deprecated	
        try{
        InputStream is = new FileInputStream(path);
	   	 Book book = (new EpubReader()).readEpub(is);
	   	Drawable test;
	    test = Drawable.createFromStream(book.getCoverImage().getInputStream(),"test");
	    if(test.getIntrinsicHeight()<(int)(0.4*height)){
			test=resize(test);
		}
	    image.setImageDrawable(test);
        }catch (Throwable e){
        	Drawable test;
        	String uri = "@drawable/epub2";

        	int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        	test=getResources().getDrawable(imageResource);
        	 if(test.getIntrinsicHeight()<(int)(0.4*height)){
     			test=resize(test);
     		}
     	    image.setImageDrawable(test);
        };
		FragmentManager fm = getFragmentManager();
	    mTaskFragment = (TaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
	    if (mTaskFragment == null) {
	      mTaskFragment = new TaskFragment();
	      Log.v("path",path);
	      mTaskFragment.setpath(path);
	      mTaskFragment.setId(id);
	      fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
	    }
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
		Toast.makeText(Epubsecond.this, getResources().getString(R.string.no_back), Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.epubsecond, menu);
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
		
	}

	@Override
	public void onCancelled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostExecute() {
		finish();
  		
	}
	

}
