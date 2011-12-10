package kr.co.water;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

/**
 *	공유설정에서 알람시간을 가져와 
 *	알람을 실행하고 알람시간이 되면 브로드캐스팅을 한다. 
 */
public class AlarmService extends Service {
	private Calendar calendar = null;			// 현재시간
	
	/** 서비스가 실행될때  */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		calendar = Calendar.getInstance();						// 비교할 현재시간
		calendar.setTimeInMillis(System.currentTimeMillis());	// 현재시간 으로 설정
		
		// 공유설정에 저장된 알람 시간 가져오기 
		SharedPreferences sp = getSharedPreferences(BaseActivity.PREFERENCE, Context.MODE_PRIVATE);
		// 알람 시간 셋팅
		calendar.add(Calendar.HOUR_OF_DAY, sp.getInt("hour", 1));
		calendar.add(Calendar.MINUTE, sp.getInt("min", 1));
		//before.
		Log.i(BaseActivity.DEBUG_TAG,
				"알람시간 : " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +
							 calendar.get(Calendar.MINUTE));
		// 시스템서비스에서 알람매니져를 얻어온다.
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		// 브로드케스트 리시버에 보낼 팬딩인텐트, 이전 팬딩인텐트가 있으면 취소하고 새로 실행
		Intent i = new Intent(getBaseContext(), AlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(getBaseContext(),
				0,  i, PendingIntent.FLAG_CANCEL_CURRENT);
		am.set(AlarmManager.RTC_WAKEUP,	calendar.getTimeInMillis(), sender);	// 알람설정		
		Log.i("dservice", "onstartCommand");
		return 0;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.i("dservice", "stop!");
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	

}
