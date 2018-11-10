package dictonary.mj.dastan;


import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.sql.SQLException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import dictonary.mj.dastan.dummy.DummyContent;

/**
 * A fragment representing a single Book detail screen. This fragment is either
 * contained in a {@link BookListActivity} in two-pane mode (on tablets) or a
 * {@link BookDetailActivity} on handsets.
 */
public class BookDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	
	public static final String ARG_ITEM_ID = "item_id";
	String status="milad";
	Context context;
	Insertword datasource;
	 DataBaseHelper myDbHelper;
	 ImageView image;
	 Button download,remove;
	 URL url;
	 long position;
	 int page=0;
//	 ProgressBar pbHorizontal;
	 Epubbooks epubb;
	 String imagepath;
	 Matns epub_status;
	 Typeface type;
	 public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	 String size;
	 Drawable test;
	 AlertDialog.Builder builder;
	 
	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;
private String isbn;
int id;
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public BookDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	/*	if (getArguments().containsKey(ARG_ITEM_ID)) {
			
			id=Integer.valueOf(getArguments().getString(ARG_ITEM_ID));
			
			
		}*/
		id = Integer.valueOf( getArguments().getString("item"));
		
	       
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_book_detail,
				container, false);
		image = (ImageView)rootView.findViewById(R.id.imageView1);
		Log.v("book detail","fragment loaded");
		 
		//pbHorizontal=(ProgressBar) rootView.findViewById(R.id.progressBar1);
		context=getActivity();  
		type=Typeface.createFromFile(IM_PATH+"Nazanin.ttf");
		myDbHelper = new DataBaseHelper(context);
		datasource=new Insertword(context);
		 builder = new AlertDialog.Builder(context);

	    builder.setTitle(getResources().getString(R.string.bookdelete));
	    builder.setMessage(getResources().getString(R.string.sure));

	    builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

	        public void onClick(DialogInterface dialog, int which) {
	            // Do nothing but close the dialog
	        	bookdelete(status);
	            dialog.dismiss();
	        }

	    });

	    builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            // Do nothing
	            dialog.dismiss();
	        }
	    });

	    
		 try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		long imagesize=myDbHelper.counttable("images");
		 epubb=myDbHelper.getimage(id , "epub_online");
		 epub_status=myDbHelper.getmatn(id,"epub_status");
		 status=epub_status.getMatn();
		
		 Log.v("status","status");
		for(long k=1;k<=imagesize;k++){
    		Epubbooks img=myDbHelper.getimage((int) k , "images");
    		if(img.getisbn().equals("cover")&&img.gettitle().equals(epubb.getisbn())){
    			size=img.getTOC();
    			imagepath=img.getcreater();
    			File file = new File(IM_PATH+img.getcreater());
    			if(file.exists()){      
    				
    				test=Drawable.createFromPath(IM_PATH+img.getcreater());
    		test=resize(test);
    		     image.setImageDrawable(test);
    		     
    		     Log.v("ba aks","ba aks");
    			}
    			break;
    		}
    	}
		scaleimage();
		int hajm=Integer.valueOf(size);
      ((TextView) rootView.findViewById(R.id.size)).setText(getResources().getString(R.string.size)+Integer.toString((int)(hajm/1024))+"kB");
      ((TextView) rootView.findViewById(R.id.size)).setTypeface(type);
		// Show the dummy content as text in a TextView.
		if (id!=0) {
			
			((TextView) rootView.findViewById(R.id.content)).setTypeface(type);
			((TextView) rootView.findViewById(R.id.content))
			.setText(epubb.getTOC());
			
			
			((TextView) rootView.findViewById(R.id.bookName))
			.setText(epubb.gettitle());
			((TextView) rootView.findViewById(R.id.bookauthor))
			.setText(epubb.getcreater());
		}
		download=(Button) rootView.findViewById(R.id.download);
		download.setTypeface(type);
		remove=(Button) rootView.findViewById(R.id.remove);
		remove.setTypeface(type);
		remove.setVisibility(View.GONE);
if(status.equals("download_ready")){
	download.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			/*	 try {
			url = new URL(epubb.getisbn());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/			 
			// new DownloadFileFromURL().execute(url.toString());
			/*  DownloadFragment taskFragment = new DownloadFragment();
		        
		        taskFragment.settitle(epubb.gettitle());
		        taskFragment.setid(id);
		        taskFragment.setsize(size);
		        taskFragment.setURL(epubb.getisbn());
		        mFM.beginTransaction().add(taskFragment,String.valueOf(id)).commit();*/
		        // And tell it to call onActivityResult() on this fragment.
		       

		        // Show the fragment.
		        // I'm not sure which of the following two lines is best to use but this one works well.
			
			Intent mIntent=new Intent(context,DownloadActivity.class);
    		mIntent.putExtra("title", epubb.gettitle());
    		mIntent.putExtra("id", id);
    		mIntent.putExtra("size",size);
    		mIntent.putExtra("url", epubb.getisbn());
    		mIntent.putExtra("path",imagepath);
    		startActivity(mIntent);
		}
	});
}//end if


if(status.equals("download_complete")){
	
	
	 download.setText(getResources().getString(R.string.install));
     
     download.setOnClickListener(new View.OnClickListener() {
 		public void onClick(View v) {
 			
 			Intent mIntent=new Intent(context,JpubInstaller.class);
 			  PackageManager m = context.getPackageManager();
		        String s = context.getPackageName();
		        try {
		            PackageInfo p = m.getPackageInfo(s, 0);
		            s = p.applicationInfo.dataDir;
		        } catch (NameNotFoundException e) {
		            Log.w("yourtag", "Error Package name not found ", e);
		        }
		        Log.v("data direction",s);
  		mIntent.putExtra("path", s+"/epubs/"+epubb.gettitle()+".jpub");
  		mIntent.putExtra("id", id);
  		startActivity(mIntent); 
 		}});
}
long tablesize=0;
try{tablesize=myDbHelper.counttable(status);}catch (Throwable f){tablesize=0;}


if(status.contains("table_")&&tablesize!=0){
	
	try{
		  long size= myDbHelper.counttable("last_page");
		  for(long j=1;j<=size;j++){
			  Log.v("tablename1",myDbHelper.getContact((int) j,"last_page")._1name);
			  if(myDbHelper.getContact((int) j,"last_page")._1name.equals(status)){
				  page=Integer.valueOf(myDbHelper.getContact((int) j,"last_page")._2name);
				  Log.v("found","first");
				  break;
			  }
		  }
	}catch (Throwable e){
		   
	}
	remove.setVisibility(View.VISIBLE);
	remove.setOnClickListener(new View.OnClickListener() {
 		public void onClick(View v) {
 			AlertDialog alert = builder.create();
 			alert.show();
 		}});
	 download.setText(getResources().getString(R.string.show_book));
	 download.setOnClickListener(new View.OnClickListener() {
	 		public void onClick(View v) {
	 			 Intent mIntent=new Intent(context,MainActivity3B.class);
	     		mIntent.putExtra("shomare_dars",page);
	     		mIntent.putExtra("table",status);
	     		
	     		startActivity(mIntent);
	 		}});
}
myDbHelper.close();
		return rootView;
	}
	 public void	bookdelete(String table){
			try{
				myDbHelper.openDataBase();
				long imagesize=myDbHelper.counttable("images");
				for(long f=1;f<=imagesize;f++){
					Epubbooks imgr=myDbHelper.getimage((int) f, "images");
					if(imgr.getisbn().equals(status)){
						File file = new File(IM_PATH+imgr.getcreater());
						boolean deleted = file.delete();
					}//end if
				}//end first for
				datasource.open();
				Log.v("tableD",table);
				datasource.deletetable(table);
				myDbHelper.openDataBase();
				long tablesize=myDbHelper.counttable("epub");
				for(long h=1;h<=tablesize;h++){
					Epubbooks epub=myDbHelper.getimage((int) h , "epub");
					if(epub.getisbn().equals(status)){
						
						position=h;
					}
				}
				datasource.deleterow("epub", position);
				
				datasource.updateMatn(id,"download_ready","epub_status");
				BookListActivity.fa.finish();
				MainActivity.fa.finish();
				Intent intentm2 = new Intent(context,MainActivity.class);
			    startActivity(intentm2);
				 Intent intentm = new Intent(context,BookListActivity.class);
				    startActivity(intentm);
					}catch (Throwable e){
				}
					myDbHelper.close();datasource.close();
				}
	/**
	 * Background Async Task to download file
	 * */
	 void scaleimage(){
		int iheight= test.getIntrinsicHeight();
		int iwidth=test.getIntrinsicWidth();
		Log.v("image size","width="+iwidth+" height="+iheight);
		
	 }
	 public boolean isInternetAvailable() {
	        try {
	            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name

	            if (ipAddr.equals("")) {
	                return false;
	            } else {
	                return true;
	            }

	        } catch (Exception e) {
	            return false;
	        }

	    }
	 private Drawable resize(Drawable imag) {
		    Bitmap b = ((BitmapDrawable)imag).getBitmap();
		    int iheight= imag.getIntrinsicHeight();
			int iwidth=imag.getIntrinsicWidth();
			int width,height;
		try{	 width=((BookListActivity)getActivity()).width;
		        height=((BookListActivity)getActivity()).height;}catch (Throwable f){
		    	    width=((BookDetailActivity)getActivity()).width;
			        height=((BookDetailActivity)getActivity()).height;
		       }
		int newwidth,newheight;
			if(width>=height){
			 newwidth=(int)(0.195*width);
			 newheight=(int)(newwidth*((double)((double)iheight/(double)iwidth)));
			}else{
				 newwidth=(int)(0.35*width);
				 newheight=(int)(newwidth*((double)((double)iheight/(double)iwidth)));
			}
			Log.v("newimage size","width="+newwidth+" height="+newheight);
		    Bitmap bitmapResized = Bitmap.createScaledBitmap(b, newwidth, newheight, false);
		    return new BitmapDrawable(getResources(), bitmapResized);
		}
		
	
	
	 private int tryGetFileSize(URL url) {
	        HttpURLConnection conn = null;
	        try {
	            conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("HEAD");
	            conn.getInputStream();
	            return conn.getContentLength();
	        } catch (IOException e) {
	            return -1;
	        } finally {
	            conn.disconnect();
	        }
	    }
	  public static BookDetailFragment newInstance(String item) {
	    	BookDetailFragment fragmentDemo = new BookDetailFragment();
	        Bundle args = new Bundle();
	        args.putString("item", item);
	        
	        fragmentDemo.setArguments(args);
	        return fragmentDemo;
	    }
	
	}

