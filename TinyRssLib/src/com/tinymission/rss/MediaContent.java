package com.tinymission.rss;

import org.xml.sax.*;

public class MediaContent extends FeedEntity {

	
	private String url;
	private String type;
	private String medium;
	private int height;
	private int width;
	private int fileSize;
	private int duration;
	private Boolean isDefault;
	private String expression;
	private int bitrate;
	private int framerate;
	private String lang;
	private String sampligRate;
	
	public MediaContent(Attributes attributes) {
		super(attributes);
	}
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url should specify the direct url to the media object. If not included, a <media:player> element must be specified.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type is the standard MIME type of the object. It is an optional attribute.
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the medium
	 */
	public String getMedium() {
		return medium;
	}
	/**
	 * @param medium the medium is the type of object (image | audio | video | document | executable). 
	 * While this attribute can at times seem redundant if type is supplied, 
	 * it is included because it simplifies decision making on the reader side, 
	 * as well as flushes out any ambiguities between MIME type and object type. 
	 * It is an optional attribute.
	 */
	public void setMedium(String medium) {
		this.medium = medium;
	}
	
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height is the height of the media object. It is an optional attribute.
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width is the width of the media object. It is an optional attribute.
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * @return the fileSize
	 */
	public int getFileSize() {
		return fileSize;
	}
	/**
	 * @param fileSize the fileSize is the number of bytes of the media object. It is an optional attribute.
	 */
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	
	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration is the number of seconds the media object plays. It is an optional attribute.
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	/**
	 * @return whether this is the default object for this <media:group>
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}
	/**
	 * @param isDefault determines if this is the default object that should be used for the <media:group>. There should only be one default object per <media:group>. It is an optional attribute.
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}
	/**
	 * @param expression the expression determines if the object is a sample or the full version of the object, or even if it is a continuous stream (sample | full | nonstop). Default value is 'full'. It is an optional attribute.
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	/**
	 * @return the bitrate
	 */
	public int getBitrate() {
		return bitrate;
	}
	/**
	 * @param bitrate the bitrate is the kilobits per second rate of media. It is an optional attribute.
	 */
	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}
	
	/**
	 * @return the framerate
	 */
	public int getFramerate() {
		return framerate;
	}
	/**
	 * @param framerate the framerate is the number of frames per second for the media object. It is an optional attribute.
	 */
	public void setFramerate(int framerate) {
		this.framerate = framerate;
	}
	
	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}
	/**
	 * @param lang the lang is the primary language encapsulated in the media object. Language codes possible are detailed in RFC 3066. This attribute is used similar to the xml:lang attribute detailed in the XML 1.0 Specification (Third Edition). It is an optional attribute.
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	/**
	 * @return the sampligRate
	 */
	public String getSampligRate() {
		return sampligRate;
	}
	/**
	 * @param sampligRate the sampligRate is the number of samples per second taken to create the media object. It is expressed in thousands of samples per second (kHz). It is an optional attribute.
	 */
	public void setSampligRate(String sampligRate) {
		this.sampligRate = sampligRate;
	}
	
}
