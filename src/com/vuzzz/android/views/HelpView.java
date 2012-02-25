package com.vuzzz.android.views;

import static android.content.Intent.ACTION_VIEW;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.HtmlRes;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.vuzzz.android.R;
import com.vuzzz.android.ShareManager;
import com.vuzzz.android.VuzZzConfig;

@EViewGroup(R.layout.help)
public class HelpView extends FrameLayout {

	private static final Uri MARKET_URI = Uri.parse("market://details?id=com.vuzzz.android");

	@Bean
	ShareManager shareManager;

	@StringRes
	String shareProjectContent;

	@HtmlRes
	Spanned aboutContent;

	@ViewById
	TextView helpText;

	@ViewById
	View shareVuzzButton;

	@ViewById
	View androidMarketButton;

	@ViewById
	View shareVuzzzQrCode;

	@ViewById
	View shareVuzzzUrl;

	public HelpView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public HelpView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HelpView(Context context) {
		super(context);
	}

	@AfterViews
	void initLayout() {
		helpText.setText(aboutContent);
		helpText.setMovementMethod(LinkMovementMethod.getInstance());
		helpText.setLinkTextColor(Color.RED);

		if (VuzZzConfig.MUI) {
			shareVuzzzQrCode.setVisibility(VISIBLE);
			shareVuzzzUrl.setVisibility(VISIBLE);
			shareVuzzButton.setVisibility(GONE);
			androidMarketButton.setVisibility(GONE);
		}
	}

	@Click
	void shareVuzzButtonClicked() {
		shareManager.share(shareProjectContent);
	}

	@Click
	void androidMarketButtonClicked() {
		getContext().startActivity(new Intent(ACTION_VIEW, MARKET_URI));
	}

}
