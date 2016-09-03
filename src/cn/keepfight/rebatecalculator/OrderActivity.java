package cn.keepfight.rebatecalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class OrderActivity extends Activity {

	ListView listView;
	TextView textRemainder;
	TextView textTotal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		initUI();
	}

	/**
	 * 初始化UI界面
	 */
	void initUI() {
		// 找组件
		listView = (ListView) findViewById(R.id.listItem);
		textRemainder = (TextView) findViewById(R.id.textRemainder);
		textTotal = (TextView) findViewById(R.id.textTotal);

		//
		
		findViewById(R.id.btnReturn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		findViewById(R.id.btnReset).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//TODO 充值
			}
		});
		
		refleshList();
	}
	


	private void refleshList() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		ArrayList<Item> itemList = ItemManager.getInstance().getList();
		for (int i = 0; i < itemList.size(); i++) {
			Map<String, Object> itemMap = new HashMap<String, Object>();
			Item item = itemList.get(i);
			itemMap.put("index", i);
			itemMap.put("name", "" + item.name);
			itemMap.put("note", "" + item.note);
			itemMap.put("price", "" + item.price);
			// TODO 处理图片的
			data.add(itemMap);
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,
				R.layout.item_xx, new String[] { "index", "name", "note",
						"price" }, new int[] { R.id.textItemIndex,
						R.id.textItemName, R.id.textItemNote,
						R.id.textItemPrice });
		listView.setAdapter(simpleAdapter);

		
		Log.i("focus", "listView.getChildCount()  "+listView.getChildCount());
		for (int i = 0; i < listView.getChildCount(); i++) {
			View view = listView.getChildAt(i);
			Log.i("focus", "class  "+view.getClass().getName());
			view.findViewById(R.id.inputNumSub).setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (!hasFocus) {
						Log.i("focus", "en j"+hasFocus);
					}
				}
			});
		}
	}
}
