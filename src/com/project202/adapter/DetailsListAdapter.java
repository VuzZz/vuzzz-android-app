package com.project202.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.project202.R;
import com.project202.model.Criterion;
import com.project202.model.Rating;
import com.project202.model.RatingDetails;
import com.project202.model.Theme;
import com.project202.model.ThemeName;

@EBean
public class DetailsListAdapter extends BaseAdapter {

	@RootContext
	Context context;

	private List<RatingDetails> details = new ArrayList<RatingDetails>();

	private Map<ThemeName, Integer> themePositions;

	public void setRating(Rating rating) {

		for (Theme t : rating.getThemes()) {
			details.add(t);
			if(t.getCriteria() != null)
				for (Criterion c : t.getCriteria())
					details.add(c);
		}

		for (int i = 0; i < details.size(); i++) {
			RatingDetails pd = details.get(i);
			if (pd instanceof Theme)
				themePositions.put(ThemeName.valueOf(pd.getName()), i);
		}
		
		notifyDataSetChanged();
	}

	private class ViewHolder {
		private TextView description;
		private TextView name;
		private TextView note;
	}

	public DetailsListAdapter() {
		this.themePositions = new HashMap<ThemeName, Integer>();
	}

	@Override
	public int getCount() {
		return details.size();
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (details.get(position) instanceof Criterion)
			return 1;
		else
			return 0;
	}

	@Override
	public RatingDetails getItem(int position) {
		return details.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			if (details.get(position) instanceof Criterion)
				convertView = View.inflate(context, R.layout.row_details_criterion, null);
			else
				convertView = View.inflate(context, R.layout.row_details, null);

			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.detail_name);
			holder.note = (TextView) convertView.findViewById(R.id.detail_grade);
			holder.description = (TextView) convertView.findViewById(R.id.detail_description);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(details.get(position).getName());
		holder.note.setText(String.valueOf(details.get(position).getNote()));
		holder.description.setText(details.get(position).getDescription());

		return convertView;
	}

	public int getThemeItemPosition(ThemeName themeName) {
		return themePositions.get(themeName);
	}

}