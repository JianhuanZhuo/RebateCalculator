package cn.keepfight.rebatecalculator;

import java.text.DecimalFormat;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WidgetOrderItem extends LinearLayout implements OrderDelivee {

	EditText inputNumSub;
	TextView textTotalSub;
	TextView textTotalAdd;
	OrderDeliver deliver;
	Item item;

	public WidgetOrderItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode()) {
			return;
		}

	}

	public void setDelive(Item item, OrderDeliver deliver) {
		this.item = item;
		this.deliver = deliver;
		// 在设置接口后，才进行界面初始化
		initUI();
		deliver.regitsterDelivee(this);
	}

	private void initUI() {
		// 货品信息格式化
		((TextView) findViewById(R.id.textItemName)).setText(item.name);
		((TextView) findViewById(R.id.textItemNote)).setText(item.note);
		((TextView) findViewById(R.id.textItemPrice)).setText("" + item.price);
		// TODO 需要添加对图片的支持

		// 输入格式化
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
			public synchronized void afterTextChanged(Editable s) {

				int numSub = 0;
				try {
					numSub = Integer.valueOf(inputNumSub.getText().toString());
				} catch (Exception e) {
				}
				int applyRes = deliver.applyRebateSub(WidgetOrderItem.this, numSub, item.price);
				if (applyRes != -1) {
					inputNumSub.setText("" + applyRes);
					numSub = applyRes;
				}
				
				textTotalSub.setText(new DecimalFormat("#.00").format(numSub
						* item.price));
			}
		});

		textTotalSub = (TextView) findViewById(R.id.textTotalSub);
		textTotalSub.setText("0");
		textTotalAdd = (TextView) findViewById(R.id.textTotalAdd);
	}

	@Override
	public void remainderNotice(double remainder) {
		textTotalAdd.setText(""+(int)(remainder/item.price));
	}

	@Override
	public void reset() {
		inputNumSub.setText("0");
	}

}
