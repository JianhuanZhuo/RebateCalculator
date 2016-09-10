package cn.keepfight.rebatecalculator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WidgetItemDetail  extends LinearLayout {

	TextView textItemPrice;
	TextView textItemPriceRelative;
	
	public WidgetItemDetail(Context context, AttributeSet attrs) {
		super(context, attrs);
		initUI();
	}

	private void initUI() {
		textItemPrice = (TextView)findViewById(R.id.textItemPrice);
		textItemPriceRelative = (TextView)findViewById(R.id.textItemPriceRelative);
	}
}
