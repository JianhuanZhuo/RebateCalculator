package cn.keepfight.rebatecalculator;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WidgetOrderItem extends LinearLayout {

	EditText inputNumSub;
	TextView textTotalSub;
	TextView textTotalAdd;

	public WidgetOrderItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode()) { return; }
		
	}

	public void set(Item item, OrderDeliver deliver) {
		
		//在设置接口后，才进行界面初始化
		initUI();
	}

	private void initUI() {
		inputNumSub = (EditText) findViewById(R.id.inputNumSub);
		inputNumSub.setText("0");
		inputNumSub.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				int numSub = 0;
				try {
					numSub = Integer.valueOf(inputNumSub.getText().toString());
				} catch (Exception e) {
				}
				inputNumSub.setText(""+numSub);
				//TODO 设置响应
			}
		});
		
		textTotalSub = (TextView)findViewById(R.id.textTotalSub);
		textTotalSub.setText("0");
		textTotalAdd = (TextView)findViewById(R.id.textTotalAdd);
	}

}
