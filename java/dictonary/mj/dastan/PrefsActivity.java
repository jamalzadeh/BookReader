package dictonary.mj.dastan;

import dictonary.mj.dastan.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.widget.Toast;

public class PrefsActivity extends PreferenceActivity {
String table;
ActionBar actionBar;
int shd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 actionBar = getActionBar();
	        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F7D32")));
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		     shd= extras.getInt("shomare_dars");
		    
		}
		if (extras != null) {
		     table= extras.getString("table");
		    
		}
		addPreferencesFromResource(R.xml.prefs);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	MainActivity.fa.finish();
	    	Intent mIntent=new Intent(PrefsActivity.this,MainActivity3B.class);
	    	mIntent.putExtra("shomare_dars",shd);
       		mIntent.putExtra("table",table);
	    	String error = getResources().getString(R.string.setting_saved);
	    	Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    		startActivity(mIntent);
    		finish();
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
}
