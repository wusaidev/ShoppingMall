package com.happy.shoppingmall.utils;

import android.util.Log;

public class LogUtil {
	public static final int VERBOSE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int NOTHING = 6;
	public static int level = VERBOSE;

	public static void v( String msg) {
		if (level <= VERBOSE) {
			Log.v("", msg);
		}
	}

	public static void d( String msg) {
		if (level <= DEBUG) {
			Log.d("", msg);
		}
	}

	public static void i( String msg) {
		if (level <= INFO) {
			Log.i("", msg);
		}
	}

	public static void w( String msg) {
		if (level <= WARN) {
			Log.w("", msg);
		}
	}

	public static void e( String msg) {
		if (level <= ERROR) {
			Log.e("", msg);
		}
	}

}
