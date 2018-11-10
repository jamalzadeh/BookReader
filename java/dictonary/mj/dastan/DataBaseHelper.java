package dictonary.mj.dastan;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    @SuppressLint("SdCardPath")
	private static String DB_PATH = "/data/data/dictonary.mj.dastan/databases/";
    public SQLiteDatabase sqliteDB = null;
    private static String DB_NAME = "datab1";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
 // All Static variables
    // Database Version
   
 
   
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "names";
 
    // Contacts Table Columns names
   public static final String KEY_ID = "_id";
   public static final String KEY_MARK="mark";
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
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
  

/**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
    		this.close();
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
	 // Getting single contact
	public names getContact(int id,String tablename) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    Cursor cursor = db.query(tablename, new String[] { KEY_ID,
	    		KEY_NAME, KEY_PH_NO,KEY_MARK}, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    names contact = new names(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2),cursor.getInt(3));
	    cursor.close();
	    // return contact
	    return contact;
	}
	public names getEpubtext(int id,String tablename) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
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
	public names getBooks(int id,String tablename) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
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
	public Epubbooks getimage(int id,String tablename){
		SQLiteDatabase db = this.getReadableDatabase();
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
	public Matns getmatn(int id,String tablename) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    Cursor cursor = db.query(tablename, new String[] { KEY_ID,
	            MATN }, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Matns matn = new Matns(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1));
	    cursor.close();
	    // return contact
	    return matn;
	}
	
	public long counttable(String table){
		 SQLiteDatabase db = this.getReadableDatabase();
		    return DatabaseUtils.queryNumEntries(db,table);
	}
	 public long count() {
		 SQLiteDatabase db = this.getReadableDatabase();
		    return DatabaseUtils.queryNumEntries(db,"names");
		}
	 public long countbook(String name) {
		 SQLiteDatabase db = this.getReadableDatabase();
		    return DatabaseUtils.queryNumEntries(db,name);
		}
	 public long countmatn() {
		 SQLiteDatabase db = this.getReadableDatabase();
		    return DatabaseUtils.queryNumEntries(db,"usermatn");
		}
	 public long countepubbooks() {
		 SQLiteDatabase db = this.getReadableDatabase();
		    return DatabaseUtils.queryNumEntries(db,"epub");
		}
	 public boolean isTableExists(String tableName, boolean openDb) {
		 SQLiteDatabase mDatabase = this.getReadableDatabase();
		    if(openDb) {
		        if(mDatabase == null || !mDatabase.isOpen()) {
		            mDatabase = getReadableDatabase();
		        }

		        if(!mDatabase.isReadOnly()) {
		            mDatabase.close();
		            mDatabase = getReadableDatabase();
		        }
		    }

		    Cursor cursor = mDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
		    if(cursor!=null) {
		        if(cursor.getCount()>0) {
		                            cursor.close();
		            return true;
		        }
		                    cursor.close();
		    }
		    return false;
		}
	 public void createfarsDataBase() throws IOException{
    	 boolean fdbExist = checkfarsDataBase();
    	 if(fdbExist){
    	    		//do nothing - database already exist
    	    	}else{
    	 //By calling this method and empty database will be created into the default system path
    	               //of your application so we are gonna be able to overwrite that database with our database.
    	    		this.close();
    	        	this.getReadableDatabase();
    	 try {
    	 copyfarsDataBase();
    	 } catch (IOException e) {
    	 throw new Error("Error copying database");
    	 }
    	    	}
    	  }
	 public String farsdict(String word){
		 String wordMean="";
		 try{
		sqliteDB = SQLiteDatabase.openOrCreateDatabase(
				DB_PATH + "dic.mp3",
				null);
	Cursor cursor = sqliteDB
				.rawQuery(
						"SELECT mean FROM dictionary WHERE word='"
								+ word
										.toLowerCase()
								+ "'", null);
cursor.moveToFirst();
		 wordMean = cursor.getString(0);
		Log.v("wordmean",wordMean);
		cursor.close();
		sqliteDB.close();
		} catch (Throwable e){
			wordMean="";
			Log.v("not found("+word+")","in persian dictionary");
		}
		return wordMean;
	}
	 private void copyfarsDataBase() throws IOException{
		   //Open your local db as the input stream
		      	InputStream myInput = myContext.getAssets().open("dic.mp3");
		   // Path to the just created empty db
		      	String outFileName = DB_PATH +"dic.mp3";
		   //Open the empty db as the output stream
		      	OutputStream myOutput = new FileOutputStream(outFileName);
		   //transfer bytes from the inputfile to the outputfile
		      	byte[] buffer = new byte[1024];
		      	int length;
		      	while ((length = myInput.read(buffer))>0){
		      		myOutput.write(buffer, 0, length);
		      	}
		   //Close the streams
		      	myOutput.flush();
		      	myOutput.close();
		      	myInput.close();
		   
		      }
	 private boolean checkfarsDataBase(){
    	 SQLiteDatabase checkDB = null;
    	 try{
    	    		String myPath = DB_PATH + "dic.mp3";
    	    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	 	}catch(SQLiteException e){
    	 //database does't exist yet.
    	 }
    	 if(checkDB != null){
    	 	checkDB.close();
    	 }
    	 return checkDB != null ? true : false;
    	    }
	 public long getLastInsertId(String table) {
		    long index = 0;
		    SQLiteDatabase sdb = getReadableDatabase();
		    Cursor cursor = sdb.query(
		            "sqlite_sequence",
		            new String[]{"seq"},
		            "name = ?",
		            new String[]{table},
		            null,
		            null,
		            null,
		            null
		    );
		    if (cursor.moveToFirst()) {
		        index = cursor.getLong(cursor.getColumnIndex("seq"));
		    }
		    cursor.close();
		    return index;
		}
	 public String similar(String text){
		 String result=text;
		 
		 return text;
		 
	 }
	 public  int getLevenshteinDistance(String s, String t) {
	      if (s == null || t == null) {
	          throw new IllegalArgumentException("Strings must not be null");
	      }

	      /*
	         The difference between this impl. and the previous is that, rather 
	         than creating and retaining a matrix of size s.length()+1 by t.length()+1, 
	         we maintain two single-dimensional arrays of length s.length()+1.  The first, d,
	         is the 'current working' distance array that maintains the newest distance cost
	         counts as we iterate through the characters of String s.  Each time we increment
	         the index of String t we are comparing, d is copied to p, the second int[].  Doing so
	         allows us to retain the previous cost counts as required by the algorithm (taking 
	         the minimum of the cost count to the left, up one, and diagonally up and to the left
	         of the current cost count being calculated).  (Note that the arrays aren't really 
	         copied anymore, just switched...this is clearly much better than cloning an array 
	         or doing a System.arraycopy() each time  through the outer loop.)

	         Effectively, the difference between the two implementations is this one does not 
	         cause an out of memory condition when calculating the LD over two very large strings.
	       */

	      int n = s.length(); // length of s
	      int m = t.length(); // length of t

	      if (n == 0) {
	          return m;
	      } else if (m == 0) {
	          return n;
	      }

	      if (n > m) {
	          // swap the input strings to consume less memory
	          String tmp = s;
	          s = t;
	          t = tmp;
	          n = m;
	          m = t.length();
	      }

	      int p[] = new int[n+1]; //'previous' cost array, horizontally
	      int d[] = new int[n+1]; // cost array, horizontally
	      int _d[]; //placeholder to assist in swapping p and d

	      // indexes into strings s and t
	      int i; // iterates through s
	      int j; // iterates through t

	      char t_j; // jth character of t

	      int cost; // cost

	      for (i = 0; i<=n; i++) {
	          p[i] = i;
	      }

	      for (j = 1; j<=m; j++) {
	          t_j = t.charAt(j-1);
	          d[0] = j;

	          for (i=1; i<=n; i++) {
	              cost = s.charAt(i-1)==t_j ? 0 : 1;
	              // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
	              d[i] = Math.min(Math.min(d[i-1]+1, p[i]+1),  p[i-1]+cost);
	          }

	          // copy current distance counts to 'previous row' distance counts
	          _d = p;
	          p = d;
	          d = _d;
	      }

	      // our last action in the above loop was to switch d and p, so p now 
	      // actually has the most recent cost counts
	      return p[n];
	  }

}
