package kr.co.water;

import java.util.Calendar;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 *	선택한 물을 디비에 저장하는 액티비티
 */
public class SelectActivity extends Activity {
	private Calendar calendar;		// 날짜
	private int selectWater = 0;	// 선택한 물의 양
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        
        // 시간 설정
		calendar = Calendar.getInstance();						// 비교할 현재시간
		calendar.setTimeInMillis(System.currentTimeMillis());	// 현재시간 으로 설정
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
    
    /**
     * 해당 버튼의 물의 양을 가져와 디비에 저장
     */
    public void mOnClick(View v){
    	switch(v.getId()){
    	case R.id.w50:
    		selectWater = 50;
    		break;
    	case R.id.w150:
    		selectWater = 150;
    		break;
    	case R.id.w200:
    		selectWater = 200;
    		break;
    	case R.id.w500:
    		selectWater = 500;
    		break;
    	case R.id.w700:
    		selectWater = 700;
    		break;
    	case R.id.w1000:
    		selectWater = 1000;
    		break;
  				
    	}
    	
    	// 마신 물이 정상적으로 insert 되면
    	if( drinkWater(selectWater) > 0 ){
    		// 메인 엑티비티로
    		Toast.makeText(this, selectWater + "mL를 마셨습니다♪", Toast.LENGTH_SHORT).show();
    	}
    	finish();	// 현재 액티비티 종료

    }

	/**
	 * 마신물을 디비에 저장
	 * @param water
	 * 	마신 물의 양
	 */
	private long drinkWater(int water) {
		// TODO Auto-generated method stub
		long result = 0;
		DBHelper dbhp =  new DBHelper(this);
		SQLiteDatabase db = dbhp.getWritableDatabase();	// 쓰기모드
		ContentValues cv = new ContentValues();
		
		cv.put("water", water);	// 마신양
		cv.put("date", makeNow());	// 날짜
		result = db.insert(DBHelper.MAIN_TABLE, null, cv);
		// 디비는 꼭 닫아준다.
		db.close();
		dbhp.close(); 
		return result;
	}
}

