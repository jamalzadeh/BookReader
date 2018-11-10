package dictonary.mj.dastan;

import android.graphics.drawable.Drawable;

public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private int icon;
    Drawable draw;
    private String count = "0";
    // boolean to set visiblity of the counter
    private boolean isCounterVisible = false;
     
    public NavDrawerItem(){}
    public NavDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }
    public NavDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }
     
    public NavDrawerItem(String title, int icon, boolean isCounterVisible, String count){
        this.title = title;
        this.icon = icon;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }
    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle(){
        return this.title;
    }
     
    public int getIcon(){
        return this.icon;
    }
     
    public String getCount(){
        return this.count;
    }
     public Drawable getDraw(){return this.draw;}
    public boolean getCounterVisibility(){
        return this.isCounterVisible;
    }
     
    public void setTitle(String title){
        this.title = title;
    }
     
    public void setIcon(int icon){
        this.icon = icon;
    }
     
    public void setCount(String count){
        this.count = count;
    }
    public void setDraw(Drawable draw){this.draw=draw;}

    public void setCounterVisibility(boolean isCounterVisible){
        this.isCounterVisible = isCounterVisible;
    }
}
