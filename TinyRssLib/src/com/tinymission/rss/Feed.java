package com.tinymission.rss;

import java.util.*;

import org.xml.sax.Attributes;

import android.util.Log;



/** Encapsulation of a collection rss items.
 *
 */
public class Feed extends FeedEntity {
	
	public Feed(Attributes attributes) {
		super(attributes);
	}
	
	public Feed() {
		super(null);
	}

	private String _title;
	
	private String _link;
	
	private String _description;
	
	private Date _pubDate;
	
	private Date _lastBuildDate;
	
	private String _language;
	
	private ImageManager _imageManager = new ImageManager();
	
	/**
	 * @return The title of the feed
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * @param The title of the feed to set
	 */
	public void setTitle(String _title) {
		this._title = _title;
	}


	/**
	 * @return The URL of the feed
	 */
	public String getLink() {
		return _link;
	}

	/**
	 * @param The URL of the feed to set
	 */
	public void setLink(String _link) {
		this._link = _link;
	}


	/**
	 * @return The feed synopsis
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * @param The feed synopsis to set
	 */
	public void setDescription(String _description) {
		this._description = _description;
	}


	/**
	 * @return Indicates when the feed was published
	 */
	public Date getPubDate() {
		return _pubDate;
	}

	/**
	 * @param Indicates when the feed was published
	 */
	public void setPubDate(Date _pubDate) {
		this._pubDate = _pubDate;
	}

	/**
	 * @param Indicates when the feed was published
	 */
	public void setPubDate(String _pubDate) {
		try {
			this._pubDate = new Date(_pubDate);
		} catch (Exception ex) {
			Log.w("rss.Item", "Unable to parse date string: " + _pubDate);
		}
	}


	/**
	 * @return Indicates when the feed was built
	 */
	public Date getLastBuildDate() {
		return _lastBuildDate;
	}

	/**
	 * @param Indicates when the feed was built
	 */
	public void setLastBuiltDate(Date lastBuildDate) {
		this._lastBuildDate = _pubDate;
	}

	/**
	 * @param Indicates when the feed was built
	 */
	public void setLatBuildDate(String lastBuildDate) {
		try {
			this._lastBuildDate = new Date(lastBuildDate);
		} catch (Exception ex) {
			Log.w("rss.Item", "Unable to parse date string: " + lastBuildDate);
		}
	}


	/**
	 * @return The feed language
	 */
	public String getLanguage() {
		return _language;
	}

	/**
	 * @param The feed language
	 */
	public void setLanguage(String language) {
		this._language = language;
	}

	
	/**
	 * @return The feed's image manager
	 */
	public ImageManager getImageManager() {
		return _imageManager;
	}

	/**
	 * @param The feed's image manager
	 */
	public void setImageManager(ImageManager imageManager) {
		this._imageManager = imageManager;
	}
	
	
	private ArrayList<Item> _items = new ArrayList<Item>();
	
	/**
	 * @return the items in the feed
	 */
	public List<Item> getItems() {
		return _items;
	}

	/**
	 * @param item an item to add to the feed
	 */
	public void addItem(Item item) {
		_items.add(item);
	}
	
	/**
	 * @return the number of items in the feed.
	 */
	public int getItemCount() {
		return _items.size();
	}
	
	/**
	 * @param position the item index you wish to retrieve
	 * @return the item at position
	 */
	public Item getItem(int position) {
		return _items.get(position);
	}

}
