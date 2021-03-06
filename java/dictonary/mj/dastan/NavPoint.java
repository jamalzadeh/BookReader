package dictonary.mj.dastan;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class NavPoint implements Parcelable {
    private int mPlayOrder;
    private String mNavLabel;
    private String mContent;
    private int Id;
    public int getPlayOrder() { return mPlayOrder; }
    public String getNavLabel() { return mNavLabel; }
    public String getContent() { return mContent; }
    public int getId() { return Id; }
    /*
     * Sometimes the content (resourceName) contains a tag 
     * into the HTML. 
     */
    public Uri getContentWithoutTag() {
        int indexOf = mContent.indexOf('#');
        String temp = mContent; 
        if (0 < indexOf) {
            temp = mContent.substring(0, indexOf);   
        }
        
        return Uri.parse(temp);
       // return Book.resourceName2Url(temp);
    }
    public String getcontent(){
    	 int indexOf = mContent.indexOf('#');
    	 int length=mContent.length();
         String temp = ""; 
         if (0 < indexOf) {
             temp = mContent.substring(indexOf+1, length);   
         }
         return temp;
    }
    
    
    public void setPlayOrder(int playOrder) { mPlayOrder = playOrder; }
    public void setNavLabel(String navLabel) { mNavLabel = navLabel; }
    public void setContent(String content) { mContent = content; }
    public void setId(int i) { Id = i; }
    /*
     * Construct as part of reading from XML
     */
    public NavPoint(String playOrder) {
       mPlayOrder = Integer.parseInt(playOrder); 
    }
    
    public NavPoint(Parcel in) {
        mPlayOrder = in.readInt();
        mNavLabel = in.readString();
        mContent = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPlayOrder);
        dest.writeString(mNavLabel);
        dest.writeString(mContent);
    }

    public static final Parcelable.Creator<NavPoint> CREATOR
        = new Parcelable.Creator<NavPoint>() {
        public NavPoint createFromParcel(Parcel in) {
            return new NavPoint(in);
        }
        
        public NavPoint[] newArray(int size) {
            return new NavPoint[size];
        }
    };

}
