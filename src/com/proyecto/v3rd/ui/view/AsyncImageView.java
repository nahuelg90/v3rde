package com.proyecto.v3rd.ui.view;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.proyecto.android.commons.core.application.container.ApplicationContainer;
import com.proyecto.android.commons.core.service.ServiceError;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;
import com.proyecto.android.commons.core.ui.view.AsyncImageListener;

import core.service.impl.ImageLoader;

public class AsyncImageView extends RelativeLayout {

	private static final float PROGRESS_BAR_DPI_WIDTH = 30.0f;
	private static final float PROGRESS_BAR_DPI_HEIGHT = 30.0f;
	
	
	private Context     			 context;
	private ProgressBar 			 progressBar;
	private Drawable			     defaultImage;
	private WeakReference<ImageView> imageRef;
	private WeakReference<Bitmap>	 bitmap; 
	private ImageLoader 			 imageLoader;
	private ImageView 				 imageView;
	private AsyncImageListener listener;

	public AsyncImageView(Context context) {
		super(context);
		this.context = context;
		findAndInitViews();
	}

	public AsyncImageView(Context context, AttributeSet attr) {
		super(context, attr);
		this.context = context;
		findAndInitViews();
	}
	
	private void findAndInitViews() {

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		
		// Convert the dps to pixels, based on density scale
		int pixelsWidth = (int) (PROGRESS_BAR_DPI_WIDTH * scale + 0.5f);
		int pixelsHeight = (int) (PROGRESS_BAR_DPI_HEIGHT * scale + 0.5f);

		this.progressBar  = new ProgressBar(this.context);
		LayoutParams layoutParams = new LayoutParams(pixelsWidth, pixelsHeight);
		layoutParams.addRule(CENTER_IN_PARENT);
		this.progressBar.setLayoutParams(layoutParams);
		
		imageView = new ImageView(this.context);
		LayoutParams layoutParams2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams2.addRule(CENTER_IN_PARENT);
		imageView.setLayoutParams(layoutParams2);
		imageView.setScaleType(ScaleType.CENTER_INSIDE);
		
		this.imageRef = new WeakReference<ImageView>(imageView);

		// Add elements
		this.addView(this.progressBar);
		this.addView(imageView);
		
		this.imageLoader  = (ImageLoader) ApplicationContainer.getInstance().getComponent(ImageLoader.class);
		this.defaultImage = (Drawable) ApplicationContainer.getInstance().getComponent(Drawable.class);
	}
	
	/**
	 * sets the image scaleType
	 * @param scaleType
	 */
	public void setScaleType(ScaleType scaleType){
		imageView.setScaleType(scaleType);
	}
	
	/**
	 * Set the default image in case of error overrriding the
	 * previous one setted via application container.
	 * 
	 * @param defaultImage
	 */
	public void setDefaultImage(Drawable defaultImage) {
		
		this.defaultImage = defaultImage;
	}
	
	/**
	 * Loads an image resource from Internet using <code>ImageLoader</code> 
	 * service and calls a <code>AsyncImageListener</code> on complete.
	 * @param imageUrl
	 * @param width
	 * @param height
	 * @param listener
	 */
	public void loadImage(String imageUrl, int width, int height, AsyncImageListener listener){
		this.listener = listener;
		loadImage(imageUrl, width, height);
	}
	
	/**
	 * Loads an image resource from Internet using <code>ImageLoader</code> 
	 * service.
	 * 
	 * @param imageUrl
	 * @param width
	 * @param height
	 */
	public void loadImage(String imageUrl, int width, int height){
		
		// load default image if url is null or empty
		if(imageUrl == null || imageUrl.length() < 1) {
			ImageView anImageView = imageRef.get();
			if(anImageView != null) {
				anImageView.setImageDrawable(this.defaultImage);
			}
			if (listener != null) {
                listener.imageLoaded();
            }
			return;
		}
		
		imageLoader.getImageAsync(new ServiceExecutionListener() {
			
			public void onError(ServiceError error) {
				progressBar.setVisibility(View.INVISIBLE);
				
				ImageView anImageView = imageRef.get();
				if(anImageView != null) {
					anImageView.setImageDrawable(defaultImage);
				}
				if (listener != null) {
	                listener.imageLoaded();
                }
			}
			
			public void onCallComplete(Object parameters) {
				
				Bitmap aBitmap = (Bitmap) parameters;
				progressBar.setVisibility(View.INVISIBLE);

					bitmap = new WeakReference<Bitmap>(aBitmap);
					
					ImageView anImageView = imageRef.get();
					if(anImageView != null) {
						if(aBitmap!=null)
							anImageView.setImageBitmap(aBitmap);
						else
							anImageView.setImageDrawable(defaultImage);
					}
				if (listener != null) {
	                listener.imageLoaded();
                }
			}
		}, imageUrl, width, height);
	}
	
	
	/* (non-Javadoc)
	 * @see android.view.View#onDetachedFromWindow()
	 */
	protected void onDetachedFromWindow() {
		
		Log.d("AsyncImageView", "onDetachedFromWindow");
		
		//image = null;
		if(bitmap != null) {
			
			Bitmap aBitmap = bitmap.get();
			if(aBitmap != null) {
				aBitmap.recycle();
				aBitmap = null;
			}
		}
		
		if(this.context != null){
			this.context = null;
		}
		if(this.defaultImage != null) {
			this.defaultImage.setCallback(null);
			this.defaultImage = null;
		}
		super.onDetachedFromWindow();
	}


	/**
	 * Recicles bitmap and sets ImageView to null.
	 */
	public void recycle(){
		
		this.onDetachedFromWindow();
	}
	
	/**
	 * @return the image
	 */
	public ImageView getImage() {
		
		return imageRef.get();
		
	}

}
