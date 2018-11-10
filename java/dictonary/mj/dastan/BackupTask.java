package dictonary.mj.dastan;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities.EscapeMode;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class BackupTask extends Fragment {
	 
	   String finalstr = "";
	   DataBaseHelper myDbHelper;
	   private static String DB_PATH = "/data/data/dictonary.mj.dastan/databases/";
	   Long te;
	   Book book ;
	   String name;
	   public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	   Button show;
	   
	   long sho;
	   long tis;
	   Resource res;
	  ArrayList <String> filesab=new ArrayList<String>();
	   long imagesize;
	   long nunimages;
	   long j=0;
	   String hgs;
	   String names;
	  public String path;
	  public int epub_id;
	   String toc;
	   Long linknum;
	   String line, line1 = "";
	   Element masthead;
	   List<Resource> resourcelist;//image resources
	   String linktext;
	   Document doc;
	   Document tocxml=Jsoup.parse("");
	   org.w3c.dom.Document docx;
	   ArrayList<String> file = new ArrayList<String>();//esm file
	   ArrayList<Document> list2 = new ArrayList<Document>();//matn spinha
	   ArrayList<String> listB2=new ArrayList<String>();
	   String html;
	   TableOfContent tablencx=new TableOfContent();
	   Context context;
	   String pfile;
	    Insertword datasource;
	    EditDatabase datasource2;
int playorder=1;
//	 Epubrender epubrender=new Epubrender(this);
	 //   Epub epub= new Epub();
	    int i = 0;
	    String temp="";
	    private static final int PROGRESS = 0x1;
	    org.w3c.dom.Element rootElement;
	    
	     


/**
* Callback interface through which the fragment will report the
* task's progress and results back to the Activity.
*/
static interface TaskCallbacks {
 void onPreExecute();
 void onProgressUpdate(int percent);
 void onCancelled();
 void onPostExecute();
}

private TaskCallbacks mCallbacks;
private DummyTask mTask;

/**
* Hold a reference to the parent Activity so we can report the
* task's current progress and results. The Android framework 
* will pass us a reference to the newly created Activity after 
* each configuration change.
*/
@Override
public void onAttach(Activity activity) {
 super.onAttach(activity);
 mCallbacks = (TaskCallbacks) activity;
 try{myDbHelper.close();}catch (Throwable e){}
 myDbHelper=new DataBaseHelper(activity);
 try{myDbHelper.openDataBase();}catch (Throwable e){}


 

 
 context=activity;
}

/**
* This method will only be called once when the retained
* Fragment is first created.
*/
@Override
public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);

 // Retain this fragment across configuration changes.
 setRetainInstance(true);
 
 // Create and execute the background task.
 mTask = new DummyTask();
 mTask.execute();
}

/**
* Set the callback to null so we don't accidentally leak the 
* Activity instance.
*/
@Override
public void onDetach() {
 super.onDetach();
 mCallbacks = null;
}

/**
* A dummy task that performs some (dumb) background work and
* proxies progress updates and results back to the Activity.
*
* Note that we need to check if the callbacks are null in each
* method in case they are invoked after the Activity's and
* Fragment's onDestroy() method have been called.
*/
private class DummyTask extends AsyncTask<Void, Integer, Void> {

 @Override
 protected void onPreExecute() {
   if (mCallbacks != null) {
     mCallbacks.onPreExecute();
   }
 }

 /**
  * Note that we do NOT call the callback object's methods
  * directly from the background thread, as this could result 
  * in a race condition.
  */
 @Override
 protected Void doInBackground(Void... ignore) {
	 copyFile(DB_PATH,"datab1",Environment.getExternalStorageDirectory() + "/jpub2/");
 long epubnumbers=myDbHelper.counttable("epub");
 try{
		myDbHelper.close();
	}catch (Throwable e){}
 	for(long i=1;i<=epubnumbers;i++){
 		try{
	 			myDbHelper.openDataBase();
	 		}catch (Throwable e){}
 		Epubbooks book=myDbHelper.getimage((int)i,"epub");
 		try{
 			myDbHelper.close();
 		}catch (Throwable e){}
 		String bookname=book.getisbn();
 		DynamicDatabase dhelper=new DynamicDatabase(context);
 		 try {
				dhelper.createDataBase();
				Log.v("created","created");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.v("not","created");
				e.printStackTrace();
			}
 		File folder = new File(Environment.getExternalStorageDirectory() + "/jpub/"+bookname);
 		boolean success = true;
 		if (!folder.exists()) {
 		    success = folder.mkdir();
 		}
 		//copyFile(DB_PATH,"jpub.db",Environment.getExternalStorageDirectory() + "/jpub/"+bookname+"/");
 	//	datasource2.setPath(Environment.getExternalStorageDirectory() + "/jpub/"+bookname+"/");
 		Log.v("path",Environment.getExternalStorageDirectory() + "/jpub/"+bookname+"/");
 		datasource2=new EditDatabase(context);
 		
 		
 			//Log.v("datasource path",datasource2.getPath());
 			
 			try{
 	 			myDbHelper.openDataBase();
 	 		}catch (Throwable e){}
 		 long linknum=myDbHelper.counttable("matn_link");
 		
 		try{
 			myDbHelper.close();
	 		}catch (Throwable e){}
 		 for(long f=1;f<=linknum;f++){
 			try{
 	 			myDbHelper.openDataBase();
 	 		}catch (Throwable e){}
 			Epubbooks link= myDbHelper.getimage((int)f,"matn_link");
 			
 			try{
 	 			myDbHelper.close();
 		 		}catch (Throwable e){}
 			if(link.getcreater().equals(bookname)){
 			//datasource2.createComment(link._1name, link._2name,"matn_link");
 			try{
 	 			 datasource2.open();
 	 			 }catch (Throwable e){
 	 				  Log.v("datasource","open failed");
 	 			 }
 			//datasource2.creatematn(link._matn, "matn_link");
 			
 			datasource2.createepubbooks("matn_link",link.getisbn(), link.gettitle(),link.getcreater(),"");
 			try{
 	 			datasource2.close();
 	 		}catch (Throwable e){}
 		 }//end if
 		 }//end for
 		 
 		
 		 
 		try{
 			 datasource2.open();
 			 }catch (Throwable e){
 				  Log.v("datasource","open failed");
 			 }
 		Log.v("datasource path",context.getDatabasePath("jpub.db").toString());
 		datasource2.createepubbooks("epub",book.getisbn(),book.gettitle(),book.getcreater(),book.getTOC());
 		try{
	 			datasource2.close();
	 		}catch (Throwable e){}
 		Log.v("book created",bookname);
 		try{
	 			myDbHelper.openDataBase();
	 		}catch (Throwable e){}
 		long textnumbers=myDbHelper.counttable(bookname);
 		try{
 			myDbHelper.close();
	 		}catch (Throwable e){}
 		for(long j=1;j<=textnumbers;j++){
 			try{
	 			myDbHelper.openDataBase();
	 		}catch (Throwable e){}
 			names text=myDbHelper.getEpubtext((int)j,bookname);
 			try{
	 			myDbHelper.close();
	 		}catch (Throwable e){}
 			try{
 	 			 datasource2.open();
 	 			 }catch (Throwable e){
 	 				  Log.v("datasource","open failed");
 	 			 }
 			datasource2.createEpubtext("content",text.get1Name(),text.getget2Name());
 			try{
	 			datasource2.close();
	 		}catch (Throwable e){}
 			
 		}
 		try{
 			myDbHelper.openDataBase();
 		}catch (Throwable e){}
 		long imagenumbers=myDbHelper.counttable("images");
 		try{
 			myDbHelper.close();
 		}catch (Throwable e){}
 		for(long k=1;k<=imagenumbers;k++){
 			try{
 	 			myDbHelper.openDataBase();
 	 		}catch (Throwable e){}
 			Epubbooks image=myDbHelper.getimage((int)k,"images");
 			try{
 	 			myDbHelper.close();
 	 		}catch (Throwable e){}
 			if(image.gettitle().equals(bookname)){
 				try{
 	 	 			 datasource2.open();
 	 	 			 }catch (Throwable e){
 	 	 				  Log.v("datasource","open failed");
 	 	 			 }
 				datasource2.createepubbooks("images",image.getisbn(),image.gettitle(),image.getcreater(),image.getTOC());
 				try{
 		 			datasource2.close();
 		 		}catch (Throwable e){}
 				copyFile(IM_PATH,image.getcreater(),Environment.getExternalStorageDirectory() + "/jpub/"+bookname+"/");
 				
 			}
 			
 		}
 		publishProgress(doWork(i,epubnumbers));
 		try{
	 			datasource2.close();
	 		}catch (Throwable e){}
 		copyFile(DB_PATH,"jpub.db",Environment.getExternalStorageDirectory() + "/jpub/"+bookname+"/");
 		 File sourceFile = new File(Environment.getExternalStorageDirectory() + "/jpub/"+bookname+"/");
 		String files[]=sourceFile.list();
 		 int length=files.length;
 		
 		for(int a=0;a<length;a++){
 			filesab.add(Environment.getExternalStorageDirectory() + "/jpub/"+bookname+"/"+files[a]);
 		}
 		 
 		
 		 
 		 
 		 zip(filesab,Environment.getExternalStorageDirectory() + "/jpub/"+bookname+".zip");
 		//zipFileAtPath( Environment.getExternalStorageDirectory() + "/jpub/"+bookname, Environment.getExternalStorageDirectory() + "/jpub/"+bookname+".zip");
 		/*AppZip zipper=new AppZip();
 		zipper.Setpath(Environment.getExternalStorageDirectory() + "/jpub/"+bookname+".zip", Environment.getExternalStorageDirectory() + "/jpub/"+bookname);
 		zipper.ZipFolder();*/
 		
 	}
		  
 		
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
public int doWork(Long j,Long te){
	int javab=(int) ((float)j/te*100);
	return javab;
}
public void zip(ArrayList<String> _files, String zipFileName) {
	int BUFFER=1024;
	try {
		BufferedInputStream origin = null;
		FileOutputStream dest = new FileOutputStream(zipFileName);
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
				dest));
		byte data[] = new byte[BUFFER];

		for (int i = 0; i < _files.size(); i++) {
			Log.v("Compress", "Adding: " + _files.get(i));
			FileInputStream fi = new FileInputStream(_files.get(i));
			origin = new BufferedInputStream(fi, BUFFER);

			ZipEntry entry = new ZipEntry(_files.get(i).substring(_files.get(i).lastIndexOf("/")));
			out.putNextEntry(entry);
			int count;

			while ((count = origin.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			origin.close();
		}

		out.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
public boolean zipFileAtPath(String sourcePath, String toLocation) {
    // ArrayList<String> contentList = new ArrayList<String>();
    final int BUFFER = 2048;


    File sourceFile = new File(sourcePath);
    try {
        BufferedInputStream origin = null;
        FileOutputStream dest = new FileOutputStream(toLocation);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                dest));
        if (sourceFile.isDirectory()) {
            zipSubFolder(out, sourceFile, sourceFile.getParent().length());
        } else {
            byte data[] = new byte[BUFFER];
            FileInputStream fi = new FileInputStream(sourcePath);
            origin = new BufferedInputStream(fi, BUFFER);
            ZipEntry entry = new ZipEntry(getLastPathComponent(sourcePath));

            out.putNextEntry(entry);
            int count;
            while ((count = origin.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
        }
        out.close();
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
    return true;
}

/*
 * 
 * Zips a subfolder
 * 
 */

private void zipSubFolder(ZipOutputStream out, File folder,
        int basePathLength) throws IOException {

    final int BUFFER = 2048;

    File[] fileList = folder.listFiles();
    BufferedInputStream origin = null;
    for (File file : fileList) {
        if (file.isDirectory()) {
            zipSubFolder(out, file, basePathLength);
        } else {
            byte data[] = new byte[BUFFER];
            String unmodifiedFilePath = file.getPath();
            String relativePath = unmodifiedFilePath
                    .substring(basePathLength);
            Log.i("ZIP SUBFOLDER", "Relative Path : " + relativePath);
            FileInputStream fi = new FileInputStream(unmodifiedFilePath);
            origin = new BufferedInputStream(fi, BUFFER);
            ZipEntry entry = new ZipEntry(relativePath);
            out.putNextEntry(entry);
            int count;
            while ((count = origin.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            origin.close();
        }
    }
}

/*
 * gets the last path component
 * 
 * Example: getLastPathComponent("downloads/example/fileToZip");
 * Result: "fileToZip"
 */
public String getLastPathComponent(String filePath) {
    String[] segments = filePath.split("/");
    String lastPathComponent = segments[segments.length - 1];
    return lastPathComponent;
}

		  }
