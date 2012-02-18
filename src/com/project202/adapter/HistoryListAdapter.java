package com.project202.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.project202.R;
import com.project202.model.PrintedRating;

@EBean
public class HistoryListAdapter extends BaseAdapter{

	@RootContext
	Context context;
	
	private List<PrintedRating> ratings;
	
	private class ViewHolder {
		private TextView locationName;
		private TextView grade;
		private TextView deleteEntry;
	}
	
	public void setRatings(List<PrintedRating> ratings){
		this.ratings = ratings;
	}
	
	@Override
	public int getCount() {
		return ratings.size();
	}

	@Override
	public Object getItem(int position) {
		return ratings.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = View.inflate(context, R.layout.row_history, null);

			holder = new ViewHolder();
			holder.locationName = (TextView) convertView.findViewById(R.id.location_name);
			holder.grade = (TextView) convertView.findViewById(R.id.grade);
			holder.deleteEntry = (TextView) convertView.findViewById(R.id.delete_entry);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.locationName.setText("Here");
		holder.grade.setText(String.valueOf(ratings.get(position).getGlobalGrade()));
		holder.deleteEntry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ratings.remove(position);
				notifyDataSetInvalidated();
			}
		});

		return convertView;
	}
	
}