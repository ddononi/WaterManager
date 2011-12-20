package kr.co.water;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DrinkActivity extends Activity {
	private int[] cupImages = {
		R.drawable.water1,
		R.drawable.water2,
		R.drawable.water3,
		R.drawable.water4,
		R.drawable.water5,
		R.drawable.water6

	};
	
	private Calendar calendar;		// 날짜
	
	// 엘리먼트
	private TextView todayTV;	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink);
        
        // 시간 설정
		calendar = Calendar.getInstance();						// 비교할 현재시간
		calendar.setTimeInMillis(System.currentTimeMillis());	// 현재시간 으로 설정
		// 후킹
		todayTV = (TextView)findViewById(R.id.today);
		
		todayTV.setText("오늘 날짜 : " + makeNow());	// 오늘 날짜 설정
		
		// 오늘 마신물 내역보여주기
		showDrunken();
    }
    
    private void showDrunken() {
		// TODO Auto-generated method stub
    	TableLayout tableTL = (TableLayout)findViewById(R.id.table);
		
		DBHelper dbhp =  new DBHelper(this);
		SQLiteDatabase db = dbhp.getReadableDatabase();	// 읽기모도로 해주자
		Cursor cursor = null;
    	// 오늘 마신 물 내역
		cursor = db.query(DBHelper.MAIN_TABLE, null, "date = ?",  new String[]{makeNow(),}, 
				null, null, "idx desc");
		int water, i=0;
		TableRow child;	// 테이블 열
		ImageView cup;	// 마신 물컵을 그릴 이미지뷰 생성
		if( cursor.moveToFirst() ){	// cursor에 row가 1개 이상 있으면 
			child = new TableRow(this); // 테이블 열 생성
			do{
				cup = new ImageView(this);
				water = cursor.getInt( cursor.getColumnIndex("water") );
				// 해당하는 리소스 아이디를 얻음
				int resid =cupImages[getIndex(water)];
				cup.setBackgroundResource(resid);
				child.addView(cup);
				i++;
				Log.i("water", i + "");
				if( i % 3 == 0){	// 한 열에 3개씩
					tableTL.addView(child);
					child = new TableRow(this);
					Log.i("water", "TableRow");
				}
				if(cursor.isLast() ){	// 마지막이면 3개가 채워지지 않더라고 테이블에 붙여준다.
					Log.i("water", "last");
					//	3개 이하면 컵 모양이 늘어나는걸 방지하기 위해  나머지 빈 이미지를 채운다.
					if(cursor.getCount() < 3){	
						Log.i("water", "1~2");
						cup = new ImageView(this);
						cup.setBackgroundResource(resid);
						cup.setVisibility(View.INVISIBLE);
						child.addView(cup);
						if(cursor.getCount() == 1){	// 컵이 한개면 하나 더 채운다.
							cup = new ImageView(this);
							cup.setBackgroundResource(resid);
							cup.setVisibility(View.INVISIBLE);					
							child.addView(cup);
						}
					}
					tableTL.addView(child);
				}
			}while( cursor.moveToNext() );	// 다음 커서가 있으면 내용을 가져온다.
		}
		
    	// 디비는 꼭 닫아준다.
		db.close();
		dbhp.close(); 
    	
	}

	/**
	 * 물의 순번 반환
	 * @param water
	 */
	private int getIndex(int water) {
		// TODO Auto-generated method stub
		int index = 0;
    	switch(water){
    	case 50:
    		index = 0;
    		break;
    	case 150:
    		index = 1;
    		break;
    	case 200:
    		index = 2;
    		break;
    	case 500:
    		index = 3;
    		break;
    	case 700:
    		index = 4;
    		break;
    	case 1000:
    		index = 5;
    		break;
 		
    	}		
		return index;
	}
	
    public void mOnClick(View v){
    	/*
    	switch(v.getId()){
    	}
    	*/
		Intent intent = new Intent(this, SelectActivity.class);
		startActivity(intent);
		finish();
    }

	/**
     * 오늘 날짜 만들기
     * @return
     * 	오늘 날짜
     */
    private String makeNow(){
    	calendar.get(Calendar.YEAR);
    	
    	int y = calendar.get(Calendar.YEAR);
    	int m = calendar.get(Calendar.MONDAY) +1;	// 달은 0 -11 
    	int d = calendar.get(Calendar.DAY_OF_MONTH);
    	
    	return String.format("%04d-%02d-%02d", y, m, d);
    }
        

}
