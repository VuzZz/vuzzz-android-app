package com.vuzzz.android.activity.download;

public interface TaskResultListener<T> {
	void onTaskSuccess(T result);

	void onTaskError(Exception exception);

	void onDownloadProgress(DownloadProgress progress);
}