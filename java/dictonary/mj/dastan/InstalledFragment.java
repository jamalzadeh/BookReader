package dictonary.mj.dastan;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InstalledFragment extends Fragment {
Context context;
    Insertword datasource;
    DataBaseHelper myDbHelper;
TextView textview;
    Typeface type;
    List<Fruit> booklist=new ArrayList<>();
    BookmAdapter bookadapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String IM_PATH = "/data/data/dictonary.mj.dastan/images/";
    public InstalledFragment() {
        // Required empty public constructor
    }
    public static Fragment newInstance(Context context) {
        InstalledFragment f = new InstalledFragment();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_installed2, container, false);
        textview=(TextView) rootView.findViewById(R.id.textview);
        context=getActivity();
        datasource=new Insertword(context);
        myDbHelper=new DataBaseHelper(context);

        try {
            myDbHelper.openDataBase();
            myDbHelper.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try{myDbHelper.close();}catch (Throwable fg){}
        try {
            myDbHelper.openDataBase();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(myDbHelper.counttable("epub")==0){

            //  Fruit book = new Fruit("b",getResources().getString(R.string.noBooks),"","0");
            // bookArrayAdapter.add(book);
            Log.v("0","0");
            textview.setVisibility(View.VISIBLE);
            type=Typeface.createFromFile(IM_PATH+"Nazanin.ttf");
            textview.setTypeface(type);
            textview.setText(getResources().getString(R.string.noBooks));
        }else{
            Log.v("hello","1");
            List<String[]> bookList = readData();
            for(String[] bookData:bookList ) {
                Log.v("hello","2");
                String bookImg = bookData[0];
                Log.v("bookImg",bookImg);
                String bookauthor = bookData[1];
                String bookName = bookData[2];
                String id=bookData[3];
                String ta=bookData[4];
                Log.v("fruit id", id);
                Fruit book = new Fruit(bookImg,bookName,bookauthor,ta,id);
                booklist.add(book);


            }
            bookadapter=new BookmAdapter(booklist);

        }
        myDbHelper.close();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        mRecyclerView.setAdapter(bookadapter);


        return rootView;
    }
    List<String[]> readData(){
        List<String[]> resultList = new ArrayList<String[]>();
        try {
            myDbHelper.openDataBase();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        long imagesize=myDbHelper.counttable("images");
        long tablesize=myDbHelper.counttable("epub");
        //   Log.v("table size",String.valueOf(tablesize));
        for(long i=1;i<=tablesize;i++){
            try{	String[] fruit = new String[5];
                fruit[3]=String.valueOf(i);
                //  Log.v("fruit3",fruit[3]);
                //	Log.v("i",String.valueOf(i));
                Epubbooks epubb=myDbHelper.getimage((int) i , "epub");

                for(long k=1;k<=imagesize;k++){
                    Epubbooks img=myDbHelper.getimage((int) k , "images");
                    //Log.v("read isbn=cover",img.getisbn());
                    //	Log.v("read title=",img.gettitle().substring(img.gettitle().lastIndexOf("/")+1,img.gettitle().lastIndexOf(".")));
                    //Log.v("read isbn",epubb.getisbn());
                    if(img.getisbn().equals("cover")&&img.gettitle().substring(img.gettitle().lastIndexOf("/")+1,img.gettitle().lastIndexOf(".")).equals(epubb.getisbn())){
                        fruit[0]=img.getcreater();
                        //fruit[0]=String.valueOf((Integer.valueOf(img.getcreater())+20));
                        Log.v("this IMG",img.getcreater()+" "+img.getisbn()+" "+img.gettitle()+" ");
                        Log.v("this EPUBB",epubb.getcreater()+" "+epubb.getisbn()+" "+epubb.gettitle()+" ");
                        break;
                    }
                }

                fruit[1] = epubb.gettitle();
                fruit[2] = epubb.getcreater();
                fruit[4]=epubb.getisbn();
                resultList.add(fruit);}catch (Throwable gh){}
        }
        return  resultList;
    }

}
