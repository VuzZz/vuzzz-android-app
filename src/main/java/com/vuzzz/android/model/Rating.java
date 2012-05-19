package com.vuzzz.android.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.vuzzz.android.LogHelper;

import android.content.Context;

public class Rating implements Serializable {

	private final static String HISTO_FILE_PREFIX = "histo_file_";

	private final static int MAX_HISTORY_FILES = 20;

	public static FileOutputStream newRatingFile(Context context) throws FileNotFoundException {
		return context.openFileOutput(Rating.HISTO_FILE_PREFIX + new Date(), 0);
	}

	public static void checkMaxHistory(Context context) {
		File[] historyFiles = listHistoryFilesSortedByCreationDesc(context);
		if (historyFiles.length > MAX_HISTORY_FILES) {
			for (int i = MAX_HISTORY_FILES; i < historyFiles.length; i++) {
				boolean deleted = historyFiles[i].delete();
				if (!deleted) {
					LogHelper.log("Could not delete history file: " + historyFiles[i].getName());
				}
			}
		}
	}

	public static File[] listHistoryFiles(Context context) {
		File directory = context.getFilesDir();
		File[] historyFiles = directory.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {
				return filename.startsWith(HISTO_FILE_PREFIX);
			}
		});

		return historyFiles;
	}

	public static File[] listHistoryFilesSortedByCreationDesc(Context context) {
		File[] historyFiles = listHistoryFiles(context);
		Arrays.sort(historyFiles, new Comparator<File>() {
			@Override
			public int compare(File lhs, File rhs) {
				return rhs.getName().compareTo(lhs.getName());
			}
		});
		return historyFiles;
	}

	private static final long serialVersionUID = 1L;

	public List<Theme> themes;

	public String address;

	public double latitude;

	public double longitude;

	public List<Theme> getThemes() {
		return themes;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}

	public Rating() {
	}

	public Rating(List<Theme> themes) {
		this.themes = themes;
	}

	public Theme getTheme(ThemeName themeName) {
		for (Theme t : themes) {
			if (t.getThemeName().equals(themeName))
				return t;
		}
		return null;
	}

	public Float getThemeMark(ThemeName themeName) {
		for (Theme t : themes) {
			if (t.getThemeName().equals(themeName))
				return t.getNote();
		}
		return 0f;
	}

	public float getGlobalMark() {
		float mark = 0;
		for (Theme t : themes) {
			mark += t.getNote();
		}
		return mark/(float)themes.size();
	}
}
