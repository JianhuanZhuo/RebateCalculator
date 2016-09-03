package cn.keepfight.rebatecalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ManageItemActivity extends Activity {

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_manage);
		initUI();
	}

	private void initUI() {
		// 找组件
		listView = (ListView) findViewById(R.id.listItem);

		// 设置监听器
		findViewById(R.id.btnReturn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		findViewById(R.id.btnAdd).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ManageItemActivity.this,
						ItemStuffActivity.class);
				intent.putExtra("new", true);
				ManageItemActivity.this.startActivity(intent);
			}
		});

		// 设置列表
		refleshList();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ManageItemActivity.this,
						ItemStuffActivity.class);
				intent.putExtra("new", false);
				int index = Integer.valueOf(((TextView) view
						.findViewById(R.id.textItemIndex)).getText().toString());
				intent.putExtra("index", index);
				ManageItemActivity.this.startActivity(intent);
			}
		});
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
				R.layout.item_detail, new String[] { "index", "name", "note",
						"price" }, new int[] { R.id.textItemIndex,
						R.id.textItemName, R.id.textItemNote,
						R.id.textItemPrice });
		listView.setAdapter(simpleAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		refleshList();
	}
}
