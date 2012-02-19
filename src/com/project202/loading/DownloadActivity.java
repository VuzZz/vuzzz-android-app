package com.project202.loading;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.ViewById;
import com.project202.HomeHelper;
import com.project202.ShowNoteActivity_;
import com.project202.model.Rating;
import com.vuzzz.android.R;

@EActivity(R.layout.loading)
@NoTitle
public class DownloadActivity extends Activity implements TaskResultListener<Rating> {

	private enum Step {
		DOWNLOADING, RETRY, DONE;
	}

	private static class NonConfigurationInstance {
		public final RatingDownloadTask<DownloadActivity> task;
		public final Step step;

		public NonConfigurationInstance(RatingDownloadTask<DownloadActivity> task, Step step) {
			this.task = task;
			this.step = step;
		}
	}

	@Extra("address")
	String address;

	@Extra("latitudeE6")
	int latitudeE6;

	@Extra("longitudeE6")
	int longitudeE6;

	private RatingDownloadTask<DownloadActivity> task;

	private Step step;

	@ViewById
	TextView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NonConfigurationInstance instance = (NonConfigurationInstance) getLastNonConfigurationInstance();

		if (instance != null) {
			step = instance.step;
			task = instance.task;
		}
	}
	
	@AfterViews
	void initLayout() {
		titleView.setText(address);
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		NonConfigurationInstance instance = new NonConfigurationInstance(task, step);
		// This activity is done, must not cancel the tasks
		step = null;
		return instance;
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (step == null) {
			startDownloadingRating();
		} else if (step == Step.DOWNLOADING) {
			task.bindActivity(this);
		}
	}

	private void startDownloadingRating() {
		step = Step.DOWNLOADING;
		task = new RatingDownloadTask<DownloadActivity>(this, address, latitudeE6, longitudeE6);
		task.execute();
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (step == Step.DOWNLOADING) {
			task.unbindActivity();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (step == Step.DOWNLOADING) {
			task.destroy();
		}
	}

	@Override
	public void onTaskSuccess(Rating result) {
		step = Step.DONE;
		task = null;
		ShowNoteActivity_.intent(this).rating(result).start();
		finish();
	}

	@Override
	public void onTaskError(Exception exception) {
		step = Step.RETRY;
		task = null;
		showDialog(R.id.retry_dialog);
	}

	@Override
	public void onDownloadProgress(DownloadProgress progress) {
		// no progress update
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case R.id.retry_dialog:
			return createRetryDialog();
		}

		return null;
	}

	private Dialog createRetryDialog() {
		return new AlertDialog.Builder(this) //
				.setTitle(R.string.downloading_error_title) //
				.setMessage(R.string.error_information_message) //
				.setCancelable(true) //
				.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						startDownloadingRating();
					}
				}) //
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						step = null;
						finish();
					}
				}) //
				.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						step = null;
						finish();
					}
				}) //
				.create();
	}

	@Click
	void homeClicked() {
		HomeHelper.goToHome(this);
	}

}
