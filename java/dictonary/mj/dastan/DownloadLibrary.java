package dictonary.mj.dastan;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadLibrary extends Activity implements DownloadLibraryFragment.TaskCallbacks {
String cpu;
String title="armeabi-v7a";
String link="http://www.jdastan.ir/files/armeabi-v7a/libplugpdf.zip";
private ProgressBar mProgress;
Button install;
TextView textv;
private DownloadLibraryFragment mTaskFragment;
private static final String TAG_TASK_FRAGMENT = "download_fragment";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_library);
		cpu=System.getProperty("os.arch");
		textv=(TextView) findViewById(R.id.textview);
		if(cpu.contains("armv5")){
			link="http://www.jdastan.ir/files/armeabi/libplugpdf.zip";
			title="armeabi";
		}
		if(cpu.contains("mips")){
			link="http://www.jdastan.ir/files/mips/libplugpdf.zip";
			title="mips";
		}
		if(cpu.contains("x86")){
			link="http://www.jdastan.ir/files/x86/libplugpdf.zip";
			title="x86";
		}
		 mProgress = (ProgressBar) findViewById(R.id.progressBar1);
	        mProgress.setMax(100);
	        ActionBar actionBar = getActionBar(); 
	        actionBar.hide();
	       install=(Button) findViewById(R.id.install);
	       install.setEnabled(false);
	       
	       install.setVisibility(View.GONE);
	       FragmentManager fm = getFragmentManager();
		    mTaskFragment = (DownloadLibraryFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);
		    if (mTaskFragment == null) {
			      mTaskFragment = new DownloadLibraryFragment();
			    
			      mTaskFragment.setURL(link);
			     mTaskFragment.settitle(title);
			      fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
			    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.download_library, menu);
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
		finish();
	}

}
