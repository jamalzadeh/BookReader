package dictonary.mj.dastan;

import java.util.Locale;




import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

public class TTS extends Fragment implements
TextToSpeech.OnInitListener {
	public String text;
	  private DummyTask mTask;
	public TextToSpeech tts;
	 @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    Log.v("tts","bound");
	    tts = new TextToSpeech(activity, this);
	 }
	public void setText(String tex){
		this.text=tex;
	}
	public void Stop(){
		mTask.cancel(true);
	}
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // Retain this fragment across configuration changes.
	    setRetainInstance(true);
	    tts = new TextToSpeech(getActivity(), this);
	    speakOut(text);
	    // Create and execute the background task.
	    mTask = new DummyTask();
	    mTask.execute();
	  }
	private class DummyTask extends AsyncTask<Void, Integer, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			speakOut(text);
			return null;
		}
		
	}//end dummytask
	@Override
	public void onInit(int status) {
		 if (status == TextToSpeech.SUCCESS) {
			 
	            int result = tts.setLanguage(Locale.US);
	 
	            if (result == TextToSpeech.LANG_MISSING_DATA
	                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
	                Log.e("TTS", "This Language is not supported");
	            } else {
	               // btnSpeak.setEnabled(true);
	               // speakOut();
	            }
	 
	        } else {
	            Log.e("TTS", "Initilization Failed!");
	        }
		
	}
	public void speakOut(String text) {
		 
        
		Log.v("start","tts");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
	public void stoptts(){
		  if (tts != null) {
			  Log.v("not","null");
	            tts.stop();
	           // tts.shutdown();
	        }
	  }
}
