package dictonary.mj.dastan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.Toast;



/**
 * A list fragment representing a list of Words. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link WordDetailFragment}.
 * <p>

 * interface.
 */
public class WordListFragment extends Fragment {
	private WordmAdapter wordArrayAdapter;
	WordmAdapter wordadapter;
	Context context;
	private ListView listView;
	 DataBaseHelper myDbHelper;
	 boolean type=false;
	 List<String[]> wordList;
	List<Word> wordmlist=new ArrayList<>();
	 long wordsize;
	 View rootView;
	private RecyclerView mRecyclerView;
	private RecyclerView.LayoutManager mLayoutManager;
	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */


	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */



	public WordListFragment() {
	}
	public static Fragment newInstance(Context context) {
		WordListFragment f = new WordListFragment();

		return f;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=getActivity();
		// TODO: replace with a real list adapter.
		/*setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, DummyContent.ITEMS));*/
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
		 rootView = inflater.inflate(R.layout.fragment_word_list, container, false);
		mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
		myDbHelper = new DataBaseHelper(context);

		try {
			myDbHelper.openDataBase();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(myDbHelper.counttable("names")==0){
    		 Toast.makeText(context, getResources().getString(R.string.empty_dictionary), Toast.LENGTH_SHORT).show();
    	}else{
    		if(!type){
    		  wordList = readData();
    		  Log.v("false","false");
    		}else{
    			Log.v("true","true");
    			wordList=readStarData();
    		}
	            myDbHelper.close();
	            for(String[] bookData:wordList ) {
	                String wordid = bookData[0];
	                String wordtext = bookData[1];
							Log.v("word",wordtext);
					String wordmean=bookData[2];
	                String mark=bookData[3];
	                boolean marked=false;
					if(mark.equals("1")){
						marked=true;
					}
	                Word word = new Word(Integer.valueOf(wordid),wordtext,wordmean,marked);
	                wordmlist.add(word);
	                }
			wordadapter=new WordmAdapter(wordmlist);
    	}
		myDbHelper.close();
		mRecyclerView.setHasFixedSize(true);

		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(mLayoutManager);

		// specify an adapter (see also next example)
		if(wordadapter!=null){
Log.v("adapter size",String.valueOf(wordadapter.getItemCount()));
		mRecyclerView.setAdapter(wordadapter);}
    	return rootView;
	}
	public int markedposition(int position){
		int result=1;
		int temp=0;
		try {
			myDbHelper.openDataBase();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=1;i<=wordsize;i++){
			if(myDbHelper.getContact(i,"names").getMarked()==1){
				temp++;
			}
			if(temp==position){
				result=i;
				break;
			}
		}
		return result;
	}
	public List<String[]> readData(){
		List<String[]> resultList = new ArrayList<String[]>();
	   
	     wordsize=myDbHelper.counttable("names");
	    Log.v("size",String.valueOf(wordsize));
	    for(int i=1;i<=wordsize;i++){
	    	String[] fruit = new String[4];
	    	names wordb=myDbHelper.getContact(i,"names");
	    	fruit[0]=String.valueOf(i);
	    	fruit[1]=wordb._1name;
	    	fruit[2]=wordb._2name;
			fruit[3]="0";
			if(wordb.getMarked()==1){
				fruit[3]="1";
			}
	    	
	    	resultList.add(fruit);
	    }
	    myDbHelper.close();
	    return  resultList;
	}
	public List<String[]> readStarData(){
		List<String[]> resultList = new ArrayList<String[]>();
	   
	    long wordsize=myDbHelper.counttable("names");
	    
	    for(int i=1;i<=wordsize;i++){
	    	
	    	names wordb=myDbHelper.getContact(i,"names");
	    	if(wordb.getMarked()==1){
	    	String[] fruit = new String[3];
	    	fruit[0]=String.valueOf(i);
	    	fruit[1]=wordb._1name;
	    	fruit[2]="";
	    	
	    	resultList.add(fruit);}
	    }
	    myDbHelper.close();
	    return  resultList;
	}
/*	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);


		if (activity instanceof OnItemSelectedListener) {
			listener = (OnItemSelectedListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implement ItemsListFragment.OnItemSelectedListener");
		}
	}*/

	/*@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}*/

/*	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
	}*/

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

public void setType(boolean _mark){
	this.type=_mark;
}
	/*private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}*/
}
