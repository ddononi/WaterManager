package kr.co.water;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// SQLiteOpenHelper 재정의 클래스
public class DBHelper extends SQLiteOpenHelper {
	public static final String MAIN_TABLE = "water_manager";	// 테이블 이름
	public static final int DB_VER = 1;							// DB 버젼
	public DBHelper(Context context){
		// 디비명 및 버젼 설정
		super(context, "waterManager.db", null, DB_VER);
	}

	/** 디비가 생성시 테이블을 만들어준다. */
	@Override
	public void onCreate(SQLiteDatabase db) {	// db가 생성될때 테이블도 생성
		// TODO Auto-generated method stub
		// 마신 물의 내역을 저장할 테이블
		// 인덱스 , 물의 양, 날짜
		String sql = "CREATE TABLE "+ MAIN_TABLE + " (idx INTEGER, " +
				     " water INTEGER NOT NULL, date TEXT NOT NULL);";
		db.execSQL(sql);
	}
	

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}