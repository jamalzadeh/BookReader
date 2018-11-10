package dictonary.mj.dastan;

import android.app.Application;
import android.graphics.PointF;

public class MyApplication extends Application {

    private String path;
    private boolean editMode=false;
    private PointF pagesize;
    private int position;
    private float TOLERANCE;
    public float getTOLERANCE(){
    	return TOLERANCE;
    }
    public void setTOLERANCE(float t){
    	this.TOLERANCE=t;
    }
public int getPosition(){
	return position;
}
    public String getPath() {
        return path;
    }
public void setPosition(int po){
	this.position=po;
}
    public void setPath(String someVariable) {
        this.path = someVariable;
    }
    
    public boolean geteditMode(){
    	return editMode;
    }
    public void seteditMode(boolean mode){
    	this.editMode=mode;
    }
    public PointF getSize(){
    	return pagesize;
    }
    public void setSize(PointF size){
    	this.pagesize=size;
    }
}
