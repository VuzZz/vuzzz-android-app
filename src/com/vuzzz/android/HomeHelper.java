package com.vuzzz.android;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import android.app.Activity;
import android.os.Build;

import com.vuzzz.android.address.AddressActivity_;

public class HomeHelper {

	public static void goToHome(Activity activity) {
		AddressActivity_.intent(activity).flags(FLAG_ACTIVITY_CLEAR_TOP).start();

		if (!isHoneycomb()) {
			activity.overridePendingTransition(R.anim.home_enter, R.anim.home_exit);
		}
	}

	public static boolean isHoneycomb() {
		/*
		 * Can use static final constants like HONEYCOMB, declared in later
		 * versions of the OS since they are inlined at compile time. This is
		 * guaranteed behavior.
		 */
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

}
