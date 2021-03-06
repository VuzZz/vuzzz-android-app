package com.vuzzz.android.activity.note.details;

import static com.vuzzz.android.common.model.Theme.getPictoId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.res.ColorRes;
import com.vuzzz.android.R;
import com.vuzzz.android.activity.poimap.PoiMapActivity_;
import com.vuzzz.android.common.helper.DimenHelper;
import com.vuzzz.android.common.model.Criterion;
import com.vuzzz.android.common.model.Rating;
import com.vuzzz.android.common.model.RatingDetails;
import com.vuzzz.android.common.model.Theme;
import com.vuzzz.android.common.model.ThemeName;

@EBean
public class DetailsListAdapter extends BaseAdapter implements OnClickListener {

	private static final int CRITERION_ROW_TYPE = 0;
	private static final int THEME_ROW_TYPE = 1;
	private static final int THEME_ROW_DISABLED_TYPE = 2;

	@RootContext
	Context context;

	@ColorRes(R.color.gray)
	int grayColor;

	@ColorRes(R.color.white)
	int whiteColor;

	private List<RatingDetails> details = new ArrayList<RatingDetails>();

	private Map<String, Integer> themePositions;
	private Map<Integer, Theme> positionToTheme;
	private Rating rating;

	public void setRating(Rating rating) {
		this.rating = rating;
		positionToTheme = new HashMap<Integer, Theme>();
		details.clear();

		for (Theme t : rating.getThemes()) {
			details.add(t);
			if (t.getCriteria() != null)
				for (Criterion c : t.getCriteria())
					details.add(c);
		}

		Theme currentTheme = null;
		for (int i = 0; i < details.size(); i++) {
			RatingDetails pd = details.get(i);
			if (pd instanceof Theme) {
				currentTheme = (Theme) pd;
				themePositions.put(pd.getName(), i);
			}
			positionToTheme.put(i, currentTheme);
		}

		notifyDataSetChanged();
	}

	private class ViewHolder {
		private TextView coefficient;
		private TextView description;
		private TextView name;
		private TextView note;
		private RatingDetails rating;
		private Theme theme;
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
		return 4;
	}

	@Override
	public int getItemViewType(int position) {
		RatingDetails row = details.get(position);
		if (row instanceof Criterion) {
			return CRITERION_ROW_TYPE;
		} else {
			if (((Theme) row).isRelevant()) {
				return THEME_ROW_TYPE;
			} else {
				return THEME_ROW_DISABLED_TYPE;
			}
		}
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
			switch (getItemViewType(position)) {
			case THEME_ROW_TYPE:
				convertView = createThemeRowView(ratingDetails,
						R.layout.row_details);
				break;
			case THEME_ROW_DISABLED_TYPE:
				convertView = createThemeRowView(ratingDetails,
						R.layout.row_details_disabled);
				break;
			case CRITERION_ROW_TYPE:
				convertView = View.inflate(context,
						R.layout.row_details_criterion, null);
				break;
			}
			holder = new ViewHolder();
			holder.rating = ratingDetails;
			holder.name = (TextView) convertView.findViewById(R.id.detail_name);
			holder.note = (TextView) convertView
					.findViewById(R.id.detail_grade);
			holder.description = (TextView) convertView
					.findViewById(R.id.detail_description);
			holder.coefficient = (TextView) convertView
					.findViewById(R.id.detail_coeff);
			holder.theme = positionToTheme.get(position);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			switch (getItemViewType(position)) {
			case THEME_ROW_DISABLED_TYPE:
			case THEME_ROW_TYPE:
				((CubeBackgroundDrawable) convertView.getBackground())
						.setColor(ratingDetails.getColor());
			}
		}

		holder.name.setText(details.get(position).getName());
		if (holder.note != null) {
			holder.note.setText(String.format("%.1f", details.get(position)
					.getNote()));
		}
		if (holder.description != null) {
			holder.description.setText(details.get(position).getDescription());
		}
		if (holder.coefficient != null) {
			holder.coefficient.setText("Coef."
					+ details.get(position).getCoefficient());
		}

		Log.i("TAG", "Test");

		convertView.setOnClickListener(this);

		return convertView;
	}

	private View createThemeRowView(RatingDetails ratingDetails, int layout) {
		View convertView;
		convertView = View.inflate(context, layout, null);
		convertView.setMinimumHeight((int) DimenHelper.pixelSize(context, 70));
		convertView.setBackgroundDrawable(new CubeBackgroundDrawable(context,
				ratingDetails.getColor()));
		return convertView;
	}

	public int getThemeItemPosition(ThemeName themeName) {
		return themePositions.get(themeName.getName());
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public void onClick(View v) {
		ViewHolder holder = (ViewHolder) v.getTag();
		if (holder.rating instanceof Criterion) {
			Criterion criterion = (Criterion) holder.rating;
			String id = criterion.getId();
			Theme theme = holder.theme;
			PoiMapActivity_.intent(context) //
					.latitude(rating.latitude) //
					.longitude(rating.longitude) //
					.pictoId(getPictoId(theme)) //
					.color(getColor(theme)) //
					.title(holder.rating.getName()) //
					.poiType(id) //
					.start();
		}
	}

	private int getColor(Theme theme) {
		return theme.name.getLightColor();
	}

}