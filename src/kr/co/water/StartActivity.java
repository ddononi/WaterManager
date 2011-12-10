package kr.co.water;

import kr.co.water.AlarmService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
        doStartService();
    }
    

	/**
	 * 공유설정에서 알람이 체크되어 있는지 확인 후
	 * 알람 서비스 시작
	 */
	private void doStartService() {
		// TODO Auto-generated method stub
		// 서비스로 알람설정
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		boolean isSetAlarm = sp.getBoolean("alarm", false);	
		if(isSetAlarm){
			Intent serviceIntent = new Intent(this, AlarmService.class);
			stopService(serviceIntent);
			startService(serviceIntent);
			Log.i(DEBUG_TAG, "service start!!");
		}	
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
