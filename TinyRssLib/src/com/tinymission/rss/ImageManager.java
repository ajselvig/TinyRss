package com.tinymission.rss;

import java.io.*;
import java.util.HashMap;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.*;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.*;


/** Retrieves and stores images for the feed.
 *
 */
public class ImageManager {
	
	public static final String LOG_TAG = "ImageManager";
	
	private HashMap<String, Bitmap> _bitmapCache;
	private HashMap<Integer, DownloaderTask> _tasks;

	public ImageManager() {
		_bitmapCache = new HashMap<String, Bitmap>();
		_tasks = new HashMap<Integer, ImageManager.DownloaderTask>();
	}
	
	
	public void download(String url, ImageView imageView) {
		Log.v(LOG_TAG, "(" + imageView.hashCode() + ") Requested download: " + url);
		// cancel any existing downloads
		cancelDownloaderTask(imageView);
		
		// try the cache first
		if (tryLoadCachedBitmap(url, imageView))
			return;
		
		// download the image
		DownloaderTask task = new DownloaderTask(imageView);
		task.execute(url);
		_tasks.put(imageView.hashCode(), task);
	}
	

	private DownloaderTask getDownloaderTask(ImageView imageView) {
		return _tasks.get(imageView.hashCode());
	}
	
	
	private void cancelDownloaderTask(ImageView imageView) {
		if (_tasks.containsKey(imageView.hashCode())) {
			DownloaderTask task = _tasks.get(imageView.hashCode());
			Log.v(LOG_TAG, "(" + imageView.hashCode() + ") Cancelled download: " + task.url);
			task.cancel(true);
		}
	}
	
	private void removeDownloaderTask(ImageView imageView) {
		_tasks.remove(imageView.hashCode());
	}
	
	private Bitmap downloadBitmap(String url) {
		Log.v(LOG_TAG, "Began download: " + url);
        // AndroidHttpClient is not allowed to be used from the main thread
        final DefaultHttpClient client = new DefaultHttpClient();
        final HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w(LOG_TAG, "Error " + statusCode + " while retrieving bitmap from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    // return BitmapFactory.decodeStream(inputStream);
                    // Bug on slow connections, fixed in future release.
                    return BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (IOException e) {
            getRequest.abort();
            Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
        } catch (IllegalStateException e) {
            getRequest.abort();
            Log.w(LOG_TAG, "Incorrect URL: " + url);
        } catch (Exception e) {
            getRequest.abort();
            Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
        }
        return null;
    }

    /*
     * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
     */
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }
        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }
	
	
	/** Tries to load a bitmap associated with the given url from the cache into the image view.
	 * @param url the url of the image
	 * @param imageView the ImageView to load the image into
	 * @return true if the bitmap was found in the cache
	 */
	public boolean tryLoadCachedBitmap(String url, ImageView imageView) {
		if (_bitmapCache.containsKey(url)) {
			imageView.setImageBitmap(_bitmapCache.get(url));
			Log.v(LOG_TAG, "(" + imageView.hashCode() + ") Loaded cached: " + url);
			return true;
		}
		return false;
	}

	/** Adds the given bitmap to the cache associated with the url.
	 * @param url
	 * @param bitmap
	 */
	public void addBitmapToCache(String url, Bitmap bitmap) {
		_bitmapCache.put(url, bitmap);
	}
	
	/**
     * The actual AsyncTask that will asynchronously download the image.
     */
    class DownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private String url;
        private final ImageView _imageView;

        public DownloaderTask(ImageView imageView) {
        	_imageView = imageView;
        }

        /**
         * Actual download method.
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            return downloadBitmap(url);
        }

        /**
         * Once the image is downloaded, associates it to the imageView
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            addBitmapToCache(url, bitmap);
            if (_imageView != null) {
                DownloaderTask bitmapDownloaderTask = getDownloaderTask(_imageView);
                // Change bitmap only if this process is still associated with it
                if (this == bitmapDownloaderTask) {
                	_imageView.setImageBitmap(bitmap);
                    removeDownloaderTask(_imageView);
        			Log.v(LOG_TAG, "(" + _imageView.hashCode() + ") Completed download: " + url);
                }
            }
        }
    }
	
}
