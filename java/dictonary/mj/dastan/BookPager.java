package dictonary.mj.dastan;

import java.sql.SQLException;

import dictonary.mj.dastan.MainActivity3B.DemoCollectionPagerAdapter;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Menu;

public class BookPager extends FragmentActivity {
	String tablename;
	public int width,height;
	int page;
	 ViewPager mViewPager;
	DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
	 final DataBaseHelper myDbHelper = new DataBaseHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_pager);
		 final ActionBar actionBar = getActionBar();
		 actionBar.hide();
		 //actionBar.setDisplayShowTitleEnabled(false);
		 Display display = getWindowManager().getDefaultDisplay(); 
			width = display.getWidth();  
			height = display.getHeight(); 
	       // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F7D32")));
	        Bundle extras = getIntent().getExtras();
	        
			if(extras !=null){
				page=extras.getInt("position");
			}
			 mDemoCollectionPagerAdapter =
		                new DemoCollectionPagerAdapter(
		                        getSupportFragmentManager());
			 
		        mViewPager = (ViewPager) findViewById(R.id.pager);
		        Log.v("shd activity",Integer.toString(page));
		       
		        
		       
		        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
		        mViewPager.setCurrentItem(page-1);
	}

	
	public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
	    public DemoCollectionPagerAdapter(FragmentManager fm) {
	    	super(fm); 
	        
	    }

	    @Override
	    public Fragment getItem(int i) {
	     /*   Fragment fragment = new BookFragment();
	    	
	        Bundle args = new Bundle();
	        
	        args.putInt(BookFragment.ARG_OBJECT, i+1 );
	      
	        
	        Log.v("current item",Integer.toString(i));
	        fragment.setArguments(args);*/
	    	Log.v("current item",Integer.toString(i));
	    	Fragment fragmentItem = new BookFragment();
	    	Bundle args = new Bundle();
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			args.putInt(DemoObjectFragment.ARG_OBJECT, i+1 );
			fragmentItem.setArguments(args);
			
			ft.commit();
			getSupportFragmentManager().executePendingTransactions();
	        return fragmentItem;
	    }

	    @Override
	    public int getCount() {
	    	 try {
					myDbHelper.openDataBase();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	 long count=myDbHelper.counttable("epub");
	    	
	    	 myDbHelper.close();
	        return (int) count;
	    }

	    @Override
	    public CharSequence getPageTitle(int position) {
	        return "OBJECT " + (position + 1);
	    }
	}

}
