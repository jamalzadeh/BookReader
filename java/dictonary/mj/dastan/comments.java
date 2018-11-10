package dictonary.mj.dastan;

public class comments {
	
		  private long id;
		  private String words;
		  private String meaning;
		  private int mark;
public int getMark(){
	return mark;
}
		  public long getId() {
		    return id;
		  }
public void setMark(int _mark){
	this.mark=_mark;
}
		  public void setId(long id) {
		    this.id = id;
		  }

		  public String getwords() {
		    return words;
		  }

		  public void setmeaning(String words) {
		    this.words =words;
		  }
		  public String getmeaning() {
			    return words;
			  }

			  public void setwords(String meaning) {
			    this.meaning =meaning;
			  }
		  // Will be used by the ArrayAdapter in the ListView
		 
		 

}
