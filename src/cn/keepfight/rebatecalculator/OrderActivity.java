package cn.keepfight.rebatecalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderActivity extends Activity {

	LinearLayout listItemLayout;
	TextView textRemainder;
	TextView textTotal;
	double rebateNum = -1;
	EditText inputRebate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		initRebate();
	}

	private void initRebate() {
		try {
			rebateNum = getIntent().getDoubleExtra("rebate", -1);
		} catch (Exception e) {
		}
		if (rebateNum < 0) {
			inputRebate = new EditText(this);
			new AlertDialog.Builder(this)
					.setTitle("请输入提成总额")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									try {
										rebateNum = Double.valueOf(inputRebate
												.getText().toString());
									} catch (Exception e) {
										finish();
									}
									if (rebateNum < -1) {
										finish();
									}
									((TextView) findViewById(R.id.textRebateAmount))
											.setText("" + rebateNum);
									initUI();
								}
							})
					.setView(inputRebate)
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							}).show();
		} else {
			((TextView) findViewById(R.id.textRebateAmount)).setText(""
					+ rebateNum);
			initUI();
		}

	}

	/**
	 * 初始化UI界面
	 */
	void initUI() {
		// 找组件
		listItemLayout = (LinearLayout) findViewById(R.id.listItem);
		textRemainder = (TextView) findViewById(R.id.textRemainder);
		textTotal = (TextView) findViewById(R.id.textTotal);

		findViewById(R.id.btnReturn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		findViewById(R.id.btnReset).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 重置
			}
		});
		findViewById(R.id.btnComplete).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO 完成
					}
				});

		// 新建数量
		WidgetOrderItem orderItem = (WidgetOrderItem) getLayoutInflater()
				.inflate(R.layout.item_with_order, listItemLayout, false);
		listItemLayout.addView(orderItem);

		// 刷新内容
		refleshList();
	}

	private void refleshList() {
		// 刷新内容
	}
}
