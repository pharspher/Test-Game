package org.ph.game.util;

import org.ph.game.testgame.BuildConfig;

import android.util.Log;

public class Logger {
	public static void d(String tag, String message) {
		if (BuildConfig.DEBUG) {
			Log.d(tag, message);
		}
	}
}
