package org.eclipse.paho.android.sample.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class Utils {
	
	// ===========================================================
    // Constants
    // ===========================================================
	private static final String TAG = Utils.class.getSimpleName();
	
	// ===========================================================
    // Fields
    // ===========================================================
    
	
    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
	
	
	// ===========================================================
    // Methods
    // ===========================================================
	/**
	 * Rooting Check
	 * 
	 * Check this device is rooted or not 
	 * @param context
	 * @return
	 */
	public static boolean checkRootingState(Context context) {

		final String EXTERNAL_ROOT_PATH = Environment.getExternalStorageDirectory() + "";
		final String ROOTING_PATH_1 = "/system/bin/su";
		final String ROOTING_PATH_2 = "/system/xbin/su";
		final String ROOTING_PATH_3 = "/system/app/SuperUser.apk";
		final String ROOTING_PATH_4 = context.getFilesDir().getPath()
				+ "data/com.noshufou.android.su";
		 

		String[] rootFilesPath = new String[] { 
				ROOTING_PATH_1,
				ROOTING_PATH_2, 
				ROOTING_PATH_3,
				ROOTING_PATH_4, 
				EXTERNAL_ROOT_PATH + ROOTING_PATH_1,
				EXTERNAL_ROOT_PATH + ROOTING_PATH_2, 
				EXTERNAL_ROOT_PATH + ROOTING_PATH_3,
				EXTERNAL_ROOT_PATH + ROOTING_PATH_4 };

		boolean isRootingFlag = false;

		try {
			Runtime.getRuntime().exec("su");
			isRootingFlag = true;
		} catch (Exception e) {
			isRootingFlag = false;
		}

		if (!isRootingFlag) {
			isRootingFlag = checkRootingFiles(createFiles(rootFilesPath));
		}

		return isRootingFlag;

	}

	
	/**
	 * Rooting Check
	 * 
	 * Check files is Exist
	 * 
	 * @param files
	 * @return
	 */
	private static boolean checkRootingFiles(File... files) {
		boolean result = false;

		for (File f : files) {
			if (f != null && f.exists() && f.isFile()) {
				result = true;
				break;
			} else {
				result = false;
			}
		}

		return result;
	}

	
	/**
	 * Rooting Check
	 * 
	 * make rootingfile file object.
	 * @param rootFilesPath
	 * @return
	 */
	private static File[] createFiles(String[] rootFilesPath) {
		File[] rootingFiles = new File[rootFilesPath.length];
		for (int i = 0; i < rootFilesPath.length; i++) {
			rootingFiles[i] = new File(rootFilesPath[i]);
		}
		return rootingFiles;
	}

	
	public static void setDataFromAsset(Context context, int res, ArrayList<String> list) {
		String[] items = context.getResources().getStringArray(res);
		
		list.clear();
		if (items != null && items.length > 0) {
			for (int i=0; i<items.length; i++) {
				list.add(items[i]);
			}
		}
	}
    
	
	/**
	 * Hide current soft-keyboard from screen
	 * @param activity
	 */
	public static void hideSoftKeyboard(Activity activity) {
		if (activity == null)
			return;
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if (activity.getCurrentFocus() != null) {
			Log.d(TAG, "hideSoftKeyboard>> " + activity.getLocalClassName());
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		}
	}

	
	/**
	 * get Current Date data
	 * @return
	 */
	public static String getFormattedCurrentDate() {
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd_HHmmss"); // 모바일 업로드 후 웹에서 오류 나는 현상을 잡기 위해서 : 를 없앱
				//"yyyy-MM-dd_HH:mm:ss");
		
		return formatter.format(now);
	}
	
	/**
	 * get Current Date data
	 * @return
	 */
	public static String getFormattedCurrentDate2() {
		Date now = new Date();
		
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd");
		
		return formatter.format(now);
	}
	
	/**
	 * get Current Date data
	 * @return
	 */
	public static String getFormattedCurrentDate3() {
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyyMMdd");
		
		return formatter.format(now);
	}
	
	
	/**
	 * get Current Date data
	 * @return
	 */
	public static String getFormattedCurrentDate(long milliseconds) {
		Date now = new Date(milliseconds);
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd_HH:mm:ss");

		return formatter.format(now);
	}
	
	
	public static String getDate(long utcTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(utcTime);
		Date d = cal.getTime();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		
		return formatter.format(d);
	}
	
	
	public static String getTime(long utcTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(utcTime);
		Date d = cal.getTime();
		
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		
		return formatter.format(d);
	}
	
	public static String getFormattedDate(String dateString) {
		if (dateString.length() != 8) {
			return dateString;
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(dateString.substring(0, 4));
		buffer.append(".");
		buffer.append(dateString.substring(4, 6));
		buffer.append(".");
		buffer.append(dateString.substring(6, 8));
		
		return buffer.toString();
	}
	
	
	/**
	 * @param context context
	 * @return boolean
	 */
	public static boolean checkNetworkStatus(Context context)
	{
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo mobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo lte_4g = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
		
		boolean blte_4g = false;
		
		if (lte_4g != null) {
			blte_4g = lte_4g.isConnected();
		}
		
		if ( (mobile != null && mobile.isConnectedOrConnecting()) ||
				(wifi != null && wifi.isConnectedOrConnecting()) ||
				blte_4g) {
			return true;
		}
		
		return false;
	}
	
	
	public static boolean checkWifiStatus(Context context)
	{
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		//NetworkInfo mobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
		if (/*mobile.isConnected() && */wifi.isConnected()) {
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * check if current activity is standing alone. 
	 * @param context
	 * @return true/false
	 */
	public static boolean isRunningAlone(Context context) {

	    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    List<RunningTaskInfo> services = activityManager
	            .getRunningTasks(1);


//	    Log.e("####","top "+services.get(0).topActivity.getShortClassName().toString() );
//	    Log.e("####","base "+services.get(0).baseActivity.getShortClassName().toString() );
//	    Log.e("####","running "+services.get(0).numRunning);
//	    Log.e("####","activity "+services.get(0).numActivities);
	    if (services.get(0).numActivities==1&&
	    		services.get(0).topActivity.getShortClassName().toString()
	            .equalsIgnoreCase(services.get(0).baseActivity.getShortClassName().toString())) {
	       
	    	return true;
	    }
	    return false;

	}

	
	/**
	 *  verify editText Email format is valid
	 * @param emailEdittext
	 * @return true/false
	 */
	public static boolean isValidEmail(EditText emailEdittext) {
		return emailEdittext.getText().toString().matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
	}
	
	
	public static void clearAppCache(Context context, File dir) {
		if (dir == null) {
			dir = context.getCacheDir();
		}
	  
		if (dir == null) {
			return;
		}
	  
		File[] children = dir.listFiles();
		try {
			int size = children.length;
			
			for (int n=0; n<size; n++) {
				if (children[n].isDirectory()) {
					clearAppCache(context, children[n]);
				} else {
					children[n].delete();
				}
			}
		} catch (Exception e) {
			EnsLogUtil.printStackTrace(TAG, e);
		}
	 }
	
	
	// 메인 UI Thread 안에서 수행되도록 한다.
	public static void makeLooperThread(Runnable r){
		Looper.prepare();
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(r);
		Looper.loop();
	}
	
	public static String getMac(Activity act){
		WifiManager wifiManager = (WifiManager) act.getSystemService(Context.WIFI_SERVICE);
		String address = "";
		
//		if(wifiManager.isWifiEnabled()) {
		    // WIFI ALREADY ENABLED. GRAB THE MAC ADDRESS HERE
		    WifiInfo info = wifiManager.getConnectionInfo();
		    address = info.getMacAddress();
//		} else {
//		    // ENABLE THE WIFI FIRST
//		    wifiManager.setWifiEnabled(true);
//
//		    // WIFI IS NOW ENABLED. GRAB THE MAC ADDRESS HERE
//		    WifiInfo info = wifiManager.getConnectionInfo();
//		    address = info.getMacAddress();
//		}
		
		return address;
	}
	
	public static List<String> getListData(String arrayData) {
		ArrayList<String> listData = new ArrayList<String> ();
		
		int start = 0;
		int size = arrayData.length();
		for (int i = 0; i<size; i++) {
			char charater = arrayData.charAt(i);
			if (',' == charater) {
				listData.add(arrayData.substring(start, i));
				start = i+1;
			}
		}
		
		listData.add(arrayData.substring(start, size));
		
		return listData;
	}
	
	
	public static String getTopActivity(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
		
		ComponentName componentName = taskInfo.get(0).topActivity;
		return componentName.getPackageName();
	}
	
	
	public static int getDayDiff(final Date compDate, final Date today)
	{
		Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(today);
        calendar2.setTime(compDate);
        long milliseconds1 = calendar1.getTimeInMillis();
        long milliseconds2 = calendar2.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        //long diffSeconds = diff / 1000;
        //long diffMinutes = diff / (60 * 1000);
        //long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);
        //System.out.println("\nThe Date Different Example");
        //System.out.println("Time in milliseconds: " + diff + " milliseconds.");
        //System.out.println("Time in seconds: " + diffSeconds + " seconds.");
        //System.out.println("Time in minutes: " + diffMinutes + " minutes.");
        //System.out.println("Time in hours: " + diffHours + " hours.");
        
        
        return (int)diffDays;
	}
	

}
