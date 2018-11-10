package dictonary.mj.dastan;

import java.sql.SQLException;
import java.util.ArrayList;

import dictonary.mj.dastan.R;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListChaptersActivity extends ListActivity {
    public static final String CHAPTERS_EXTRA = "CHAPTERS_EXTRA";
    public static final String CHAPTER_EXTRA = "CHAPTER_EXTRA";
    ListView mListView;
    String table;
    final DataBaseHelper myDbHelper = new DataBaseHelper(this);
    ArrayList<TOCItem> mItems = new ArrayList<TOCItem>(); 
    private TableOfContent mToc;
    private LayoutInflater mInflater;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(getBaseContext());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
		     table= extras.getString("table");
		    
		}
        setTitle(getResources().getString(R.string.fehrest));
        
        mToc = new TableOfContent(getIntent(), CHAPTERS_EXTRA);
        getListView().setAdapter(new EpubChapterAdapter(this));
        
    }

    /*
     * Class implementing the "ViewHolder pattern", for better ListView
     * performance
     */
    private static class ViewHolder {
        public TextView textView; // refers to ListView item's TextView
        public NavPoint chapter;
    }

    /*
     * Populates entries on the list
     */
    private class EpubChapterAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public EpubChapterAdapter(Context context) {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder; // holds references to current item's GUI

            // if convertView is null, inflate GUI and create ViewHolder;
            // otherwise, get existing ViewHolder
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.epub_list_item, null);
                viewHolder = new ViewHolder();

                viewHolder.textView = (TextView) convertView.findViewById(R.id.epub_title);
                convertView.setTag(viewHolder); // store as View's tag
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // Populate the list item (view) with the chapters details
            viewHolder.chapter = (NavPoint)getItem(position);
            String title = viewHolder.chapter.getNavLabel();
            viewHolder.textView.setText(title);

            return convertView;
        }

        @Override
        public int getCount() {
            return mToc.size();
        }

        @Override
        public Object getItem(int position) {
            return mToc.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // return URI of selected chapter
     //   Intent intent = new Intent();
        Uri uri = ((ViewHolder)v.getTag()).chapter.getContentWithoutTag(); 
        Log.v("file",uri.toString());
        String content=((ViewHolder)v.getTag()).chapter.getcontent();
        Log.v("content",content);
        try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        Long te=myDbHelper.counttable(table);
        Log.v("tablesize",Long.toString(te));
        for(long j=1;j<=te;j++){
        
        	if(myDbHelper.getBooks((int) j, table)._2name.equals(uri.toString())){
        		Log.v("firstif","firstif");
        		if(content.equals("")){
        			Intent mIntent=new Intent(ListChaptersActivity.this,MainActivity3B.class);
	        		mIntent.putExtra("shomare_dars",(int) j);
	        		mIntent.putExtra("table",table);
	        		mIntent.putExtra("scroll",0);
	        		
	        		startActivity(mIntent);
	        		startActivity(mIntent);
	        		break;
        		}
        		int indexha=myDbHelper.getBooks((int) j, table)._1name.indexOf("¦"+content+"¦");
        		Log.v("index",Integer.toString(indexha));
        		if(indexha!=-1){
        			Intent mIntent=new Intent(ListChaptersActivity.this,MainActivity3B.class);
	        		mIntent.putExtra("shomare_dars",(int) j);
	        		mIntent.putExtra("table",table);
	        		mIntent.putExtra("scroll",indexha);
	        		startActivity(mIntent);
        		}
        	}
        }
     //   intent.putExtra(CHAPTER_EXTRA, uri);
      //  setResult(RESULT_OK, intent);
       finish();
    } 
    private void refreshList()
	{
		mListView.setAdapter(new ListAdapter() {
			public boolean areAllItemsEnabled() {
				return true;
			}

			public boolean isEnabled(int arg0) {
				return true;
			}

			public int getCount() {
				return mItems.size();
			}

			public Object getItem(int position) {
				return mItems.get(position);
			}

			public long getItemId(int position) {
				return position;
			}

			public int getItemViewType(int position) {
				return 0;
			}

			
			public View getView(int position, View convertView, ViewGroup parent) {
				View view;
				if ( convertView==null ) {
					//view = new TextView(getContext());
					view = mInflater.inflate(R.layout.toc_item, null);
				} else {
					view = (View)convertView;
				}
				TextView pageTextView = (TextView)view.findViewById(R.id.toc_page);
				TextView titleTextView = (TextView)view.findViewById(R.id.toc_title);
				TextView marginTextView = (TextView)view.findViewById(R.id.toc_level_margin);
				ImageView expandImageView = (ImageView)view.findViewById(R.id.toc_expand_icon);
				TOCItem item = mItems.get(position);
				StringBuilder buf = new StringBuilder(item.getLevel()*2);
				for ( int i=1; i<item.getLevel(); i++ )
					buf.append("  ");
				if ( item.getChildCount()>0 ) {
					if ( item.getExpanded() ) {
						expandImageView.setImageResource(R.drawable.cr3_toc_item_expanded);
					} else {
						expandImageView.setImageResource(R.drawable.cr3_toc_item_collapsed);
					}
				} else {
					expandImageView.setImageResource(R.drawable.cr3_toc_item_normal);
				}
				marginTextView.setText(buf.toString());
				titleTextView.setText(item.getName());
				pageTextView.setText(String.valueOf(item.getPage()+1));
				return view;
			}

			public int getViewTypeCount() {
				return 1;
			}

			public boolean hasStableIds() {
				return true;
			}

			public boolean isEmpty() {
				return false;
			}

			private ArrayList<DataSetObserver> observers = new ArrayList<DataSetObserver>();
			
			public void registerDataSetObserver(DataSetObserver observer) {
				observers.add(observer);
			}

			public void unregisterDataSetObserver(DataSetObserver observer) {
				observers.remove(observer);
			}
		});
	}
}
