package dictonary.mj.dastan;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;







import java.io.StringWriter;





import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;





import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Browser extends Fragment {
	private final int REQUEST_CODE_PICK_DIR = 1;
	private final int REQUEST_CODE_PICK_FILE = 2;
	TextView Text;
	ImageView image;
	String Title;
	String Author;
	 private static String IM_PATH = "/data/data/dictonary.mj.dastan/images/";
	Typeface type;
	String firstpage="";
	String newFile;
	Button install;
	 Context context;
	 Activity activityForButton;
	 public void setpath(String path){
		 this.newFile=path;
	 }
	 public String getpath(){
		 return newFile;
	 }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_browser);
		  View rootView = inflater.inflate(R.layout.activity_browser, container, false);
		  context=getActivity();
		  type=Typeface.createFromFile(IM_PATH+"Nazanin.ttf");
	        
		
	
		image = (ImageView)rootView.findViewById(R.id.imageView1);
		 Text=(TextView) rootView.findViewById(R.id.textView1);
		 Text.setTypeface(type);
		 Title= getResources().getString(R.string.title);
		 Author= getResources().getString(R.string.Author);
		 install=(Button) rootView.findViewById(R.id.install);
		 install.setTypeface(type);
		 install.setEnabled(false);
        final Button start4FileHideNonReadButton = 
        		(Button)rootView.findViewById(R.id.startBrowse4FileHideNonReadButtonID);
        install.setOnClickListener(new OnClickListener(){
        	public void onClick(View arg0){
        		//Intent mIntent=new Intent(context,Epubsecond.class);
        		//Intent mIntent=new Intent(context,JpubInstaller.class);
        		if(newFile.substring(newFile.lastIndexOf('.') + 1).equals("pdf")){
        		Intent mIntent=new Intent(context,PdfReader.class);
        		mIntent.putExtra("path", newFile);
        		startActivity(mIntent); 
        		}
        		if(newFile.substring(newFile.lastIndexOf('.') + 1).equals("epub")){
        			Intent mIntent=new Intent(context,Epubsecond.class);
            		mIntent.putExtra("path", newFile);
            		startActivity(mIntent); 
            		}
        		if(newFile.substring(newFile.lastIndexOf('.') + 1).equals("jpub")){
        			Intent mIntent=new Intent(context,JpubInstaller.class);
            		mIntent.putExtra("path", newFile);
            		startActivity(mIntent); 
            		}
        	}});
        start4FileHideNonReadButton.setTypeface(type);
        start4FileHideNonReadButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			Log.d("log", "StartFileBrowser4File button pressed");
    			Intent fileExploreIntent = new Intent(
    				FileBrowserActivity.INTENT_ACTION_SELECT_FILE,
        				null,
        				context,
        				FileBrowserActivity.class
        				);
    			fileExploreIntent.putExtra(
    					FileBrowserActivity.showCannotReadParameter, 
    					false);
    			if(firstpage.equals("")){
    				
    			}else{
    				Log.v("else","else");
    				fileExploreIntent.putExtra(
        					FileBrowserActivity.startDirectoryParameter, 
        					firstpage);
    			}
        		startActivityForResult(
        				fileExploreIntent,
        				REQUEST_CODE_PICK_FILE
        				);
    		}//public void onClick(View v) {
        });
        return rootView;
	}
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browser, menu);
		return true;
	} */
	 @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    activityForButton=activity;
	 }
	 @Override
	 public void onSaveInstanceState(Bundle savedInstanceState) {
	   super.onSaveInstanceState(savedInstanceState);
	   // Save UI state changes to the savedInstanceState.
	   // This bundle will be passed to onCreate if the process is
	   // killed and restarted.
	   
	   savedInstanceState.putString("MyString", newFile);
	   // etc.
	 }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_PICK_DIR) {
        	if(resultCode == activityForButton.RESULT_OK) {
        		String newDir = data.getStringExtra(
        				FileBrowserActivity.returnDirectoryParameter);
        		
        		Toast.makeText(
        				activityForButton, 
        				"Received DIRECTORY path from file browser:\n"+newDir, 
        				Toast.LENGTH_LONG).show(); 
	        	
        	} else {//if(resultCode == this.RESULT_OK) {
        		Toast.makeText(
        				activityForButton, 
        				 getResources().getString(R.string.no_book_selected),
        				Toast.LENGTH_LONG).show(); 
        	}//END } else {//if(resultCode == this.RESULT_OK) {
        }//if (requestCode == REQUEST_CODE_PICK_DIR) {
		
		if (requestCode == REQUEST_CODE_PICK_FILE) {
        	if(resultCode == activityForButton.RESULT_OK) {
        		 newFile = data.getStringExtra(
        				FileBrowserActivity.returnFileParameter);
        		firstpage=newFile.substring(0,newFile.lastIndexOf("/"));
        	Log.v("path",newFile);
        		try{
        			InputStream is = new FileInputStream(newFile);
        	   	 Book book = (new EpubReader()).readEpub(is);
        	   	Drawable test;
			    test = Drawable.createFromStream(book.getCoverImage().getInputStream(),"test");
			    image.setImageDrawable(test);
			   
			    
        			}catch (Throwable e){
        				
        	      	image.setImageDrawable(getResources().getDrawable(R.drawable.epub2));
        	        }
        		try{
        			InputStream is = new FileInputStream(newFile);
           	   	 Book book = (new EpubReader()).readEpub(is);
           	   	
        			 Text.setText(Title+book.getTitle()+"\n"+Author+book.getMetadata().getAuthors());
     			    Text.setTextSize(getResources().getDimension(R.dimen.textsize));
        		}catch (Throwable e){
      	      	  
      	        }
        		
        		install.setEnabled(true);
        	} else {//if(resultCode == this.RESULT_OK) {
        		Toast.makeText(
        				context, 
        				getResources().getString(R.string.no_book_selected),
        				Toast.LENGTH_LONG).show(); 
        		
        	}//END } else {//if(resultCode == this.RESULT_OK) {
        }//if (requestCode == REQUEST_CODE_PICK_FILE) {
		
		
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	public static String toString(Document doc) {
	    try {
	        StringWriter sw = new StringWriter();
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

	        transformer.transform(new DOMSource(doc), new StreamResult(sw));
	        return sw.toString();
	    } catch (Exception ex) {
	        throw new RuntimeException("Error converting to String", ex);
	    }
	}

}
