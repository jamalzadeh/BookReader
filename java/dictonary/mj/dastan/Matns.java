package dictonary.mj.dastan;

public class Matns {
	  //private variables
    long _id;
    String _matn;
    
     
    // Empty constructor
    public Matns(){
         
    }
    // constructor
    public Matns(long id,String _matn){
        this._id = id;
        this._matn = _matn;
        
    }
     
    // constructor
    public Matns(String _matn){
    	 this._matn = _matn;
         
    }
    // getting ID
    public long getID(){
        return this._id;
    }
     
    // setting id
    public void setID(long id){
        this._id = id;
    }
    public void setmatn(String _matn){
        this._matn = _matn;
    }
    // getting name
    public String getMatn(){
        return this._matn;
    }
     
    // setting name
   
    // getting phone number
  
}
