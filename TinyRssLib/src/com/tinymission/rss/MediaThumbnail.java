package com.tinymission.rss;

import org.xml.sax.Attributes;

public class MediaThumbnail extends FeedEntity {

	public MediaThumbnail(Attributes attributes) {
		super(attributes);
	}

	private String url;
	private int width;
	private int height;

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}


	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param width the width to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
}
