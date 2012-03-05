package com.tinymission.rssdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class HomeActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViewById(R.id.simple_button).setOnClickListener(this);
        findViewById(R.id.advanced_button).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.simple_button:
			intent = new Intent(this, SimpleActivity.class);
			break;
		case R.id.advanced_button:
			intent = new Intent(this, AdvancedActivity.class);
			break;
		}
		if (intent != null) {
			startActivity(intent);
		}
	}
}