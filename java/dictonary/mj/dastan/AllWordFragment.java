package dictonary.mj.dastan;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class AllWordFragment extends Fragment {
	 private static final String TAG = "ListViewActivity";
	    private BookArrayAdapter bookArrayAdapter;
		private ListView listView;
		private static int colorIndex;
		 DataBaseHelper myDbHelper;
		Context context;
		String tablename;
		Insertword datasource;
		TextView textview;
		Typeface type;
		int Position;
		AlertDialog.Builder builder;
		ArrayList<Epubbooks> bookq=new ArrayList<Epubbooks>();
		 public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);
	        context=getActivity();
	        datasource=new Insertword(context);
	        type=Typeface.createFromFile(IM_PATH+"Nazanin.ttf");
	        listView = (ListView) rootView.findViewById(R.id.listView);
	        textview=(TextView) rootView.findViewById(R.id.textview);
	        return rootView;
	 }


}
