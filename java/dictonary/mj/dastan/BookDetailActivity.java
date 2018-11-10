package dictonary.mj.dastan;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * An activity representing a single Book detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link BookListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link BookDetailFragment}.
 */
public class BookDetailActivity extends FragmentActivity {
	public static final String ARG_ITEM_ID = "item_id";
	String status="milad";
	public static Activity fa;
	Context context;
	Insertword datasource;
	 DataBaseHelper myDbHelper;
	 ImageView image;
	 Button download,remove;
	 URL url;
	 long position;
	 int page=0;
	 public int width,height;
	 Epubbooks epubb;
	 String imagepath;
	 Matns epub_status;
	 BookDetailFragment fragmentItemDetail;
	 public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	 String size;
	 Drawable test;
	 AlertDialog.Builder builder;
	 
	 int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_detail);

		// Show the Up button in the action bar.
		 ActionBar actionBar = getActionBar(); 
	        actionBar.hide();
		fa=this;
		Display display = getWindowManager().getDefaultDisplay(); 
		width = display.getWidth();  
		height = display.getHeight(); 
		
	/*	if (savedInstanceState == null) {
		
			Bundle arguments = new Bundle();
			arguments.putString(BookDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(BookDetailFragment.ARG_ITEM_ID));
			BookDetailFragment fragment = new BookDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.book_detail_container, fragment).commit();
		}*/
		 Bundle extras = getIntent().getExtras();
		 String item=extras.getString(ARG_ITEM_ID);
		 if (savedInstanceState == null) {
				fragmentItemDetail = BookDetailFragment.newInstance(item);
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.book_detail_container, fragmentItemDetail);
				ft.commit();
			}
		
	}
	 public void	bookdelete(String table){
			try{
				myDbHelper.openDataBase();
				long imagesize=myDbHelper.counttable("images");
				for(long f=1;f<=imagesize;f++){
					Epubbooks imgr=myDbHelper.getimage((int) f, "images");
					if(imgr.getisbn().equals(status)){
						File file = new File(IM_PATH+imgr.getcreater());
						boolean deleted = file.delete();
					}//end if
				}//end first for
				datasource.open();
				Log.v("tableD",table);
				datasource.deletetable(table);
				myDbHelper.openDataBase();
				long tablesize=myDbHelper.counttable("epub");
				for(long h=1;h<=tablesize;h++){
					Epubbooks epub=myDbHelper.getimage((int) h , "epub");
					if(epub.getisbn().equals(status)){
						
						position=h;
					}
				}
				datasource.deleterow("epub", position);
				
				datasource.updateMatn(id,"download_ready","epub_status");
				BookListActivity.fa.finish();
				MainActivity.fa.finish();
				Intent intentm2 = new Intent(context,MainActivity.class);
			    startActivity(intentm2);
				 Intent intentm = new Intent(context,BookListActivity.class);
				    startActivity(intentm);
					}catch (Throwable e){
				}
					myDbHelper.close();datasource.close();
				}
	/**
	 * Background Async Task to download file
	 * */
	 void scaleimage(){
		int iheight= test.getIntrinsicHeight();
		int iwidth=test.getIntrinsicWidth();
		Log.v("image size","width="+iwidth+" height="+iheight);
		
	 }
	 private Drawable resize(Drawable imag) {
		    Bitmap b = ((BitmapDrawable)imag).getBitmap();
		    int iheight= imag.getIntrinsicHeight();
			int iwidth=imag.getIntrinsicWidth();
			Display display = getWindowManager().getDefaultDisplay(); 
		int	width = display.getWidth();  // deprecated
		int	height = display.getHeight();  // deprecated
			
			int newwidth=(int)(0.195*width);
			int newheight=(int)(newwidth*((double)((double)iheight/(double)iwidth)));
			
			Log.v("newimage size","width="+newwidth+" height="+newheight);
		    Bitmap bitmapResized = Bitmap.createScaledBitmap(b, newwidth, newheight, false);
		    return new BitmapDrawable(getResources(), bitmapResized);
		}
		
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this,
					new Intent(this, BookListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
