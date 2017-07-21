package org.eclipse.paho.android.sample.util;

//from ... http://www.javacodegeeks.com/2010/07/java-best-practices-dateformat-in.html
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConcurrentDateFormatAccess {

	private ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat> () {

		@Override
		public DateFormat get() {
			return super.get();
		}

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss");
		}

		@Override
		public void remove() {
			super.remove();
		}

		@Override
		public void set(DateFormat value) {
			super.set(value);
		}

	};

	public Date convertStringToDate(String dateString) throws ParseException {
		return df.get().parse(dateString);
	}
 
	public String convertTodayToString() {
		//df.remove();
		 Calendar cal = Calendar.getInstance();
         cal.add(Calendar.DATE, 365);    
         String maturityDateIn = df.get().format(cal.getTime());

		//return df.get().format(new Date());
         return maturityDateIn;
	}

}
