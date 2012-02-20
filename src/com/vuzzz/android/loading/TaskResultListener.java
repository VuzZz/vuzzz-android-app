package com.vuzzz.android.loading;

public interface TaskResultListener<T> {
	void onTaskSuccess(T result);

	void onTaskError(Exception exception);

	void onDownloadProgress(DownloadProgress progress);
}