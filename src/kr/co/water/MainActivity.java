package kr.co.water;

import java.util.Calendar;
import kr.co.water.chart.IDemoChart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 메뉴 및 현재 마신 물의 상태를 보여주는 메인 액티비티
 */
public class MainActivity extends BaseActivity {
	
	private Calendar calendar; 	// 날짜
	private ImageView personIV;	// 사람 이미지
	
	// 엘리먼트
	private TextView todayWaterTV;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 시간 설정
		calendar = Calendar.getInstance(); // 비교할 현재시간
		calendar.setTimeInMillis(System.currentTimeMillis()); // 현재시간 으로 설정
		
		// hocking
		todayWaterTV = (TextView) findViewById(R.id.today_water_txt);
		personIV = (ImageView) findViewById(R.id.person);
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 현재 마신 물 
		int total = todayDrunken();
		todayWaterTV.setText("현재 마신물 : " + total + "mL");
		drawMyBody(total);
		
	}



	/**
	 * 마신양에 따라 몸의 이미지 변경
	 * @param total
	 * 	총 마신양
	 */
	private void drawMyBody(int total) {
		// TODO Auto-generated method stub
		int r;
		if(total >= 0 && total < 150){
			r = R.drawable.p1;
		}else if(total >= 150 && total < 300){
			r = R.drawable.p2;
		}else if(total >= 300 && total < 500){
			r = R.drawable.p3;
		}else if(total >= 500 && total < 700){
			r = R.drawable.p4;
		}else if(total >= 700 && total < 850){
			r = R.drawable.p5;
		}else if(total >= 850 && total < 1000){
			r = R.drawable.p6;
		}else if(total >= 1000 && total < 1150){
			r = R.drawable.p7;
		}else if(total >= 1150 && total < 1300){
			r = R.drawable.p8;
		}else if(total >= 1300 && total < 1500){
			r = R.drawable.p9;
		}else if(total >= 1500 && total < 1700){
			r = R.drawable.p10;
		}else if(total >= 1700 && total < 1900){
			r = R.drawable.p11;
		}else if(total >= 1900 && total < 2000){
			r = R.drawable.p12;
		}else{
			r = R.drawable.p13;
		}
		
		// 그리기 작업
		personIV.setImageResource(r);
	}



	/**
	 * 오늘 날짜 만들기
	 * 
	 * @return 오늘 날짜
	 */
	private String makeNow() {
		calendar.get(Calendar.YEAR);

		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONDAY) + 1; // 달은 0 -11
		int d = calendar.get(Calendar.DAY_OF_MONTH);

		return String.format("%04d-%02d-%02d", y, m, d);
	}
	
	/**
	 * 버튼 클릭시 해당 인텐로 넘김
	 */
	public void mOnClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.drink_btn: // 물 마시기
			intent = new Intent(this, DrinkActivity.class);
			break;
		case R.id.alarm_btn: // 알람 설정
			intent = new Intent(this, AlarmActivity.class);
			break;
		case R.id.chart_btn: // 일주일간 마신 물의 차트
			IDemoChart chart = new MyChart();
			intent =  chart.execute(this);

			break;
		case R.id.intro_btn: // 앱 소개
			intent = new Intent(this, IntroActivity.class);
			break;
		}
		// 해당 액티비티 시작
		startActivity(intent);

	}

	/**
	 * 오늘 마신 물의 양을 가져온다.
	 */
	private int todayDrunken() {
		// TODO Auto-generated method stub
		int total = 0, water = 0;
		DBHelper dbhp = new DBHelper(this);
		SQLiteDatabase db = dbhp.getReadableDatabase(); // 읽기모도로 해주자
		Cursor cursor = null;
		// 오늘 마신 물 내역
		cursor = db.query(DBHelper.MAIN_TABLE, null, "date = ?",
				new String[] { makeNow(), }, null, null, null);
		if (cursor.moveToFirst()) { // cursor에 row가 1개 이상 있으면
			do {
				water = cursor.getInt(cursor.getColumnIndex("water"));
				total += water;
			} while (cursor.moveToNext()); // 다음 커서가 있으면 내용을 가져온다.
		}

		// 디비는 꼭 닫아준다.
		db.close();
		dbhp.close();

		return total;
	}
	
	/** 뒤로가기 버튼 클릭시  앱 종료 */
	@Override
	public void onBackPressed() {
		finishDialog(this);
		
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
}
