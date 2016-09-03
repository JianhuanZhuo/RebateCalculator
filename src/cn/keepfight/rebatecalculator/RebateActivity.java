package cn.keepfight.rebatecalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RebateActivity extends Activity {

	EditText totalAmount;
	EditText rebateRate;
	EditText amountRebate;
	Button exchangeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rebate);
		initUI();
	}

	/**
	 * 初始化UI界面
	 */
	void initUI() {
		// 找组件
		totalAmount = (EditText) findViewById(R.id.textTotalAmount);
		rebateRate = (EditText) findViewById(R.id.textRebateRate);
		amountRebate = (EditText) findViewById(R.id.textAmountRebate);
		exchangeButton = (Button) findViewById(R.id.btnExchange);

		findViewById(R.id.btnExchange).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						try {
							float rebateNum = Float.valueOf(amountRebate
									.getText().toString());
							if (rebateNum < 0.1) {
								throw new Exception();
							}
							Intent intent = new Intent(RebateActivity.this,
									OrderActivity.class);
							RebateActivity.this.startActivity(intent);
						} catch (Exception e) {
							new AlertDialog.Builder(RebateActivity.this)
									.setTitle("提成金额错误，请正确输入金额并计算后再使用该功能")
									.setPositiveButton("确定", null).show();
						}
					}
				});

		findViewById(R.id.btnCalculate).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						try {
							float amount = Float.valueOf(totalAmount.getText()
									.toString());
							float rate = Float.valueOf(rebateRate.getText()
									.toString());
							if (amount < 0.1) {
								throw new Exception();
							}
							amountRebate.setText("" + amount * rate);
						} catch (Exception e) {
							new AlertDialog.Builder(RebateActivity.this)
									.setTitle("计算错误，请正确输入后再计算")
									.setPositiveButton("确定", null).show();
						}
					}
				});
		findViewById(R.id.btnReturn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
