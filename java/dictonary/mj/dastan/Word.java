package dictonary.mj.dastan;

public class Word {
private int id;
private String word;
private boolean marked=false;
	private String mean;
public Word(int _id,String _text){
	super();
	this.id=_id;
	this.word=_text;
	
}
public Word(int _id,String _text,boolean _marked){
	super();
	this.id=_id;
	this.word=_text;
	this.marked=_marked;
}
	public Word(int _id,String _text,String _mean,boolean _marked){
		super();
this.mean=_mean;
		this.id=_id;
		this.word=_text;
		this.marked=_marked;
	}
public int getId(){
	return id;
}
public String getText(){
	return word;
}
	public String getMean(){return mean;}
public boolean getMarked(){
	return marked;
}
public void setId(int _id){
	this.id=_id;
}
public void setText(String _text){
	this.word=_text;
}
	public void setMean(String _mean){this.mean=_mean;}
public void setMarked(boolean _marked){
	this.marked=_marked;
}

}
