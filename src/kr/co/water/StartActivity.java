package kr.co.water;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 *	시작 액티비티
 */
public class StartActivity extends BaseActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

    }

	/**
	 *	화면 터치시 다음화면으로...
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {	//화면 터치시
		// TODO Auto-generated method stub
		  if ( event.getAction() == MotionEvent.ACTION_DOWN ){
			 Intent intent =  new Intent(StartActivity.this, MainActivity.class);
			 startActivity(intent);
			 return true;
		  }
		  
		  return super.onTouchEvent(event);
		  
	}		
	

}
