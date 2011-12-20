package kr.co.water;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kr.co.water.chart.AbstractDemoChart;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;

/**
 *	차트 설정 
 */
public class MyChart extends AbstractDemoChart {
	private static final int WEEK = -6;	// 최근 일주일
	/**
	 * Returns the chart name.
	 * 
	 * @return the chart name
	 */
	public String getName() {
		return "최근 일주일 마신 물 양";
	}

	/**
	 * Returns the chart description.
	 * 
	 * @return the chart description
	 */
	public String getDesc() {
		return "최근 일주일간 마신 물을 나타냅니다.";
	}

	public XYMultipleSeriesRenderer getBarRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(28);
		renderer.setLabelsTextSize(10);
		renderer.setLegendTextSize(10);
		renderer.setMargins(new int[] { 50, 50, 50, 0 });
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(Color.argb(250, 0, 210, 250));
		renderer.addSeriesRenderer(r);
		renderer.setInScroll(false);
		setChartSettings(renderer);
		return renderer;
	}

	private void setChartSettings(XYMultipleSeriesRenderer renderer) {
		// 타이틀 설정
		renderer.setChartTitle("최근 일주일 물 마신 양");
		renderer.setXTitle("날짜( 최근 일주일전)");
		renderer.setYTitle("내가 마신 물");
	    renderer.setBarSpacing(1);

		// 화면에 보여줄 날짜 설정
		renderer.setXAxisMax(1);	// 1일후까지
		renderer.setXAxisMin(-7);	// 7일 전까지
	}
	
	/**
	 * 
	 * 차트 데이터 설정
	 * DB에서 마신 물의 데이터를 차트에 넣어준다.
	 * @param context
	 * @return
	 */
	private XYMultipleSeriesDataset getBarDataset(Context context) {
		    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		    XYSeries waterSeries = new XYSeries("마신물의 양");
		    
		    // DB에 저장된 마신물 검색
		    DBHelper dbhp = new DBHelper(context);
		    SQLiteDatabase db = dbhp.getReadableDatabase();
		    int water, total = 0;
		    // 최근 일주일의 데이터를 가져오기 위해 검색조건을 6일전으로 한다.			
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal, tempCal;	// 오늘날짜의 캘린더, 복제 캘린더
			cal = Calendar.getInstance();	// 오늘 날짜를 가져온다.		
			Cursor cursor = null;
			for(int i=WEEK; i<=0; i++){	// 6일전부터 오늘까지 검색
				// 캘린터를 복제한후 복제된 캘린더로 날짜를 빼준다.
				tempCal = (Calendar)cal.clone();	
				// 날짜를 i만큼 빼준다.
				tempCal.add(Calendar.DAY_OF_MONTH, i);	
			    String whereDate = sdf.format(tempCal.getTime());	// 검색할 날		
			    Log.i(BaseActivity.DEBUG_TAG , "검색할날짜-->" + whereDate);
			    // 해당날짜 쿼리 검색
			    cursor = db.rawQuery("select * from " + 
		    			DBHelper.MAIN_TABLE + " where date like ? ", new String[]{whereDate, });				
				if (cursor.moveToFirst()) { // 해당날에 마신 물이 있으면 마신물을 더한다.
					do {
						// 마신물 검색
						water = cursor.getInt(cursor.getColumnIndex("water"));
						total += water;	
					} while (cursor.moveToNext()); // 다음 커서가 있으면 내용을 가져온다.
					Log.i(BaseActivity.DEBUG_TAG , "마신물-->" + total);
					waterSeries.add(i, total);
				}else{	// 마신물이 없으면.. 0으로
					waterSeries.add(i, 0);
				}		    	
				total = 0;	// 마신물 초기화
			}

			// 디비 close
		    cursor.close();
		    dbhp.close();
		    // 데이터 셋 설정
	        dataset.addSeries(0, waterSeries);		    

		    return dataset;
		  }	

	/**
	 * 차트 실행 
	 * 
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public Intent execute(Context context) {
		// 랜더러 설정
		XYMultipleSeriesRenderer renderer = getBarRenderer();
		return ChartFactory.getBarChartIntent(context,
				getBarDataset(context), renderer, Type.DEFAULT);
	}

}
