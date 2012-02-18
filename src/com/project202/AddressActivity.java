package com.project202;

import android.view.View;
import android.widget.EditText;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.address_map)
@NoTitle
public class AddressActivity extends MapActivity {
	
	@ViewById
	MapView mapView;
	
	@ViewById
	EditText addressEditText;
	
	@ViewById
	View addressHint;
	
	@ViewById
	View searchButton;
	
	
	@AfterViews
	void initLayout() {
		addressEditText.addTextChangedListener(new AbstractTextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() == 0) {
					onAddressEmpty();
				} else {
					onAddressNotEmpty();
				}
			}
		});
	}

	protected void onAddressEmpty() {
		searchButton.setVisibility(View.GONE);
		addressHint.setVisibility(View.VISIBLE);
	}

	protected void onAddressNotEmpty() {
		searchButton.setVisibility(View.VISIBLE);
		addressHint.setVisibility(View.GONE);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
