package dictonary.mj.dastan;

public class Epubbooks {
	
	  private long id;
	  private String isbn;
	  private String title;
	  private String creater;
	  private String toc;
	  public Epubbooks(){
		  
	  }
	  public Epubbooks(long _id,String _isbn,String _table,String mean,String _toc){
	        this.id = _id;
	        this.isbn = _isbn;
	        this.title=_table;
	        this.creater=mean;
	        this.toc=_toc;
	    }
	  public String getTOC(){
		  return toc;
	  }
	  public long getId() {
	    return id;
	  }
	  public void setTOC(String _toc){
		  this.toc=_toc;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getisbn() {
	    return isbn;
	  }

	  public void setisbn(String isbn) {
	    this.isbn =isbn;
	  }
	  public String gettitle() {
		    return title;
		  }
	  public String getcreater() {
		    return creater;
		  }

		  public void settitle(String title) {
		    this.title =title;
		  }
		  public void setcreater(String creater) {
			    this.creater =creater;
			  }
	  // Will be used by the ArrayAdapter in the ListView
	 
	 

}

