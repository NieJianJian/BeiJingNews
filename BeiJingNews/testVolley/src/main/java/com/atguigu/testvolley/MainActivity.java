package com.atguigu.testvolley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atguigu.testvolley.imagerlist.ImagerListActivity;
import com.atguigu.testvolley.textdata.TextDataOkActivity;

public class MainActivity extends Activity {

	protected static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


	}
	
	public void getStringDataFromVolley(View view){
		Intent intent = new Intent(this, TextDataActivity.class);
		startActivity(intent);
		
	}
	public void getStringDataFromVolley2(View view){
		Intent intent = new Intent(this, TextDataOkActivity.class);
		startActivity(intent);
	}

	
	public void getImageViewDataFromVolley(View view){
		Intent intent = new Intent(this, ImageViewDataActivity.class);
		startActivity(intent);
	}

	public void getImageListDataFromVolley(View view){
		Intent intent = new Intent(this, ImagerListActivity.class);
		startActivity(intent);
	}

}
