package dictonary.mj.dastan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A list fragment representing a list of Books. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link BookDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class BookListFragment extends Fragment {
	private BookArrayAdapter2 bookArrayAdapter;
	Context context;
	private ListView listView;
	 DataBaseHelper myDbHelper;
	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(String id);
	}
	private OnItemSelectedListener listener;

	public interface OnItemSelectedListener {
		public void onItemSelected(String i);
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public BookListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
context=getActivity();  

    	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		

		// Restore the previously serialized activated item position.
	/*	if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}*/
		View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);
		  listView = (ListView) rootView.findViewById(R.id.listView);
		myDbHelper = new DataBaseHelper(context);
		bookArrayAdapter = new BookArrayAdapter2(context, R.layout.listview_row_2_layout);
				// TODO: replace with a real list adapter.
		listView.setAdapter(bookArrayAdapter);
				
		    	try {
					myDbHelper.openDataBase();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	if(myDbHelper.counttable("epub_online")==0){

		            Fruit book = new Fruit("b",getResources().getString(R.string.noBooks),"","0");
		            bookArrayAdapter.add(book);
		    	}else{
		    		
		            List<String[]> bookList = readData();
		            myDbHelper.close();
		            for(String[] bookData:bookList ) {
		                String bookImg = bookData[0];
		                String bookauthor = bookData[1];
		                String bookName = bookData[2];
		                
		                Fruit book = new Fruit(bookImg,bookName,bookauthor,"");
		                bookArrayAdapter.add(book);
		                }
		            
		           
		    	}
		    	 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		    	      @Override
		    	      public void onItemClick(AdapterView<?> parent, final View view,
		    	          int position, long id) {
		    	    	  String i =String.valueOf(position+1);
		  				// Fire selected event for item
		    	    	  Log.v("first i",i);
		  				listener.onItemSelected(i);
		    	      }});
		    	return rootView;
	}
	public List<String[]> readData(){
		List<String[]> resultList = new ArrayList<String[]>();
	   
	    long imagesize=myDbHelper.counttable("images");
	    long tablesize=myDbHelper.counttable("epub_online");
	    for(long i=1;i<=tablesize;i++){
	    	String[] fruit = new String[3];
	    	Epubbooks epubb=myDbHelper.getimage((int) i , "epub_online");
	    	String tname=epubb.getisbn();
	    	for(long k=1;k<=imagesize;k++){
	    		Epubbooks img=myDbHelper.getimage((int) k , "images");
	    		if(img.getisbn().equals("cover")&&img.gettitle().equals(epubb.getisbn())){
	    			fruit[0]=img.getcreater();
	    			
	    			break;
	    		}
	    	}
	    	
	    	fruit[1] = epubb.gettitle();
	    	fruit[2] = "";
	    	resultList.add(fruit);
	    }
	    myDbHelper.close();
	    return  resultList;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnItemSelectedListener) {
			listener = (OnItemSelectedListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implement ItemsListFragment.OnItemSelectedListener");
		}
	
		// Activities containing this fragment must implement its callbacks.
		/*if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;*/
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}
/*
	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		 
	      
	     
	    
  		mCallbacks.onItemSelected(Integer.toString(position+1));
  	
	      }*/

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
	//	mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		listView.setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}
	
/*	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}*/
}
