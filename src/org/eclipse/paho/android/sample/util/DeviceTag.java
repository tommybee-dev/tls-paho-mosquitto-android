package org.eclipse.paho.android.sample.util;

public interface DeviceTag {
	
	
	interface DUTY_WORK
	{
		String DUTY_WORK_ID = "DUTY_WORK";
		String DUTY_WORK_TITLE= "DUTY_WORK_NAME";
		String DUTY_WORK_VALUE = "DUTY_WORK_VALUE";
		String DUTY_WORK_TITLEs= "DUTY_WORK_NAMES";
		String DUTY_WORK_VALUEs = "DUTY_WORK_VALUES";
		String DUTY_WORK_SELECTED = "DUTY_WORK_SELECTED";
	}
	
	
	
	interface SharedPreferencesType
	{
		String SIGNAL = "SIGNAL";
		String TELINFO = "TELINFO";
		String BATTERY = "BATTERY";
		String DNS = "DNS";
		String GPS = "GPS";
		String MEMORY = "MEMORY";
	}
	
	
	interface MEMORY
	{
		String PROC_NAME_PREFIX = "Proc_Name_";
		String PROC_RATE_PREFIX = "Proc_Rate_";
		String TOTAL = "Total_Mem";
		String AVAILABLE = "Avail_Mem";
	}
	
	
	interface GPS
	{
		String SAT_CNT = "SAT_CNT";
		String LAT = "LAT";
		String LNT = "LNT";
		
		String SPEED = "SPEED";
		String BEARING = "BEARING";
		String GDATE = "GDATE";
		String GTIME = "GTIME";
		String STAT = "STAT";
	}
	
	
	interface DNS
	{
		String DNS1_RTT = "DNS1_RTT";
		String DNS1_RTT_MIN = "DNS1_RTT_MIN";
		String DNS1_RTT_AVG = "DNS1_RTT_AVG";
		String DNS1_RTT_MAX = "DNS1_RTT_MAX";
	}
	
	interface BATTERY
	{
		String STATUS = "sStatus";
		String PLUG = "sPlug";
		String RATIO = "ratio";
	}
	
	interface NETTYPE
	{
		String NET_2G = "2G";
		String NET_3G = "3G";
		String NET_LTE = "4G";
	}
	
	interface SIGNAL
	{
		String LTE_RSRP = "LTE_RSRP";
		String LTE_LEV = "LTE_LEV";
		String LTE_DBM = "LTE_DBM";
		String LTE_ASU = "LTE_ASU";
		String GSM_DBM = "GSM_DBM";
	}
	
	interface TELINFO
	{
		String TELECOM = "TELECOM";
		String PHONENUM = "PHONENUM";
		String IMEI = "IMEI";
		String IMSI = "IMSI";
		String LOCALE_CODE = "LOCALE_CODE";
		String NETWORK_TYPE = "NETWORK_TYPE";
		
	}
}
