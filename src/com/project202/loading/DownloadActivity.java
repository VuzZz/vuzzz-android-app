package com.project202.loading;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.UiThread;
import com.project202.HomeHelper;
import com.project202.R;
import com.project202.ShowNoteActivity_;
import com.project202.actionbar.ActionBarActivity;
import com.project202.model.Rating;

@EActivity(R.layout.loading)
@OptionsMenu(R.menu.download_menu)
public class DownloadActivity extends ActionBarActivity implements TaskResultListener<Rating> {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitle(address);
		super.onCreate(savedInstanceState);
		NonConfigurationInstance instance = (NonConfigurationInstance) getLastNonConfigurationInstance();

		if (instance != null) {
			step = instance.step;
			task = instance.task;
		}
		
		
		showRefreshing();
	}
	
	@UiThread(delay = 200)
	void showRefreshing() {
		getActionBarHelper().setRefreshActionItemState(true);
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
		task = new RatingDownloadTask<DownloadActivity>(this, latitudeE6, longitudeE6);
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
		ShowNoteActivity_.intent(this).address(address).rating(result).start();
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
	
	@OptionsItem
	void homeSelected() {
		HomeHelper.goToHome(this);
	}


}
