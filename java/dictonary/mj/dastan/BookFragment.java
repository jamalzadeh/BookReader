package dictonary.mj.dastan;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BookFragment extends Fragment{
	View rootView;
	public static final String ARG_OBJECT = "object";
	Context context;
	 Insertword datasource;
	 DataBaseHelper myDbHelper;
	 Button show;
	 AlertDialog.Builder builder;
	 Button remove;
	 TextView textview;
	 String Title;
	 String Author;
	 ImageView image;
	 int position,page;
	 boolean coveri=false;
	 String name;
	 String author;
	 String tablename;
	 ArrayList<Epubbooks> bookq=new ArrayList<Epubbooks>();
	 public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	  @Override
	    public View onCreateView(LayoutInflater inflater,
	            ViewGroup container, Bundle savedInstanceState) {
	        // The last two arguments ensure LayoutParams are inflated
	        // properly.
	    	 
	         rootView = inflater.inflate(
	                R.layout.activity_showbook, container, false);
	         context=getActivity();
	         myDbHelper = new DataBaseHelper(context);
	         datasource=new Insertword(context);
	         image=(ImageView)rootView.findViewById(R.id.imageView1);
	 		textview=(TextView)rootView.findViewById(R.id.textView1);
	 		 Title= getResources().getString(R.string.title);
	 		 Author= getResources().getString(R.string.Author);
	 		 show=(Button)rootView.findViewById(R.id.show);
	 		 remove=(Button)rootView.findViewById(R.id.remove);
	 		  Bundle args = getArguments();
	 	       // shd=((MainActivity3B)getActivity()).shd;
	 	        
	 	        position=args.getInt(ARG_OBJECT);
	 	       
	  
      try {
			myDbHelper.openDataBase();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      Log.v("positon",Integer.toString(position));
      Epubbooks book=myDbHelper.getimage(position,"epub");
      tablename=book.getisbn();
      try{
    	  long size= myDbHelper.counttable("last_page");
    	  for(long j=1;j<=size;j++){
    		  Log.v("tablename1",myDbHelper.getContact((int) j,"last_page")._1name);
    		  if(myDbHelper.getContact((int) j,"last_page")._1name.equals(tablename)){
    			  page=Integer.valueOf(myDbHelper.getContact((int) j,"last_page")._2name);
    			  Log.v("found","first");
    			  break;
    		  }
    	  }
       }catch (Throwable e){
    	   
       }
      try{
      long imagesize=myDbHelper.counttable("images");
		for(long k=1;k<=imagesize;k++){
  		Epubbooks img=myDbHelper.getimage((int) k , "images");
  		if(img.getisbn().equals("cover")&&img.gettitle().equals(tablename)){
  			File file = new File(IM_PATH+img.getcreater());
  			if(file.exists()){ 
  			
  				Drawable test;
  				test=Drawable.createFromPath(IM_PATH+img.getcreater());
  				test=resize(test);
  				 image.setImageDrawable(test);
  				 coveri=true;
  				break;
  			}//end inner if
  			
  		}
		}//end for
		if(!coveri){
			Log.v("else","else");
			Drawable test;
		test=	getResources().getDrawable(R.drawable.epub2);
		test=resize(test);
		image.setImageDrawable(test);
		}
		}catch (Throwable e){
			
		}
      try{
			name=book.gettitle();
					author=book.getcreater();
					Log.v("title:",name);
					 textview.setText(Title+name+"\n"+Author+author);
			    textview.setTextSize(getResources().getDimension(R.dimen.textsize));
			}catch (Throwable e){
				
		}
      show.setOnClickListener(new OnClickListener(){
      	public void onClick(View arg0){
      		 if(page==0){
      			Intent mIntent=new Intent(context,MainActivity3B.class);
           		mIntent.putExtra("shomare_dars",1);
           		mIntent.putExtra("table",tablename);
           		
           		startActivity(mIntent);    
    	       }else{
    	       Intent mIntent=new Intent(context,MainActivity3B.class);
       		mIntent.putExtra("shomare_dars",page);
       		mIntent.putExtra("table",tablename);
       		
       		startActivity(mIntent); 
    	      }
      	}});
      remove.setOnClickListener(new OnClickListener(){
      	public void onClick(View arg0){
      		 AlertDialog alert = builder.create();
      		    alert.show();
      	}});
	 
	 builder = new AlertDialog.Builder(context);

	    builder.setTitle(getResources().getString(R.string.bookdelete));
	    builder.setMessage(getResources().getString(R.string.sure));

	    builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

	        public void onClick(DialogInterface dialog, int which) {
	            // Do nothing but close the dialog
	        	bookdelete(tablename);
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
	         return rootView;
	  }//end oncreate
	  public void	bookdelete(String table){
			
				try {
					myDbHelper.openDataBase();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				long imagesize=myDbHelper.counttable("images");
				for(long f=1;f<=imagesize;f++){
					Epubbooks imgr=myDbHelper.getimage((int) f, "images");
					if(imgr.getisbn().equals(tablename)){
						File file = new File(IM_PATH+imgr.getcreater());
						boolean deleted = file.delete();
					}//end if
				}//end first for
				datasource.open();
				datasource.deletetable(tablename);
				Log.v("last position",String.valueOf(position));
				updatetable(position);
			//	datasource.deleterow("epub", position);
				long status_size=myDbHelper.counttable("epub_status");
				Log.v("status_size",String.valueOf(status_size));
				for(long b=1;b<=status_size;b++){
					Matns state=myDbHelper.getmatn((int) b, "epub_status") ;
					if(state._matn.equals(table)){
						datasource.updateMatn(b,"download_ready","epub_status");
						break;
					}
				}
				MainActivity.fa.finish();
				getActivity().finish();
				 Intent intentm = new Intent(context,MainActivity.class);
				    startActivity(intentm);
					
					
				}
	  public void updatetable(int pos){
		  long size=myDbHelper.counttable("epub");
		 Log.v("update position",String.valueOf(position));
		  
		  for(long i=1;i<=size;i++){
			  Log.v("update i",String.valueOf(i));
			  
			if(i!=pos){
				Epubbooks bookd=myDbHelper.getimage((int) i,"epub"); 
				Log.v("update title",bookd.gettitle());
				Log.v("update creater",bookd.getcreater());
				Log.v("update isbn",bookd.getisbn());
				if(bookd!=null){
				bookq.add(bookd);
				Log.v("book","added");
				}
			}
		  }
		  datasource.deletetable("epub");
		  
		  datasource.createepubtable();
		  if(bookq!=null){
		  for(int i=0;i<bookq.size();i++){
			  Epubbooks a=bookq.get(i);
			  datasource.createepubbooks("epub",a.getisbn(),a.gettitle(),a.getcreater(),a.getcreater());
		  }
		  }
	  }
	  private Drawable resize(Drawable imag) {
		    Bitmap b = ((BitmapDrawable)imag).getBitmap();
		    int iheight= imag.getIntrinsicHeight();
			int iwidth=imag.getIntrinsicWidth();
			int width,height;
		try{	 width=((BookPager)getActivity()).width;
		        height=((BookPager)getActivity()).height;}catch (Throwable f){
		    	    width=((BookPager)getActivity()).width;
			        height=((BookPager)getActivity()).height;
		       }
		int newwidth,newheight;
			if(width>=height){
				 newheight=(int)(0.9*height);
				 newwidth=(int)(newheight*((double)((double)iwidth/(double)iheight)));
			}else{
				 newheight=(int)(0.4*height);
				 newwidth=(int)(newheight*((double)((double)iwidth/(double)iheight)));
			}
			Log.v("newimage size","width="+newwidth+" height="+newheight);
		    Bitmap bitmapResized = Bitmap.createScaledBitmap(b, newwidth, newheight, false);
		    return new BitmapDrawable(getResources(), bitmapResized);
		}
		

}
