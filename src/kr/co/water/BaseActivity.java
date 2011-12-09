package kr.co.water;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;


/**
 * 기본 설정 클래스 다른 액티비티 클래스의 베이스 클래스가 된다.
 */
public class BaseActivity extends Activity {
	public final static String DB_NAME = "water";
	public final static String DEBUG_TAG = "water";
	/*	preperence */
	public final static String PREFERENCE = "water_preference";
    /* Server setting */


	/** 뒤로가기 버튼 클릭시  */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		//finishDialog(this);
		
	}

	/**
	 * 종료 confirm 다이얼로그 창
	 * @param context
	 */
	public void finishDialog(Context context){
		AlertDialog.Builder ad = new AlertDialog.Builder(context);
		ad.setTitle("").setMessage("프로그램을 종료하시겠습니까?")
		.setPositiveButton("종료", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				moveTaskToBack(true);moveTaskToBack(true);
                finish();
			}
		}).setNegativeButton("취소",null).show();
    }
	
	/**
	 * 회전이 다시 화면을 불러오는걸 방지하기위해 스크린을 잠가준다.
	 */
	public void mLockScreenRotation() {
		// Stop the screen orientation changing during an event
		switch (this.getResources().getConfiguration().orientation) {
		case Configuration.ORIENTATION_PORTRAIT:
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			break;
		case Configuration.ORIENTATION_LANDSCAPE:
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			break;
		}
	}
	
	/**
	 *	화면 고정 해제
	 */
	public void unLockScreenRotation(){
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); 		
	}
}
