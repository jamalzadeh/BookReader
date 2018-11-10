package dictonary.mj.dastan;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.epapyrus.plugpdf.SimpleDocumentReader;
import com.epapyrus.plugpdf.SimpleDocumentReaderListener;
import com.epapyrus.plugpdf.SimpleReaderFactory;
import com.epapyrus.plugpdf.core.PDFDocument;
import com.epapyrus.plugpdf.core.PlugPDF;
import com.epapyrus.plugpdf.core.PropertyManager;
import com.epapyrus.plugpdf.core.PlugPDFException.InvalidLicense;
import com.epapyrus.plugpdf.core.PlugPDFException.LicenseMismatchAppID;
import com.epapyrus.plugpdf.core.PlugPDFException.LicenseTrialTimeOut;
import com.epapyrus.plugpdf.core.PlugPDFException.LicenseUnusableOS;
import com.epapyrus.plugpdf.core.PlugPDFException.LicenseWrongProductVersion;
import com.epapyrus.plugpdf.core.annotation.AnnotEventListener;
import com.epapyrus.plugpdf.core.annotation.BaseAnnot;
import com.epapyrus.plugpdf.core.annotation.acroform.BaseField;
import com.epapyrus.plugpdf.core.annotation.acroform.BaseField.FieldState;
import com.epapyrus.plugpdf.core.annotation.acroform.ButtonField;
import com.epapyrus.plugpdf.core.annotation.acroform.CheckBoxField;
import com.epapyrus.plugpdf.core.annotation.acroform.FieldEventListener;
import com.epapyrus.plugpdf.core.annotation.tool.BaseAnnotTool.AnnotToolType;
import com.epapyrus.plugpdf.core.viewer.BasePlugPDFDisplay.PageDisplayMode;
import com.epapyrus.plugpdf.core.viewer.DocumentState;
import com.epapyrus.plugpdf.core.viewer.PageViewListener;
import com.epapyrus.plugpdf.core.viewer.ReaderView;
import com.google.code.microlog4android.Level;













import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class PdfReader extends Activity {
	MotionEvent g;
	DataBaseHelper myDbHelper;
	Insertword datasource;
	long position;
	public static Activity fa;
	 PDFDocument doc;
	public String fileName;
	SimpleDocumentReader v;
	int page;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pdf_reader);
		try {
			PlugPDF.init(getApplicationContext(),
					"A6F52DE2GGB8C4G6ED34H74ED873889G4AB2723H99CDBFGHDF76FBF5");
			PlugPDF.deployAssetFontResource(getApplicationContext());
			PlugPDF.enableUncaughtExceptionHandler();
			PlugPDF.setUpdateCheckEnabled(false);
		} catch (LicenseWrongProductVersion ex) {
			Log.d("LicenseEx", "LicenseWrongProductVersion");
		} catch (LicenseTrialTimeOut ex) {
			Log.d("LicenseEx", "LicenseTrialTimeOut");
		} catch (LicenseUnusableOS ex) {
			Log.d("LicenseEx", "LicenseUnusableOS");
		} catch (LicenseMismatchAppID ex) {
			Log.d("LicenseEx", "LicenseMismatchAppID");
		} catch (InvalidLicense ex) {
			Log.d("LicenseEx", "Invalid License");
		} catch (Exception ex) {
			Log.d("Exception", (ex.getMessage() == null) ? "Unknown Error!" : ex.getMessage());
		}
		PropertyManager.setScrollFrictionCoef(1);
		PropertyManager.setScrollVelocityCoef(1);
		PropertyManager.setPreviewQualityCoef(1.5);
		datasource=new Insertword(this);
	//	CheckBoxField.setGlobalCustomPainter(new annot.MyCheckBoxPainter(this));
		Bundle extras = getIntent().getExtras();
		 fileName=extras.getString("path");
		 myDbHelper = new DataBaseHelper(this);
		 try {
				myDbHelper.openDataBase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 page=Integer.valueOf(myDbHelper.getimage(extras.getInt("position"),"pdf").getTOC());
		 myDbHelper.close();
		 position=(long)extras.getInt("number");
		 ((MyApplication) this.getApplication()).setPath(fileName);
		fa=this;
		datasource=new Insertword(this);
		try {
			InputStream is = new FileInputStream(fileName);;

			int size = is.available();
			if (size > 0) {
				byte[] data = new byte[size];
				is.read(data);
				open(data);
			}

			is.close();
		} catch (Exception ex) {
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pdf_reader, menu);
		return true;
	}
	
	

	protected void open(byte[] data) {
		Log.v("first","hello");
	/*	mViewer = SimpleReaderFactory.createSimpleViewer(this, mViewerListener);
		mViewer.setPageViewListener(mPageViewListener);
		mViewer.openData(data, data.length, "");
		mViewer.goToPage(page);
		Log.v("second","hello");
		mViewer.setPageDisplayMode(PageDisplayMode.HORIZONTAL);*/
	//	String code=mViewer.getDocument().loadAnnotList(1).toString();
		//int id=Integer.valueOf(code.substring(code.lastIndexOf("@"), code.length()+1));
		 v = SimpleReaderFactory.createSimpleViewer(this, null);
		v.openData(data, data.length, "");
		doc=v.getDocument();
		v.goToPage(page);
		v.setPageDisplayMode(PageDisplayMode.VERTICAL);
		Log.v("hello","hello");
		
		
	}

	/*protected void open(String path) {
		mViewer = SimpleReaderFactory.createSimpleViewer(this, mViewerListener);
		mViewer.openFile(path, "");
		mViewer.enableAnnotationMenu(true);
	
		mViewer.setTitle("Sample");
		doc=mViewer.getDocument();
		Log.v("doc",doc.getFilePath());
	}*/
	public void highlight(int page,RectF[] rect){
		doc.insertTextMarkupAnnot(page, rect,(float)255, (float)255, (float)0, "HIGHLIGHT");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		v.refreshLayout();
		super.onConfigurationChanged(newConfig);
	}

	SimpleDocumentReaderListener mViewerListener = new SimpleDocumentReaderListener() {

		@Override
		public void onLoadFinish(DocumentState.OPEN state) {
			v.setAnnotEventLisener(mAnnotEventListener);

			testSetFieldState();
			testSetFieldValue();
		}

		private void testSetFieldValue() {
			ReaderView readerView = v.getReaderView();
			readerView.setFieldValue(0, "TextField1[0]", "TEST3");

			}

		private void testSetFieldState() {
			ReaderView readerView = v.getReaderView();
			readerView.setFieldState(0, "test1", FieldState.READONLY);
			readerView.setFieldState(0, "test2", FieldState.DISABLE);

		

			readerView.setFieldState(0, "test1", FieldState.ENABLE);
			
		}
	};

	AnnotEventListener mAnnotEventListener = new AnnotEventListener() {

		@Override
		public boolean onTapUp(BaseAnnot annot) {
			return false;
		}

		@Override
		public boolean onLongPress(BaseAnnot annot) {
			return false;
		}
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    Log.d(this.getClass().getName(), "back button pressed");
	   
	    MainActivity.fa.finish();
	    Intent intentm = new Intent(PdfReader.this,MainActivity.class);
	   finish();
	    startActivity(intentm);
	}
	return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.v("pause","called");
		datasource.open();
		datasource.updatePdfPage("pdf",position, fileName, fileName.substring(fileName.lastIndexOf("/")+1,fileName.length()), String.valueOf(page));
		datasource.close();
		//ReaderView readerView = v.getReaderView();
		if(v!=null){
		v.saveAsFile(fileName);}
		
		//readerView.saveAsFile(fileName);
	}

	@Override
	protected void onStop() {
		super.onStop();
		//ReaderView readerView = mViewer.getReaderView();
		//readerView.clear();
	};
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    // Read values from the "savedInstanceState"-object and put them in your textview
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    // Save the values you need from your textview into "outState"-object
		
	    super.onSaveInstanceState(outState);
	    
	}
	
	private void printHeapState() {
		if (Runtime.getRuntime() == null) return ;
		long maxMemory = Runtime.getRuntime().maxMemory();
		long totalMemory = Runtime.getRuntime().totalMemory();
		long freeMemory = Runtime.getRuntime().freeMemory();
		long allocMemory = totalMemory - freeMemory;

	
	}

}
