package com.project202;

import static com.project202.Config.LOG_TO_CONSOLE;
import static java.lang.System.currentTimeMillis;
import android.util.Log;

public class LogHelper {

	public static void logException(String message, Exception exception) {
		if (LOG_TO_CONSOLE) {
			Log.e("Project202", message, exception);
		}
	}

	public static void log(String message) {
		if (LOG_TO_CONSOLE) {
			Log.d("Project202", message);
		}
	}

	public static void logDuration(String message, long startTimestamp) {
		if (LOG_TO_CONSOLE) {
			log(message + " Duration in ms: " + (currentTimeMillis() - startTimestamp));
		}
	}

}
