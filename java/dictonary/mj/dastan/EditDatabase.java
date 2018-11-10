package dictonary.mj.dastan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EditDatabase  {
	private SQLiteDatabase database;
	  private DynamicDatabase dbHelper;
	  private String[] allColumns = { DynamicDatabase.KEY_ID,
			  DynamicDatabase.KEY_NAME,DynamicDatabase.KEY_PH_NO };
	  private String[] hrefColumns = { DynamicDatabase.KEY_ID,
			  DynamicDatabase.MATN,DynamicDatabase.FILE};
	  private String[] matnColumns = { DynamicDatabase.KEY_ID,
			  DynamicDatabase.MATN };
	  private String[] epubbooksColumns = { DynamicDatabase.KEY_ID,
			  DynamicDatabase.ISBN,DynamicDatabase.TITLE,DynamicDatabase.CREATER, DynamicDatabase.TOC};
	  private String[] epubTextColumnes = { DynamicDatabase.KEY_ID, DynamicDatabase.MATN,
			  DynamicDatabase.FILE};
	  private String[] epubcontentColumns = { DynamicDatabase.KEY_ID,
			  DynamicDatabase.ISBN,DynamicDatabase.CHAPTER,DynamicDatabase.CONTENT };
	  public EditDatabase(Context context) {
	    dbHelper = new DynamicDatabase(context);
	  }
	  public void setPath(String path){
		  dbHelper.setPath(path);
	  }
	  public String getPath(){
		 return dbHelper.getpath();
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	  public names createEpubtext(String table,String matn,String file){
			ContentValues valuem = new ContentValues();
			valuem.put(DynamicDatabase.MATN,matn);
		  valuem.put(DynamicDatabase.FILE,file);
		 
		  long insertIm = database.insert(table, null, valuem);
		  Cursor cursor = database.query(table,
				  epubTextColumnes, DynamicDatabase.KEY_ID + " = " + insertIm, null,
			        null, null, null);
		  cursor.moveToFirst();
		  names epubbooks=cursorTonames(cursor);
		  cursor.close();
		  return epubbooks;
		} 
public Epubbooks createepubbooks(String table,String isbn,String title,String creater,String toc){
	ContentValues valuem = new ContentValues();
	valuem.put(DynamicDatabase.ISBN, isbn);
  valuem.put(DynamicDatabase.TITLE,title);
  valuem.put(DynamicDatabase.CREATER,creater);
  valuem.put(DynamicDatabase.TOC, toc);
  long insertIm = database.insert(table, null, valuem);
  Cursor cursor = database.query(table,
	        epubbooksColumns, DynamicDatabase.KEY_ID + " = " + insertIm, null,
	        null, null, null);
  cursor.moveToFirst();
  Epubbooks epubbooks=cursorToepubbooks(cursor);
  cursor.close();
  return epubbooks;
}
public Epubcontent createepubcontent(String table,String isbn,String chapter,String content){
	ContentValues valuem = new ContentValues();
	valuem.put(DynamicDatabase.ISBN, isbn);
  valuem.put(DynamicDatabase.TITLE,chapter);
  valuem.put(DynamicDatabase.CREATER,content);
  long insertIm = database.insert(table, null, valuem);
  Cursor cursor = database.query(table,
	        epubcontentColumns, DynamicDatabase.KEY_ID + " = " + insertIm, null,
	        null, null, null);
  cursor.moveToFirst();
  Epubcontent epubcontent=cursorToepubcontent(cursor);
  cursor.close();
  return epubcontent;
}
	  public comments createComment(String words,String meaning,String table) {
	    ContentValues values = new ContentValues();
	    values.put(DynamicDatabase.KEY_NAME, words);
	    values.put(DynamicDatabase.KEY_PH_NO,meaning);
	    long insertId = database.insert(table, null,
	        values);
	    Cursor cursor = database.query(table,
	        allColumns, DynamicDatabase.KEY_ID + " = " + insertId, null,
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
		  values.put(DynamicDatabase.MATN,words);
		  values.put(DynamicDatabase.FILE,meaning);
		  long insertId = database.insert(table, null,
			        values);
			    Cursor cursor = database.query(table,
			        hrefColumns, DynamicDatabase.KEY_ID + " = " + insertId, null,
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
	  private names cursorTonames(Cursor cursor) {
		    names comment = new names();
		    comment.setID((int)cursor.getLong(0));
		    comment.set1Name(cursor.getString(1));
		    comment.set2Name(cursor.getString(2));
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
		    
		    value.put(DynamicDatabase.MATN,meaning);
		    
		    long insertId = database.insert(table, null,
		        value);
		    Cursor cursor = database.query(table,
		        matnColumns, DynamicDatabase.KEY_ID + " = " + insertId, null,
		        null, null, null);
		    
		    cursor.moveToFirst();
		    Matns newComment = cursorTomatn(cursor);

		    cursor.close();
		    return newComment;
		  }
	  public boolean updateMatn(long rowId, String address,String table)
	  {
	   ContentValues args = new ContentValues();
	   args.put(DynamicDatabase.KEY_ID, rowId);          
	   args.put(DynamicDatabase.MATN, address);
	 int i= database.update(table, args, DynamicDatabase.KEY_ID + "=" + rowId, null);  
	 return i>0;
	 }
	  public boolean updatepage(String table,long rowId, String first,String second)
	  {
	   ContentValues args = new ContentValues();
	   args.put(DynamicDatabase.KEY_ID, rowId);          
	   args.put(DynamicDatabase.KEY_NAME, first);
	   args.put(DynamicDatabase.KEY_PH_NO, second);
	 int i= database.update(table, args, DynamicDatabase.KEY_ID + "=" + rowId, null);  
	 return i>0;
	 }
	  public boolean updatemean(long rowId, String first,String second)
	  {
	   ContentValues args = new ContentValues();
	   args.put(DynamicDatabase.KEY_ID, rowId);          
	   args.put(DynamicDatabase.KEY_NAME, first);
	   args.put(DynamicDatabase.KEY_PH_NO, second);
	 int i= database.update("names", args, DynamicDatabase.KEY_ID + "=" + rowId, null);  
	 return i>0;
	 }
	  public boolean updatebook(long rowId, String first,String second,String table)
	  {
	   ContentValues args = new ContentValues();
	   args.put(DynamicDatabase.KEY_ID, rowId);          
	   args.put(DynamicDatabase.MATN, first);
	   args.put(DynamicDatabase.FILE, second);
	 int i= database.update(table, args, DynamicDatabase.KEY_ID + "=" + rowId, null);  
	 return i>0;
	 }
	  public void deleteEntry(long row,String table) {

		  database.delete(table, DynamicDatabase.KEY_ID + "=" + row, null);

	      /*if you just have key_name to select a row,you can ignore passing rowid(here-row) and use:

	      db.delete(DATABASE_TABLE, KEY_NAME + "=" + key_name, null);
	      */  

	}
	  public void deletetable(String name){
		  database.execSQL("DROP TABLE IF EXISTS "+ name);
		  //delete from
	  }
	  public void deleterow(String table, long id){
		  database.delete(table, DynamicDatabase.KEY_ID + "=" + id, null);
	  }
	  public void createepubtable(){
		  final String  CREATE_TABLE_TODO = "CREATE TABLE "
		            + "epub" + "(" + "_id" + " INTEGER PRIMARY KEY," + DynamicDatabase.TOC+ " TEXT," + DynamicDatabase.ISBN+" TEXT," + DynamicDatabase.TITLE+ " TEXT,"+ DynamicDatabase.CREATER+" TEXT" +")";
		  database.execSQL(CREATE_TABLE_TODO);
	  }
	  public void createtable(String name){
		  final String  CREATE_TABLE_TODO = "CREATE TABLE "
		            + name + "(" + "_id" + " INTEGER PRIMARY KEY," + DynamicDatabase.MATN+ " TEXT," + DynamicDatabase.FILE+" TEXT"+")";
		  database.execSQL(CREATE_TABLE_TODO);
	  }
}
