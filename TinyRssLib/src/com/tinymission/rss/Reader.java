package com.tinymission.rss;

import android.util.Log;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Stack;

/**
 * Reads an RSS feed and creates and RssFeed object.
 */
public class Reader {

	private URL _url;
	
	/** The allowed tags to parse content from (everything else gets lumped into its parent content, which allows for embedding html content.
	 * 
	 */
	public final static String[] CONTENT_TAGS = {"title", "link", "description", "language", "pubDate", "lastBuildDate", "docs", "generator", "managingEditor", "webMaster", "guid", "author"};
	
	/** The tags that should be parsed into separate entities, not just properties of existing entities.
	 * 
	 */
	public final static String[] ENTITY_TAGS = {"channel", "item", "media:content", "media:thumbnail"};

	/**
	 * @return whether tag is a valid content tag
	 */
	public static boolean isContentTag(String tag) {
		for (String t: CONTENT_TAGS) {
			if (t.equals(tag)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return whether tag is a valid entity tag
	 */
	public static boolean isEntityTag(String tag) {
		for (String t: ENTITY_TAGS) {
			if (t.equals(tag)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param url The URL of the feed.
	 */
	public Reader(URL url) {
		_url = url;
	}
	
	/**
	 * Assumes the URL is valid and will throw a runtime excpetion if not.
	 * Use the constructor taking a URL value as the argument if you aren't sure if you have a valid url.
	 * @param url The URL of the feed.
	 */
	public Reader(String url) {
		try {
			_url = new URL(url);
		}
		catch (MalformedURLException mue) {
			throw new RuntimeException("Bad URL for RSS feed reader");
		}
	}

	/** Actually grabs the feed from the URL and parses it into java objects.
	 * 
	 * @return The feed object containing all the feed data.
	 */
	public Feed fetchFeed() {
		Feed feed = null;

		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			XMLReader xr = sp.getXMLReader();

			Handler handler = new Handler();
			xr.setContentHandler(handler);
			
			URLConnection ucon = _url.openConnection();
			InputStream is = ucon.getInputStream();
			xr.parse(new InputSource(is));
			is.close();

			feed = handler.getFeed();

		} catch (ParserConfigurationException pce) {
			Log.e("SAX XML", "sax parse error", pce);
		} catch (SAXException se) {
			Log.e("SAX XML", "sax error", se);
		} catch (IOException ioe) {
			Log.e("SAX XML", "sax parse io error", ioe);
		}
		return feed;
	}
	
	
	/** SAX handler for parsing RSS feeds.
	 *
	 */
	public class Handler extends DefaultHandler {

		// Keep track of which entity we're currently assigning properties to
		private Stack<FeedEntity> _entityStack;
		
		public Handler() {
			_feed = new Feed();
			_entityStack = new Stack<FeedEntity>();
			_entityStack.add(_feed);
		}

		private Feed _feed;
		
		private StringBuilder _contentBuilder;
		
		public Feed getFeed() {
			return _feed;
		}

		@Override
		public void startDocument() throws SAXException {
			_contentBuilder = new StringBuilder();
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.endDocument();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (isContentTag(localName)) {
				_contentBuilder = new StringBuilder();
			}
			else if (isEntityTag(qName)) {
				if (qName.equals("item")) {
					Item item = new Item(attributes);
					_entityStack.add(item);
					_feed.addItem(item);
				}
				else if (qName.equals("media:content")) {
					MediaContent mediaContent = new MediaContent(attributes);
					FeedEntity lastEntity = _entityStack.lastElement();
					if (lastEntity.getClass() == Item.class) {
						((Item)lastEntity).setMediaContent(mediaContent);
					}
					_entityStack.add(mediaContent);
				} 
				else if (qName.equals("media:thumbnail")) {
					MediaThumbnail mediaThumbnail = new MediaThumbnail(attributes);
					FeedEntity lastEntity = _entityStack.lastElement();
					if (lastEntity.getClass() == Item.class) {
						Item item = (Item)lastEntity;
						MediaThumbnail existingMt = item.getMediaThumbnail();
						if (existingMt == null) {
							item.setMediaThumbnail(mediaThumbnail);
						}
					}
					_entityStack.add(mediaThumbnail);
				} 
				else if (qName.equals("channel")) {
					// this is just the start of the feed
				}
				else {
					throw new RuntimeException("Don't know how to create an entity from tag " + qName);
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			// get the latest parsed content, if there is any
			String content = "";
			if (isContentTag(qName)) {
				content = _contentBuilder.toString().trim();
				
				_entityStack.lastElement().setProperty(localName, content);
			}
			else if (isEntityTag(qName)) {
				_entityStack.pop();
			}
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			
			String content = new String(ch, start, length); 
			_contentBuilder.append(content); 
		}
		
		
		
	}

}
