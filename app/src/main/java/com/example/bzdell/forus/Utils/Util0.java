package com.example.bzdell.forus.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Util0 {
	
	/**
	 * 获取时间戳
	 * @return
	 */
	public static long getSeq() {
		return System.currentTimeMillis();
	}

	/**
	 * 检查网络状态
	 */
	public static boolean isNetUseable(Context context) {
		boolean have=false;
		ConnectivityManager cwjManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()){ 
			have=true;
		} 
		
		return have;
	}

	/**
	 * 弹出吐司
	 * @param mContext
	 * @param content
	 */
	public static void showToast(Context mContext, String content) {
		Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
	}

}
