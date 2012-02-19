package com.project202.address;

import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.ViewById;
import com.project202.AboutDialogHelper;
import com.project202.LogHelper;
import com.project202.R;
import com.project202.loading.DownloadActivity_;

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

	@SystemService
	InputMethodManager inputMethodManager;

	@Bean
	AboutDialogHelper aboutDialogHelper;

	@SystemService
	LocationManager locationManager;

	private MyLocationOverlay myLocationOverlay;

	private MapController mapController;

	private AddressOverlay addressOverlay;

	private boolean shouldMoveToMyLocationOnFirstFix;

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
		addressEditText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					searchButtonClicked();
					return true;
				}
				return false;
			}
		});

		List<Overlay> mapOverlays = mapView.getOverlays();

		GestureOverlay gestureOverlay = new GestureOverlay(this, new AbstractGestureListener() {
			@Override
			public void onLongPress(MotionEvent e) {
				Projection projection = mapView.getProjection();
				GeoPoint location = projection.fromPixels((int) e.getX(), (int) e.getY());
				showAddressPopup(location);
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				shouldMoveToMyLocationOnFirstFix = false;
				float x = e.getX();
				float y = e.getY();
				boolean addressTapped = addressOverlay.onSingleTapUp(x, y);
				if (addressTapped) {
					GeoPoint addressLocation = addressOverlay.getAddressLocation();
					noteAddress(addressLocation);
				} else {
					Projection projection = mapView.getProjection();
					GeoPoint location = projection.fromPixels((int) x, (int) y);
					boolean myLocationTapped = myLocationOverlay.onTap(location, mapView);
					if (!myLocationTapped) {
						addressOverlay.hideAddressPopup();
					}
				}
				return true;
			}
		});

		myLocationOverlay = new MyLocationOverlay(this, mapView) {
			@Override
			protected boolean dispatchTap() {
				GeoPoint myLocation = getMyLocation();
				showAddressPopup(myLocation);
				return true;
			}
		};

		addressOverlay = new AddressOverlay();

		mapOverlays.add(myLocationOverlay);
		mapOverlays.add(addressOverlay);
		mapOverlays.add(gestureOverlay);

		mapController = mapView.getController();
		mapController.setZoom(18);

		/*
		 * Init location
		 */

		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
		if (provider != null) {
			Location lastKnownLocation = locationManager.getLastKnownLocation(provider);

			if (lastKnownLocation != null) {
				int lastKnownLatitude = (int) (lastKnownLocation.getLatitude() * 1E6);
				int lastKnownLongitude = (int) (lastKnownLocation.getLongitude() * 1E6);
				GeoPoint initGeoPoint = new GeoPoint(lastKnownLatitude, lastKnownLongitude);
				mapController.animateTo(initGeoPoint);
			}
		}

		moveToMyLocationOnFirstFix();
	}

	protected void onAddressEmpty() {
		searchButton.setVisibility(View.GONE);
		addressHint.setVisibility(View.VISIBLE);
	}

	protected void onAddressNotEmpty() {
		searchButton.setVisibility(View.VISIBLE);
		addressHint.setVisibility(View.GONE);
	}

	@Click
	void searchButtonClicked() {
		addressOverlay.hideAddressPopup();
		inputMethodManager.hideSoftInputFromWindow(addressEditText.getWindowToken(), 0);
		String address = addressEditText.getText().toString();
		Toast.makeText(this, "MOCK Point for " + address, Toast.LENGTH_SHORT).show();
		GeoPoint mockLocation = new GeoPoint(48831320, 2356224);
		showAddressPopup(mockLocation);
	}

	private void showAddressPopup(GeoPoint location) {
		shouldMoveToMyLocationOnFirstFix = false;
		addressOverlay.showAddressPopup(location);
		mapController.animateTo(location);
	}

	@Click
	void locationButtonClicked() {
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			moveToMyLocation();
		} else {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		myLocationOverlay.enableMyLocation();
	}

	@Override
	protected void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private void moveToMyLocation() {
		GeoPoint currentLocation = myLocationOverlay.getMyLocation();
		if (currentLocation != null) {
			try {
				mapView.getController().animateTo(currentLocation);
			} catch (Exception e) {
				LogHelper.logException("Could not animate to " + currentLocation, e);
			}
		} else {
			moveToMyLocationOnFirstFix();
			Toast.makeText(this, R.string.searching_location, Toast.LENGTH_SHORT).show();
		}
	}

	private void moveToMyLocationOnFirstFix() {
		shouldMoveToMyLocationOnFirstFix = true;
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				if (shouldMoveToMyLocationOnFirstFix) {
					moveToMyLocation();
				}
			}
		});
	}

	protected void noteAddress(GeoPoint location) {
		addressOverlay.hideAddressPopup();
		String mockAddress = "42 rue des petits indiens, 75006 Paris";

		DownloadActivity_.intent(this) //
				.address(mockAddress) //
				.latitudeE6(location.getLatitudeE6()) //
				.longitudeE6(location.getLongitudeE6()) //
				.start();
	}

	@Click
	void homeClicked() {
		showDialog(R.id.about_dialog);
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case R.id.about_dialog:
			return aboutDialogHelper.createAboutDialog();
		default:
			return null;
		}
	}
}
