package org.eclipse.paho.android.sample.util;

import android.util.Log;

public class EnsLogUtil
{
	//private static String[] mTAGs;
	
	//static{
	//	mTAGs = new String[]{
	//		GisMainActivity.class.getSimpleName(),  // 0 
	//		GisEditActivity.class.getSimpleName(), //1
	//		LayerManager.class.getSimpleName(),     //2  
	//	};
	//}
	
	public static final void logD(final String tag, final String message)
	{
		logD(tag, "%s", message);
		//Log.d(tag, message);
		return;
	}
	
	public static final void logI(final String tag, final String message)
	{
		logI(tag, "%s", message);
		//Log.d(tag, message);
		return;
	}
	
	public static final void logE(final String tag, final String message)
	{
		logE(tag, "%s", message);
		//Log.d(tag, message);
		return;
	}
	
	public static final void logW(final String tag, final String message)
	{
		logW(tag, "%s", message);
		//Log.d(tag, message);
		return;
	}
	
	//private static final SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	//private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	//private static final Date dateFormat = new Date();
	
	//private static int lock[] = new int[1];
	
	public static final void logD(final String tag, final String format_, final Object ... args)
	{
		
		ConcurrentDateFormatAccess cdf = new ConcurrentDateFormatAccess();
		
		//String dateStr = dateFormat.format(new Date()) ;
		String dateStr = cdf.convertTodayToString() ;
		String format = "[%s]" + format_;
		
		Object cpargs[] = new Object[args.length+1];
		cpargs[0] = dateStr;
		
		System.arraycopy(args, 0, cpargs, 1, args.length);
		
		//synchronized(lock)
		//{
			String formtted = String.format(format, cpargs);
			//String formtted = String.format(format, args);
			Log.d(tag, formtted);
			
			formtted = null;
		//}
		
		
		
		
		cpargs = null;
		//dateStr = null;
		format = null;

		return;
	}
	
	public static final void logI(final String tag, final String format_, final Object ... args)
	{
		
		ConcurrentDateFormatAccess cdf = new ConcurrentDateFormatAccess();
		
		//String dateStr = dateFormat.format(new Date()) ;
		String dateStr = cdf.convertTodayToString() ;
		String format = "[%s]" + format_;
		
		Object cpargs[] = new Object[args.length+1];
		cpargs[0] = dateStr;
		
		System.arraycopy(args, 0, cpargs, 1, args.length);
		
		//synchronized(lock)
		//{
			String formtted = String.format(format, cpargs);
			//String formtted = String.format(format, args);
			Log.i(tag, formtted);
			
			formtted = null;
		//}
		
		
		
		
		cpargs = null;
		//dateStr = null;
		format = null;

		return;
	}
	
	public static final void logE(final String tag, final String format_, final Object ... args)
	{
		
		ConcurrentDateFormatAccess cdf = new ConcurrentDateFormatAccess();
		
		//String dateStr = dateFormat.format(new Date()) ;
		String dateStr = cdf.convertTodayToString() ;
		String format = "[%s]" + format_;
		
		Object cpargs[] = new Object[args.length+1];
		cpargs[0] = dateStr;
		
		System.arraycopy(args, 0, cpargs, 1, args.length);
		
		//synchronized(lock)
		//{
			String formtted = String.format(format, cpargs);
			//String formtted = String.format(format, args);
			Log.e(tag, formtted);
			
			formtted = null;
		//}
		
		
		
		
		cpargs = null;
		//dateStr = null;
		format = null;

		return;
	}
	
	public static final void logW(final String tag, final String format_, final Object ... args)
	{
		
		ConcurrentDateFormatAccess cdf = new ConcurrentDateFormatAccess();
		
		//String dateStr = dateFormat.format(new Date()) ;
		String dateStr = cdf.convertTodayToString() ;
		String format = "[%s]" + format_;
		
		Object cpargs[] = new Object[args.length+1];
		cpargs[0] = dateStr;
		
		System.arraycopy(args, 0, cpargs, 1, args.length);
		
		//synchronized(lock)
		//{
			String formtted = String.format(format, cpargs);
			//String formtted = String.format(format, args);
			Log.w(tag, formtted);
			
			formtted = null;
		//}
		
		
		
		
		cpargs = null;
		//dateStr = null;
		format = null;

		return;
	}
	
	
	public static final void logErrorStack(String tag, Exception e)
	{
		StackTraceElement elem[] = e.getStackTrace();
		for(int i = 0; i < elem.length; i++)
		{
			logD(tag, "ERROR......[%s]", elem[i].toString());
		}

		//logD(tag, "ERROR......[%s]", e.getMessage());
	}
	
	public static void printStackTrace(final String tag, final Throwable e)
	{
		String innerTag = "EnsErrorLog";
		
		//if (!AlopexApplication.sSigned) {
		Log.w(innerTag, Log.getStackTraceString(e));
		//}
	}
}