package dictonary.mj.dastan;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Insertword {
	private SQLiteDatabase database;
	  private DataBaseHelper dbHelper;
	  private String[] allColumns = { DataBaseHelper.KEY_ID,
			  DataBaseHelper.KEY_NAME,DataBaseHelper.KEY_PH_NO,DataBaseHelper.KEY_MARK };
	  private String[] hrefColumns = { DataBaseHelper.KEY_ID,
			  DataBaseHelper.MATN,DataBaseHelper.FILE};
	  private String[] matnColumns = { DataBaseHelper.KEY_ID,
			  DataBaseHelper.MATN };
	  private String[] epubbooksColumns = { DataBaseHelper.KEY_ID,
			  DataBaseHelper.ISBN,DataBaseHelper.TITLE,DataBaseHelper.CREATER, DataBaseHelper.TOC};
	  private String[] epubcontentColumns = { DataBaseHelper.KEY_ID,
			  DataBaseHelper.ISBN,DataBaseHelper.CHAPTER,DataBaseHelper.CONTENT };
	  public Insertword(Context context) {
	    dbHelper = new DataBaseHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	 
public Epubbooks createepubbooks(String table,String isbn,String title,String creater,String toc){
	ContentValues valuem = new ContentValues();
	valuem.put(DataBaseHelper.ISBN, isbn);
    valuem.put(DataBaseHelper.TITLE,title);
    valuem.put(DataBaseHelper.CREATER,creater);
    valuem.put(DataBaseHelper.TOC, toc);
    long insertIm = database.insert(table, null, valuem);
    Cursor cursor = database.query(table,
	        epubbooksColumns, DataBaseHelper.KEY_ID + " = " + insertIm, null,
	        null, null, null);
    cursor.moveToFirst();
    Epubbooks epubbooks=cursorToepubbooks(cursor);
    cursor.close();
    return epubbooks;
}
public boolean updatePdfPage(String table,long rowId, String address,String name,String page)
{
 ContentValues args = new ContentValues();
 args.put(DataBaseHelper.KEY_ID, rowId);          
 args.put(DataBaseHelper.ISBN, address);
 args.put(DataBaseHelper.TITLE, name);
 args.put(DataBaseHelper.CREATER,"");
 args.put(DataBaseHelper.TOC, page);
int i= database.update(table, args, DataBaseHelper.KEY_ID + "=" + rowId, null);  
return i>0;
}
public Epubcontent createepubcontent(String table,String isbn,String chapter,String content){
	ContentValues valuem = new ContentValues();
	valuem.put(DataBaseHelper.ISBN, isbn);
    valuem.put(DataBaseHelper.TITLE,chapter);
    valuem.put(DataBaseHelper.CREATER,content);
    long insertIm = database.insert(table, null, valuem);
    Cursor cursor = database.query(table,
	        epubcontentColumns, DataBaseHelper.KEY_ID + " = " + insertIm, null,
	        null, null, null);
    cursor.moveToFirst();
    Epubcontent epubcontent=cursorToepubcontent(cursor);
    cursor.close();
    return epubcontent;
}
	  public comments createComment(String words,String meaning,String table) {
	    ContentValues values = new ContentValues();
	    values.put(DataBaseHelper.KEY_NAME, words);
	    values.put(DataBaseHelper.KEY_PH_NO,meaning);
	    values.put(DataBaseHelper.KEY_MARK, 0);
	    long insertId = database.insert(table, null,
	        values);
	    Cursor cursor = database.query(table,
	        allColumns, DataBaseHelper.KEY_ID + " = " + insertId, null,
	        null, null, null);
	    
	    cursor.moveToFirst();
	    comments newComment = cursorToComment(cursor);

	    cursor.close();
	    return newComment;
	  }
	  public comments createhrefs(String words,String meaning,String table){
	
		  if(table.equals("")){
			  Log.v("helll","helll");
		  }
		  ContentValues values = new ContentValues();
		  values.put(DataBaseHelper.MATN,words);
		  values.put(DataBaseHelper.FILE,meaning);
		  long insertId = database.insert(table, null,
			        values);
			    Cursor cursor = database.query(table,
			        hrefColumns, DataBaseHelper.KEY_ID + " = " + insertId, null,
			        null, null, null);
			    cursor.moveToFirst();
			    comments newComment = cursorToComment(cursor);

			    cursor.close();
			    return newComment;
	  }

	  private Epubbooks cursorToepubbooks(Cursor cursor) {
		    Epubbooks epubbooks = new Epubbooks();
		    epubbooks.setId(cursor.getLong(0));
		    epubbooks.setisbn(cursor.getString(1));
		    epubbooks.settitle(cursor.getString(2));
		    epubbooks.setcreater(cursor.getString(3));
		    return epubbooks;
		  }
	  private Epubcontent cursorToepubcontent(Cursor cursor) {
		    Epubcontent epubcontent = new Epubcontent();
		    epubcontent.setId(cursor.getLong(0));
		    epubcontent.setisbn(cursor.getString(1));
		    epubcontent.setchapter(cursor.getString(2));
		    epubcontent.setcontent(cursor.getString(3));
		    return epubcontent;
		  }
	  private comments cursorToComment(Cursor cursor) {
		    comments comment = new comments();
		    comment.setId(cursor.getLong(0));
		    comment.setwords(cursor.getString(1));
		    comment.setmeaning(cursor.getString(2));
		    return comment;
		  }
	  private Matns cursorTomatn(Cursor cursor) {
		  Matns comment = new Matns();
		    comment.setID(cursor.getLong(0));
		    comment.setmatn(cursor.getString(1));
		    
		    return comment;
		    
		  }
	  public Matns creatematn(String meaning,String table) {
		    ContentValues value = new ContentValues();
		    
		    value.put(DataBaseHelper.MATN,meaning);
		    long insertId = database.insert(table, null,
		        value);
		    Cursor cursor = database.query(table,
		        matnColumns, DataBaseHelper.KEY_ID + " = " + insertId, null,
		        null, null, null);
		    
		    cursor.moveToFirst();
		    Matns newComment = cursorTomatn(cursor);

		    cursor.close();
		    return newComment;
		  }
	  public boolean updateMatn(long rowId, String address,String table)
	  {
	   ContentValues args = new ContentValues();
	   args.put(DataBaseHelper.KEY_ID, rowId);          
	   args.put(DataBaseHelper.MATN, address);
	 int i= database.update(table, args, DataBaseHelper.KEY_ID + "=" + rowId, null);  
	 return i>0;
	 }
	  public boolean updatepage(String table,long rowId, String first,String second)
	  {
	   ContentValues args = new ContentValues();
	   args.put(DataBaseHelper.KEY_ID, rowId);          
	   args.put(DataBaseHelper.KEY_NAME, first);
	   args.put(DataBaseHelper.KEY_PH_NO, second);
	 int i= database.update(table, args, DataBaseHelper.KEY_ID + "=" + rowId, null);  
	 return i>0;
	 }
	  public boolean updatemean(long rowId, String first,String second)
	  {
	   ContentValues args = new ContentValues();
	   args.put(DataBaseHelper.KEY_ID, rowId);          
	   args.put(DataBaseHelper.KEY_NAME, first);
	   args.put(DataBaseHelper.KEY_PH_NO, second);
	 int i= database.update("names", args, DataBaseHelper.KEY_ID + "=" + rowId, null);  
	 return i>0;
	 }
	  public boolean updatemark(long rowId,int mark)
	  {
	   ContentValues args = new ContentValues();
	   args.put(DataBaseHelper.KEY_ID, rowId);          
	   args.put(DataBaseHelper.KEY_MARK, mark);
	   
	 int i= database.update("names", args, DataBaseHelper.KEY_ID + "=" + rowId, null);  
	 return i>0;
	 }
	  public boolean updatebook(long rowId, String first,String second,String table)
	  {
	   ContentValues args = new ContentValues();
	   args.put(DataBaseHelper.KEY_ID, rowId);          
	   args.put(DataBaseHelper.MATN, first);
	   args.put(DataBaseHelper.FILE, second);
	 int i= database.update(table, args, DataBaseHelper.KEY_ID + "=" + rowId, null);  
	 return i>0;
	 }
	  public void deleteEntry(long row,String table) {

		  database.delete(table, DataBaseHelper.KEY_ID + "=" + row, null);

	      /*if you just have key_name to select a row,you can ignore passing rowid(here-row) and use:

	      db.delete(DATABASE_TABLE, KEY_NAME + "=" + key_name, null);
	      */  

	}
	  public void deletetable(String name){
		  database.execSQL("DROP TABLE IF EXISTS "+ name);
		  //delete from
	  }
	  public void deleterow(String table, long id){
		  database.delete(table, DataBaseHelper.KEY_ID + "=" + id, null);
	  }
	  public void createepubtable(){
		  final String  CREATE_TABLE_TODO = "CREATE TABLE "
		            + "epub" + "(" + "_id" + " INTEGER PRIMARY KEY," + DataBaseHelper.TOC+ " TEXT," + DataBaseHelper.ISBN+" TEXT," + DataBaseHelper.TITLE+ " TEXT,"+ DataBaseHelper.CREATER+" TEXT" +")";
		  database.execSQL(CREATE_TABLE_TODO);
	  }
	  public void createtable(String name){
		  final String  CREATE_TABLE_TODO = "CREATE TABLE "
		            + name + "(" + "_id" + " INTEGER PRIMARY KEY," + DataBaseHelper.MATN+ " TEXT," + DataBaseHelper.FILE+" TEXT"+")";
		  database.execSQL(CREATE_TABLE_TODO);
	  }
}
