package com.vuzzz.android.adapter;

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
import com.vuzzz.android.DimenHelper;
import com.vuzzz.android.R;
import com.vuzzz.android.model.Criterion;
import com.vuzzz.android.model.Rating;
import com.vuzzz.android.model.RatingDetails;
import com.vuzzz.android.model.Theme;
import com.vuzzz.android.model.ThemeName;

@EBean
public class DetailsListAdapter extends BaseAdapter {

	@RootContext
	Context context;

	private List<RatingDetails> details = new ArrayList<RatingDetails>();

	private Map<String, Integer> themePositions;

	public void setRating(Rating rating) {
		details.clear();

		for (Theme t : rating.getThemes()) {
			details.add(t);
			if (t.getCriteria() != null)
				for (Criterion c : t.getCriteria())
					details.add(c);
		}

		for (int i = 0; i < details.size(); i++) {
			RatingDetails pd = details.get(i);
			if (pd instanceof Theme)
				themePositions.put(pd.getName(), i);
		}

		notifyDataSetChanged();
	}

	private class ViewHolder {
		private TextView coefficient;
		private TextView description;
		private TextView name;
		private TextView note;
	}

	public DetailsListAdapter() {
		this.themePositions = new HashMap<String, Integer>();
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

		RatingDetails ratingDetails = details.get(position);

		if (convertView == null) {
			if (ratingDetails instanceof Criterion) {
				convertView = View.inflate(context, R.layout.row_details_criterion, null);
			} else {
				convertView = View.inflate(context, R.layout.row_details, null);
				convertView.setMinimumHeight((int) DimenHelper.pixelSize(context, 70));
				convertView.setBackgroundDrawable(new CubeBackgroundDrawable(context, ratingDetails.getColor()));
			}
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.detail_name);
			holder.note = (TextView) convertView.findViewById(R.id.detail_grade);
			holder.description = (TextView) convertView.findViewById(R.id.detail_description);
			holder.coefficient = (TextView) convertView.findViewById(R.id.detail_coeff);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			if (!(ratingDetails instanceof Criterion)) {
				((CubeBackgroundDrawable) convertView.getBackground()).setColor(ratingDetails.getColor());
			}
		}

		holder.name.setText(details.get(position).getName());
		holder.note.setText(String.format("%.1f", details.get(position).getNote()));
		if (holder.description != null) {
			holder.description.setText(details.get(position).getDescription());
		}
		if (holder.coefficient != null) {
			holder.coefficient.setText("Coef." + details.get(position).getCoefficient());

		}

		return convertView;
	}

	public int getThemeItemPosition(ThemeName themeName) {
		return themePositions.get(themeName.getName());
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

}