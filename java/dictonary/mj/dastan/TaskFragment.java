package dictonary.mj.dastan;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities.EscapeMode;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * This Fragment manages a single background task and retains 
 * itself across configuration changes.
 */
public class TaskFragment extends Fragment {
	 private String TAG = "TagOpenTxt";
	    private String uri ="";
	    private Uri uri2;
	   String finalstr = "";
	   DataBaseHelper myDbHelper;
	   Long te;
	   Book book ;
	   String name;
	   public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
	   Button show;
	   long sho;
	   long tis;
	   Resource res;
	   private final static int LIST_CHAPTER_ACTIVITY_ID = 1; 
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

	   org.w3c.dom.Document docx;
	   ArrayList<String> file = new ArrayList<String>();//esm file
	   ArrayList<Document> list2 = new ArrayList<Document>();//matn spinha
	   ArrayList<String> listB2=new ArrayList<String>();
	   String html;
	   TableOfContent tablencx=new TableOfContent();
	   Context context;
	   String pfile;
	    Insertword datasource;
int playorder=1;
// 	 Epubrender epubrender=new Epubrender(this);
	 //   Epub epub= new Epub();
	    int i = 0;
	    String temp="";
	    private static final int PROGRESS = 0x1;
	    org.w3c.dom.Element rootElement;
	     private ProgressBar mProgress;
	     private int mProgressStatus = 0;
	     
public void setpath(String re) {
    this.path=re;
  }
public void setId(int id){
	this.epub_id=id;
}

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
    
    	 DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		   try{
			 
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			 docx = docBuilder.newDocument();
			 rootElement = docx.createElement("ncx");
			docx.appendChild(rootElement);}catch (Throwable e){
		      	  
		        }
		   try{
    	ReadPath(path);
    	Spine contents = book.getSpine();
         Resources rs = book.getResources();
         //resourcelist images
         ListImages(rs);
         Log.v("before","list spins");
     ListSpins(contents);
     Log.v("after","list spins");
		   } catch (Throwable h){}
     
 te=(long)(list2.size());
 
 if(te!=0){
 logTableOfContents(book.getTableOfContents().getTocReferences(), 0);
 
 CreateBook(book);
             datasource.close();
             myDbHelper.close();
             
             String jkl="";
             String hj;
             String fin;
             String mfil;
             StringBuilder sb=new StringBuilder("");
            Log.v("before first","for");
		  for( j=1;j<=te;j++){
			 
			 list2.get((int)(j-1)).outputSettings().escapeMode(EscapeMode.xhtml);
			 
          	// jkl =list2.get((int)(j-1)).toString();
			  sb.setLength(0);
          	//  sb.append(list2.get((int)(j-1)));
			  Log.v("before","mfil");
          	 mfil=file.get((int)(j-1));
          	Log.v("after","mfil");
          	// hj= jkl.replaceAll("&brvbar;","¦");
          //	fin=Html.fromHtml(jkl).toString();
          	Log.v("before","append");
          	
          sb.append(Html.fromHtml(list2.get((int)(j-1)).toString()));
Log.v("after"," append");
          datasource.open();
          //cuttext(fin,mfil,name);
        
      cuttextB(sb,mfil,name);
         
          datasource.close();
          publishProgress(doWork(j,te));
         // mProgress.setProgress(doWork(j,te));
       
          	// datasource.updatebook(j,fin,mfil,"steve");
           }
		 if(epub_id !=0){
			 datasource.open();
			 datasource.updateMatn((long) epub_id,name,"epub_status");
			 datasource.close();
		 }
           Intent mIntent=new Intent(context,MainActivity3B.class);
    		mIntent.putExtra("shomare_dars",1);
    		mIntent.putExtra("table",name);
    		
    		startActivity(mIntent); }else{
    			
			   
			   Intent jIntent=new Intent(context,MainActivity.class);
			   startActivity(jIntent); 
    			
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
      if(list2.size()==0){
    	
			Toast.makeText(context, getResources().getString(R.string.epub_error), Toast.LENGTH_SHORT).show();
      }
    }
  }
  public void ListSpins(Spine contents){
	  
	   List<SpineReference> spinelist  =  contents.getSpineReferences();
      StringBuilder string = new StringBuilder();
      String line = null;
      int count = spinelist.size();
      InputStream his;
      int size;
      byte[] buffer;
      String str;
      String href;
      Document tdoc;
      for (int i=0;i<count;i++){
         res = contents.getResource(i);
  
         try{ 
         	his= res.getInputStream();
         	size = his.available();

              buffer= new byte[size];
             his.read(buffer);
             his.close();

             str = new String(buffer);
         	href=res.getHref();
//datasource.createhrefs(str,href,"steve");
tdoc=Jsoup.parse(str);


cleandoc(tdoc);

list2.add(tdoc);
file.add(href);

         }catch (IOException e) {e.printStackTrace();}//end try
         }//end for    
}//end listspins
public void CreateBook(Book book){
	   
	   try{
	   nunimages=myDbHelper.counttable("images");}catch (Throwable e){
		   try {
			myDbHelper.openDataBase();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		   nunimages=myDbHelper.counttable("images");
	   }
    
     try{
     writeimage(book.getCoverImage().getInputStream(),IM_PATH,String.valueOf(1001+nunimages));
     try{ datasource.createepubbooks("epub",hgs, book.getTitle(), book.getMetadata().getAuthors().toString(),toString(docx));}catch (Throwable e){
    	  datasource.open();datasource.createepubbooks("epub", hgs, book.getTitle(), book.getMetadata().getAuthors().toString(),toString(docx));
    }
  	try{
  	   	datasource.createepubbooks("images", "cover", hgs, String.valueOf(1001+nunimages),"");}catch (Throwable e){
  	   		datasource.open();
  	   		datasource.createepubbooks("images", "cover", hgs, String.valueOf(1001+nunimages),"");
  	   	}
     }catch (Throwable n){
    	 try{ datasource.createepubbooks("epub",hgs, book.getTitle(), book.getMetadata().getAuthors().toString(),toString(docx));}catch (Throwable e){
       	  datasource.open();datasource.createepubbooks("epub", hgs, book.getTitle(), book.getMetadata().getAuthors().toString(),toString(docx));
       	try{
      	   	datasource.createepubbooks("images", "cover", hgs, "none","");}catch (Throwable g){
      	   		datasource.open();
      	   		datasource.createepubbooks("images", "cover", hgs, "none","");
      	   	}
       }
     }
   
     /* try {
			myDbHelper.openDataBase();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      long ti;
      try{
       ti=myDbHelper.counttable("epub");}catch (Throwable e){
    	  try {
  			myDbHelper.openDataBase();
  			
  		} catch (SQLException m) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
    	  ti=myDbHelper.counttable("epub");
      }
      name="table_"+String.valueOf(ti);
      try{datasource.open();}catch (Throwable e){
    	
      }*/
        try{ datasource.createtable(hgs);}catch (Throwable e){
        	datasource.open();
        	datasource.createtable(hgs);
        }
        try{
        	datasource.createComment(hgs,"1","last_page");
        }catch(Throwable e){
        	datasource.open();
        	datasource.createComment(hgs,"1","last_page");
        }
   
          
      // Start lengthy operation in a background thread
}
public void TOC(int playorder,String navlab,String content){
	
}
public void ListImages(Resources rs){
	  resourcelist=   rs.getResourcesByMediaType(MediatypeService.JPG);
     resourcelist.addAll(rs.getResourcesByMediaType(MediatypeService.PNG));
     resourcelist.addAll(rs.getResourcesByMediaType(MediatypeService.GIF));
  int resourcesize=resourcelist.size();

  datasource.open();
  try {
			myDbHelper.openDataBase();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  try{
  linknum=myDbHelper.counttable("matn_link");
  tis=myDbHelper.counttable("epub");
  if(tis==0){
	  
  }else{
	  tis=Long.valueOf(myDbHelper.getimage((int)tis,"epub").getisbn().replace("table_",""));
  }
  nunimages=myDbHelper.counttable("images");}catch (Throwable e){
	  try {
			myDbHelper.openDataBase();
			
		} catch (SQLException m) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  linknum=myDbHelper.counttable("matn_link");
	  tis=myDbHelper.counttable("epub");
	  if(tis==0){
		  
	  }else{
		  tis=Long.valueOf(myDbHelper.getimage((int)tis,"epub").getisbn().replace("table_",""));
	  }
	   nunimages=myDbHelper.counttable("images");
	   if(nunimages!=0){
		   nunimages=Long.valueOf(myDbHelper.getimage((int)nunimages,"images").gettitle());
	   }else{
		   nunimages=1001;
	   }
  }
  
   hgs=name="table_"+String.valueOf(tis+1);
 //  MatnActivity3.IM_PATH=path.replaceAll(names,"");
   try{
   for(int g=1;g<=resourcesize;g++){
   	resourcelist.get(g).getInputStream();

   	writeimage(resourcelist.get(g).getInputStream(),IM_PATH,String.valueOf(nunimages+g+1000));
   	try{	datasource.open();} catch (Throwable e){
    	  
    }
   	try{
   	datasource.createepubbooks("images", resourcelist.get(g).getHref(), hgs, String.valueOf(nunimages+g+1000),"");}catch (Throwable e){
   		datasource.open();
   		datasource.createepubbooks("images", resourcelist.get(g).getHref(), hgs, String.valueOf(nunimages+g+1000),"");
   	}
  
   }//end for
   }catch (Throwable e){
   	  
     }
   try{
   imagesize=myDbHelper.counttable("images");}catch (Throwable e){
	   try {
		myDbHelper.openDataBase();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	   imagesize=myDbHelper.counttable("images");
   }
   datasource.close();myDbHelper.close();
}
public int doWork(Long j,Long te){
	int javab=(int) ((float)j/te*100);
	return javab;
}
public static String toString(org.w3c.dom.Document doc) {
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
public void cuttextB(StringBuilder text,String id,String table){
	if(text.length()<1000){
		try{
		datasource.createhrefs(text.toString(), id, table);}catch (Throwable e){
			datasource.open();datasource.createhrefs(text.toString(), id, table);
		}
		}else{
		int x=text.length()/3500;
		int start=0;
		int end=3500;
		for(int i=1;i<=x;i++){
		
			for(int j=i*3500;j<(i+1)*3500;j++){
				if(text.charAt(j)=='\n'||j==text.length()){
					
					end=j;
					
				
					try{datasource.createhrefs(text.substring(start,end), id, table);}catch (Throwable e){
						datasource.open();datasource.createhrefs(text.substring(start,end), id, table);
					}
					start=end+1;
					break;
				}//end if
			}
			
		}//end for
		end=text.length();
		try{
		datasource.createhrefs(text.substring(start,end), id, table);}catch (Throwable e){
			datasource.open();
			datasource.createhrefs(text.substring(start,end), id, table);
		}
		
		
	}//end else
}
public void cuttext(String text,String id,String table){
	if(text.length()<1000){
		try{
		datasource.createhrefs(text, id, table);}catch (Throwable e){
			datasource.open();datasource.createhrefs(text, id, table);
		}
		}else{
		int x=text.length()/3500;
		int start=0;
		int end=3500;
		for(int i=1;i<=x;i++){
		
			for(int j=i*3500;j<(i+1)*3500;j++){
				if(text.charAt(j)=='\n'||j==text.length()){
			
					end=j;
				
			
					try{datasource.createhrefs(text.substring(start,end), id, table);}catch (Throwable e){
						datasource.open();datasource.createhrefs(text.substring(start,end), id, table);
					}
					start=end+1;
					break;
				}//end if
			}
			
		}//end for
		end=text.length();
		try{
		datasource.createhrefs(text.substring(start,end), id, table);}catch (Throwable e){
			datasource.open();
			datasource.createhrefs(text.substring(start,end), id, table);
		}
		
		
	}//end else
}// end cuttext
public void writeimage(InputStream input,String path,String name){
	 try {
           
        
           OutputStream os = new FileOutputStream(path+name);
            
           byte[] buffer = new byte[1024];
           int bytesRead;
           //read from is to buffer
           while((bytesRead = input.read(buffer)) !=-1){
               os.write(buffer, 0, bytesRead);
           }
           input.close();
           //flush OutputStream to write any buffered data to file
           os.flush();
           os.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
	
}
public void ReadPath(String path){
	try{
	InputStream is = new FileInputStream(path);
	  book = (new EpubReader()).readEpub(is);
	}catch (Throwable e){
 	  
   }
	
	  // Log the book's authors

 


   // Log the book's title



}
public void cleandoc(Document doc){
	datasource.open();
	for(Element element :doc.select("[href]")){
		element.text("¦"+Long.toString(-(1000+linknum))+"¦"+element.ownText()+" ¦");
		//title=matn asli
		//isbn=href
		//creater=bookname

	try{	datasource.createepubbooks("matn_link",element.attr("href"),Long.toString(-(1000+linknum)), hgs,"");
		//datasource.creatematn(element.attr("href"),"matn_link");
	}	catch (Throwable e){
	
	  datasource.open();
	  datasource.createepubbooks("matn_link",element.attr("href"),Long.toString(-(1000+linknum)), hgs,"");
	  //datasource.creatematn(element.attr("href"),"matn_link");
	}
		
		linknum++;
	}
	
	for(Element element :doc.select("li")){
		element.before("<p>"+"\n"+"</p>");
	}
	for(Element element :doc.select("td")){
		element.before("<p>"+"\n"+"</p>");
	}
	
	for(Element element :doc.select("[id]")){
		if( element.ownText().equals("")){
			element.before("<p>"+"¦"+element.id()+"¦"+"  "+"¦"+"</p>");
			//element.text("¦"+element.id()+"¦"+"  "+"¦");
			
		}else{
			element.text("¦"+element.id()+"¦"+element.ownText()+"¦");
			
		}
	}
	
	 for( Element element : doc.select("style") )
       {
		 
           element.remove();
       }
	 
	 
	 for( Element element : doc.select("img") )
       {
		 try{
		 String url="";
		  url = element.attr("src");
			 //url= element.attr("xlink:href");}
		 if(url.charAt(0)=='.'){
			 
			 url=url.substring(url.indexOf("/")+1, url.length());
		 }
	
		 for(long i=1;i<=imagesize;i++){
			 Epubbooks image;
			 try{
			 image=myDbHelper.getimage((int) i, "images");}catch (Throwable e){
				 try {
					myDbHelper.openDataBase();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  image=myDbHelper.getimage((int) i, "images");
			 }
			 if(image.getisbn().equals(url)&&image.gettitle().equals(hgs)){
		
				 element.after("<p>"+"¦0¦"+image.getcreater()+"¦");
			 }
		 }
		 
           element.remove();}catch (Throwable gh){}
       }
	 
}//end cleandoc


  private void logTableOfContents(List<TOCReference> tocReferences, int depth) {
		
		    if (tocReferences == null) {

		      return;

		    }
		    
		    for (TOCReference tocReference : tocReferences) {
		    	
		      StringBuilder tocString = new StringBuilder();
		      
		      for (int i = 0; i < depth; i++) {

		        tocString.append("\t");

		      }
		     NavPoint navpoint=new NavPoint(Integer.toString(playorder));
		     navpoint.setNavLabel(tocReference.getTitle());
		     navpoint.setContent(tocReference.getCompleteHref());
		 
		    tablencx.add(navpoint);
		    org.w3c.dom.Element staff = docx.createElement("Navpoint");
			rootElement.appendChild(staff);
			org.w3c.dom.Element firstname = docx.createElement("playorder");
			firstname.appendChild(docx.createTextNode(Integer.toString(playorder)));
			staff.appendChild(firstname);
			// lastname elements
			org.w3c.dom.Element lastname = docx.createElement("NavLab");
			lastname.appendChild(docx.createTextNode(navpoint.getNavLabel()));
			staff.appendChild(lastname);
			// nickname elements
			org.w3c.dom.Element nickname = docx.createElement("content");
			nickname.appendChild(docx.createTextNode(tocReference.getCompleteHref()));
			staff.appendChild(nickname);
			
		    
		    playorder++;
		      tocString.append(tocReference.getTitle());
		   if(tocReference.getFragmentId().equals("")){
			
		   }else{
			  
			  
			  String address=tocReference.getCompleteHref().replace("#"+tocReference.getFragmentId(),"");
			  for(long i=1;i<=te;i++){
				
				  if(file.get((int) (i-1)).equals(address)){
					  
					/*  if(sho!=i){
						  sho=i;
					  
					   html=list.get((int) (i-1));
					   
					   pfile=myDbHelper.getBooks((int) i, "steve")._2name;
					   
					   doc = Jsoup.parse(html);
					   }*/
				/*	  doc=list2.get((int) (i-1));
					try{  masthead = doc.select("#"+tocReference.getFragmentId()).first();
					  
					   linktext=masthead.ownText();
					   if(linktext.equals("")){
						   linktext="\n";
					   }
					   masthead.text("¦"+tocReference.getFragmentId()+"¦"+linktext+"¦");
					//  masthead.attr("text","¦id¦"+linktext+"¦");
					  
					  //String jkl=doc.toString();
					  
					}catch (Throwable e){
	                	  
	                  }
					  list2.set((int) (i-1), doc);
					  break;*/
					//  datasource.updatebook(i,doc.toString(),pfile,"steve");
					 
				  }//end if
			  }//end for 
			
		   }
		      
	//	    Log.i("epublib", tocString.toString()+ " "+tocReference.getFragmentId()+" "+tocReference.getCompleteHref()+" "+tocReference.getResourceId()+" "+tocReference.getCompleteHref());
		      
/*
		      try {
	                InputStream is = tocReference.getResource().getInputStream();
	                BufferedReader r = new BufferedReader(new InputStreamReader(is));

	                while ((line = r.readLine()) != null) {
	                    // line1 = Html.fromHtml(line).toString();
	                    
	                    // line1 = (tocString.append(Html.fromHtml(line).toString()+
	                    // "\n")).toString();
	                    line1 = line1.concat(Html.fromHtml(line).toString());
	                }
	                finalstr = finalstr.concat("\n").concat(line1);
	                
	                i++;
	            } catch (IOException e) {

	            } */
		      logTableOfContents(tocReference.getChildren(), depth + 1);

		    }

		  }
}