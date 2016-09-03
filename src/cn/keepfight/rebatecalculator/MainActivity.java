package cn.keepfight.rebatecalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
	}

	/**
	 * 初始化UI界面
	 */
	void initUI() {
		((Button)findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ManageItemActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		((Button)findViewById(R.id.button2)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, RebateActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		((Button)findViewById(R.id.button3)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, OrderActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
	}
}
