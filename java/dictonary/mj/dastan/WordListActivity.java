package dictonary.mj.dastan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ActionBar.OnNavigationListener;
/**
 * An activity representing a list of Words. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link WordDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link WordListFragment} and the item details (if present) is a
 * {@link WordDetailFragment}.
 * <p>

 
 * 
 * 
 * 
 
 
 */
public class WordListActivity extends AppCompatActivity  {
	private Toolbar mToolbar;
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	Context context;
	private boolean mTwoPane;
boolean type=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_list);

		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);


context=this;
	    final String[] dropdownValues = getResources().getStringArray(R.array.dropdown);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.word_actions, menu);
		getMenuInflater().inflate(R.menu.menu_word, menu);
		return true;
	}
  	private void createDialog(){
  	  AlertDialog.Builder builder = new AlertDialog.Builder(context);

      builder.setTitle(getResources().getString(R.string.backup));
      builder.setMessage(getResources().getString(R.string.backup_do));

      builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

          public void onClick(DialogInterface dialog, int which) {
              // Do nothing but close the dialog
        	  PackageManager m = getPackageManager();
  	        String s = getPackageName();
  	        try {
  	            PackageInfo p = m.getPackageInfo(s, 0);
  	            s = p.applicationInfo.dataDir;
  	        } catch (NameNotFoundException e) {
  	            Log.w("yourtag", "Error Package name not found ", e);
  	        }
copyFile(s,"datab1",Environment.getExternalStorageDirectory().getAbsolutePath());
Toast.makeText(context, getResources().getString(R.string.backup_completed), Toast.LENGTH_SHORT).show();
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

      AlertDialog alert = builder.create();
      alert.show();
  }    
  	private void copyFile(String inputPath, String inputFile, String outputPath) {

  	    InputStream in = null;
  	    OutputStream out = null;
  	    try {

  	        //create output directory if it doesn't exist
  	        File dir = new File (outputPath); 
  	        if (!dir.exists())
  	        {
  	            dir.mkdirs();
  	        }


  	        in = new FileInputStream(inputPath + inputFile);        
  	        out = new FileOutputStream(outputPath + "Dastan-backup.db");

  	        byte[] buffer = new byte[1024];
  	        int read;
  	        while ((read = in.read(buffer)) != -1) {
  	            out.write(buffer, 0, read);
  	        }
  	        in.close();
  	        in = null;

  	            // write the output file (You have now copied the file)
  	            out.flush();
  	        out.close();
  	        out = null;        

  	    }  catch (FileNotFoundException fnfe1) {
  	        Log.e("tag", fnfe1.getMessage());
  	    }
  	            catch (Exception e) {
  	        Log.e("tag", e.getMessage());
  	    }

  	}

  	
	  @Override
		 public boolean onOptionsItemSelected(MenuItem item) {  
	       switch (item.getItemId()) {  
	      
	           case R.id.action_setting:
	        	 //  createDialog();
	        	   return true;
	           default:  
	               return super.onOptionsItemSelected(item);
	       }
	  }

	
}
