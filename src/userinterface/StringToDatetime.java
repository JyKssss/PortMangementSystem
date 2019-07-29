package userinterface;

import java.sql.SQLData;
import java.sql.SQLDataException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDatetime {
	static java.sql.Date sqlDate = null;
	public static java.sql.Date transStringToDatetime(String stringdatetime) {
		// TODO Auto-generated constructor stub
		Date returndatetime = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String format = simpleDateFormat.format(new Date());
		try {
			returndatetime= simpleDateFormat.parse(stringdatetime);
			sqlDate = new java.sql.Date(returndatetime.getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sqlDate;
	}
}

