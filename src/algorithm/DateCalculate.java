package algorithm;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class DateCalculate {
	//时间相减返回天数
	public static long dateMinus(String starttime, String endtime) {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 long day =0;
		 try {
			Date start = sdf.parse(starttime);
			Date end = sdf.parse(endtime);
			
			long stateTimeLong = start.getTime();
			long endTimeLong = end.getTime();
			
			day = (endTimeLong-stateTimeLong)/(24*60*60*1000);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return day;
		
	}
	//将string类型时间转换为long型
	public static long transDate(String datetime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long dtime =0;
		
		try {
			Date date = sdf.parse(datetime);
			dtime = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dtime;
		
	}
	//时间加上一段时间长度后的时间
	public static String dateAdd(String datetime,int daylength) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long dtime;
		Date backdate;
		String back = null;
		try {
			Date date = sdf.parse(datetime);
			dtime = date.getTime()+daylength* 24 * 60 * 60 * 1000;
			backdate = new Date();
			backdate.setTime(dtime);
			back = sdf.format(backdate);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return back;
		
	}
}
