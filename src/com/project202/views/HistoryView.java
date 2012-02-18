package com.project202.views;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.project202.R;
import com.project202.controller.RatingController;
import com.project202.model.PrintedRating;

@EViewGroup(R.layout.history)
public class HistoryView extends MyLinearLayout {

	private List<PrintedRating> oldRatings;
	private HistoryListAdapter adapter;
	@ViewById
	protected ListView historyList;
	
	private class HistoryListAdapter extends BaseAdapter{

		private List<PrintedRating> ratings;
		
		private LayoutInflater inflater;

		private class ViewHolder {
			private TextView locationName;
			private TextView grade;
		}
		
		public HistoryListAdapter(Context context, List<PrintedRating> ratings){
			inflater = LayoutInflater.from(context);
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
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.row_history, null);

				holder = new ViewHolder();
				holder.locationName = (TextView) convertView.findViewById(R.id.location_name);
				holder.grade = (TextView) convertView.findViewById(R.id.grade);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.locationName.setText("Here");
			holder.grade.setText(String.valueOf(ratings.get(position).getGlobalGrade()));

			return convertView;
		}
		
	}
	

	public HistoryView(Context context) {
		super(context);
	}

	@AfterViews
	public void afterViews(){
		oldRatings = RatingController.getTestHistoryRatings();
		adapter = new HistoryListAdapter(getContext(), oldRatings);
		
		historyList.setAdapter(adapter);
		
	}
	
	@Override
	public String getTitle(Context context) {
		return context.getString(R.string.history_title);
	}
}
