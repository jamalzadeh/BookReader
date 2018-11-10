package dictonary.mj.dastan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

/**
 * An activity representing a single Word detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link WordListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link WordDetailFragment}.
 */
public class WordDetailActivity extends FragmentActivity {
	public static final String ARG_ITEM_ID = "id";
	WordDetailFragment fragmentItemDetail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_detail);

		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		 Bundle extras = getIntent().getExtras();
		 String item=extras.getString(ARG_ITEM_ID);
		Log.v("item",item);
		 if (savedInstanceState == null) {
				fragmentItemDetail = WordDetailFragment.newInstance(item);
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.book_detail_container, fragmentItemDetail);
				ft.commit();
			}
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
					new Intent(this, WordListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
