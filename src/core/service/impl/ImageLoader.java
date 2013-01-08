package core.service.impl;

import java.util.concurrent.Callable;

import android.graphics.Bitmap;

import com.proyecto.android.commons.core.service.AbstractService;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;
import com.proyecto.android.commons.core.util.BitmapUtils;

public class ImageLoader extends AbstractService {
private static class ServiceImageCallable implements Callable<Object> {
		
		/** url for remote image */
		private String url;
		private int width = 0;
		private int height = 0;
		
		public ServiceImageCallable(String url) {
			this.url = url;
		}
		
		public ServiceImageCallable(String url, int width, int height) {
			this.url = url;
			this.width = width;
			this.height = height;
		}
		
		public Object call() throws Exception {

			Bitmap bitmap = BitmapUtils.getScaledImageFromUri(url, width, height);
			this.url = null;
			this.width = 0;
			this.height = 0;
			return bitmap; 
		}
	}
    
	/**
	 * Download an image from internet
	 * 
	 * @param listener to notified when the image is ready to be consumed
	 * @param url to download to image.
	 * @param width
	 * @param height
	 */
	public void getImageAsync(ServiceExecutionListener listener, final String url, int width, int height) {
		
		run(new ServiceImageCallable(url, width, height), listener);
	}
    
    
	/**
	 * Download an image from internet
	 * 
	 * @param listener to notified when the image is ready to be consumed
	 * @param url to download to image.
	 */
	@Deprecated
	public void getImageAsync(ServiceExecutionListener listener, final String url) {
		
		run(new ServiceImageCallable(url), listener);
	}
}
