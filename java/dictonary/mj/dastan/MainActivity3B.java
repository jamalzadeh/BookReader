package dictonary.mj.dastan;


import java.io.File;
import java.sql.SQLException;

import java.util.Locale;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;

import android.app.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.appcompat.*;

import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.app.AppCompatActivity;
public class MainActivity3B extends ActionBarActivity   implements
TextToSpeech.OnInitListener {
	public int width;
	public int height;
	public int shd;
	long pages;
	MediaPlayer player;
	public TextToSpeech tts;
	public String table;
	TextView pagetext;
	LinearLayout layout;
	public SeekBar seekbar;
	private static final String TAG_TASK_FRAGMENT = "tts_fragment";
	public static Activity fa;
	private static String IM_PATH = "/data/data/dictonary.mj.dastan/images/";
	public int scroll=0;
	DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    int ttsg;
    final DataBaseHelper myDbHelper = new DataBaseHelper(this);
    // Title navigation Spinner data
    
     
    // Navigation adapter
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity3_b);
		 final ActionBar actionBar = getSupportActionBar();


		actionBar.setDisplayShowTitleEnabled(false);

		
	 
	      
	      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F7D32")));
		 Display display = getWindowManager().getDefaultDisplay(); 
		 width = display.getWidth();  // deprecated
		 height = display.getHeight();  // deprecated
		Log.v("asli",Integer.toString(width));
		fa=this;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		     shd= extras.getInt("shomare_dars");
		    
		}
		ttsg=shd-1;
		if (extras != null) {
		     table= extras.getString("table");
		    
		}
		if (extras != null) {
			 scroll= extras.getInt("scroll");
		    
		}
		Log.v("activity shd",Integer.toString(shd));
		 mDemoCollectionPagerAdapter =
	                new DemoCollectionPagerAdapter(
	                        getSupportFragmentManager());
		 
	        mViewPager = (ViewPager) findViewById(R.id.pager);
	        seekbar=(SeekBar) findViewById(R.id.seekbar);
	       pagetext=(TextView) findViewById(R.id.pagetext);
	       layout=(LinearLayout) findViewById(R.id.layout);
	       hide();
	        Log.v("shd activity",Integer.toString(shd));
	       
	        
	       
	        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
	        mViewPager.setCurrentItem(shd-1);
	        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
	        	 public void onPageSelected(int newPosition) {
	        		 Log.v("ttsg",String.valueOf(ttsg));
	       changetext(newPosition+1);
	       seekbar.setProgress(newPosition);
					 stoptts();
					 ttsg=newPosition;
	        	    }
	        });
	        try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	 pages=myDbHelper.counttable(table);
    	 seekbar.setMax((int) pages-1);
    	 changetext(shd);
    	 myDbHelper.close();
	        tts = new TextToSpeech(getApplicationContext(), this);
	        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				int progressChanged = 0;

				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
					progressChanged = progress;
					Log.v("page",Integer.toString(progressChanged));
					changetext(progressChanged+1);
					
				}

				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}

				public void onStopTrackingTouch(SeekBar seekBar) {
					mViewPager.setCurrentItem(progressChanged);
				}
			});
	        
	}
	public void tts_speed(float d){
		tts.setSpeechRate(d);
		Log.v("speech rate",String.valueOf(d));
	}
	public void setpage(int page){
		seekbar.setProgress(page);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    Log.d(this.getClass().getName(), "back button pressed");
	    tts.shutdown();
	    MainActivity.fa.finish();
	    Intent intentm = new Intent(MainActivity3B.this,MainActivity.class);
	   finish();
	    startActivity(intentm);
	}
	return super.onKeyDown(keyCode, event);
	}
	public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
	    public DemoCollectionPagerAdapter(FragmentManager fm) {
	    	super(fm); 
	        
	    }

	    @Override
	    public Fragment getItem(int i) {
	     /*   Fragment fragment = new DemoObjectFragment();
	    	
	        Bundle args = new Bundle();
	        
	        args.putInt(DemoObjectFragment.ARG_OBJECT, i+1 );
	      
	        
	        Log.v("current item",Integer.toString(i));
	        fragment.setArguments(args);*/
	    	Log.v("current item",Integer.toString(i));
	    	Fragment fragmentItem = DemoObjectFragment.newInstance(Integer.toString(i));
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
	    	 long count=myDbHelper.counttable(table);
	    	 myDbHelper.close();
	        return (int) count;
	    }

	    @Override
	    public CharSequence getPageTitle(int position) {
	        return "OBJECT " + (position + 1);
	    }
	}
	public void changetext(int page){
pagetext.setText(Integer.toString(page)+"/"+Long.toString(pages));
	}
	public void hide(){
		if(layout.getVisibility()==View.VISIBLE){
			layout.setVisibility(View.INVISIBLE);
		}
	}
	public void show(){
		if(layout.getVisibility()==View.INVISIBLE){
			layout.setVisibility(View.VISIBLE);
		}
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		
		finish();
		Intent mIntent=new Intent(MainActivity3B.this,MainActivity3B.class);
		mIntent.putExtra("shomare_dars",shd);
		mIntent.putExtra("table",table);
		startActivity(mIntent);
		
	}
	public void speakOut(String text) {
		 
        
		/*HashMap<String, String> myHashRender = new HashMap();
		String textToConvert = text;
		String destinationFileName = Environment.getExternalStorageDirectory().getAbsolutePath()+"/" +"test.wav";
		myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, textToConvert);
		
		tts.synthesizeToFile(textToConvert, myHashRender, destinationFileName);
		File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "test.wav");
		long tool=file.length();
		Log.v("sound size",Long.toString(tool));*/
		//Uri myUri = Uri.parse(IM_PATH+"test.wav");
		//player = MediaPlayer.create(this,myUri);
		//player.start();
		  tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

	
	  public void stoptts(){
		 
		  if (tts != null) {
			  Log.v("not","null");
	            tts.stop();
	           
	            File file=new File(IM_PATH, "test.wav");
	            boolean deleted = file.delete();
	            if(deleted){
	            	Log.v("file","deleted");
	            }
	           // tts.shutdown();
	        }
	  }

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		 if (status == TextToSpeech.SUCCESS) {
			 
	            int result = tts.setLanguage(Locale.US);
	 
	            if (result == TextToSpeech.LANG_MISSING_DATA
	                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
	                Log.e("TTS", "This Language is not supported");
	            } else {
	               // btnSpeak.setEnabled(true);
	               // speakOut();
	            }
	 
	        } else {
	            Log.e("TTS", "Initilization Failed!");
	        }
		
	}
	public boolean check(){
		return tts.isSpeaking();
	}
	@Override
	protected void onDestroy() {

	    super.onDestroy();
	    tts.shutdown();
	}
	
	
 }
 //end program
