package dictonary.mj.dastan;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;


public class JpubTask extends Fragment {
	  public static final String KEY_ID = "_id";
	   public static final String FILE="file";
	    public static final String KEY_NAME = "first_name";
	    public static final String MATN = "mohtava";
	    public static final String KEY_PH_NO = "last_name";
	    public static final String ISBN = "isbn";
	    public static final String TITLE = "title";
	    public static final String CREATER = "creater";
	    public static final String CHAPTER = "chapter";
	    public static final String CONTENT = "content";
	    public static final String TOC="toc";
	    public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	Context context;
	DataBaseHelper myDbHelper;
	Insertword datasource;
	String path;
	SQLiteDatabase  db;
	 private TaskCallbacks mCallbacks;
	  private DummyTask mTask;
	static interface TaskCallbacks {
	    void onPreExecute();
	    void onProgressUpdate(int percent);
	    void onCancelled();
	    void onPostExecute();
	  }
	public void setpath(String re) {
	    this.path=re;
	  }
	  @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    mCallbacks = (TaskCallbacks) activity;
	    try{myDbHelper.close();}catch (Throwable e){}
	   
	    myDbHelper=new DataBaseHelper(activity);
	   
	    try{myDbHelper.openDataBase();}catch (Throwable e){}
	    
	   try{
		   datasource.close();
	   }catch (Throwable e){
	 	  
	   }
	    datasource=new Insertword(activity);
	    try{
	    datasource.open();
	    }catch (Throwable e){
	  	  
	    }
	    context=activity;
	  }
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // Retain this fragment across configuration changes.
	    setRetainInstance(true);
	    
	    // Create and execute the background task.
	    mTask = new DummyTask();
	    mTask.execute();
	  }
	 @Override
	  public void onDetach() {
	    super.onDetach();
	    mCallbacks = null;
	  }
	 public Epubbooks getimage(int id,String tablename){
			
			 Cursor cursor = db.query(tablename, new String[] { KEY_ID,
			            ISBN,TITLE,CREATER,TOC }, KEY_ID + "=?",
			            new String[] { String.valueOf(id) }, null, null, null, null);
	         
		    if (cursor != null)
		        cursor.moveToFirst();

		    Epubbooks image = new Epubbooks(Integer.parseInt(cursor.getString(0)),
		            cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
		    cursor.close();
		    return image;
		}
	 public long counttable(String table){
		
		 
		    return DatabaseUtils.queryNumEntries(db,table);
	}
	 public names getEpubtext(int id,String tablename) {
		    
		 
		    Cursor cursor = db.query(tablename, new String[] { KEY_ID,
		    		MATN, FILE}, KEY_ID + "=?",
		            new String[] { String.valueOf(id) }, null, null, null, null);
		    if (cursor != null)
		        cursor.moveToFirst();
		 
		    names contact = new names(Integer.parseInt(cursor.getString(0)),
		            cursor.getString(1), cursor.getString(2));
		    cursor.close();
		    // return contact
		    return contact;
		}
	 private void copyFile(String inputPath, String inputFile, String outputPath) {

		    InputStream in = null;
		    OutputStream out = null;
		    try {

		        //create output directory if it doesn't exist
		        File dir = new File (outputPath); 
		        if (!dir.exists())
		        {
		            dir.mkdirs();
		        }


		        in = new FileInputStream(inputPath + inputFile);        
		        out = new FileOutputStream(outputPath + inputFile);

		        byte[] buffer = new byte[1024];
		        int read;
		        while ((read = in.read(buffer)) != -1) {
		            out.write(buffer, 0, read);
		        }
		        in.close();
		        in = null;

		            // write the output file (You have now copied the file)
		            out.flush();
		        out.close();
		        out = null;        

		    }  catch (FileNotFoundException fnfe1) {
		        Log.e("tag", fnfe1.getMessage());
		    }
		            catch (Exception e) {
		        Log.e("tag", e.getMessage());
		    }

		}
	 private class DummyTask extends AsyncTask<Void, Integer, Void> {

		    @Override
		    protected void onPreExecute() {
		      if (mCallbacks != null) {
		        mCallbacks.onPreExecute();
		      }
		    }
		    @Override
		    protected Void doInBackground(Void... ignore) {
		    	String folder_path=path.substring(0,path.lastIndexOf("/")+1);
		    	Log.v("folder_path",folder_path);
		    	String file_name=path.substring(path.lastIndexOf("/")+1,path.length());
		    	Log.v("file_name",file_name);
		    	File folder = new File(folder_path+file_name.substring(0,file_name.lastIndexOf(".")));
		 		boolean success = true;
		 		if (!folder.exists()) {
		 		    success = folder.mkdir();
		 		}
		    	unpackZip(folder_path,file_name);
		    	
/*  try {
					dhelper.openDataBase();
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}*/
  File dbfile = new File(folder_path+file_name.substring(0, file_name.lastIndexOf("."))+"/"+"jpub.db" ); 
   db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
Epubbooks epub=getimage(1,"epub");
long last=myDbHelper.counttable("epub");


String newname;

	
	
//newname="table_"+String.valueOf(Integer.valueOf(lastbook.getisbn().replace("table_",""))+1);
	newname=getimage(1,"epub").getisbn();
datasource.open();
datasource.createtable(newname); 

try{ datasource.createepubbooks("epub",newname, epub.gettitle(), epub.getcreater(),epub.getTOC());}catch (Throwable e){
	  datasource.open();datasource.createepubbooks("epub",epub.getisbn(), epub.gettitle(), epub.getcreater(),epub.getTOC());
}
long textnum=counttable("content");
Log.v("newname",newname);
try{
	datasource.createComment(newname,"1","last_page");
}catch(Throwable e){
	datasource.open();
	datasource.createComment(newname,"1","last_page");
}

for(long f=1;f<=textnum;f++){
	names text=getEpubtext((int)f,"content");
	datasource.createhrefs(text.get1Name(),text.getget2Name(),newname);
}
Log.v("href","finished");
 long linknum=counttable("matn_link");
 for(long g=1;g<=linknum;g++){
	 Epubbooks link= getimage((int)g,"matn_link");
			 datasource.createepubbooks("matn_link",link.getisbn(), link.gettitle(),newname,"");
 }
 Log.v("linknum",String.valueOf(linknum));
 long imagenum=counttable("images");
 for(long h=1;h<=imagenum;h++){
	 Epubbooks image=getimage((int)h,"images");
	 datasource.createepubbooks("images",image.getisbn(),newname,image.getcreater(),image.getTOC());
	 copyFile(folder_path+file_name.substring(0, file_name.lastIndexOf("."))+"/",image.getcreater(),IM_PATH);
	 Log.v("image path",folder_path+file_name.substring(0, file_name.lastIndexOf("."))+"/"+image.getcreater());
 }
 Log.v("imagenum","finished");
 File dir = new File(folder_path+file_name); 
 if (dir.isDirectory()) {
         String[] children = dir.list();
         for (int i = 0; i < children.length; i++) {
             new File(dir, children[i]).delete();
         }
     }
 Intent mIntent=new Intent(context,MainActivity3B.class);
	mIntent.putExtra("shomare_dars",1);
	mIntent.putExtra("table",newname);
	
	startActivity(mIntent);
 
		    	return null;
		    }
		    
		    @Override
		    protected void onProgressUpdate(Integer... percent) {
		      if (mCallbacks != null) {
		        mCallbacks.onProgressUpdate(percent[0]);
		      }
		    // mCallbacks.onProgressUpdate(doWork(j,te));
		    }

		    @Override
		    protected void onCancelled() {
		      if (mCallbacks != null) {
		        mCallbacks.onCancelled();
		      }
		    }
		    @Override
		    protected void onPostExecute(Void ignore) {
		      if (mCallbacks != null) {
		        mCallbacks.onPostExecute();
		      }
		     
		    }
		    
}
	 private boolean unpackZip(String path, String zipname)
	 {       
	      InputStream is;
	      ZipInputStream zis;
	      try 
	      {
	          String filename;
	          is = new FileInputStream(path + zipname);
	          zis = new ZipInputStream(new BufferedInputStream(is));          
	          ZipEntry ze;
	          byte[] buffer = new byte[1024];
	          int count;

	          while ((ze = zis.getNextEntry()) != null) 
	          {
	              // zapis do souboru
	              filename = ze.getName();

	              // Need to create directories if not exists, or
	              // it will generate an Exception...
	              if (ze.isDirectory()) {
	                 File fmd = new File(path + filename);
	                 fmd.mkdirs();
	                 continue;
	              }

	              FileOutputStream fout = new FileOutputStream(path +zipname.substring(0, zipname.lastIndexOf("."))+"/"+ filename);

	              // cteni zipu a zapis
	              while ((count = zis.read(buffer)) != -1) 
	              {
	                  fout.write(buffer, 0, count);             
	              }

	              fout.close();               
	              zis.closeEntry();
	          }

	          zis.close();
	      } 
	      catch(IOException e)
	      {
	          e.printStackTrace();
	          return false;
	      }

	     return true;
	 }
}
