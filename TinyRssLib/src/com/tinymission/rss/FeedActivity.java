package com.tinymission.rss;

import java.text.SimpleDateFormat;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/** Base class for activities that show an RSS feed.
 *
 */
public abstract class FeedActivity extends ListActivity {

	
	/** Subclasses can override to provide a different main list layout
	 * This layout must have a ListView with id android:id/list
	 * @return the list layout id
	 */	
	public int getListLayoutId() {
		return R.layout.feed;
	}
	
	/** Subclasses can override to provide a different item layout
	 * @return the feed item layout id
	 */	
	public int getItemLayoutId() {
		return R.layout.feed_list_item;
	}
	
	/** Subclasses can override this to show an image next to the feed text.
	 * 
	 * @return true if an image should be shown next to the feed text (default false)
	 */
	public boolean isImageVisible() {
		return false;
	}
	
	/** Subclasses can override to make the item date visible under the title
	 * 
	 * @return true to show the item date under the title (default false)
	 */
	public boolean isDateVisible() {
		return false;
	}
	

	private SimpleDateFormat _dateformat;
	
	/**
	 * @param format the date format string to use when displaying dates (default "MM/dd/yy 'at' hh:mm a ")
	 */
	public void setDateFormat(String format) {
		_dateformat = new SimpleDateFormat(format);
	}
	
	
	private ProgressDialog _progressDialog;

	/** Shows a progress dialog.
	 * @param title
	 * @param text
	 */
	public void showProgressDialog(String title, String text) {
		_progressDialog = ProgressDialog.show(this, title, text);
	}
	
	/** Closes the currently open progress dialog, if there is one.
	 */
	public void cancelProgressDialog() {
		if (_progressDialog != null) {
			_progressDialog.cancel();
		}
	}
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getListLayoutId());
        
        setDateFormat("MM/dd/yy 'at' hh:mm a ");
        
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

		showProgressDialog("Loading...", "Downloading latest feed data");
		FeedFetcher fetcher = new FeedFetcher();
		fetcher.execute(getFeedUrl());
    }


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Item item = (Item)getListAdapter().getItem(position);
		onFeedItemClick(item);
	}
	
	protected void onFeedItemClick(Item item) {
		Uri url = Uri.parse(item.getLink());
		if (url != null) {
			Intent intent = new Intent(Intent.ACTION_VIEW, url);
			startActivity(intent);
		}
	}

	/** Subclasses must override this to provide their feed url.
	 */
	public abstract String getFeedUrl();

	@Override
	protected void onStart() {
		super.onStart();
	}
    
	/** Class for fecthing the feed content in the background.
	 * 
	 */
	public class FeedFetcher extends AsyncTask<String, Integer, Integer> {

		Feed _feed;
		
		@Override
		protected Integer doInBackground(String... params) {
			Reader reader = new Reader(params[0]);
			_feed = reader.fetchFeed();
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (_feed != null)
				setListAdapter(new FeedAdapter(_feed));
			else
				Log.w("FeedFetcher", "Unable to fetch feed. See previous errors.");
			cancelProgressDialog();
		}
		
	}
	

	/** A list adapter that maps a feed to a list view.
	 *
	 */
	public class FeedAdapter extends BaseAdapter {
	
		private Feed _feed;
		
		public FeedAdapter(Feed feed) {
			_feed = feed;
		}
		
		@Override
		public int getCount() {
			if (_feed == null)
				return 0;
			return _feed.getItemCount();
		}
	
		@Override
		public Object getItem(int position) {
			if (_feed == null)
				return null;
			return _feed.getItem(position);
		}
	
		@Override
		public long getItemId(int position) {
			return 0;
		}
	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
	
			View view = null;
			
			if (convertView == null) {
				LayoutInflater inflater = getLayoutInflater();
				view = inflater.inflate(getItemLayoutId(), parent, false);
			}
			else {
				view = convertView;
			}
			
			Item item = (Item)getItem(position);
			
			TextView titleView = (TextView)view.findViewById(R.id.feed_item_title);
			if (titleView != null)
				titleView.setText(item.getTitle());
			
			TextView descView = (TextView)view.findViewById(R.id.feed_item_description);
			if (descView != null)
				descView.setText(item.getCleanDescription());
			
			TextView pubDateView = (TextView)view.findViewById(R.id.feed_item_pubDate);
			if (pubDateView != null) {
				if (isDateVisible()) {
					pubDateView.setVisibility(View.VISIBLE);
					pubDateView.setText(_dateformat.format(item.getPubDate()));
				}
				else
					pubDateView.setVisibility(View.GONE);
			}
			
			ImageView imageView = (ImageView)view.findViewById(R.id.feed_item_image);
			if (imageView != null) {
				if (isImageVisible()) {
					//imageView.setLayoutParams(new LayoutParams(getIconWidth(), LayoutParams.WRAP_CONTENT));
					imageView.setVisibility(View.VISIBLE);
					MediaContent mc = item.getMediaContent();
					if (mc != null) {
						Log.v("Feed Adapter", "trying to use image url: " + mc.getUrl());
						imageView.setImageURI(Uri.parse(mc.getUrl()));
					}
				}
				else {
					imageView.setVisibility(View.GONE);
				}
			}
			
			return view;
		}
	
	}
	
}
