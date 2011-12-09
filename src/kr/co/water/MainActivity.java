package kr.co.water;

import java.util.Calendar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * 메뉴 및 현재 마신 물의 상태를 보여주는 메인 액티비티
 */
public class MainActivity extends BaseActivity {
	private int[] images = {	// 마신양에 따라 보여줄 이미지
			R.drawable.cup,		// 1 단계
			R.drawable.cup,		// 2 단계
			R.drawable.cup,		// 3 단계
			R.drawable.cup,		// 4 단계
			R.drawable.cup,		// 5 단계
			R.drawable.cup,		// 6 단계
			R.drawable.cup,		// 7 단계
			R.drawable.cup,		// 8 단계
			R.drawable.cup,		// 9 단계
			R.drawable.cup,		// 10 단계
			
	};
	private Calendar calendar; // 날짜
	
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
		if(total >= 0 && total < 100){
			
		}else if(total >= 0 && total < 100){
			
		}else if(total >= 100 && total < 200){
			
		}else if(total >= 200 && total < 300){
			
		}else if(total >= 300 && total < 400){
			
		}else if(total >= 400 && total < 500){
			
		}else if(total >= 500 && total < 600){
			
		}else if(total >= 600 && total < 700){
			
		}else if(total >= 700 && total < 800){
			
		}else if(total >= 800 && total < 900){
			
		}else{	// 900 이상이면
			
		}
		
		// 그리기 작업
		
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
			intent = new Intent(this, ChartActivity.class);
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
}
