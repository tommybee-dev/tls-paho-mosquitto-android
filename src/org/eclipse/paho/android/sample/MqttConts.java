package org.eclipse.paho.android.sample;

public interface MqttConts {
	
	
	static final class ClassConsts
	{
		//org.eclipse.paho.android.sample.service.LastWill -> com.skens.sms.mqtt.service.sample.LastWill
		public static final String CLS_LastWill = "org.eclipse.paho.android.sample.service.LastWill";
		//org.eclipse.paho.android.sample.service.ClientConnections -> com.skens.sms.mqtt.service.sample.ClientConnections
		public static final String CLS_ClientConnections = "org.eclipse.paho.android.sample.service.ClientConnections";
		//org.eclipse.paho.android.service.sample.ConnectionDetails -> com.skens.sms.mqtt.service.sample.ConnectionDetails
		public static final String CLS_ConnectionDetails = "org.eclipse.paho.android.sample.service.ConnectionDetails";
		// org.eclipse.paho.android.sample.service.NewConnection -> com.skens.sms.mqtt.service.sample.NewConnection
		public static final String CLS_NewConnection = "org.eclipse.paho.android.sample.service.NewConnection";
		// org.eclipse.paho.android.sample.service.Advanced -> com.skens.sms.mqtt.service.sample.Advanced
		public static final String CLS_Advanced = "org.eclipse.paho.android.sample.service.Advanced";
		//private static final String SERVICE_NAME = "org.eclipse.paho.android.sample.service.MqttService";
		public static final String CLS_MqttService = "org.eclipse.paho.android.sample.service.MqttService";
		
		//public static final String CERT_TLSService = "/org/eclipse/paho/android/sample/ca/";
		
		public static final String CERT_RootCA = "/org/eclipse/paho/android/sample/ca/ca.crt";
		public static final String CERT_ClientCA = "/org/eclipse/paho/android/sample/ca/client.crt";
		public static final String CERT_ClientKey = "/org/eclipse/paho/android/sample/ca/client.key";
		public static final String CERT_ClientPass = "/org/eclipse/paho/android/sample/ca/screte";
	}
	
	//static final class VpnConsts
	//{
	//	public static final int ON_VPN_PREPARED = 1000;
	//}
	
}
