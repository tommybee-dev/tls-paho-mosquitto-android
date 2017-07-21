package org.eclipse.paho.android.sample.util;

import org.json.JSONArray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

public class PlatformUIComponent {
	private static final String TAG = PlatformUIComponent.class.getSimpleName();
	
	public final String KEY_CALLBACK = "callback";
	
	public final String KEY_OPTION_CANCEL_CALLBACK = "cancelCallback";
	private JSONArray mOptionMenuItemsArray;
	private ProgressDialog mProgressDialog;
	private ProgressDialog mProgressBarDialog;
	
	private static PlatformUIComponent _selfInstance;
	
	public static PlatformUIComponent getInstance(){
		if(_selfInstance == null){
			synchronized(PlatformUIComponent.class){
			//double checked locking - because second check of Singleton instance with lock
				if(_selfInstance == null){
					_selfInstance = new PlatformUIComponent();
				}
			}
		}
		return _selfInstance;
	}
	
	public JSONArray getOptionMenuItemsArray() {
		return this.mOptionMenuItemsArray;
	}

	public void dismissProgressBarDialog(Activity activity) {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				if (mProgressBarDialog != null) {
					mProgressBarDialog.dismiss();
					mProgressBarDialog = null;
				}
			}
		});
	}

	public void dismissProgressDialog(Activity activity) {
		//if(!activity.isDestroyed())
		if(!activity.isFinishing())
			activity.runOnUiThread(new Runnable() {
				public void run() {
					if (mProgressDialog != null) {
						if(mProgressDialog.isShowing())
							mProgressDialog.dismiss();
						mProgressDialog = null;
					}
				}
			});
	}

	public void showProgressDialog(final Activity activity, final String message,	final Boolean cancelable) {
		EnsLogUtil.logD(TAG, "ENTER showProgressDialog");
		
		activity.runOnUiThread(new Runnable() {
			public void run() {
				if ((mProgressDialog == null)
						|| (!mProgressDialog.isShowing())) {
					mProgressDialog = ProgressDialog.show(activity, null,message, false, cancelable.booleanValue());
					mProgressDialog.setCanceledOnTouchOutside(false);
					mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
								public void onCancel(DialogInterface dialog) {
									dismissProgressDialog(activity);
								}
							});
				}
			}
		});
		
		EnsLogUtil.logD(TAG, "EXIT showProgressDialog");
	}

}
