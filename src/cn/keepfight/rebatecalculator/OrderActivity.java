package cn.keepfight.rebatecalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderActivity extends Activity implements OrderDeliver {

	LinearLayout listItemLayout;
	TextView textRemainder;
	TextView textTotal;
	double rebateNum = -1;
	EditText inputRebate;

	ArrayList<OrderDelivee> delivees = new ArrayList<OrderDelivee>();
	Map<OrderDelivee, Double> consumption = new HashMap<OrderDelivee, Double>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		initRebate();
	}

	private void initRebate() {
		try {
			rebateNum = PreciseKit.dou2(getIntent()
					.getDoubleExtra("rebate", -1));
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
										rebateNum = PreciseKit.dou2(inputRebate
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
				for (OrderDelivee delivee : consumption.keySet()) {
					delivee.reset();
				}
			}
		});
		findViewById(R.id.btnComplete).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});

		// 新建数量
		for (Item item : ItemManager.getInstance().getList()) {
			WidgetOrderItem orderItem = (WidgetOrderItem) getLayoutInflater()
					.inflate(R.layout.item_with_order, listItemLayout, false);
			listItemLayout.addView(orderItem);
			orderItem.setDelive(item, this);
		}

		// 刷新内容
		refleshList();
		textTotal.setText("0");
		textRemainder.setText("" + getRebateRemainder());
	}

	private void refleshList() {
		// 刷新内容
	}

	@Override
	public void regitsterDelivee(OrderDelivee delivee) {
		delivees.add(delivee);
		consumption.put(delivee, Double.valueOf(0));
		delivee.remainderNotice(getRebateRemainder());
	}

	@Override
	public double getRebateTotal() {
		return rebateNum;
	}

	@Override
	public double getRebateRemainder() {
		double rebateUsed = 0;
		for (Double cons : consumption.values()) {
			rebateUsed += cons;
		}
		return rebateNum - rebateUsed;
	}

	@Override
	public void updateRebate() {
	}

	@Override
	public synchronized int applyRebateSub(OrderDelivee delivee, int subNum,
			double price) {
		double rebateUsed = 0;
		for (Entry<OrderDelivee, Double> cons : consumption.entrySet()) {
			if (cons.getKey() == delivee) {
				continue;
			}
			rebateUsed += cons.getValue();
		}
		double remainder = rebateNum - rebateUsed;

		int res = -1;

		if (remainder < price * subNum) {
			res = (int) (remainder / price);
			subNum = res;
		}
		consumption.put(delivee, Double.valueOf(price * subNum));
		noticeRemainder();

		// 更新总价与剩余
		textTotal.setText("" + (getRebateTotal() - getRebateRemainder()));
		textRemainder.setText("" + getRebateRemainder());

		return res;
	}

	private void noticeRemainder() {
		for (OrderDelivee delivee : consumption.keySet()) {
			delivee.remainderNotice(getRebateRemainder());
		}
	}

}
