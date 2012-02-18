package com.project202;

import com.googlecode.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

@EActivity(R.layout.show_note)
public class ShowNoteActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Uri data = intent.getData();

		if (data != null) {
			Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show();
		}
	}
}