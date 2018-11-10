package dictonary.mj.dastan;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;

public class Backup extends Activity implements BackupTask.TaskCallbacks {
	private BackupTask mTaskFragment;
	  private ProgressBar mProgress;
	  private static final String TAG_TASK_FRAGMENT = "task_fragment";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_epubsecond);
		 mProgress = (ProgressBar) findViewById(R.id.progressBar1);
	        mProgress.setMax(100);
	        FragmentManager fm = getFragmentManager();
		    mTaskFragment = (BackupTask) fm.findFragmentByTag(TAG_TASK_FRAGMENT);
	        if (mTaskFragment == null) {
	  	      mTaskFragment = new BackupTask();
	  	     
	  	      fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
	  	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.backup, menu);
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
		// TODO Auto-generated method stub
		
	}

}
