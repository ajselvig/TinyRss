package com.tinymission.rss;

import java.util.Date;

import org.jsoup.Jsoup;
import org.xml.sax.Attributes;

import android.util.Log;



/** Encapsulates one RSS item.
 * 
 */
public class Item extends FeedEntity {
	
	public Item(Attributes attributes) {
		super(attributes);
	}
	
	private String _title;
	private String _link;
	private String _description;
	private Date _pubDate;
	private String _guid;
	private String _author;
	private String _comments;
	private String _source;
    private String _category;
	
	private MediaContent _mediaContent;
	private MediaThumbnail _mediaThumbnail;
	
	/**
	 * @return The title of the item
	 */
	public String getTitle() {
		return _title;
	}

    public String getCleanTitle() {
        return Jsoup.parse(_title).text();
    }

    /**
	 * @param The title of the item to set
	 */
	public void setTitle(String title) {
		this._title = title;
	}


	/**
	 * @return The URL of the item
	 */
	public String getLink() {
		return _link;
	}

	/**
	 * @param The URL of the item to set
	 */
	public void setLink(String link) {
		this._link = link;
	}


	/**
	 * @return The item synopsis
	 */
	public String getDescription() {
		return _description;
	}
	
	/**
	 * @return the description without any html tags.
	 */
	public String getCleanDescription() {
        try {
            return Jsoup.parse(_description).text();
        }
        catch (Exception ex) {
            return null;
        }
    }

	/**
	 * @param The item synopsis to set
	 */
	public void setDescription(String description) {
		this._description = description;
	}


	/**
	 * @return Indicates when the item was published
	 */
	public Date getPubDate() {
		return _pubDate;
	}

	/**
	 * @param Indicates when the item was published
	 */
	public void setPubDate(Date pubDate) {
		this._pubDate = pubDate;
	}

	/**
	 * @param Indicates when the item was published
	 */
	public void setPubDate(String pubDate) {
		try {
			this._pubDate = new Date(pubDate);
		} catch (Exception ex) {
			Log.w("rss.Item", "Unable to parse date string: " + pubDate);
		}
	}


	/**
	 * @return the _guid
	 */
	public String getGuid() {
		return _guid;
	}

	/**
	 * @param _guid the _guid to set
	 */
	public void setGuid(String guid) {
		this._guid = guid;
	}


	/**
	 * @return Email address of the author of the item
	 */
	public String getAuthor() {
		return _author;
	}

    public String getCleanAuthor() {
        try {
            return Jsoup.parse(_author).text();
        }
        catch (Exception ex) {
             return null;
        }
    }

    /**
	 * @param Email address of the author of the item to set
	 */
	public void setAuthor(String author) {
		this._author = author;
	}


	/**
	 * @return URL of a page for comments relating to the item
	 */
	public String getComments() {
		return _comments;
	}

	/**
	 * @param URL of a page for comments relating to the item to set
	 */
	public void setComments(String comments) {
		this._comments = comments;
	}

    public String getCategory() {
        return _category;
    }

    /**
     * @param Category of the item to set
     */
    public void setCategory(String _category) {
        this._category = _category;
    }


    /**
	 * @return The RSS channel that the item came from
	 */
	public String getSource() {
		return _source;
	}

	/**
	 * @param The RSS channel that the item came from to set
	 */
	public void setSource(String source) {
		this._source = source;
	}

	
	/**
	 * @return the media content for this item
	 */
	public MediaContent getMediaContent() {
		return _mediaContent;
	}
	
	/**
	 * @param mc the media content for this item
	 */
	public void setMediaContent(MediaContent mc) {
		_mediaContent = mc;
	}
	

	/**
	 * @return the media thumbnail for this item
	 */
	public MediaThumbnail getMediaThumbnail() {
		return _mediaThumbnail;
	}
	
	/**
	 * @param mc the media thumbnail for this item
	 */
	public void setMediaThumbnail(MediaThumbnail mt) {
		_mediaThumbnail = mt;
	}
	
	
}
