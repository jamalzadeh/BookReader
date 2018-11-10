package dictonary.mj.dastan;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import android.support.v7.widget.*;
public class BookCards extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_cards);
		//CardView mCardView;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_cards, menu);
		return true;
	}

}
