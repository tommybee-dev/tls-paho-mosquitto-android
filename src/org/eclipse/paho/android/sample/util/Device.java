package org.eclipse.paho.android.sample.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class Device {
	private static final String TAG = Device.class.getSimpleName();
	
	//private static Device sInstance;
	private int mDeviceDpi;
	private String mCarrier;
	private String mDeviceId;
	private String mDeviceModel;
	private String mDeviceManufacturer;
	private String mMobileEquipmentId;
	
	private static Device _selfInstance;
	
	private static Activity mCurrentActivity;
	
	public static Device getInstance() {
		if (_selfInstance == null) {
			synchronized (Device.class) {
				// double checked locking - because second check of Singleton
				// instance with lock
				if (_selfInstance == null) {
					_selfInstance = new Device();
				}
			}
		}
		return _selfInstance;
	}
	
	private Device()
	{
		EnsLogUtil.logD(TAG, "In Contructor Device...");
	}
	
	
	public boolean isTablet() {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
			return false;
		}
		if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) && (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR2)) {
			return true;
		}
		Configuration con = getCurrentActivity().getResources()
				.getConfiguration();
		if ((con.screenLayout & 0xF) == 4) {
			return true;
		}
		return false;
	}

	public boolean isTV() {
		return getCurrentActivity().getPackageManager()
				.hasSystemFeature("com.google.android.tv");
	}

	public String getPlatform() {
		return "Android";
	}

	public String getPlatformName() {
		return "Android";
	}

	public String getDeviceId() {
		if (this.mDeviceId == null) {
			this.mDeviceId = Settings.Secure.getString(getCurrentActivity().getContentResolver(), "android_id");
			if ((this.mDeviceId == null) || ("".equals(this.mDeviceId))) {
				return "No DeviceID";
			}
			return this.mDeviceId;
		}
		return this.mDeviceId;
	}

	public String getOsVersion() {
		return Build.VERSION.RELEASE;
	}

	public String getDeviceModel() {
		if (this.mDeviceModel == null) {
			this.mDeviceModel = Build.MODEL;
		}
		return this.mDeviceModel;
	}

	public String getDeviceManufacturer() {
		if (this.mDeviceManufacturer == null) {
			this.mDeviceManufacturer = Build.MANUFACTURER;
		}
		return this.mDeviceManufacturer;
	}

	public String getMobileEquipmentId() {
		if (this.mMobileEquipmentId == null) {
			TelephonyManager tm = (TelephonyManager) getCurrentActivity().getSystemService("phone");
			this.mMobileEquipmentId = tm.getDeviceId();
		}
		return this.mMobileEquipmentId;
	}

	public int getDeviceDpi() {
		if (this.mDeviceDpi == 0) {
			DisplayMetrics dm = new DisplayMetrics();
			getCurrentActivity().getWindowManager()
					.getDefaultDisplay().getMetrics(dm);

			this.mDeviceDpi = dm.densityDpi;
		}
		return this.mDeviceDpi;
	}

	public String getLanguage() {
		return java.util.Locale.getDefault().getLanguage().split("_")[0];
	}

	public String getNetworkType() {
		ConnectivityManager cm = (ConnectivityManager) getCurrentActivity().getSystemService("connectivity");
		NetworkInfo ni = cm.getActiveNetworkInfo();
		String type = "null";
		if (ni != null) {
			type = cm.getActiveNetworkInfo().getTypeName().toLowerCase();
		}
		return type;
	}
	
	public boolean isDNSRTTAvailable(Context context){
		EnsLogUtil.logD(TAG, "ENTER getDNS");
		ArrayList<String> servers = new ArrayList<String>();
		boolean isOK = false;
		
		try {
			Class<?> SystemProperties = Class.forName("android.os.SystemProperties");
			Method method = SystemProperties.getMethod("get", new Class[]{String.class});
			for (String name : new String[] { "net.dns1", "net.dns2"}){
				String value = (String)method.invoke(null, name);
				if(value != null && !"".equals(value) && !servers.contains(value))
					servers.add(value);
			}
			SharedPreferences pref = context.getSharedPreferences(DeviceTag.SharedPreferencesType.DNS, 0);
			SharedPreferences.Editor pref_edit = pref.edit();
			
			if(servers.size() > 0){
				for(int i=0; i<servers.size();i++){
					pref_edit.putString("DNS"+i+"_IP", servers.get(i));
				}
			}else{
				servers.add("168.126.63.1");
				servers.add("168.126.63.2");
				for(int i=0; i<servers.size();i++){
					pref_edit.putString("DNS"+i+"_IP", servers.get(i));
				}
			}
			
			/**
			 * KT
			 * 168.126.63.1
			 * 168.126.63.2
			 * SK 
			 * 219.250.36.130
			 * 210.220.163.82
			 * LG
			 * 164.124.101.2
			 * 203.248.252.2
			 * Open DNS
			 * 208.67.222.222
			 * 208.67.220.220
			 */
			
			String pingResult = getPingResult("168.126.63.1");
			
			if(pingResult == null)
			{
				//skipped!!!!!
				isOK = false;
			}
			else
			{
				EnsLogUtil.logD(TAG, "Ping result-->[%s]", pingResult);
				
//				String a[] = getPingResult(servers.get(0)).split("ms");
				String a[] = pingResult.split("ms");
				String b[] = a[1].split("=");
				String c[] = b[1].split("/");
				
//				System.out.println("DNS1_RTT : "+getPingResult(servers.get(0)));
//				System.out.println("DNS_RTT"+b[1]);
//				System.out.println("MIN_RTT : "+c[0]);
//				System.out.println("AVG_RTT : "+c[1]);
//				System.out.println("MAX_RTT : "+c[2]);
				
				pref_edit.putString(DeviceTag.DNS.DNS1_RTT, getPingResult(servers.get(0)));
				pref_edit.putString(DeviceTag.DNS.DNS1_RTT_MIN, c[0]);
				pref_edit.putString(DeviceTag.DNS.DNS1_RTT_AVG, c[1]);
				pref_edit.putString(DeviceTag.DNS.DNS1_RTT_MAX, c[2]);
				pref_edit.commit();
				
				isOK = true;
			}
			
		} catch (IllegalArgumentException e) {
			EnsLogUtil.printStackTrace(TAG, e);
			isOK = false;
		} catch (ClassNotFoundException e) {
			EnsLogUtil.printStackTrace(TAG, e);
			isOK = false;
		} catch (NoSuchMethodException e) {
			EnsLogUtil.printStackTrace(TAG, e);
			isOK = false;
		} catch (IllegalAccessException e) {
			EnsLogUtil.printStackTrace(TAG, e);
			isOK = false;
		} catch (InvocationTargetException e) {
			EnsLogUtil.printStackTrace(TAG, e);
			isOK = false;
		} catch (Exception e)
		{
			EnsLogUtil.printStackTrace(TAG, e);
			isOK = false;
		}
		
		EnsLogUtil.logD(TAG, "EXIT getDNS");
		
		return isOK;
	}
	
	public String getPingResult(String a){
		
//		System.out.println("DNS : "+a);
		
	    String str = "";
	    String result = "";
	    
		try {
			Runtime r = Runtime.getRuntime();
			Process process = r.exec("/system/bin/ping -c 3 "+a);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			
			int i;
	        char[] buffer = new char[4096];
	        StringBuffer output = new StringBuffer();
	        while ((i = reader.read(buffer)) > 0)
	            output.append(buffer,0, i);
	        reader.close();
	        
	        // body.append(output.toString()+"\n");
	        str = output.toString();
	        
	        
	        EnsLogUtil.logD(TAG, "Ping result-->[%s]", str);
	        
	        String[] b = str.split("---");
	        String[] c = b[2].split("rtt");
	        
	        if(b.length == 0 || c.length == 0)
	        	return null;
	        
	        result = b[1].substring(1,b[1].length())+c[0]+c[1].substring(1,c[1].length());
	        
		} catch (IOException e) {
			EnsLogUtil.printStackTrace(TAG, e);
			return null;
		} catch (Exception e) {
			EnsLogUtil.printStackTrace(TAG, e);
			return null;
		}
		
		return result;
	}
	
	public String getOsName() {
		return "Android";
	}

	public String getCarrier() {
		if (this.mCarrier == null) {
			TelephonyManager tm = (TelephonyManager) getCurrentActivity().getSystemService("phone");
			this.mCarrier = tm.getNetworkOperatorName();
		}
		return this.mCarrier;
	}

	public static Activity getCurrentActivity() {
		return mCurrentActivity;
	}

	public static void setCurrentActivity(Activity currentActivity) {
		mCurrentActivity = currentActivity;
	}
}
