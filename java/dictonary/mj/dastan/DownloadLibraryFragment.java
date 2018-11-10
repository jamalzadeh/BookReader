package dictonary.mj.dastan;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import dictonary.mj.dastan.DownloadLibraryFragment.DummyTask;
import dictonary.mj.dastan.DownloadFragment.TaskCallbacks;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class DownloadLibraryFragment  extends Fragment{
	Context context;
	String s;
	URL url;
	String size;
	String title;
	int id;
	int dastansize;
	String status;
	 Insertword datasource;
	  private DummyTask mTask;
	 public void cancell(){
		 mTask.cancel(true);
	 }
	 public void setid(int hj){
		 this.id=hj;
	 }
	public void settitle(String ht){
		this.title=ht;
	}
	public void setsize(String fe){
		this.size=fe;
	}
	public void setURL(String path){
		try {
			this.url=new URL(path);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void stopdownload(){
		mTask.cancel(true);
		
	}
	 static interface TaskCallbacks {
		    void onPreExecute();
		    void onProgressUpdate(int percent);
		    void onCancelled();
		    void onPostExecute();
		  }
	 private TaskCallbacks mCallbacks;
	
	  @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    mCallbacks = (TaskCallbacks) activity;
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
	    context=getActivity();
	    // Create and execute the background task.
	    mTask = new DummyTask();
	    
	    mTask.execute();
	  }
	 
	  public class DummyTask extends AsyncTask<Void, Integer, Void> {

		   
		  DownloadFragment mFragment;
		    /**
		     * Note that we do NOT call the callback object's methods
		     * directly from the background thread, as this could result 
		     * in a race condition.
		     */
		    @Override
		    protected Void doInBackground(Void... ignore) {
		    	int count;long total=0;
		    	 
		    	 
		        try {
		        /*	 HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
					    urlConnection.setRequestMethod("GET");
					    urlConnection.setConnectTimeout(30000);
					    urlConnection.setReadTimeout(30000);
					    urlConnection.setInstanceFollowRedirects(true);
					    urlConnection.connect();*/
		        	
		        		Log.v("url",url.toString());
		        		HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
		        		try{
		          
		            ucon.setInstanceFollowRedirects(true); 
		          //  URL secondURL = new URL(ucon.getHeaderField("Location")); 
		            
		          //  Log.v("second url",secondURL.toString());
		          //  URLConnection conection = secondURL.openConnection();
		           
		            ucon.setRequestMethod("GET");
		            ucon.setConnectTimeout(30000);
		            ucon.setReadTimeout(30000);
	          
		            ucon.connect();
		            dastansize= ucon.getContentLength();
		           Log.v("dastan size",String.valueOf(dastansize));
		            // getting file length
		        		}catch (IOException e) {
		        			System.out.println("I/O Error: " + e.getMessage());
		        		}
		
		            		
		           
		            // input stream to read file - with 8k buffer
		           InputStream input = ucon.getInputStream();
					//    InputStream input =   urlConnection.getInputStream();
		            // Output stream to write file
		           PackageManager m = context.getPackageManager();
			         s = context.getPackageName();
			        try {
			            PackageInfo p = m.getPackageInfo(s, 0);
			            s = p.applicationInfo.dataDir;
			        } catch (NameNotFoundException e) {
			            Log.w("yourtag", "Error Package name not found ", e);
			        }
			        Log.v("data direction",s);
		            OutputStream output = new FileOutputStream(s+"/images/"+"libplugpdf"+".zip");
		           
		            byte data[] = new byte[1024];
		 
		            total=0;
		 
		            while ((count = input.read(data)) != -1) {
		                total += count;
		                // publishing the progress....
		                // After this onProgressUpdate will be called
		                publishProgress((int)((total*100)/dastansize));
		 
		                // writing data to file
		                output.write(data, 0, count);
		            }
		 
		            // flushing output
		            output.flush();
		 
		            // closing streams
		            output.close();
		            input.close();
		            Log.v("total",String.valueOf(total));
		            File directory = new File(s + "/"+title);
			        directory.mkdirs();
		            unpackZip(s+"/images/","libplugpdf.zip");
		            if(total==dastansize){
		       		 status="download_complete";}else{total=0;Log.v("url","replaced");}
		        	
		        	
		        } catch (Throwable e) {
		            Log.e("Error: ","download failed");
		            status="download_ready";
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

	  }//end dummy
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

		              FileOutputStream fout = new FileOutputStream(s+"/"+title+"/"+"libplugpdf.so");

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
