package com.tinymission.rssdemo;

import java.util.Date;

import com.tinymission.rss.*;

import android.test.*;

public class Tests extends InstrumentationTestCase {

	public Tests() {
		super();
		
	}
	
	Feed _feed;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		Reader reader = new Reader("http://www.rssboard.org/files/sample-rss-2.xml");
		_feed = reader.fetchFeed();
	}

	public void testChannel() {
		assertEquals("Liftoff News", _feed.getTitle());
		assertEquals("http://liftoff.msfc.nasa.gov/", _feed.getLink());
		assertEquals("Liftoff to Space Exploration.", _feed.getDescription());
		
		Date pubDate = _feed.getPubDate();
		assertNotNull(pubDate);
		assertEquals(2003, pubDate.getYear() + 1900);
		assertEquals(5, pubDate.getMonth());
		assertEquals(9, pubDate.getDate());
	}
	
	public void testItems() {
		assertEquals(4, _feed.getItemCount());
		
		Item item = _feed.getItems().get(0);
		assertEquals("Star City", item.getTitle());
		assertEquals("http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp", item.getLink());
		assertEquals("How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's <a href=\"http://howe.iki.rssi.ru/GCTC/gctc_e.htm\">Star City</a>.", item.getDescription());
		
		Date pubDate = item.getPubDate();
		assertEquals(2003, pubDate.getYear() + 1900);
		assertEquals(5, pubDate.getMonth());
		assertEquals(3, pubDate.getDate());
	}
	
	
}
