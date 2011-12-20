package kr.co.water;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.ToggleButton;

/**
 *	알람 설정을 위한 액티비티
 */
public class AlarmActivity extends BaseActivity implements 
				OnCheckedChangeListener, OnTimeChangedListener, OnClickListener{
	private SharedPreferences sp;	// 공유 환경설정

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        sp = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);

		// 후킹
		TimePicker alarmTP = (TimePicker)findViewById(R.id.timepicker);
		ToggleButton soundTB = (ToggleButton)findViewById(R.id.sound_toggle);
		ToggleButton vibrationTB = (ToggleButton)findViewById(R.id.vibration_toggle);
		Button startBtn = (Button)findViewById(R.id.start_btn);
		Button stopBtn = (Button)findViewById(R.id.stop_btn);
		
		// 시간 초기화
		alarmTP.setCurrentHour(0);
		alarmTP.setCurrentMinute(0);
		alarmTP.setIs24HourView(true);
		
		// 체크 설정
		soundTB.setOnCheckedChangeListener(this);
		soundTB.setChecked(sp.getBoolean("sound", true));
		vibrationTB.setOnCheckedChangeListener(this);
		vibrationTB.setChecked(sp.getBoolean("vibration", true));
		
		// 타임피커 이벤트 설정
		alarmTP.setOnTimeChangedListener(this);
		
		// 버튼 이벤트 설정
		startBtn.setOnClickListener(this);
		stopBtn.setOnClickListener(this);
		
    }
    /**
     *	토클버튼의 토글시 공유설정에 저장
     */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		// 소리 알람이면
		SharedPreferences.Editor editor = sp.edit();
		if( buttonView.getId() == R.id.sound_toggle ){
			// 공유설정 환경에 저장
			editor = sp.edit();
			editor.putBoolean("sound", isChecked);
		}else if( buttonView.getId() == R.id.vibration_toggle ){
			// 공유설정 환경에 저장
			editor = sp.edit();
			editor.putBoolean("vibration", isChecked);
		}
		
		editor.commit();

	}
	/**
	 * 시간 변경시 공유 설정에 저장
	 */
	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		// 시간 저장
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("hour", hourOfDay);
		editor.putInt("min", minute);
		editor.commit();
	}
	
	/**
	 * 알람 서비스 시작 or 중지
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SharedPreferences.Editor editor = sp.edit();
		boolean alarm;
		CharSequence msg;
		if(v.getId() == R.id.start_btn){	// 알람 시작
			alarm = true;
			// 알람 시작
			Intent serviceIntent = new Intent(this, AlarmService.class);
			stopService(serviceIntent);
			startService(serviceIntent);
			msg = "알람 시작♪";
		}else{
			alarm = false;
			msg = "알람을  종료합니다.";
			//	서비스명을 통한 서비스 종료 
			ComponentName service = startService(new Intent(this, AlarmService.class));
			try {
				Class<?> serviceClass = Class.forName(service.getClassName());
				stopService(new Intent(this, serviceClass));
			} catch (ClassNotFoundException e) {
			}			
	

		}
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		editor.putBoolean("alarm", alarm);
		editor.commit();
		finish();
	}
}
