/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 */
package org.eclipse.paho.android.sample.service;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import org.eclipse.paho.android.sample.MqttConts;
import org.eclipse.paho.android.sample.R;
import org.eclipse.paho.android.sample.service.Connection.ConnectionStatus;
import org.eclipse.paho.android.sample.util.EnsLogUtil;
import org.eclipse.paho.android.sample.util.SslUtil;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * ClientConnections is the main activity for the sample application, it
 * displays all the active connections.
 * 
 */
public class ClientConnections extends ListActivity {
	private static final String TAG = ClientConnections.class.getSimpleName();

	/**
	 * Token to pass to the MQTT Service
	 */
	// tommybee
	final static String TOKEN = MqttConts.ClassConsts.CLS_ClientConnections;

	/**
	 * ArrayAdapter to populate the list view
	 */
	private ArrayAdapter<Connection> arrayAdapter = null;

	/**
	 * {@link ChangeListener} for use with all {@link Connection} objects
	 * created by this instance of <code>ClientConnections</code>
	 */
	private ChangeListener changeListener = new ChangeListener();

	/**
	 * This instance of <code>ClientConnections</code> used to update the UI in
	 * {@link ChangeListener}
	 */
	private ClientConnections clientConnections = this;

	/**
	 * Contextual action bar active or not
	 */
	private boolean contextualActionBarActive = false;

	/**
	 * @see android.app.ListActivity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ListView connectionList = getListView();
		connectionList.setOnItemLongClickListener(new LongClickItemListener());
		connectionList.setTextFilterEnabled(true);
		arrayAdapter = new ArrayAdapter<Connection>(this, R.layout.connection_text_view);
		setListAdapter(arrayAdapter);

		// get all the available connections
		Map<String, Connection> connections = Connections.getInstance(this).getConnections();

		if (connections != null) {
			for (String s : connections.keySet()) {
				arrayAdapter.add(connections.get(s));
			}
		}

		//String pResult = Device.getInstance().getPingResult("168.154.182.40");// 192.9.150.200,

		//EnsLogUtil.logD(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ping	result[%s]", pResult);
	}

	// Vpn 서비스를 시작한다.
	// public void startVpnService() {
	// EnsLogUtil.logD(TAG, "START");
	// VpnClass.setOwnerActivity(this);
	// VpnClass vpn = VpnClass.getInstance();
	// vpn.setVpnConnectedListener(this);
	// vpn.setVpnDisconnectedListener(vpnDisconnectedListener);
	// vpn.setVpnPreparedListener(vpnPreparedListener);
	//
	// boolean isVpnOn = vpn.isConnected();
	// Log.e(TAG, String.valueOf(isVpnOn).toString());
	//
	// if(isVpnOn){
	// Toast.makeText(this, "VPN이 이미 연결되어 있습니다.", Toast.LENGTH_SHORT).show();
	// }else{
	// vpn.stopVpnTimer();
	// vpn.fnc_vpnTimer(20000);
	// }
	// }

	// public void stopVpnService() {
	// EnsLogUtil.logD(TAG, "ENTER stopVpnService");
	//
	// VpnClass.setOwnerActivity(this);
	// VpnClass vpn = VpnClass.getInstance();
	//
	// boolean isVpnOn = vpn.isConnected();
	// Log.e(TAG, String.valueOf(isVpnOn).toString());
	//
	// if (vpn.isConnected()) {
	// vpn.disconnectVpn();
	// }
	//
	// EnsLogUtil.logD(TAG, "EXIT stopVpnService");
	// }

	/**
	 * Creates the action bar for the activity
	 * 
	 * @see ListActivity#onCreateOptionsMenu(Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		OnMenuItemClickListener menuItemClickListener = new Listener(this);

		// load the correct menu depending on the status of logging
		if (Listener.logging) {
			getMenuInflater().inflate(R.menu.activity_connections_logging, menu);
			menu.findItem(R.id.endLogging).setOnMenuItemClickListener(menuItemClickListener);
		} else {
			getMenuInflater().inflate(R.menu.activity_connections, menu);
			menu.findItem(R.id.startLogging).setOnMenuItemClickListener(menuItemClickListener);
		}

		menu.findItem(R.id.newConnection).setOnMenuItemClickListener(menuItemClickListener);

		// stopVpnService();

		// runOnUiThread(
		// new Runnable()
		{
			// @Override
			// public void run()
			{
				// if(Device.getInstance().isDNSRTTAvailable(ClientConnections.this))
				{
					// PlatformUIComponent.getInstance().showProgressDialog(ClientConnections.this,
					// "연결이 확인되었습니다.", true);
					// String pResult
					// =Device.getInstance().getPingResult("192.9.112.69");//192.9.150.200,
					// 201, 202
					// EnsLogUtil.logD(TAG, "ping result[%s]", pResult);
					// VpnClass.getInstance().connect();
					// startVpnService();
				}
			}
		}
		// );

		return true;
	}

	/**
	 * Listens for item clicks on the view
	 * 
	 * @param listView
	 *            The list view where the click originated from
	 * @param view
	 *            The view which was clicked
	 * @param position
	 *            The position in the list that was clicked
	 */
	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);

		if (!contextualActionBarActive) {
			Connection c = arrayAdapter.getItem(position);

			// start the connectionDetails activity to display the details about
			// the
			// selected connection
			Intent intent = new Intent();
			// tommybee
			intent.setClassName(getApplicationContext().getPackageName(), MqttConts.ClassConsts.CLS_ConnectionDetails);
			intent.putExtra("handle", c.handle());
			startActivity(intent);
		}

	}

	/**
	 * @see ListActivity#onActivityResult(int,int,Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_CANCELED) {
			return;
		}

		EnsLogUtil.logD(TAG, "onActivityResult...code_req[%d]:res[%d]", requestCode, resultCode);

		//switch (requestCode) {
		//case MqttConts.VpnConsts.ON_VPN_PREPARED:
		//	switch (resultCode) {
		//	case RESULT_OK:
		//		// VpnClass.getInstance().connect();
		//		break;
		//	case RESULT_CANCELED:
		//		Toast.makeText(this, "앱을 사용하기 위해서는 VPN 연결을 하여야 합니다.", Toast.LENGTH_LONG).show();
		//		break;
		//	default:
		//		break;
		//	}
		//	break;
		//default:
		//	//Bundle dataBundle = data.getExtras();
		//	// perform connection create and connect
		//	//connectAction(dataBundle);
		//}
		
		//Toast.makeText(this, "앱을 사용하기 위해서는 VPN 연결을 하여야 합니다.", Toast.LENGTH_LONG).show();
		
		Bundle dataBundle = data.getExtras();
		// perform connection create and connect
		connectAction(dataBundle);

	}

	/**
	 * @see ListActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		arrayAdapter.notifyDataSetChanged();

		// Recover connections.
		Map<String, Connection> connections = Connections.getInstance(this).getConnections();

		// Register receivers again
		for (Connection connection : connections.values()) {
			connection.getClient().registerResources(this);
			connection.getClient().setCallback(new MqttCallbackHandler(this,
					connection.getClient().getServerURI() + connection.getClient().getClientId()));
		}
	}

	/**
	 * @see ListActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {

		Map<String, Connection> connections = Connections.getInstance(this).getConnections();

		for (Connection connection : connections.values()) {
			connection.registerChangeListener(changeListener);
			connection.getClient().unregisterResources();
		}

		// stopVpnService();

		super.onDestroy();
	}

	/**
	 * Process data from the connect action
	 * 
	 * @param data
	 *            the {@link Bundle} returned by the {@link NewConnection}
	 *            Acitivty
	 */
	private void connectAction(Bundle data) {
		EnsLogUtil.logD(TAG, "ENTER connectAction");

		MqttConnectOptions conOpt = new MqttConnectOptions();
		/*
		 * Mutal Auth connections could do something like this
		 * 
		 * 
		 * SSLContext context = SSLContext.getDefault(); context.init({new
		 * CustomX509KeyManager()},null,null); //where CustomX509KeyManager
		 * proxies calls to keychain api SSLSocketFactory factory =
		 * context.getSSLSocketFactory();
		 * 
		 * MqttConnectOptions options = new MqttConnectOptions();
		 * options.setSocketFactory(factory);
		 * 
		 * client.connect(options);
		 * 
		 */

		// The basic client information
		String server = (String) data.get(ActivityConstants.server);
		String clientId = (String) data.get(ActivityConstants.clientId);
		int port = Integer.parseInt((String) data.get(ActivityConstants.port));
		boolean cleanSession = (Boolean) data.get(ActivityConstants.cleanSession);
		boolean tlsssl = (Boolean) data.get(ActivityConstants.tlsssl);
		
		boolean ssl = (Boolean) data.get(ActivityConstants.ssl);
		String ssl_key = (String) data.get(ActivityConstants.ssl_key);
		String uri = null;
		if (ssl || tlsssl) {
			Log.e("SSLConnection", "Doing an SSL Connect");
			uri = "ssl://";

		} else {
			uri = "tcp://";
		}

		uri = uri + server + ":" + port;

		MqttAndroidClient client;
		client = Connections.getInstance(this).createClient(this, uri, clientId);

		if (ssl) {
			try {
				if (ssl_key != null && !ssl_key.equalsIgnoreCase("")) {
					FileInputStream key = new FileInputStream(ssl_key);
					conOpt.setSocketFactory(client.getSSLSocketFactory(key, "mqtttest"));
				}

			} catch (MqttSecurityException e) {
				Log.e(this.getClass().getCanonicalName(), "MqttException Occured: ", e);

				EnsLogUtil.printStackTrace(TAG, e);

			} catch (FileNotFoundException e) {
				Log.e(this.getClass().getCanonicalName(), "MqttException Occured: SSL Key file not found", e);
				EnsLogUtil.printStackTrace(TAG, e);
			}
		}
		
		if (tlsssl) {
			java.io.Reader cafileRder = null, clicrtfileRder = null, clikeyfileRder = null;
			
			
			try
			{
				final String cafile =  MqttConts.ClassConsts.CERT_RootCA;
				final String clicrtfile = MqttConts.ClassConsts.CERT_ClientCA;
				final String clikeyfile = MqttConts.ClassConsts.CERT_ClientKey;
				final String password = MqttConts.ClassConsts.CERT_ClientPass;
				
				
				//System.out.println("=========> " + new java.io.File(getClass().getResource("/org/eclipse/paho/client/mqttv3/ca/ca.crt").toURI()).exists());
				//System.out.println(cafile + "=========> " );
				
				cafileRder 
					= new java.io.BufferedReader(
						new java.io.InputStreamReader(getClass().getResourceAsStream(cafile)));
				//System.out.println("=========> " + cafileRder.ready());
				
				clicrtfileRder 
					= new java.io.BufferedReader(
						new java.io.InputStreamReader(getClass().getResourceAsStream(clicrtfile)));
				//System.out.println("=========> " + clicrtfileRder.ready());
				
				clikeyfileRder 
					= new java.io.BufferedReader(
						new java.io.InputStreamReader(getClass().getResourceAsStream(clikeyfile)));
				//System.out.println("=========> " + clikeyfileRder.ready());
				
				conOpt.setSocketFactory(
					SslUtil.getSocketFactory(
						cafileRder, 
						clicrtfileRder, 
						clikeyfileRder, 
						password
					)
				);
			}catch(Exception e)
			{
				Log.e(this.getClass().getCanonicalName(), "MqttException Occured: TLS/SSL Key file not found", e);
				EnsLogUtil.printStackTrace(TAG, e);
			}
			finally
			{
				if(cafileRder!=null)
					try{cafileRder.close(); cafileRder=null;}catch(java.io.IOException ie){ie.printStackTrace();}
				
				if(clicrtfileRder!=null)
					try{clicrtfileRder.close(); clicrtfileRder=null;}catch(java.io.IOException ie){ie.printStackTrace();}
					
				if(clikeyfileRder!=null)
					try{clikeyfileRder.close(); clikeyfileRder=null;}catch(java.io.IOException ie){ie.printStackTrace();}
			}
			
			
		}

		// create a client handle
		String clientHandle = uri + clientId;

		// last will message
		String message = (String) data.get(ActivityConstants.message);
		String topic = (String) data.get(ActivityConstants.topic);
		Integer qos = (Integer) data.get(ActivityConstants.qos);
		Boolean retained = (Boolean) data.get(ActivityConstants.retained);

		// connection options

		String username = (String) data.get(ActivityConstants.username);

		String password = (String) data.get(ActivityConstants.password);

		int timeout = (Integer) data.get(ActivityConstants.timeout);
		int keepalive = (Integer) data.get(ActivityConstants.keepalive);

		Connection connection = new Connection(clientHandle, clientId, server, port, this, client, ssl);
		arrayAdapter.add(connection);

		connection.registerChangeListener(changeListener);
		// connect client

		String[] actionArgs = new String[1];
		actionArgs[0] = clientId;
		connection.changeConnectionStatus(ConnectionStatus.CONNECTING);

		conOpt.setCleanSession(cleanSession);
		conOpt.setConnectionTimeout(timeout);
		conOpt.setKeepAliveInterval(keepalive);
		if (!username.equals(ActivityConstants.empty)) {
			conOpt.setUserName(username);
		}
		if (!password.equals(ActivityConstants.empty)) {
			conOpt.setPassword(password.toCharArray());
		}

		final ActionListener callback = new ActionListener(this, ActionListener.Action.CONNECT, clientHandle,
				actionArgs);

		boolean doConnect = true;

		if ((!message.equals(ActivityConstants.empty)) || (!topic.equals(ActivityConstants.empty))) {
			// need to make a message since last will is set
			try {
				conOpt.setWill(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
			} catch (Exception e) {
				Log.e(this.getClass().getCanonicalName(), "Exception Occured", e);
				EnsLogUtil.printStackTrace(TAG, e);
				doConnect = false;
				callback.onFailure(null, e);
			}
		}
		client.setCallback(new MqttCallbackHandler(this, clientHandle));

		EnsLogUtil.logD(TAG, ">>connectAction");
		// set traceCallback
		client.setTraceCallback(new MqttTraceCallback());

		connection.addConnectionOptions(conOpt);
		Connections.getInstance(this).addConnection(connection);
		if (doConnect) {
			try {
				EnsLogUtil.logD(TAG, ">>connectAction>>connect");
				client.connect(conOpt, null, callback);
			} catch (MqttException e) {
				EnsLogUtil.printStackTrace(TAG, e);
				Log.e(this.getClass().getCanonicalName(), "MqttException Occured", e);
				EnsLogUtil.printStackTrace(TAG, e);
			}
		}

		EnsLogUtil.logD(TAG, "EXIT connectAction[%s]", uri);
	}

	/**
	 * <code>LongClickItemListener</code> deals with enabling and disabling the
	 * contextual action bar and processing the actions selected.
	 *
	 */
	private class LongClickItemListener implements OnItemLongClickListener, ActionMode.Callback, OnClickListener {

		/** The index of the item selected, or -1 if an item is not selected **/
		private int selected = -1;
		/** The view of the item selected **/
		private View selectedView = null;
		/** The connection the view is representing **/
		private Connection connection = null;

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(
		 * android.widget.AdapterView, android.view.View, int, long)
		 */
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			clientConnections.startActionMode(this);
			selected = position;
			selectedView = view;
			clientConnections.getListView().setSelection(position);
			view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.view.ActionMode.Callback#onActionItemClicked(android.view.
		 * ActionMode, android.view.MenuItem)
		 */
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			selectedView.setBackgroundColor(getResources().getColor(android.R.color.white));
			switch (item.getItemId()) {
			case R.id.delete:
				delete();
				mode.finish();
				return true;
			default:
				return false;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.view.ActionMode.Callback#onCreateActionMode(android.view.
		 * ActionMode, android.view.Menu)
		 */
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.activity_client_connections_contextual, menu);
			clientConnections.contextualActionBarActive = true;
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.view.ActionMode.Callback#onDestroyActionMode(android.view.
		 * ActionMode)
		 */
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			selected = -1;
			selectedView = null;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.view.ActionMode.Callback#onPrepareActionMode(android.view.
		 * ActionMode, android.view.Menu)
		 */
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		/**
		 * Deletes the connection, disconnecting if required.
		 */
		private void delete() {
			connection = arrayAdapter.getItem(selected);
			if (connection.isConnectedOrConnecting()) {

				// display a dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(clientConnections);
				builder.setTitle(R.string.disconnectClient).setMessage(getString(R.string.deleteDialog))
						.setNegativeButton(R.string.cancelBtn, new OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// do nothing user cancelled action
							}
						}).setPositiveButton(R.string.continueBtn, this).show();
			} else {
				arrayAdapter.remove(connection);
				Connections.getInstance(clientConnections).removeConnection(connection);
			}

		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// user pressed continue disconnect client and delete
			try {
				connection.getClient().disconnect();
			} catch (MqttException e) {
				e.printStackTrace();
				EnsLogUtil.printStackTrace(TAG, e);
			}
			arrayAdapter.remove(connection);
			Connections.getInstance(clientConnections).removeConnection(connection);

		}
	}

	/**
	 * This class ensures that the user interface is updated as the Connection
	 * objects change their states
	 * 
	 *
	 */
	private class ChangeListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		@Override
		public void propertyChange(PropertyChangeEvent event) {

			if (!event.getPropertyName().equals(ActivityConstants.ConnectionStatusProperty)) {
				return;
			}
			clientConnections.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					clientConnections.arrayAdapter.notifyDataSetChanged();
				}

			});

		}

	}

	// OnVpnDisconnectedListener vpnDisconnectedListener = new
	// OnVpnDisconnectedListener() {
	//
	// @Override
	// public void onVpnDisconnected() {
	// EnsLogUtil.logD(TAG, "onVpnDisconnected");
	// }
	//
	// };
	//
	// OnVpnPreparedListener vpnPreparedListener = new OnVpnPreparedListener() {
	//
	// @Override
	// public void onVpnPrepared(Intent intent) {
	// EnsLogUtil.logD(TAG, ">>>>>>>>>>>>>>> onVpnPrepared");
	// startActivityForResult(intent, MqttConts.VpnConsts.ON_VPN_PREPARED);
	// }
	//
	// };
	//
	// @Override
	// public void onVpnConnectionFailed(String result) {
	// EnsLogUtil.logD(TAG,
	// ">>>>>>>>>>>>>>>>>>>onVpnConnectionFailed>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// EnsLogUtil.logD(TAG, "[%s]", result);
	// }
	//
	// @Override
	// public void onVpnConnectionSucceed() {
	// EnsLogUtil.logD(TAG,
	// ">>>>>>>>>>>>>>>>>>>onVpnConnectionSucceed>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// String pResult = null;
	//
	// // String pResult
	// // =Device.getInstance().getPingResult("192.9.112.69");//192.9.150.200,
	// // 201, 202
	// // EnsLogUtil.logD(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ping
	// // result[%s]", pResult);
	//
	// pResult = Device.getInstance().getPingResult("192.9.150.200");//
	// 192.9.150.200,
	// // 201,
	// // 202
	// EnsLogUtil.logD(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ping
	// result[%s]", pResult);
	//
	// pResult = Device.getInstance().getPingResult("192.9.100.175");//
	// 192.9.150.200,
	// // 201,
	// // 202
	// EnsLogUtil.logD(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ping
	// result[%s]", pResult);
	// }
}
