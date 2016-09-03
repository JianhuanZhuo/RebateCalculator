package cn.keepfight.rebatecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ItemStuffActivity extends Activity {

	ImageView itemImage;
	EditText itemName;
	EditText itemNote;
	EditText itemPrice;
	Item currentItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_stuff);
		initUI();
	}

	/**
	 * 初始化UI界面
	 */
	void initUI() {
		itemName = (EditText) findViewById(R.id.textItemName);
		itemNote = (EditText) findViewById(R.id.textItemNote);
		itemPrice = (EditText) findViewById(R.id.textItemPrice);
		((Button) findViewById(R.id.btnReturn))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		((Button) findViewById(R.id.btnDel))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 无论是不是新建都不会删除错
						ItemManager.getInstance().remove(currentItem);
						finish();
					}
				});
		((Button) findViewById(R.id.btnOK))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						currentItem.name = itemName.getText().toString();
						currentItem.note = itemNote.getText().toString();
						currentItem.price = Double.valueOf(itemPrice.getText()
								.toString());
						// TODO 还要加图片的处理
						if (getIntent().getBooleanExtra("new", true)) {
							ItemManager.getInstance().add(currentItem);
						}
						finish();
					}
				});

		// 初始化数据
		if (!getIntent().getBooleanExtra("new", true)) {
			currentItem = (Item) ItemManager.getInstance().get(
					getIntent().getIntExtra("index", -1));
			itemName.setText(currentItem.name);
			itemNote.setText(currentItem.note);
			itemPrice.setText("" + currentItem.price);
			// TODO 添加对图片的
		} else {
			currentItem = new Item();
			itemPrice.setText("0.0");
		}
	}
}
