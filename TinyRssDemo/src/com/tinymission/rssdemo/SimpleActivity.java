package com.tinymission.rssdemo;

import com.tinymission.rss.FeedActivity;

public class SimpleActivity extends FeedActivity {

	@Override
	public String getFeedUrl() {
		return "http://www.engadget.com/rss.xml";
	}

}
