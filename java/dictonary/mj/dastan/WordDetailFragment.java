package dictonary.mj.dastan;

import java.sql.SQLException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import dictonary.mj.dastan.dummy.DummyContent;

/**
 * A fragment representing a single Word detail screen. This fragment is either
 * contained in a {@link WordListActivity} in two-pane mode (on tablets) or a
 * {@link WordDetailActivity} on handsets.
 */
public class WordDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	TextView titleText;
	public static String AUTHORITY = "livio.pack.lang.en_US.DictionaryProvider";
	  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/dictionary");
	  public static final Uri CONTENT_URI3 = Uri.parse("content://" + AUTHORITY + "/" + "search_suggest_query");
	protected Dialog mDialog;
	public static final String ARG_ITEM_ID = "item_id";
String mean;
EditText contentsText;
String word;
ImageButton star;
ImageButton edit;
int mark;
TextView maintitle;
TextView mainmean;
	/**
	 * The dummy content this fragment is presenting.
	 */
	Context context;
	public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	Typeface type;
	Insertword datasource;
	 DataBaseHelper myDbHelper;
	private DummyContent.DummyItem mItem;
	int id;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public WordDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("detail fragment",getArguments().getString("item"));
		id = Integer.valueOf( getArguments().getString("item"))+1;
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_word_detail,
				container, false);
		context=getActivity();  
		type=Typeface.createFromFile(IM_PATH+"Nazanin.ttf");
		myDbHelper = new DataBaseHelper(context);
		 datasource=new Insertword(context);
		 try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 word=myDbHelper.getContact(id,"names")._1name;
		 mean=myDbHelper.getContact(id,"names")._2name;
		 mark=myDbHelper.getContact(id,"names").getMarked();
		 star=(ImageButton) rootView.findViewById(R.id.star);
		 edit=(ImageButton) rootView.findViewById(R.id.edit);
		 edit.setOnClickListener(new View.OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	createDialog(context);
		        }});
		 if(mark==1){
			 star.setImageResource(R.drawable.starpressed);		 }
		 star.setOnClickListener(new View.OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	Log.v("mark value",String.valueOf(mark));
		           if(mark==0){
		        	   mark=1;
		        	   Log.v("inner mark",String.valueOf(mark));
		        	   star.setImageResource(R.drawable.starpressed);
		        	   datasource.open();
		        	   
		        	   datasource.updatemark(id,mark);
		        	   datasource.close();
		        	    }
		           else{
	star.setImageResource(R.drawable.normalstar);
	mark=0;
	 datasource.open();
	   
	   datasource.updatemark(id,mark);
	   datasource.close();
}
		        }
		    });
		 // Show the dummy content as text in a TextView.
		 maintitle=(TextView)rootView.findViewById(R.id.word);
		 mainmean=(TextView)rootView.findViewById(R.id.content);
		 ((TextView) rootView.findViewById(R.id.word))
			.setText(word);
			((TextView) rootView.findViewById(R.id.content))
					.setText(mean);
			type=Typeface.createFromFile(IM_PATH+"Mirta.ttf");
			 ((TextView) rootView.findViewById(R.id.word)).setTypeface(type);
			 ((TextView) rootView.findViewById(R.id.content)).setTypeface(type);
		return rootView;
	}
	 public static WordDetailFragment newInstance(String item) {
	    	WordDetailFragment fragmentDemo = new WordDetailFragment();
	        Bundle args = new Bundle();
	        args.putString("item", item);
	        
	        fragmentDemo.setArguments(args);
	        return fragmentDemo;
	    }
	 protected void createDialog(final Context ctx)
	  {
	    this.mDialog = new Dialog(ctx);
	    this.mDialog.requestWindowFeature(1);
	    this.mDialog.setContentView(R.layout.annot_linkj);
	    this.mDialog.setCancelable(true);
	    
	     titleText = (TextView)this.mDialog.findViewById(R.id.annot_note_title);
	    
	    titleText.setText(this.word);
	    
	    this.contentsText = (EditText)this.mDialog.findViewById(R.id.annot_note_contents);
	    
	    this.contentsText.setText(this.mean);
	    ImageButton fa=(ImageButton)this.mDialog.findViewById(R.id.fa);
	    fa.setOnClickListener(new View.OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  word=titleText.getText().toString();
	        try
	        {
	          myDbHelper.openDataBase();
	        }
	        catch (SQLException e)
	        {
	          e.printStackTrace();
	        }
	        if (myDbHelper.farsdict("hello").length() > 1)
	        {
	          
	          String mean = myDbHelper.farsdict(word);
	          
	          if (mean.length() > 0) {
	           contentsText.setText(mean);
	          } else {
	           // Toast.makeText(ctx, ctx.getResources().getString(2131230810), 0).show();
	          }
	        }
	        else
	        {
	         // Toast.makeText(ctx, ctx.getResources().getString(2131230803), 0).show();
	        }
	        myDbHelper.close();
	      }
	    });
	    ImageButton eng=(ImageButton) this.mDialog.findViewById(R.id.eng);
	    eng.setOnClickListener(new View.OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  word=titleText.getText().toString();
	        if (word.equals(""))
	        {
	          Log.v("lookdict", "none");
	        }
	        else
	        {
	          Log.v("lookdict", word);
	          Log.v("text length", String.valueOf(word.length()));
	        }
	        Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, new String[] { word }, null);
	        if ((cursor != null) && (cursor.moveToFirst()))
	        {
	          int wIndex = cursor.getColumnIndexOrThrow("suggest_text_1");
	          int dIndex = cursor.getColumnIndexOrThrow("suggest_text_2");
	          
	          String customHtml = cursor.getString(wIndex) + ":<br>" + cursor.getString(dIndex);
	          Log.v("customhtml", customHtml);
	         contentsText.setText(Html.fromHtml(htmldecode(customHtml)));
	          Log.v("if", "called");
	        }
	        else
	        {
	          Log.v("else", "called");
	          if (checkdict() == 1)
	          {
	          //  Toast.makeText(ctx, ctx.getResources().getString(2131230810), 0).show();
	          }
	          else
	          {
	            Log.d("demo", "missing word");
	            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
	            
	          //  builder.setTitle(ctx.getResources().getString(2131230811));
	          //  builder.setMessage(ctx.getResources().getString(2131230812));
	            
	            builder.setPositiveButton("yes", new DialogInterface.OnClickListener()
	            {
	              public void onClick(DialogInterface dialog, int which)
	              {
	                Intent intent = new Intent("android.intent.action.VIEW");
	                intent.setData(Uri.parse("market://details?id=livio.pack.lang.en_US"));
	                startActivity(intent);
	                dialog.dismiss();
	              }
	            });
	            builder.setNegativeButton("no", new DialogInterface.OnClickListener()
	            {
	              public void onClick(DialogInterface dialog, int which)
	              {
	                dialog.dismiss();
	              }
	            });
	            AlertDialog alert = builder.create();
	            alert.show();
	          }
	        }
	      }
	    });
	    ImageButton note_cancel=(ImageButton)this.mDialog.findViewById(R.id.annot_note_cancel);
	   note_cancel.setOnClickListener(new View.OnClickListener()
	    {
	      public void onClick(View v)
	      {
	        mDialog.dismiss();
	      }
	    });
	   ImageButton note_add=(ImageButton) this.mDialog.findViewById(R.id.annot_note_add);
	    note_add.setOnClickListener(new View.OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  EditText mContent=(EditText)mDialog.findViewById(R.id.annot_note_contents);
	        String mContents = mContent.getText().toString();
	        EditText textb=(EditText)mDialog.findViewById(R.id.annot_note_title);
	        String text = textb.getText().toString();
	        try
	        {
	          myDbHelper.openDataBase();
	        }
	        catch (SQLException e)
	        {
	          e.printStackTrace();
	        }
	        
	        
	        datasource.open();
	        datasource.createComment(text, mContents, "names");
	        maintitle.setText(text);
	        mainmean.setText(mContents);
	        myDbHelper.close();
	        datasource.close();
	        contentsText.setText("");
	        mDialog.dismiss();
	        
	       
	      } 
	    });
	    this.mDialog.show();
	  }
	 public String htmldecode(String word)
	  {
	    String result = word;
	    String translate = "<span class=head>translations</span>";
	    result = result.replaceAll("<li>", "<br>");
	    result = result.replaceAll("<hr style='clear:both'>", "<br>");
	    


	    return result;
	  }
	 int checkdict()
	  {
	    int x = 0;
	    Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, new String[] { "hello" }, null);
	    if ((cursor != null) && (cursor.moveToFirst()))
	    {
	      int wIndex = cursor.getColumnIndexOrThrow("suggest_text_1");
	      int dIndex = cursor.getColumnIndexOrThrow("suggest_text_2");
	      
	      x = 1;
	    }
	    else
	    {
	      x = 0;
	    }
	    return x;
	  }
}
