package dictonary.mj.dastan;

/**
 * Created by Milad on 8/16/2015.
 */
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
public class BookmAdapter extends RecyclerView.Adapter<BookmAdapter.bookViewHolder>  {

        // each data item is just a string in this case
        private List<Fruit> bookList = new ArrayList<Fruit>();
    public static String IM_PATH= "/data/data/dictonary.mj.dastan/images/" ;
        String Table;
    Insertword datasource;
    DataBaseHelper myDbHelper;
Context contexta;
    ArrayList<Epubbooks> bookq=new ArrayList<Epubbooks>();
    AlertDialog.Builder builder;
    public  class bookViewHolder extends RecyclerView.ViewHolder {
            ImageView bookImg;
            TextView bookName;
            TextView bookauthor;
            ImageView more;
            public bookViewHolder(View v) {
                super(v);
                bookImg = (ImageView) v.findViewById(R.id.bookImg);
                bookName = (TextView) v.findViewById(R.id.bookName);
                bookauthor = (TextView) v.findViewById(R.id.bookauthor);
                more = (ImageView) v.findViewById(R.id.more);
            }
        }
        public void add(Fruit object) {
            bookList.add(object);
        }
        // Provide a suitable constructor (depends on the kind of dataset)
        public BookmAdapter(List<Fruit> bookList) {
            this.bookList=bookList;

        }
        @Override
        public int getItemCount() {
            return bookList.size();
        }
        // Create new views (invoked by the layout manager)
        @Override
        public bookViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            contexta=parent.getContext();
            myDbHelper = new DataBaseHelper(contexta);
            datasource=new Insertword(contexta);
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_row_layout, parent, false);
            // set the view's size, margins, paddings and layout parameters
            bookViewHolder vh = new bookViewHolder( v);
            return vh;
        }
        @Override
        public void onBindViewHolder(bookViewHolder holder, int position) {

            Fruit ci=bookList.get(position);
            holder.bookName.setText(ci.getFruitName());
            holder.bookName.setTag(holder);
            holder.bookauthor.setTag(holder);
            holder.bookImg.setTag(holder);
holder.bookauthor.setText(ci.getCalories());
            File file = new File(IM_PATH+ci.getFruitImg());
            Log.v("image",ci.getFruitImg());
            if(file.exists()){
                Drawable test;
                test=Drawable.createFromPath(IM_PATH+ci.getFruitImg());

                holder.bookImg.setImageDrawable(test);
                holder.bookImg.getLayoutParams().width = 80;
                holder.bookImg.getLayoutParams().height = 120;
                holder.bookImg.setAdjustViewBounds(true);

            }else{

                holder.bookImg.setImageResource(R.drawable.epub2);
                holder.bookImg.getLayoutParams().width = 80;
                holder.bookImg.getLayoutParams().height = 120;
                holder.bookImg.setAdjustViewBounds(true);
            }
            builder = new AlertDialog.Builder(contexta);

            builder.setTitle(contexta.getResources().getString(R.string.bookdelete));
            builder.setMessage(contexta.getResources().getString(R.string.sure));
            Log.v("set button",String.valueOf(position));
            //  builder.setPositiveButton(contexta.getResources().getString(R.string.yes),new OnClickListener2(position));
		  /*  builder.setPositiveButton(contexta.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

		        public void onClick(DialogInterface dialog, int which) {
		            // Do nothing but close the dialog
		        	int gh=position;
		        	Log.v("clicked tablename",viewHolder.Table);
		        	bookdelete(viewHolder.Table);
		            dialog.dismiss();
		        }

		    });*/
            builder.setNegativeButton(contexta.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                }
            });

            holder.more.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {


                    PopupMenu popup = new PopupMenu(contexta, v);
                    popup.getMenuInflater().inflate(R.menu.popup_menu,
                            popup.getMenu());
                    popup.show();

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.remove:


                                    AlertDialog alert = builder.create();
                                    alert.show();


                                    break;


                                default:
                                    break;
                            }

                            return true;
                        }
                    });


                }
            });
            holder.more.setOnClickListener((OnClickListener) new OnClickListener2(position));
            holder.bookImg.setOnClickListener(clickListener);
            holder.bookauthor.setOnClickListener(clickListener);
            holder.bookName.setOnClickListener(clickListener);
        }
    public void	bookdelete(String table,int Lposition){
        Log.v("deleted book", table);
        try {
            myDbHelper.openDataBase();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long imagesize=myDbHelper.counttable("images");
        for(long f=1;f<=imagesize;f++){
            Epubbooks imgr=myDbHelper.getimage((int) f, "images");

            if(imgr.gettitle().equals(table)){
                File file = new File(IM_PATH+imgr.getcreater());
                boolean deleted = file.delete();
            }//end if
        }//end first for
        datasource.open();

        datasource.deletetable(table);

        updatetable(Lposition);
        //	datasource.deleterow("epub", position);
        long status_size=myDbHelper.counttable("epub_status");

        for(long b=1;b<=status_size;b++){
            Matns state=myDbHelper.getmatn((int) b, "epub_status") ;
            if(state._matn.equals(table)){
                datasource.updateMatn(b,"download_ready","epub_status");
                break;
            }
        }
        bookList.remove(Lposition-1);
        notifyItemRemoved(Lposition);


    }
    public void updatetable(int pos){
        long size=myDbHelper.counttable("epub");


        for(long i=1;i<=size;i++){


            if(i!=pos){
                Epubbooks bookd=myDbHelper.getimage((int) i,"epub");
                if(bookd!=null){
                    bookq.add(bookd);

                }
            }
        }
        datasource.deletetable("epub");

        datasource.createepubtable();
        if(bookq!=null){
            for(int i=0;i<bookq.size();i++){
                Epubbooks a=bookq.get(i);
                //  Log.v("new table name",a.getisbn());
                datasource.createepubbooks("epub",a.getisbn(),a.gettitle(),a.getcreater(),a.getTOC());
            }
        }
    }
    public Fruit getItem(int index) {
        return this.bookList.get(index);
    }
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bookViewHolder holder=(bookViewHolder) view.getTag();
            int position = holder.getAdapterPosition();
            int page=0;
    	    int Position;
            Position=position;
            // Log.v("position",Integer.toString(position));
            //Log.v("array size=",String.valueOf(bookArrayAdapter.getCount()));
            if(getItem(position).getid().equals("0")){

            }else{

                //   Intent mIntent=new Intent(context,BookPager.class);
                //	mIntent.putExtra("shomare_dars",page);
                //	mIntent.putExtra("table",tablename);
                //  int ids= Integer.valueOf( bookArrayAdapter.getItem(position).getid());
                //mIntent.putExtra("position",position+1);
                //startActivity(mIntent);

String tablename;
                Epubbooks book=myDbHelper.getimage(position+1,"epub");
                tablename=book.getisbn();
                try{
                    long size= myDbHelper.counttable("last_page");
                    for(long j=1;j<=size;j++){
                        //  Log.v("tablename1",myDbHelper.getContact((int) j,"last_page")._1name);
                        if(myDbHelper.getContact((int) j,"last_page")._1name.equals(tablename)){
                            page=Integer.valueOf(myDbHelper.getContact((int) j,"last_page")._2name);
                            // Log.v("found","first");
                            break;
                        }
                    }
                }catch (Throwable e){

                }
                Intent mIntent;
                if(page==0){
                    mIntent=new Intent(contexta,MainActivity3B.class);
                    mIntent.putExtra("shomare_dars",1);

                    mIntent.putExtra("table",tablename);

                    contexta.startActivity(mIntent);
                }else{
                    mIntent=new Intent(contexta,MainActivity3B.class);
                    mIntent.putExtra("shomare_dars",page);
                    mIntent.putExtra("table",tablename);

                    contexta.startActivity(mIntent);
                }//if-else
            }//if-else
        }

    };

    private class OnClickListener2 implements OnClickListener {

        private int mPosition;

        private OnClickListener2(int position){
            mPosition = position;
        }


        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            PopupMenu popup = new PopupMenu(contexta, v);
            popup.getMenuInflater().inflate(R.menu.popup_menu,
                    popup.getMenu());
            popup.show();

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.remove:
                            AlertDialog.Builder builder2;
                            builder2 = new AlertDialog.Builder(contexta);

                            builder2.setTitle(contexta.getResources().getString(R.string.bookdelete));
                            builder2.setMessage(contexta.getResources().getString(R.string.sure));
                            builder2.setPositiveButton(contexta.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog
                                    int gh=mPosition;
                                    Log.v("mPosition",String.valueOf(mPosition));
                                    Fruit fruit = getItem(mPosition);

                                    String innername=fruit.getTableName();
                                    Log.v("clicked tablename",innername);

                                    bookdelete(innername,mPosition+1);
                                    dialog.dismiss();
                                }

                            });
                            builder2.setNegativeButton(contexta.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing
                                    dialog.dismiss();
                                }
                            });


                            AlertDialog alert = builder2.create();
                            alert.show();


                            break;


                        default:
                            break;
                    }

                    return true;
                }
            });




            //	bookdelete(innername);
            //  arg0.dismiss();

        }
    }
    }

