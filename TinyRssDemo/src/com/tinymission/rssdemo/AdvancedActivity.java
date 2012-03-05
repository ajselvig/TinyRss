package com.tinymission.rssdemo;

import android.os.Bundle;

import com.tinymission.rss.FeedActivity;;

public class AdvancedActivity extends FeedActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public boolean isImageVisible() {
		return true;
	}

	@Override
	public boolean isDateVisible() {
		return true;
	}

	@Override
	public String getFeedUrl() {
		return "http://feeds.arstechnica.com/arstechnica/open-source?format=xml";
	}
	

}
