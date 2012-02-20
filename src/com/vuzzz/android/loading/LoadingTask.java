package com.vuzzz.android.loading;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.vuzzz.android.LogHelper;

/**
 * 
 * @param <T>
 *            the activity should call {@link #bindActivity(Activity)} in it's
 *            onResume() method and {@link #unbindActivity()} in its onPause()
 *            method.
 */
public abstract class LoadingTask<T extends Activity & TaskResultListener<U>, U> extends AsyncTask<Void, DownloadProgress, TaskResult<U>> {

	private T activity;

	private TaskResult<U> result;

	private DownloadProgress currentProgress = DownloadProgress.create(0, 0, "");

	private final Context applicationContext;
	
	/**
	 * @param activity
	 *            cannot be null
	 */
	public LoadingTask(T activity) {
		applicationContext = activity.getApplicationContext();
		bindActivity(activity);
	}

	@Override
	protected final TaskResult<U> doInBackground(Void... params) {
		try {
			U result = load();
			return TaskResult.fromResult(result);
		} catch (Exception e) {
			LogHelper.logException("Could not execute background task", e);
			return TaskResult.fromException(e);
		}
	}

	protected abstract U load() throws Exception;

	protected void publishProgress(int currentProgress, int nextProgress, String progressMessage) {
		publishProgress(DownloadProgress.create(currentProgress, nextProgress, progressMessage));
	}

	protected void publishProgress(int currentProgress, int nextProgress, int progressMessageId) {
		publishProgress(currentProgress, nextProgress, applicationContext.getString(progressMessageId));
	}

	protected void publishProgress(int currentProgress, int nextProgress) {
		publishProgress(DownloadProgress.create(currentProgress, nextProgress));
	}

	@Override
	protected void onCancelled() {
		unbindActivity();
	}

	/**
	 * Should be called when the activity is paused.
	 * 
	 * Must be called from the UI thread
	 */
	public void unbindActivity() {
		activity = null;
	}

	/**
	 * Should be called when the activity is resumed.
	 * 
	 * Must be called from the UI thread
	 * 
	 * @param activity
	 *            cannot be null
	 */
	public void bindActivity(T activity) {
		this.activity = activity;
		if (result != null) {
			taskDone();
		}
	}

	/**
	 * Should be called to cancel the task, for example when the activity is
	 * destroyed
	 */
	public void destroy() {
		cancel(true);
		activity = null;
	}

	public DownloadProgress getCurrentProgress() {
		return currentProgress;
	}

	@Override
	protected void onProgressUpdate(DownloadProgress... values) {
		DownloadProgress newProgress = values[0];
		if (newProgress.progressMessage == null) {
			newProgress = DownloadProgress.create(newProgress.currentProgress, newProgress.nextProgress, currentProgress.progressMessage);
		}
		currentProgress = newProgress;

		if (activity != null) {
			activity.onDownloadProgress(currentProgress);
		}
	}

	@Override
	protected void onPostExecute(TaskResult<U> result) {
		if (isCancelled()) {
			return;
		}

		this.result = result;

		if (activity != null) {
			taskDone();
		}
	}

	/**
	 * Must be called from the UI thread.
	 */
	private void taskDone() {
		if (result.isException()) {
			activity.onTaskError(result.asException());
		} else {
			activity.onTaskSuccess(result.asResult());
		}
		result = null;
	}
	
	protected Context getApplicationContext() {
		return applicationContext;
	}

}
