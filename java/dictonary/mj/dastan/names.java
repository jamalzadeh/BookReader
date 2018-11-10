package dictonary.mj.dastan;

public class names {
    
    //private variables
    int _id;
    String _1name;
    String _2name;
    int _marked;
     
    // Empty constructor
    public names(){
         
    }
    // constructor
    public names(int id,String _1name, String _2name){
        this._id = id;
        this._1name = _1name;
        this._2name = _2name;
    }
    // constructor
    public names(int id,String _1name, String _2name,int _marked){
        this._id = id;
        this._1name = _1name;
        this._2name = _2name;
        this._marked=_marked;
    }
    // constructor
    public names(String _1name, String _2name){
    	 this._1name = _1name;
         this._2name = _2name;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting name
    public String get1Name(){
        return this._1name;
    }
    public int getMarked(){
    	return this._marked;
    }
     
    // setting name
    public void set1Name(String _1name){
        this._1name = _1name;
    }
     
    // getting phone number
    public String getget2Name(){
        return this._2name;
    }
     
    // setting phone number
    public void set2Name(String _2name){
        this._2name = _2name;
    }
    public void setMark(int _mark){
    	this._marked=_mark;
    }
}
