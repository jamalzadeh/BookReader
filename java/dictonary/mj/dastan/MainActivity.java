package dictonary.mj.dastan;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dictonary.mj.dastan.FirstRun;
import dictonary.mj.dastan.R;
import android.app.ActionBar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {




	TextView textview;
	    String firstpage;


		private final int REQUEST_CODE_PICK_FILE = 2;
	    private static String IM_PATH = "/data/data/dictonary.mj.dastan/images/";
	    Typeface type;
	    private ActionBar actionBar;
	    public  String DB_PATH = "/data/data/dictonary.mj.dastan/databases/";
	    // Tab titles
	    public static Activity fa;

	    private DrawerLayout mDrawer;


	 List<Fruit> booklist=new ArrayList<>();
	    // nav drawer title

	Insertword datasource=new Insertword(this);;
	    private ArrayList<NavDrawerItem> navDrawerItems;

	    static SharedPreferences sharedPreferences;
	   // ScrollView mScrollView;
	final DataBaseHelper myDbHelper = new DataBaseHelper(this);

	private Toolbar mToolbar;
	private FragmentDrawer drawerFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mToolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		final android.support.v7.app.ActionBar ab = getSupportActionBar();
		//ab.setHomeAsUpIndicator(R.drawable.ic_menu);
		ab.setDisplayHomeAsUpEnabled(true);

		NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
		// Setup drawer view
		setupDrawerContent(nvDrawer);
        // load slide menu items

        fa=this;

		setupDrawerContent(nvDrawer);





        // Adding Tabs


		 try {
				myDbHelper.createDataBase();
				Log.v("created","created");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.v("not","created");
				e.printStackTrace();
			}


	        try {
				myDbHelper.createfarsDataBase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        PackageManager m = getPackageManager();
	        String s = getPackageName();
	        try {
	            PackageInfo p = m.getPackageInfo(s, 0);
	            s = p.applicationInfo.dataDir;
	        } catch (NameNotFoundException e) {
	            Log.w("yourtag", "Error Package name not found ", e);
	        }
	        File directory = new File(s + "/images");
	        directory.mkdirs();
	        File edirectory = new File(s + "/epubs");
	        edirectory.mkdirs();
	        sharedPreferences = getSharedPreferences("firstRunPreference", 0);
	        if (FirstRun.isFirstRun() == true) {


	        	  copyAssetFolder(getAssets(), "images",
		                    IM_PATH);
	           //calling this method changes the boolean value to false.
	           //on new launch of the activity this if block is not interpreted.
	            FirstRun.appRunned();


	        }
		Fragment fragment = null;
		Class fragmentClass=null;
	        type=Typeface.createFromFile(IM_PATH+"Nazanin.ttf");
		fragmentClass = InstalledFragment.class;
		try {
			fragment = (Fragment) fragmentClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();




		// Highlight the selected item, update the title, and close the drawer


	}//end oncreate
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  super.onSaveInstanceState(savedInstanceState);
	  // Save UI state changes to the savedInstanceState.
	  // This bundle will be passed to onCreate if the process is
	  // killed and restarted.
	Browser br=new Browser();
	  savedInstanceState.putString("MyString", br.getpath());
	  // etc.
	}
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	  super.onRestoreInstanceState(savedInstanceState);
	  // Restore UI state from the savedInstanceState.
	  // This bundle has also been passed to onCreate.
	 
	  String myString = savedInstanceState.getString("MyString");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private void setupDrawerContent(NavigationView navigationView) {
		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(MenuItem menuItem) {
						selectDrawerItem(menuItem);
						return true;
					}
				});
	}

	public void selectDrawerItem(MenuItem menuItem) {
		// Create a new fragment and specify the planet to show based on
		// position

		// Create a new fragment and specify the planet to show based on
		// position
		Fragment fragment = null;

		Class fragmentClass=null;

		switch(menuItem.getItemId()) {
			case R.id.nav_first_fragment:
				//Intent mIntent=new Intent(this,BookListActivity.class);
				fragmentClass = InstalledFragment.class;

				//startActivity(mIntent);
				break;
			case R.id.nav_second_fragment:
				//Intent cIntent=new Intent(this,WordListActivity.class);
				//Intent cIntent=new Intent(this,WordListMaterial.class);
				fragmentClass = WordListFragment.class;
				//startActivity(cIntent);
				break;
			case R.id.nav_third_fragment:
				//  Intent bIntent=new Intent(this,Backup.class);
				fragmentClass = InstalledFragment.class;
				Log.d("log", "StartFileBrowser4File button pressed");
				try {
					myDbHelper.openDataBase();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				firstpage=myDbHelper.getmatn(1,"config").getMatn();
				Log.v("first page",firstpage);
				Intent fileExploreIntent = new Intent(
						FileBrowserActivity.INTENT_ACTION_SELECT_FILE,
						null,
						this,
						FileBrowserActivity.class
				);
				fileExploreIntent.putExtra(
						FileBrowserActivity.showCannotReadParameter,
						false);
				if(firstpage.equals("1")){

				}else{
					Log.v("else","else");
					fileExploreIntent.putExtra(
							FileBrowserActivity.startDirectoryParameter,
							firstpage);
				}
				startActivityForResult(
						fileExploreIntent,
						REQUEST_CODE_PICK_FILE
				);
				//mDrawerLayout.closeDrawers();
				break;
			case R.id.nav_forth_fragment:
				fragmentClass = InstalledFragment.class;
				Intent bIntent=new Intent(this,Backup.class);
				startActivity(bIntent);
				break;
			case R.id.nav_fifth_fragment:
update();
				break;
			default:

		}
		try {
			fragment = (Fragment) fragmentClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try{FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();}catch (Throwable e){}
		// Insert the fragment by replacing any existing fragment





		// Highlight the selected item, update the title, and close the drawer
		menuItem.setChecked(true);
		setTitle(menuItem.getTitle());
		mDrawer.closeDrawers();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		switch (item.getItemId()) {
			case android.R.id.home:
				mDrawer.openDrawer(GravityCompat.START);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
	@Override
	public void onDrawerItemSelected(View view, int position) {

	}

	/***
	     * Called when invalidateOptionsMenu() is triggered
	     */
	  private class SlideMenuClickListener implements
      ListView.OnItemClickListener {
  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position,
          long id) {
      // display view for selected nav drawer item
      displayView(position);
  }
}
	  private void displayView(int pos){
		  switch(pos){
		  case 0:
			  Intent mIntent=new Intent(this,BookListActivity.class);
      		
			 // mDrawerLayout.closeDrawers();
      		startActivity(mIntent); 
			  break;
		  case 2:
			//  Intent bIntent=new Intent(this,Backup.class);
				Log.d("log", "StartFileBrowser4File button pressed");
				 try {
						myDbHelper.openDataBase();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				firstpage=myDbHelper.getmatn(1,"config").getMatn();
				Log.v("first page",firstpage);
    			Intent fileExploreIntent = new Intent(
    				FileBrowserActivity.INTENT_ACTION_SELECT_FILE,
        				null,
        				this,
        				FileBrowserActivity.class
        				);
    			fileExploreIntent.putExtra(
						FileBrowserActivity.showCannotReadParameter,
						false);
    			if(firstpage.equals("1")){
    				
    			}else{
    				Log.v("else","else");
    				fileExploreIntent.putExtra(
        					FileBrowserActivity.startDirectoryParameter, 
        					firstpage);
    			}
        		startActivityForResult(
						fileExploreIntent,
						REQUEST_CODE_PICK_FILE
				);
        		//mDrawerLayout.closeDrawers();
				  break;
		  case 1:
			  Intent cIntent=new Intent(this,WordListActivity.class);
			//  mDrawerLayout.closeDrawers();
			  startActivity(cIntent);
				break;  
		  		
		  }
	  }

	 


		
	private static boolean copyAssetFolder(AssetManager assetManager,
	        String fromAssetPath, String toPath) {
	    try {
	        String[] files = assetManager.list(fromAssetPath);
	        new File(toPath).mkdirs();
	        boolean res = true;
	        for (String file : files)
	          //  if (file.contains("."))
	                res &= copyAsset(assetManager, 
	                        fromAssetPath + "/" + file,
	                        toPath + "/" + file);
	          /*  else 
	                res &= copyAssetFolder(assetManager, 
	                        fromAssetPath + "/" + file,
	                        toPath + "/" + file);*/
	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	private static boolean copyAsset(AssetManager assetManager,
	        String fromAssetPath, String toPath) {
	    InputStream in = null;
	    OutputStream out = null;
	    try {
	      in = assetManager.open(fromAssetPath);
	      new File(toPath).createNewFile();
	      out = new FileOutputStream(toPath);
	      copyFile(in, out);
	      in.close();
	      in = null;
	      out.flush();
	      out.close();
	      out = null;
	      return true;
	    } catch(Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	private static void copyFile(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    int read;
	    while((read = in.read(buffer)) != -1){
	      out.write(buffer, 0, read);
	    }
	}
	private void copyFile(String filename) {
	    AssetManager assetManager = this.getAssets();

	    InputStream in = null;
	    OutputStream out = null;
	    try {
	        in = assetManager.open(filename);
	        String newFileName = "/data/data/" + this.getPackageName() + "/" + filename;
	        out = new FileOutputStream(newFileName);

	        byte[] buffer = new byte[1024];
	        int read;
	        while ((read = in.read(buffer)) != -1) {
	            out.write(buffer, 0, read);
	        }
	        in.close();
	        in = null;
	        out.flush();
	        out.close();
	        out = null;
	    } catch (Exception e) {
	        Log.e("tag", e.getMessage());
	    }

	}
	 List<String[]> readData(){
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
						//fruit[0]=String.valueOf((Integer.valueOf(img.getcreater())+20));
Log.v("this IMG",img.getcreater()+" "+img.getisbn()+" "+img.gettitle()+" ");
						Log.v("this EPUBB",epubb.getcreater()+" "+epubb.getisbn()+" "+epubb.gettitle()+" ");
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
	public void update(){
		File file = new File(new File("/data/data/dictonary.mj.dastan/databases/"),"datab1");
		if (file.exists())
			Log.v("file ","exists");
			file.delete();
		try {
			copyFile(Environment.getExternalStorageDirectory() + "/jpub2/","datab1","/data/data/dictonary.mj.dastan/databases/");

		} catch (Exception e) {
			e.printStackTrace();
		}
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
			out = new FileOutputStream(outputPath + inputFile);

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


}
