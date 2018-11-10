package dictonary.mj.dastan;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Locale;

import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.util.Xml;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public  class DemoObjectFragment extends Fragment  implements
TextToSpeech.OnInitListener, FragmentLifecycle {
    public static final String ARG_OBJECT = "object";
  
//	final MyClipboardManager myclipboard = new MyClipboardManager();
	 Insertword datasource;
	 DataBaseHelper myDbHelper;
		Context context;
		public TextToSpeech tts;
	public static String AUTHORITY = "livio.pack.lang.en_US.DictionaryProvider"; 
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/dictionary"); 
	public static final Uri CONTENT_URI3 = Uri.parse("content://" + AUTHORITY + "/"+ SearchManager.SUGGEST_URI_PATH_QUERY);
	String famil;
	Typeface type;
	int clicked=0;
	private final static int LIST_CHAPTER_ACTIVITY_ID = 1; 
	private float x1;
	Handler handel = new Handler();
	public LinearLayout mylayout;

	public ScrollView scrol;
//	OnSwipeTouchListener onSwipeTouchListener;
	 int shd;
	 int aval;
	 int akhar;
	int scroll=0;
	String code;
	 public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	 String table;
	 String mainword="";
	 
	 static final int MIN_DISTANCE = 150;
	String milad;
	
	OnTouchListener otl;
	
	Long time;
	int strt=(-3);
	
	final String text="";
	EditText matna;
	 TextView item; 
	 int width;
	 int height;
 int snipe=-1;
	int a;
	View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
    	 
         rootView = inflater.inflate(
                R.layout.fragment_collection_object, container, false);
        
        context=getActivity();
        setHasOptionsMenu(true);
        type=Typeface.createFromFile(IM_PATH+"Nazanin.ttf");
        myDbHelper = new DataBaseHelper(context);
        datasource=new Insertword(context);
        tts = new TextToSpeech(getActivity(),this);
        table=((MainActivity3B)getActivity()).table;
        Bundle args = getArguments();

        
        shd=args.getInt(ARG_OBJECT);
        
        Log.v("fragment first",Integer.toString(shd));
        width=((MainActivity3B)getActivity()).width;
        height=((MainActivity3B)getActivity()).height;
		scrol=(ScrollView)rootView.findViewById(R.id.asd);
		
		if(scrol.getHeight()<(height/3)){
			scrol.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}else{
		scrol.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, height/3));}
		item = (TextView)rootView.findViewById(R.id.textView2);
		registerForContextMenu(item);
		item.setText("select word");
		item.setTypeface(type);
		item.setMaxHeight(height/3);
		item.setMovementMethod(new ScrollingMovementMethod());
	//	item.setBackgroundResource(R.color.white);
	       // prv=(Button) rootView.findViewById(R.id.prv);
	         
	         
	         
		
	       matna = (EditText) rootView.findViewById(R.id.textView1);
	       matna.setBackgroundResource(R.color.white);
		FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//download();
				aval=matna.getSelectionStart();
				akhar=matna.getSelectionEnd();
				Log.v("download","called");
				final int startasl=matna.getSelectionStart();
				final int endasl=matna.getSelectionEnd();
				addmaterial(startasl,endasl);
			}
		});
	    	 Milad aval=new Milad();
	       aval.sum=0;
	       aval.arz=0;
	        try {
				myDbHelper.createDataBase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         code=myDbHelper.getmatn(shd,table)._matn;
	      
	       
		       matna.setText(code);
		       
		       
		       myDbHelper.close();
		       
		        	 
		        	matna.setOnLongClickListener(new View.OnLongClickListener() {
		        		MotionEvent event;
		                @Override
		                public boolean onLongClick(View v) {
		                	 Log.v("long","click");
		                    Layout layout = ((EditText) v).getLayout();
		        	          float x = event.getX() + matna.getScrollX();
		        	          float y = event.getY();
		        	          int m = layout.getLineForVertical((int) y);  
		        	          int offset = layout.getOffsetForHorizontal(m, x);
		        	       if(x>layout.getLineMax(m)&&offset>0){
		        	                 offset=layout.getOffsetForHorizontal(m,layout.getLineMax(m))-1;  matna.setSelection(offset);
		        	          
		        	          // touch was at end of text
		        	              } else{
		        	            	  
		        	            	  try{
		        	                  matna.setSelection(offset - 1);}catch (Throwable e){
		        	                	  
		        	                  }
		        	            	 
		        	                  }
		        	              
	
		                 		                    return false;
		                }
		            });
		        	 otl = new OnTouchListener() {
		        		 
			        	  @Override
			        	  public boolean onTouch(View v, MotionEvent event) {
			 switch (event.getAction()) {
			        	      case MotionEvent.ACTION_DOWN:
			        	    	
			        	    	 ((MainActivity3B)getActivity()).hide();
			        	    	  x1 = event.getX(); 
			        	    	  
			        	    	  v.requestFocus();
			        	    	 
			        	     handel.postDelayed(run2, 500/* OR the amount of time you want */);
			        	          Layout layout = ((EditText) v).getLayout();
			        	          float x = event.getX() + matna.getScrollX();
			        	          float y = event.getY();
			        	          int m = layout.getLineForVertical((int) y);  
			        	          int offset = layout.getOffsetForHorizontal(m, x);
			        	       if(x>layout.getLineMax(m)&&offset>0){
			        	                 offset=layout.getOffsetForHorizontal(m,layout.getLineMax(m))-1;  matna.setSelection(offset);
			        	          
			        	          // touch was at end of text
			        	              } else{
			        	            	  
			        	            	  try{
			        	                  matna.setSelection(offset - 1);}catch (Throwable e){
			        	                	  
			        	                  }
			        	                  }
			        	              
			        	          break;
			        	      case MotionEvent.ACTION_CANCEL:
			        	    	  handel.removeCallbacks(run2);
			        	    	  Log.v("action","cancel");
			        	    	  break;
			        	      case MotionEvent.ACTION_UP:
			        	    	  Log.v("action","up");
			        	    	  showmean(matna);

			                      handel.removeCallbacks(run2);
			                      break;
			        	          }
			        	      
			        	      int a=start(matna);
			        	      int b=end(matna);
			        	      
			        	      if(a<b&&a<matna.getText().toString().length()){
			        	      try{
			        	     if(a!=b&&matna.getText().charAt(a)!=' '&&b<=matna.getText().toString().length()){
			        	    	 
			        	    	 matna.setSelection(a,b);
			        	    
			        	     }}catch (Throwable e){
			        	    	 
			        	    	 if(a!=b&&matna.getText().charAt(a)!=' '&&b<matna.getText().toString().length()&&a<matna.getText().toString().length()&&b>a){
			        	    		 
			        	    		 matna.setSelection(a,b-1);
			        	    		 
				        	     }//end id
			        	    	 
			        	     }//end catch
			        	      
			        	      
			        	     
			    }
			        	      
			        	       return true;
			        	       }
			        	  };
			        	  
			        	  matna.setOnTouchListener(otl);
			        	
		   /*    prv.setOnClickListener(new OnClickListener(){
		        	public void onClick(View arg0){	
		        	  	}
		        	}); */
		      
		      		      
spank(matna);
Log.v("fragment last",Integer.toString(shd));
//Log.v("mid scroll",Integer.toString(((MainActivity3B)getActivity()).scroll));
scroll=((MainActivity3B)getActivity()).scroll;
Log.v("scroll",Integer.toString(scroll));
if(scroll!=0){
	 matna.setSelection(scroll);
}

milad=matna.getText().toString();

displaySharedPreferences();

hideKeyboard();
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	registerForContextMenu(matna);
        super.onActivityCreated(savedInstanceState);
    }
   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
    	menu.clear();
         inflater.inflate(R.menu.activity_main_actions, menu);
  }

    @Override
	 public boolean onOptionsItemSelected(MenuItem item) {  
       switch (item.getItemId()) {  
      
           case R.id.action_setting:  
           	Intent mIntent=new Intent(context,PrefsActivity.class);
           	mIntent.putExtra("shomare_dars",shd);
       		mIntent.putExtra("table",table);
       		startActivity(mIntent); 
       		((MainActivity3B)getActivity()).finish();
           return true; 
           case R.id.tts:
        	   if(((MainActivity3B)getActivity()).check()){
        		   ((MainActivity3B)getActivity()).stoptts();  
        	   }else{
        		   if(selectedtext()!=null){
        		   ((MainActivity3B)getActivity()).speakOut(selectedtext()); 
        	  // ((MainActivity3B)getActivity()).speakOut(noImage());
        		   }else{
        			   Toast.makeText(context, getResources().getString(R.string.select_word), Toast.LENGTH_SHORT).show();  
        		   }
        	   }
        	   return true;
           case R.id.table_content:
        	   try {
       			myDbHelper.openDataBase();
       			
       		} catch (SQLException e) {
       			// TODO Auto-generated catch block
       			e.printStackTrace();
       		}
          	 long epubsize=myDbHelper.counttable("epub");
          	 int epubnum=1;
         	 for(long h=1;h<=epubsize;h++){
          		try{ if(myDbHelper.getimage((int) h,"epub").getisbn().equals(table)){
          			 epubnum=(int) h;
          		 }}catch (Throwable df){}
          	 }
          	 
          //	epubnum=Integer.valueOf(table.substring(table.lastIndexOf("_")+1));
          	Log.v("epubnum",Integer.toString(epubnum));
          	XMLParser parser = new XMLParser();
          Log.v("count",	Long.toString(myDbHelper.counttable("epub")));
          	String xml=myDbHelper.getimage(epubnum, "epub").getTOC();
          	myDbHelper.close();
          	 Document doc = parser.getDomElement(xml); // getting DOM element
          	 
               NodeList nl = doc.getElementsByTagName("Navpoint");
               TableOfContent tablencx=new TableOfContent();
            // looping through all item nodes <item>      
               for (int i = 0; i < nl.getLength(); i++) {
              	 Element e = (Element) nl.item(i);
                   String playorder = parser.getValue(e, "playorder"); // name child value
                   String NavLab = parser.getValue(e, "NavLab"); // cost child value
                   String content = parser.getValue(e, "content"); // description child value
                   Log.v("firstcontent",content);
                   NavPoint navpoint=new NavPoint(playorder);
       		     navpoint.setNavLabel(NavLab);
       		     navpoint.setContent(content);
       		    tablencx.add(navpoint);
               }
          	  Intent listChaptersIntent = new Intent(context, ListChaptersActivity.class);
              tablencx.pack(listChaptersIntent, ListChaptersActivity.CHAPTERS_EXTRA);
              Log.v("table",table);
              listChaptersIntent.putExtra("table",table);
              startActivityForResult(listChaptersIntent, LIST_CHAPTER_ACTIVITY_ID); 
          	return true;
  // case R.id.action_prv: 
   	/*
 	try{shd--;
		 try {
			myDbHelper.openDataBase();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	       matna.setText(myDbHelper.getmatn(shd,table)._matn);
	       myDbHelper.close();
	       spank(matna);
	      milad=matna.getText().toString();
	     
	      ((TextView) findViewById(R.id.textView2)).setText("select word");}catch (Throwable e){
	    	  Toast.makeText(getApplicationContext(), "صفحه درخواستی موجود نیست", Toast.LENGTH_SHORT).show();
	    	  shd++;
	    	  
	       } */
   	
   case R.id.action_download:
	   
   	download();
   	return true;
  case R.id.action_help:  
Intent nIntent=new Intent(context,HelpActivity.class);
       		startActivity(nIntent); 
             return true;     
              default:  
               return super.onOptionsItemSelected(item);  
       }  
   }  
	
	public int hamarz(String matnasli,int sh10){
		matnasli=matnasli+"  ";
	int charmosavi=0;
	int sharpha=0;
	int tool=matnasli.length();
	for(int i=0;i<tool;++i){

      	 if(matnasli.charAt(i)=='¦'){
      		sharpha++;
      		 }
      	 if(charmosavi==(sh10+1)){ charmosavi=(i-1);break;}
      		if(sharpha==0){ ++charmosavi;}
      		if(sharpha==2&&matnasli.charAt(i)!='¦'){++charmosavi;}
      		if(sharpha==3){ sharpha=0;}
      	}
return charmosavi;
	}
	public void errordialog(){
		AlertDialog alertDialog = new AlertDialog.Builder(
				context).create();

// Setting Dialog Title
alertDialog.setTitle("برای تغییر معنی کلمات ابی رنگ از گزینه ویرایش استفاده کنید");

// Setting Dialog Message
alertDialog.setMessage("کلمات ابی رنگ را انتخاب نکنید");



// Setting OK Button
alertDialog.setButton("تایید", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        // Write your code here to execute after dialog closed
       
        }
});

// Showing Alert Message
alertDialog.show();
		
	}
	public void spank(EditText matna){
		Milad aval=new Milad();
		int arz=0;
		int sum=0;
		  try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		String garbage=myDbHelper.getmatn(shd,table)._matn;
		long lastpagetable=myDbHelper.counttable("last_page");
		long idpage=1;
		long row=1;
		for(long m=1;m<=lastpagetable;m++){
			if(myDbHelper.getContact((int) m,"last_page")._1name.equals(table)){
				
				row=m;
				idpage=Long.valueOf(myDbHelper.getContact((int) m,"last_page")._2name);
				break;
			}
		}
		
	
		datasource.open();
datasource.updatepage("last_page", row, table,Integer.toString(shd-1));

datasource.close();
int tool=myDbHelper.getmatn(shd,table)._matn.length();
		  for(int i=0;i<tool;++i){
		       	        	 if(garbage.charAt(i)=='¦'){
		       		 aval.sharp[sum][arz]=i;
		       		++arz;
		       		 }
		       	 if(arz==3){arz=0; ++sum;}
		       	  }
		        final SpannableStringBuilder text = new SpannableStringBuilder(myDbHelper.getmatn(shd,table)._matn);
		           for( int k=0;k<(sum);k++){
		   	   String sh="";
		   	   for(int t=(aval.sharp[k][0]+1);t<aval.sharp[k][1];t++)
		   			   {
		   			   sh=sh+(myDbHelper.getmatn(shd,table)._matn.charAt(t));
		   			   try{
		   			    aval.sharp[k][3]=Integer.parseInt(sh);}catch (Throwable e){
		   			    	aval.sharp[k][3]=-3;
		   			    }
		   			     }
		   	   if(aval.sharp[k][3]==0){
		   		   String nameimage=garbage.substring(aval.sharp[k][1]+1, aval.sharp[k][2]);
		   		Drawable d;
				
					//d = Drawable.createFromStream(getAssets().open(IM_PATH+nameimage), null);
					d=Drawable.createFromPath(IM_PATH+nameimage);
					// d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight()); 
					double ratio= (double)((double)(d.getIntrinsicWidth())/(double)(d.getIntrinsicHeight()));
					
					if(d.getIntrinsicWidth()<width){
					d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
					
					}else{
						
						d.setBounds(0, 0,(int)(width*.95),(int)(d.getIntrinsicHeight()/ratio*.95));
					}
			            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
					    text.setSpan(span, aval.sharp[k][1]+1, aval.sharp[k][2], Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
			
		   	//	Drawable  d = getResources().getDrawable(getResources().getIdentifier("drawable/" + nameimage, "drawable", getPackageName())); 
		            
		   	   }else if(aval.sharp[k][3]==-3){
		   		  
		   	   }
		   
		   	   else{
		          text.setSpan(new RelativeSizeSpan(1.1f),aval.sharp[k][1]+1,aval.sharp[k][2],
		                  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		          CallbackSpan clickableSpan = new CallbackSpan(aval.sharp[k][3]) ;
		           text.setSpan(clickableSpan,aval.sharp[k][1]+1,aval.sharp[k][2], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		           text.setSpan(new ForegroundColorSpan(Color.BLUE),aval.sharp[k][1]+1,aval.sharp[k][2],0);
		          matna.setMovementMethod(ArrowKeyMovementMethod.getInstance());
		      }}
		     for(int v=(sum)-1;v>=0;v--){
		    		text.delete(aval.sharp[v][2],aval.sharp[v][2]+1);
		    	text.delete(aval.sharp[v][0],aval.sharp[v][1]+1);
		    }
		    matna.setText(text);
		    myDbHelper.close();datasource.close();
		         }// end spank
	public String hazf(String matn,int id,String word){
		matn=matn+" ";
		String jadid="2";
		int tool=matn.length();
		String submatn="¦"+Integer.toString(id)+"¦"+word+"¦";
		int subtool=submatn.length();
		
		for(int i=0;i<tool-subtool;++i){
			
		String asd=matn.substring(i,i+subtool);
		
			if(asd.equals(submatn)){
				
				jadid=matn.substring(0,i)+word+matn.substring(i+subtool,tool-1);break;
			}//end if
}//end for
		
		return jadid;
	}//end class hazf
	public final class CallbackSpan extends ClickableSpan {

	    public int id;
	    

	    public CallbackSpan(int nums) {
	      id=nums;

	    }

	    public void onClick(View view) {
	    	try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if(id>=0){
	    	final int startp= hamarz(myDbHelper.getmatn(shd,table)._matn,matna.getSelectionStart());
	    	 
	    	if(snipe!=id){
	    		
	    		snipe=id;
	    		 try {
	     				myDbHelper.openDataBase();
	     				
	     			} catch (SQLException e) {
	     				// TODO Auto-generated catch block
	     				e.printStackTrace();
	     			}
	    		 ((TextView) rootView.findViewById(R.id.textView2)).setText(myDbHelper.getContact(id,"names")._1name+":"+myDbHelper.getContact(id,"names")._2name );
		    	 
		    
		    	 
		    myDbHelper.close();
	        
	        
	       
	       }else{
	    	   
	    	   LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.prompts, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);
				userInput.setTextColor(getResources().getColor(R.color.black));
				final EditText wordd = (EditText) promptsView
						.findViewById(R.id.worddialog);
				wordd.setTextColor(getResources().getColor(R.color.black));
       		 try {
	     				myDbHelper.openDataBase();
	     				
	     			} catch (SQLException e) {
	     				// TODO Auto-generated catch block
	     				e.printStackTrace();
	     			}
       		wordd.setText(myDbHelper.getContact(id,"names")._1name);
       		 userInput.setText(myDbHelper.getContact(id,"names")._2name);
       		 final String wordasli=myDbHelper.getContact(id,"names")._1name;
       		myDbHelper.close();
       		final int ids=id;
       		
       		alertDialogBuilder.setMessage("ویرایش کلمه و معنی");
       		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("تایید",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    	datasource.open();
			    	 try {
		     				myDbHelper.openDataBase();
		     				
		     			} catch (SQLException e) {
		     				// TODO Auto-generated catch block
		     				e.printStackTrace();
		     			}
			    	 String xa=myDbHelper.getmatn(shd,table)._matn;
			    	 String tr=wordd.getText().toString();
			    	 if(check(tr)==1&&tr.length()!=0){
			    	String h=xa.substring(0,startp)+tr+xa.substring(startp+myDbHelper.getContact(ids,"names")._1name.length(),xa.length());
			    	datasource.updatemean(ids,wordd.getText().toString(), userInput.getText().toString());
			    	datasource.updateMatn(shd,h, table);
			    	item.setText(myDbHelper.getContact(ids,"names")._1name+":"+myDbHelper.getContact(ids,"names")._2name );
			    	
			    	matna.setText(h);
			    	Toast.makeText(context, getResources().getString(R.string.meaning_updated), Toast.LENGTH_SHORT).show();
			    	spank(matna);}else{
			    		if(check(tr)==0){
			    		Toast.makeText(context, "Dont use '¦' character", Toast.LENGTH_SHORT).show();
			    	}
			    	if(tr.length()==0){
			    		Toast.makeText(context, getResources().getString(R.string.to_delete_word), Toast.LENGTH_SHORT).show();
			    	}
			    	}
			    	 
				    	datasource.close();
				    	myDbHelper.close();
			    }})
       		.setNegativeButton("انصراف",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
					    	 try {
				     				myDbHelper.openDataBase();
				     				
				     			} catch (SQLException e) {
				     				// TODO Auto-generated catch block
				     				e.printStackTrace();
				     			}
					    	 
					    
					    	 matna.setText(myDbHelper.getmatn(shd,table)._matn);
						spank(matna);
					    myDbHelper.close();
					    	dialog.cancel();
						
					    }
					  })
			
       		
       		.setNeutralButton("حذف معنی",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
					    	
					    	 try {
				     				myDbHelper.openDataBase();
				     				
				     			} catch (SQLException e) {
				     				// TODO Auto-generated catch block
				     				e.printStackTrace();
				     			}
					    	 datasource.open();
					    datasource.updateMatn(shd,hazf(myDbHelper.getmatn(shd, table)._matn,ids,myDbHelper.getContact(ids, "names")._1name),table);
					    	 matna.setText(myDbHelper.getmatn(shd,table)._matn);
						spank(matna);
					    myDbHelper.close();
					    
						item.setText("select word");
					    }
					  });
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				snipe=id;
				// show it
				alertDialog.show();
	       }}//end main if in onclick
	    	if(id<=(-100)){
	    		 try {
	     				myDbHelper.openDataBase();
	     				
	     			} catch (SQLException e) {
	     				// TODO Auto-generated catch block
	     				e.printStackTrace();
	     			}
	    	String link=myDbHelper.getmatn(-(id+99), "matn_link")._matn;
	    	String file=link;
	    	String content="";
	    	try{
	    		file=link.substring(0,link.lastIndexOf("#"));
	    		
	    		content=link.substring(link.lastIndexOf("#"), link.length());
	    		
	    	}catch (Throwable e){
          	  
            }
	    	Long te=myDbHelper.counttable(table);
	        
	        for(long j=1;j<=te;j++){
	        
	        	if(myDbHelper.getBooks((int) j, table)._2name.equals(file)){
	        		
	        		if(myDbHelper.getBooks((int) j, table)._1name.indexOf("¦"+content+"¦")!=-1){
	        			
		        		 matna.setText(myDbHelper.getmatn((int) j,table)._matn);
		      	       myDbHelper.close();
		      	       spank(matna);
		      	       milad=matna.getText().toString();
		      	       ((TextView) rootView.findViewById(R.id.textView2)).setText("select word");
	        		}
	        	}
	        }
	    	}//end if
	    	myDbHelper.close();datasource.close();
	    }//end onclick
	    @Override 
	    public void updateDrawState(TextPaint ds) {// override updateDrawState
	    	   ds.setUnderlineText(false); // set to false to remove underline
	    	}
	 }//end class
	int position(int a,String t){
		int javab=0;
		t=t+" ";
		
		for(int i=0;i<a;++i){
			if(t.charAt(i)=='¦'){javab++;}
	}
		Log.v("javab",Integer.toString(javab));
		javab=javab%3;
		return javab;
		}
	int check(String te){
		int javab=1;
		
		int tool=te.length();
		for(int i=0;i<tool;++i){
		if(te.charAt(i)=='¦') {javab=0;break;}
		}
		
		return javab;
	}
	private void changeSharedPreferences(){
		SharedPreferences prefs2=PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs2.edit();
		boolean checkbox2= prefs2.getBoolean("checkBox",true);
		editor.remove("checkbox");
		if(checkbox2){
			
		editor.putBoolean("checkBox", false);
		
		editor.commit();
		
	}else{
		
		editor.putBoolean("checkBox", true);
		
		editor.commit();
	}
	}
	private void displaySharedPreferences(){
		
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(context);
        TextView mit = (TextView)rootView.findViewById(R.id.textView2);

		String listPrefs = prefs.getString("listpref","16");
		String textprefs = prefs.getString("textpref","16");
		String tts_speed=prefs.getString("ttspref",".75");
		Log.v("speed test",tts_speed);
		((MainActivity3B)getActivity()).tts_speed(Float.parseFloat(tts_speed));
		matna.setTextSize(Float.parseFloat(listPrefs));
		item.setTextSize(Float.parseFloat(textprefs));
	
			
			
			
			
		
	}//end preferences
/*	@Override
	public void onWindowFocusChanged (boolean hasFocus) {
	        // the height will be set at this point
		 Display display = getWindowManager().getDefaultDisplay(); 
	         width = display.getWidth(); 
	         Log.v("arz",Integer.toString(width));
	         height= display.getHeight();// deprecated}
	}*/
	
	public int start(EditText input){
		int start=(-2);
		int position=input.getSelectionStart();
		while(start==(-2)){
			if(((input.getText()+" ").charAt(position)>=97&&(input.getText()+" ").charAt(position)<=122)||((input.getText()+" ").charAt(position)>=65&&(input.getText()+" ").charAt(position)<=90)||((input.getText()+" ").charAt(position)>=48&&(input.getText()+" ").charAt(position)<=57)){
				if(position==0){start=0;break;}
				--position;}else{start=position;position=input.getSelectionStart();}
		}
		
		if(start!=0){++start;}
		if(((input.getText()+" ").charAt(start)>=97&&(input.getText()+" ").charAt(start)<=122)||((input.getText()+" ").charAt(start)>=65&&(input.getText()+" ").charAt(start)<=90)||((input.getText()+" ").charAt(position)>=48&&(input.getText()+" ").charAt(position)<=57)){}else{++start;}
		
		return start;
	}

public int end(EditText input){
	int end=(-1);
	int position=input.getSelectionStart();
	while(end==(-1)){
		if(((input.getText()+" ").charAt(position)>=97&&(input.getText()+" ").charAt(position)<=122)||((input.getText()+" ").charAt(position)>=65&&(input.getText()+" ").charAt(position)<=90)||((input.getText()+" ").charAt(position)>=48&&(input.getText()+" ").charAt(position)<=57)){++position;}else{end=position;position=input.getSelectionStart();}
	}
	if(end>input.getText().toString().length()){end=input.getText().toString().length();}
	return end;
}
	public static void closeKeyboard(Context c, IBinder windowToken) {
		InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(windowToken, 0);
	}
private void hideKeyboard() {
    InputMethodManager inputManager = (InputMethodManager) ((MainActivity3B)getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);

    // check if no view has focus:
    View view = ((MainActivity3B)getActivity()).getCurrentFocus();
    if (view != null) {
    	Log.v("not null","hide keyboard");
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    inputManager.hideSoftInputFromWindow(((MainActivity3B)getActivity()).getWindow().getDecorView()
            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
}
	public void showmean(EditText input){
		
			Log.v("clicked",Integer.toString(clicked));
			//clicked=1;
			//handel.postDelayed(runclick, 2000/* OR the amount of time you want */);
		int start=input.getSelectionStart();
		int end=input.getSelectionEnd();
		 try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 datasource.open();
	    	 String text=myDbHelper.getmatn(shd,table)._matn;
	    	
		if(start!=end&&hamarz(text,start)!=0&&hamarz(text,end)!=text.length()){
			
	    	
	    	 
	    	if((sharpnums(text,0,hamarz(text,start))%3)==2){ 
	    	// if(text.charAt(hamarz(text,start)-1)=='¦'&&text.charAt(hamarz(text,end)-1)=='¦'){
	    		int id=findid(text,text.substring(0,hamarz(text,start)).lastIndexOf("¦")) ;
	    		// int id=findid(text,hamarz(text,start)-1);
	    		 if(id>0){
	    		 String str=myDbHelper.getContact(id,"names")._1name+":"+myDbHelper.getContact(id,"names")._2name;
	    		 
	    		 ((TextView) rootView.findViewById(R.id.textView2)).setText(str);
	    		 Log.v("meaning","showed");
	    		 }
	    		 if(id<=(-100)){
	    		
	    			 try {
		     				myDbHelper.openDataBase();
		     				
		     			} catch (SQLException e) {
		     				// TODO Auto-generated catch block
		     				e.printStackTrace();
		     			}
	    			// String link=myDbHelper.getmatn(-(id+99), "matn_link")._matn;
	    			 String link="";
	    			 long linknum=myDbHelper.counttable("matn_link");
	    			 for(long i=1;i<=linknum;i++){
	    				Epubbooks linkmat= myDbHelper.getimage((int)i,"matn_link");
	    				if(linkmat.getcreater().equals(table)&&linkmat.gettitle().equals(String.valueOf(id))){
	    					link=linkmat.getisbn();
	    				}
	    			 }
		    	
		    	
	    			 try{
	    				 
	    			 Intent i = new Intent(Intent.ACTION_VIEW);
	    			 i.setData(Uri.parse(link));
	    			 startActivity(i);
	    			 
	    			 }catch (Throwable e){
	    				 
		    	String file=link;
		    	String content="";
		    	try{
		    		file=link.substring(0,link.lastIndexOf("#"));
		    		Log.v("file",file);
		    		content=link.substring(link.lastIndexOf("#")+1, link.length());
		    		
		    		Log.v("content",content);
		    	}catch (Throwable m){
	          	  
	            }
		    	Long te=myDbHelper.counttable(table);
		       
		        for(long j=1;j<=te;j++){
		        
		        	if(myDbHelper.getBooks((int) j, table)._2name.equals(file)){
		        		
		        		if(content.equals("")){
		        			
			        	//	matna.setText(myDbHelper.getBooks((int) j, table)._2name);
			        	//	shd=(int) j;
			        		//  spank(matna);
						      	 //      milad=matna.getText().toString();
						      	    //   ((TextView) rootView.findViewById(R.id.textView2)).setText("select word");
						      	    //   scroll=0;
		        			myDbHelper.close();
						      	     Intent mIntent=new Intent(context,MainActivity3B.class);
						        		mIntent.putExtra("shomare_dars",(int) j);
						        		mIntent.putExtra("table",table);
						        		mIntent.putExtra("scroll",0);
						        		stoptts();
						        		MainActivity3B.fa.finish();
						        		Log.v("link","showed");
						        		startActivity(mIntent);
						      	     
			        		break;
		        		}
		        		
		        		String matnha=myDbHelper.getBooks((int) j, table)._1name;
		        		int indexha=matnha.indexOf("¦"+content+"¦");
		        		if(indexha!=-1){
		        			myDbHelper.close();
		        			 Intent mIntent=new Intent(context,MainActivity3B.class);
				        		mIntent.putExtra("shomare_dars",(int) j);
				        		mIntent.putExtra("table",table);
				        		Log.v("first scroll",Integer.toString(reverseposition(matnha,indexha)));
				        		mIntent.putExtra("scroll",reverseposition(matnha,indexha));
				        		stoptts();
				        		MainActivity3B.fa.finish();
				        		Log.v("link","showed");
				        		startActivity(mIntent);
			        /*	 matna.setText(matnha);
			        		 
			      	       
			      	       shd=(int) j;
			      	     
			      	       spank(matna);
			      	      
			      	       ((TextView) rootView.findViewById(R.id.textView2)).setText("select word");
			      	       scroll=reverseposition(matnha,indexha);
			      	     matna.setSelection(scroll);
			      	     myDbHelper.close();*/
			      	     
		        		/*milad=matna.getText().toString();
		        		 * 	Intent mIntent=new Intent(MatnActivity3.this,MatnActivity3.class);
			        		mIntent.putExtra("shomare_dars",(int) j);
			        		mIntent.putExtra("table",table);
			        		startActivity(mIntent);
			        		finish();*/
			      	       break;
		        		}
		        	}
		        }}
	    		 }
	    	 }//end second if
			
		}//end main if
		myDbHelper.close();
		datasource.close();
		
	}//end showmean
	
	public int findid(String text,int start){
		int num=0;
		try{
		for(int i=start-1;i>=0;--i){
			if(text.charAt(i)=='¦'){num=Integer.parseInt(text.substring(i+1,start));break;}
		}}catch (Throwable e){
        	  
          }
		
		return num;
	}//end findid
	public void changeblue(EditText input){
		int start=input.getSelectionStart();
		int end=input.getSelectionEnd();
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.prompts, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText userInput = (EditText) promptsView
				.findViewById(R.id.editTextDialogUserInput);
		final EditText wordd = (EditText) promptsView
				.findViewById(R.id.worddialog);
		 try {
 				myDbHelper.openDataBase();
 				
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
		 String text=myDbHelper.getmatn(shd,table)._matn;
		 int id=findid(text,hamarz(text,start)-1);
		 final int startp= hamarz(myDbHelper.getmatn(shd,table)._matn,matna.getSelectionStart());
		wordd.setText(myDbHelper.getContact(id,"names")._1name);
		 userInput.setText(myDbHelper.getContact(id,"names")._2name);
		 final String wordasli=myDbHelper.getContact(id,"names")._1name;
		myDbHelper.close();
		final int ids=id;
		
		alertDialogBuilder.setMessage("ویرایش کلمه و معنی");
		alertDialogBuilder
	.setCancelable(false)
	.setPositiveButton("تایید",
	  new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog,int id) {
	    	datasource.open();
	    	 try {
     				myDbHelper.openDataBase();
     				
     			} catch (SQLException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			}
	    	 String xa=myDbHelper.getmatn(shd,table)._matn;
	    	 String tr=wordd.getText().toString();
	    	 if(check(tr)==1&&tr.length()!=0){
	    	String h=xa.substring(0,startp)+tr+xa.substring(startp+myDbHelper.getContact(ids,"names")._1name.length(),xa.length());
	    	datasource.updatemean(ids,wordd.getText().toString(), userInput.getText().toString());
	    	datasource.updateMatn(shd,h, table);
	    	item.setText( myDbHelper.getContact(ids,"names")._1name+":"+myDbHelper.getContact(ids,"names")._2name );
	    	
	    	matna.setText(h);
	    	Toast.makeText(context, getResources().getString(R.string.meaning_updated), Toast.LENGTH_SHORT).show();
	    	spank(matna);}else{
	    		if(check(tr)==0){
	    		Toast.makeText(context, "Dont use '¦' character", Toast.LENGTH_SHORT).show();
	    	}
	    	if(tr.length()==0){
	    		Toast.makeText(context, getResources().getString(R.string.delete_word), Toast.LENGTH_SHORT).show();
	    	}
	    	}
	    	 
		    	datasource.close();
		    	myDbHelper.close();
	    }})
		.setNegativeButton("انصراف",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    	 try {
		     				myDbHelper.openDataBase();
		     				
		     			} catch (SQLException e) {
		     				// TODO Auto-generated catch block
		     				e.printStackTrace();
		     			}
			    	 
			    
			    	 matna.setText(myDbHelper.getmatn(shd,table)._matn);
				spank(matna);
			    myDbHelper.close();
			    	dialog.cancel();
				
			    }
			  })
	
		
		.setNeutralButton("حذف معنی",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    	
			    	 try {
		     				myDbHelper.openDataBase();
		     				
		     			} catch (SQLException e) {
		     				// TODO Auto-generated catch block
		     				e.printStackTrace();
		     			}
			    	 datasource.open();
			    datasource.updateMatn(shd,hazf(myDbHelper.getmatn(shd, table)._matn,ids,myDbHelper.getContact(ids, "names")._1name),table);
			    	 matna.setText(myDbHelper.getmatn(shd,table)._matn);
				spank(matna);
			    myDbHelper.close();
			    
			    item.setText("select word");
			    }
			  });
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		snipe=id;
		// show it
		alertDialog.show();
   
		 myDbHelper.close();
		 datasource.close();
	}//end change blue
	Runnable run2=new Runnable(){
		 @Override
	 	    public void run() {
			 ((MainActivity3B)getActivity()).setpage(shd);
			 ((MainActivity3B)getActivity()).show();
		 }
	};
	Runnable runclick=new Runnable(){
		 @Override
	 	    public void run() {
			 Log.v("runned",Integer.toString(clicked));
			 clicked=0;
		 }
	};
	 Runnable run = new Runnable() {

 	    @Override
 	    public void run() {
 	    	// Your code to run on long click
 	    	aval=matna.getSelectionStart();
 	    	akhar=matna.getSelectionEnd();

 	    	final int startasl=matna.getSelectionStart();
 	   	final int endasl=matna.getSelectionEnd();
 	   try {
			myDbHelper.openDataBase();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	   	 String text=myDbHelper.getmatn(shd,table)._matn;
 	   	 myDbHelper.close();
 	  
 	 	
 	   	if(startasl>=3){
 	   	if(startasl>=3&&startasl!=endasl&&!(text.substring(hamarz(text,startasl)-3,hamarz(text,startasl)).equals("¦0¦"))){
 	   	
 	   	
 	   		((Activity) context).openContextMenu(matna);
 	   	
 	   	}//end if
 	   	}//end main if
 	   	if(startasl<3){
 	   	((Activity) context).openContextMenu(matna);
 	   	}
 	    } //end run 
 	};//end long click
 	 @Override
 	  public void onCreateContextMenu(ContextMenu menu, View v,
 	      ContextMenuInfo menuInfo) {
 	    // TODO Auto-generated method stub
 		 
 	    switch (v.getId()) {
 	    
 	    case R.id.textView2:
 	      menu.add(0,1, 0, "پاک کردن قسمت معنی");
 	     
 	     menu.add(0,2, 0, "ذخیره این معنی به کلمه انتخاب شده");
 	      break;
 	    case R.id.textView1:
 	    menu.add(0,4,0,"جستجوی این کلمه در دیکشنری");
 	   menu.add(0,5,0,"افزودن معنی به این کلمه");
 	  menu.add(0,6,0,"ویرایش این کلمه");
 	 menu.add(0,7,0,"جستجوی این کلمه در دیکشنری فارسی");
 	
 	      break;
 	    }
 	  }//end oncreatecontextmenu
 	 @Override
 	  public boolean onContextItemSelected(MenuItem itemha) {
 	    // TODO Auto-generated method stub
 		if (getUserVisibleHint()) {
 	    switch (itemha.getItemId()) {
 	    // menu items for tvColor
 	     case 1:
 	      
 	    	item.setText("select word");
 	    	scrol.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
 	    	break;
 	    
 	     case 6:
 	    	 change();
 	    	 break;
 	     case 4:
 	    	 Log.v("mainword","mainword");
 	    	 lookdict(selectedtext());
 	    	 break;
 	     case 7:
 	    	lookfars(selectedtext());
 	    	 break;
 	     case 5:
 	    	add(aval,akhar,"");
 	    	 break;
 	     case 2:
 	    	 aval=matna.getSelectionStart();
 	    	 akhar=matna.getSelectionEnd();
 	    	 addfromdictionary(aval,akhar);
 	    	 break;
 	    }
 		}
 	    return super.onContextItemSelected(itemha);
 	  }//end onContextItemSelected
 	 int checkdict(){
 		 int x=0;
 		Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, new String[] {"hello"}, null); 
 		 if ((cursor != null) && cursor.moveToFirst()) { 
             int wIndex = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1); 
             int dIndex = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_2); 
             
            x=1;
         } else {
        	 x=0;
         }
 		return x;
 	 }
 	 public int reverseposition(String original,int posi){
 		 int resul=0;
 		Milad avalg=new Milad();
 		
 		int arz=0;
		int sum=0;
		String garbage=original.substring(0, posi);
		 final SpannableStringBuilder text = new SpannableStringBuilder(garbage);
		int tool=garbage.length();
		  for(int i=0;i<tool;++i){
		       	        	 if(garbage.charAt(i)=='¦'){
		       		 avalg.sharp[sum][arz]=i;
		       		++arz;
		       		 }
		       	 if(arz==3){arz=0; ++sum;}
		       	  }
		  for(int v=(sum)-1;v>=0;v--){
	    		text.delete(avalg.sharp[v][2],avalg.sharp[v][2]+1);
	    		text.delete(avalg.sharp[v][0],avalg.sharp[v][1]+1);
	    }
		  resul=text.length();
		 
 		 return resul;
 	 }
	String findword(String word){
		String result=word;
		Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, new String[]{word}, null);
		if ((cursor != null) && cursor.moveToFirst()) {
			int wIndex = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1);
			result = cursor.getString(wIndex);

		}else {
			Log.v("else","called");
			if(checkdict()==1){
				result=word;
			}else{
				result=word;
			}

	}
		return result;}
	String lookdictmaterial(String word){
		String result="";
		if(word.equals("")){
			Log.v("lookdict", "none");
		}else{
			Log.v("lookdict", word);
		}
		Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, new String[]{word}, null);
		if ((cursor != null) && cursor.moveToFirst()) {
			int wIndex = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1);
			int dIndex = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_2);
			// Log.d("demo", "found word: "+ cursor.getString(wIndex)+", definition: "+ cursor.getString(dIndex));
			String customHtml = cursor.getString(wIndex) + ":<br>" + cursor.getString(dIndex);
			Log.v("html", customHtml);
		/*	org.jsoup.nodes.Document docd= Jsoup.parse(customHtml);
			for(org.jsoup.nodes.Element element :docd.select("div.translations")) {
				Log.v("translation","found");
element.remove();
			}
			Log.v("text1",docd.toString());*/
			result = customHtml;

			Log.v("if", "called");
		}else {
			Log.v("else","called");
			if(checkdict()==1){
			result="no_ENG";
			}else{
				result="no_ENG_DICT";
			}

	}
		return result;}
 	 void lookdict(String word){
 		 if(word.equals("")){
 			 Log.v("lookdict", "none");
 		 }else{
 			 Log.v("lookdict", word);
 		 }
 		Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, new String[]{word}, null);
        if ((cursor != null) && cursor.moveToFirst()) { 
            int wIndex = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1); 
            int dIndex = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_2); 
           // Log.d("demo", "found word: "+ cursor.getString(wIndex)+", definition: "+ cursor.getString(dIndex));
            String customHtml=cursor.getString(wIndex)+":<br>"+ cursor.getString(dIndex);
            Log.v("html",customHtml);
            ((TextView) rootView.findViewById(R.id.textView2)).setText(Html.fromHtml(htmldecode(customHtml)));
            Log.v("if","called");
        } else { 
        	 Log.v("else","called");
        	if(checkdict()==1){
        		Toast.makeText(context, "کلمه مورد نظر در دیکشنری موجود نیست", Toast.LENGTH_SHORT).show();
        	}else{
            Log.d("demo", "missing word");
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("دانلود دیکشنری");
            builder.setMessage("دیکشنری بر روی دستگاه شما نصب نشده است.ایا مایل به نصب ان هستید؟");

            builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                	Intent intent = new Intent(Intent.ACTION_VIEW);
        	        intent.setData(Uri.parse("market://details?id=livio.pack.lang.en_US"));
        	        startActivity(intent);
                    dialog.dismiss();
                }

            });

            builder.setNegativeButton("نه", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }    }
        scrol.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
       
 	 }//end lookdict
 	 public void change(){
 		try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
	final int startasl=matna.getSelectionStart();
	final int endasl=matna.getSelectionEnd();
	 String text=myDbHelper.getmatn(shd,table)._matn;
		if(startasl!=endasl&&hamarz(text,startasl)!=0&&hamarz(text,endasl)!=text.length()&&text.charAt(hamarz(text,startasl)-1)=='¦'&&text.charAt(hamarz(text,endasl)-1)=='¦'){
			
	    	
	    	 
	    	
	    		changeblue(matna);
	    	 
			
		}else{
   LayoutInflater li = LayoutInflater.from(context);
	View promptsView = li.inflate(R.layout.prompts2, null);

	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
			context);
	final int starta= hamarz(myDbHelper.getmatn(shd,table)._matn,matna.getSelectionStart());
	final int enda= hamarz(myDbHelper.getmatn(shd,table)._matn,matna.getSelectionEnd());
	// set prompts.xml to alertdialog builder
	alertDialogBuilder.setView(promptsView);
if(startasl!=endasl){
	final EditText userInput = (EditText) promptsView
			.findViewById(R.id.editTextDialogUserInput);
	userInput.setTextColor(getResources().getColor(R.color.black));
	 try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 userInput.setText(matna.getText().toString().substring(startasl, endasl));
	final String xa=myDbHelper.getmatn(shd,table)._matn;
	myDbHelper.close();
	
	alertDialogBuilder.setTitle("ویرایش");
	alertDialogBuilder.setMessage("ویرایش کلمه");	
	alertDialogBuilder
	.setCancelable(false)
	.setPositiveButton("تایید",
	  new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog,int id) {
	    	datasource.open();
	    	 try {
     				myDbHelper.openDataBase();
     				
     			} catch (SQLException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			}
	    	 
	    	 if(check(userInput.getText().toString())==1&&check(xa.substring(starta, enda))==1&&position(starta,xa)==0&&endasl!=(-1)){
	    	 String xa=myDbHelper.getmatn(shd,table)._matn;
	    	String y= xa.substring(0, starta) + userInput.getText().toString()+xa.substring( enda, xa.length());
	    	
	    	datasource.updateMatn(shd,y,table );
	    	
	    	
	    	matna.setText(myDbHelper.getmatn(shd,table)._matn);
	    	
	    	Toast.makeText(context, getResources().getString(R.string.text_changed), Toast.LENGTH_SHORT).show();
	    	spank(matna);
	    	hideKeyboard();
	    	datasource.close();
	    	myDbHelper.close();
	    }else{
	    	datasource.close();
	    	myDbHelper.close();
	    	if(check(userInput.getText().toString())==0){
	    	Toast.makeText(context, "Dont use '¦' character", Toast.LENGTH_SHORT).show();
	    	hideKeyboard();
	    }
	    	if(check(xa.substring(starta, enda))==0||position(starta,xa)==2){
	    		errordialog();
	    	}
	    	
	    	if(endasl==(-1)){
	    		Toast.makeText(context,  getResources().getString(R.string.select_word), Toast.LENGTH_SHORT).show();
	    	}}
	    	 }})
		.setNegativeButton("انصراف",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
				hideKeyboard();
			    }
			  })
	
		
		.setNeutralButton("حذف کلمه",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    	
			    	 try {
		     				myDbHelper.openDataBase();
		     				
		     			} catch (SQLException e) {
		     				// TODO Auto-generated catch block
		     				e.printStackTrace();
		     			}
			    	 datasource.open();
			    	 String z=xa.substring(0, starta)+xa.substring( enda, xa.length());
			    datasource.updateMatn(shd,z,table);
			    	 matna.setText(myDbHelper.getmatn(shd,table)._matn);
				spank(matna);
			    myDbHelper.close();
			    hideKeyboard();
			    }
			  });
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();}else{
			Toast.makeText(context,  getResources().getString(R.string.select_word), Toast.LENGTH_SHORT).show();
		}
}hideKeyboard();
		 myDbHelper.close();
		 datasource.close();
 	 }//end change
 	 public String selectedtext(){
 		final int startasli=matna.getSelectionStart();
		final int endasli=matna.getSelectionEnd();
		String word="";
		Log.v("start", Integer.toString(startasli));
		Log.v("end", Integer.toString(endasli));
		if(endasli!=startasli){
		
		  word=matna.getText().toString().substring( startasli,endasli);
		 }
		return word;
 	 }
	public void addmaterial(int startasli,int endasli){
		try {
			myDbHelper.openDataBase();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String thistext=myDbHelper.getmatn(shd,table)._matn;
		if(position(hamarz(thistext,startasli),thistext)==0){
			if(endasli!=startasli){
				final int start= hamarz(myDbHelper.getmatn(shd,table)._matn,startasli);
				final int end= hamarz(myDbHelper.getmatn(shd,table)._matn,endasli);
				// get prompts.xml view
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.promptmaterial, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);
final TextView textdialog=(TextView) promptsView.findViewById(R.id.textviewdialog);
				String mean;
				final String word=matna.getText().toString().substring(startasli, endasli);
				alertDialogBuilder.setTitle(word);
				textdialog.setTextColor(getResources().getColor(R.color.black));
				if(myDbHelper.farsdict("hello").length()>1){
					mean=myDbHelper.farsdict(findword(word));
Log.v("last mean",mean);
					if(mean.length()>0){
						textdialog.setText( mean);
					}else{
						textdialog.setText(getResources().getString(R.string.word_notfound));

					}

				}else{
					textdialog.setText(getResources().getString(R.string.no_dictionary));

				}
				//final AlertDialog alertDialog = alertDialogBuilder.create();
				final Button downloadm=(Button) promptsView.findViewById(R.id.downloaddialog);
		/*		downloadm.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("market://details?id=livio.pack.lang.en_US"));
						startActivity(intent);
alertDialog.dismiss();
					}

				});*/
final WebView webview=(WebView) promptsView.findViewById(R.id.webviewdialog);
				String eng_mean=lookdictmaterial(word);
				if(eng_mean.equals("no_ENG_DICT")){
					String no_eng_dict=getResources().getString(R.string.no_eng_dict);
					webview.loadData(no_eng_dict, "text/html", "UTF-8");
					downloadm.setVisibility(View.VISIBLE);
				}
				if(eng_mean.equals("no_ENG")){
					String no_eng_html=getResources().getString(R.string.no_eng_html);
					webview.loadData(no_eng_html, "text/html", "UTF-8");
					downloadm.setVisibility(View.GONE);
				}
				if(!eng_mean.equals("no_ENG") && !eng_mean.equals("no_ENG_DICT")) {
					webview.loadDataWithBaseURL(null, eng_mean, "text/html", "UTF-8", null);
					//webview.loadData(eng_mean,"text/html; charset=utf-8", "utf-8");
					Log.v("html",eng_mean);
					downloadm.setVisibility(View.GONE);
				}
				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);

				userInput.setTextColor(getResources().getColor(R.color.black));
				datasource.open();



				//final String word=matna.getText().toString().substring( startasli,endasli);
			//	alertDialogBuilder.setMessage(matna.getText().toString().substring( startasli,endasli)+":");

				// set dialog message
				alertDialogBuilder
						.setCancelable(true)
						.setPositiveButton("تایید",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										// get user input and set it to result
										// edit text
										hideKeyboard();
										datasource.open();
										try {
											myDbHelper.openDataBase();

										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										String x = myDbHelper.getmatn(shd, table)._matn;


										if (matna.getSelectionEnd() != (-1)) {


											String y = x.substring(0, start) + "¦" + String.valueOf(myDbHelper.count() + 1) + "¦" + x.substring(start, end) + "¦" + x.substring(end, x.length());
											datasource.updateMatn(shd, y, table);

										}

										matna.setText(myDbHelper.getmatn(shd, table)._matn);

										datasource.open();

										try {
											spank(matna);
											datasource.open();

											datasource.createComment(x.substring(start, end), userInput.getText().toString(), "names");
											Toast.makeText(context, getResources().getString(R.string.meaning_updated), Toast.LENGTH_SHORT).show();

										} catch (Throwable e) {
											datasource.open();
											datasource.updateMatn(shd, x, table);
											int scr=matna.getSelectionStart();
											matna.setText(myDbHelper.getmatn(shd, table)._matn);

											spank(matna);
											matna.setSelection(scr);
											errordialog();
											e.printStackTrace();
										}

										myDbHelper.close();
										datasource.close();
									}

								})

				.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						// Do something...
						hideKeyboard();
					}
				})
						.setNegativeButton("انصراف",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										hideKeyboard();
										dialog.cancel();
										closeKeyboard(getActivity(), matna.getWindowToken());

									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}else{

				Toast.makeText(context, getResources().getString(R.string.select_word), Toast.LENGTH_SHORT).show();
			}
			myDbHelper.close();datasource.close();
		}else{
			changeblue(matna);
			//Toast.makeText(context, "do not select blue words", Toast.LENGTH_SHORT).show();
		}
	}
 	 public void add(int startasli,int endasli,String ma){
 		 try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		String thistext=myDbHelper.getmatn(shd,table)._matn;
		if(position(hamarz(thistext,startasli),thistext)==0){
		if(endasli!=startasli){
		final int start= hamarz(myDbHelper.getmatn(shd,table)._matn,startasli);
		final int end= hamarz(myDbHelper.getmatn(shd,table)._matn,endasli);
		// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.prompts2, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText userInput = (EditText) promptsView
				.findViewById(R.id.editTextDialogUserInput);
		userInput.setText(ma);
			userInput.setTextColor(getResources().getColor(R.color.black));
		datasource.open();
		
		 try {
 				myDbHelper.openDataBase();
 				
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
		
		final String word=matna.getText().toString().substring( startasli,endasli);
		alertDialogBuilder.setMessage(matna.getText().toString().substring( startasli,endasli)+":");
		myDbHelper.close();
		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("تایید",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				// get user input and set it to result
				// edit text
			    	
			    	datasource.open();
	        		 try {
		     				myDbHelper.openDataBase();
		     				
		     			} catch (SQLException e) {
		     				// TODO Auto-generated catch block
		     				e.printStackTrace();
		     			}
	        		 
	        		String x=myDbHelper.getmatn(shd,table)._matn;
	        		 
	        		
	        		
	        		if(matna.getSelectionEnd()!=(-1)){
	        			
	        			
	        		String y= x.substring(0, start) + "¦"+String.valueOf(myDbHelper.count()+1)+"¦" + x.substring( start,end)+"¦"+x.substring( end, x.length());
	        		datasource.updateMatn(shd, y,table);
	        		
	        		}
	        	
	matna.setText(myDbHelper.getmatn(shd,table)._matn);
	  		       
	        		datasource.open();
	     	       
	     	      try{
	     	    	 spank(matna);
	     	    	 datasource.open();
	     	    	 
	     	    	datasource.createComment(x.substring( start,end),userInput.getText().toString(),"names");
	     	    	Toast.makeText(context, getResources().getString(R.string.meaning_updated), Toast.LENGTH_SHORT).show();
	     	    	
	  			} catch (Throwable e) {
	  				datasource.open();
	  		datasource.updateMatn(shd,x,table);
	  		matna.setText(myDbHelper.getmatn(shd,table)._matn);
	  		spank(matna);
	  		errordialog();
	  				e.printStackTrace();
	  			}
	     	     hideKeyboard();
	     	     myDbHelper.close(); 
	     	    datasource.close();
			    }
			    
			  })
			 

			.setNegativeButton("انصراف",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
				hideKeyboard();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
				
			        	}else{
			        		
			        		Toast.makeText(context, getResources().getString(R.string.select_word), Toast.LENGTH_SHORT).show();
			        	}
			      myDbHelper.close();datasource.close();  	
		}else{
			changeblue(matna);
			//Toast.makeText(context, "do not select blue words", Toast.LENGTH_SHORT).show();
		}
 	 }//end add
 	 public void addfromdictionary(int startasli,int endasli){
 		try {
 			myDbHelper.openDataBase();
 			
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		   	 String text=myDbHelper.getmatn(shd,table)._matn;
 		   	 myDbHelper.close();
 		   	 
 		   
 		 if(aval!=akhar){	
 		   	if(startasli>=3){
 		   		if(startasli>=3&&startasli!=endasli&&(sharpnums(text,0,hamarz(text,aval))%3)==2&&findid(text,text.substring(0,hamarz(text,aval)).lastIndexOf("¦"))<=(-100)){
 		   		   	
 		   		 Toast.makeText(context, getResources().getString(R.string.no_link), Toast.LENGTH_SHORT).show();
 			   		//getActivity().openContextMenu(matna);
 			   	
 			   	}else if(startasli>=3&&startasli!=endasli&&(sharpnums(text,0,hamarz(text,aval))%3)==2&&findid(text,text.substring(0,hamarz(text,aval)).lastIndexOf("¦"))>0){
 			   	changeblue(matna);
 			   	}
 		   		else{
 			   	add(startasli,endasli,item.getText().toString().substring(endasli-startasli+2));	
 			   	}
	
 		   	}}}//end addfromdictionary
 	 public int sharpnums(String matnas,int avalin,int akharin){
 		 int result=0;
 		 for(int i=avalin;i<akharin;i++){
 			 if (matnas.charAt(i)=='¦'){
 				 result++;
 			 }
 		 }
 		 return result;
 	 }
 	 public void makesmall(){
 		 
 		
         scrol.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, height / 3));
 	 }
 	 public String htmldecode(String word){
 		 String result=word;
 		 String translate="<span class=head>translations</span>";
 		result=result.replaceAll("<li>","<br>");
 		result=result.replaceAll("<hr style='clear:both'>","<br>");
		try{ int start=result.indexOf("<span class=head>translations</span>");
			Log.v("start",String.valueOf(start));
		 int start2=result.substring(0,start).lastIndexOf("<silence>");
		 int end2=result.substring(start,result.length()).indexOf("</silence>")+start;
			Log.v("long",String.valueOf(start2)+" "+String.valueOf(end2));
		 result=result.replace(result.substring(start2,end2+8),"");}catch (Throwable e){

		}
	/*	 try {
		  DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		 Document doc = docBuilder.parse(result);

		 Element element = (Element) doc.getElementsByTagName("translations").item(0);
		 element.getParentNode().removeChild(element);
		 doc.normalize();
			 try{
			 result=toString(doc);}catch (Throwable e){

			 }
}
		 catch (ParserConfigurationException e)
		 {

		 }
		 catch (SAXException e) {
		 }
		 catch (IOException e)
		 {

		 }*/
Log.v("result", result);
		 return result;
 	 }
	private  String toString(Document newDoc) throws Exception{
		DOMSource domSource = new DOMSource(newDoc);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StringWriter sw = new StringWriter();
		StreamResult sr = new StreamResult(sw);
		transformer.transform(domSource, sr);
		System.out.println(sw.toString());
		return sw.toString();
	}
 	 public void lookfars(String word){
 		String mean;
 		try {
			myDbHelper.openDataBase();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		 if(myDbHelper.farsdict("hello").length()>1){
 			 mean=myDbHelper.farsdict(word);
 			 
 	 		 if(mean.length()>0){
 	 		((TextView) rootView.findViewById(R.id.textView2)).setText(word+": "+mean);
 	 		 }else{
 	 			Toast.makeText(context, "کلمه مورد نظر در دیکشنری موجود نیست", Toast.LENGTH_SHORT).show();
 	 		 }
 			 
 		 }else{
 			 
 			Toast.makeText(context, getResources().getString(R.string.no_dictionary), Toast.LENGTH_SHORT).show(); 
 		 }
 		myDbHelper.close();
 	 }//end lookfars
 	 public void download(){
 		
 		aval=matna.getSelectionStart();
	    	akhar=matna.getSelectionEnd();
Log.v("download","called");
	    	final int startasl=matna.getSelectionStart();
	   	final int endasl=matna.getSelectionEnd();
	   try {
		myDbHelper.openDataBase();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   	 String text=myDbHelper.getmatn(shd,table)._matn;
	   	 myDbHelper.close();
	   	 
	   
	 if(aval!=akhar){	
	   	if(startasl>=3){
	   		if(startasl>=3&&startasl!=endasl&&(sharpnums(text,0,hamarz(text,aval))%3)==2&&findid(text,text.substring(0,hamarz(text,aval)).lastIndexOf("¦"))<=(-100)){
	   		   	
	   		 Toast.makeText(context, getResources().getString(R.string.no_link), Toast.LENGTH_SHORT).show();
		   		//getActivity().openContextMenu(matna);
		   	
		   	}//end if
	   		else if(startasl>=3&&startasl!=endasl&&!(text.substring(hamarz(text,startasl)-3,hamarz(text,startasl)).equals("¦0¦"))){
	   	
	   		matna.showContextMenu();
	   		//getActivity().openContextMenu(matna);
	   	
	   	}//end if
	   	}//end main if
	   	if(startasl<3){
	   		getActivity().openContextMenu(matna);
	   	}
	 }else{
		 String error = getResources().getString(R.string.select_word);
		 Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
	 }
	 myDbHelper.close();
 	 }//end download
 	  private void speakOut() {
 		 
 	        String texthj = matna.getText().toString();
 	 
 	        tts.speak(texthj, TextToSpeech.QUEUE_FLUSH, null);
 	    }
 	 @Override
 	  public void onPause() {
 	     Log.e("DEBUG", "pause called");
 	     super.onPause();
 	  }
	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		 if (status == TextToSpeech.SUCCESS) {
			 
	            int result = tts.setLanguage(Locale.US);
	 
	            if (result == TextToSpeech.LANG_MISSING_DATA
	                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
	                Log.e("TTS", "This Language is not supported");
	            } else {
	               // btnSpeak.setEnabled(true);
	               // speakOut();
	            }
	 
	        } else {
	            Log.e("TTS", "Initilization Failed!");
	        }
	}
	  public static DemoObjectFragment newInstance(String item) {
		  DemoObjectFragment fragmentDemo = new DemoObjectFragment();
	        Bundle args = new Bundle();
	        args.putString("item", item);
	        
	        fragmentDemo.setArguments(args);
	        return fragmentDemo;
	    }
	  public void stoptts(){
		  if (tts != null) {
			  Log.v("not","null");
	            tts.stop();
	            tts.shutdown();
	        }
	  }
	@Override
	public void onPauseFragment() {
		// TODO Auto-generated method stub
		Log.v("selected",Integer.toString(matna.getSelectionStart()));
		stoptts();
		
	}
	@Override
	public void onResumeFragment() {
		// TODO Auto-generated method stub
		((MainActivity3B)getActivity()).getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		hideKeyboard();
	}
	public void speak_split(String textd){
		int ttlength=textd.length();
		int start_tts=0;
		int end_tts=0;
		for(int g=0;g<ttlength;g++){
			if(textd.charAt(g)=='\n'||g==ttlength-1){
				end_tts=g;
				Log.v("substring",textd.substring(start_tts, end_tts));
				((MainActivity3B)getActivity()).speakOut(textd.substring(start_tts, end_tts));
				start_tts=g+1;
			}
		}
	}
	public String noImage(){
		Milad aval=new Milad();
		int arz=0;
		int sum=0;
		  try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		String garbage=myDbHelper.getmatn(shd,table)._matn;
		long lastpagetable=myDbHelper.counttable("last_page");
		long idpage=1;
		long row=1;
		for(long m=1;m<=lastpagetable;m++){
			if(myDbHelper.getContact((int) m,"last_page")._1name.equals(table)){
				
				row=m;
				idpage=Long.valueOf(myDbHelper.getContact((int) m,"last_page")._2name);
				break;
			}
		}
		
	
		
int tool=myDbHelper.getmatn(shd,table)._matn.length();
		  for(int i=0;i<tool;++i){
		       	        	 if(garbage.charAt(i)=='¦'){
		       		 aval.sharp[sum][arz]=i;
		       		++arz;
		       		 }
		       	 if(arz==3){arz=0; ++sum;}
		       	  }
		        final SpannableStringBuilder text = new SpannableStringBuilder(myDbHelper.getmatn(shd,table)._matn);
		           for( int k=0;k<(sum);k++){
		   	   String sh="";
		   	   for(int t=(aval.sharp[k][0]+1);t<aval.sharp[k][1];t++)
		   			   {
		   			   sh=sh+(myDbHelper.getmatn(shd,table)._matn.charAt(t));
		   			   try{
		   			    aval.sharp[k][3]=Integer.parseInt(sh);}catch (Throwable e){
		   			    	aval.sharp[k][3]=-3;
		   			    }
		   			     }
	}
		           myDbHelper.close();
		           for(int v=(sum)-1;v>=0;v--){
			    		
			    	if(aval.sharp[v][3]==0){
			    		text.delete(aval.sharp[v][0],aval.sharp[v][2]+1);
			    	}else{
			    		text.delete(aval.sharp[v][2],aval.sharp[v][2]+1);
				    	text.delete(aval.sharp[v][0],aval.sharp[v][1]+1);
			    	}
			    }
		           
		           return text.toString();
}//end noimage
}
