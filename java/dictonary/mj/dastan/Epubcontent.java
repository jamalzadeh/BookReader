package dictonary.mj.dastan;

public class Epubcontent {
	  private long id;
	  private String isbn;
	  private String chapter;
	  private String content;

	  public long getId() {
	    return id;
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
	  public String getchapter() {
		    return chapter;
		  }
	  public String getcontent() {
		    return content;
		  }

		  public void setchapter(String chapter) {
		    this.chapter =chapter;
		  }
		  public void setcontent(String content) {
			    this.content =content;
			  }
	  // Will be used by the ArrayAdapter in the ListView
	 
	 

}
