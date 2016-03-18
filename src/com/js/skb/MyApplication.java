package com.js.skb;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Application;
import android.graphics.Bitmap;

public class MyApplication extends Application {
	private static MyApplication myApplication = null;
	
	public static String TAG;
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		TAG = this.getClass().getSimpleName();
		myApplication = this;
		initImageLoader();
	}
	public static MyApplication getInstance(){
		return myApplication;
	}
	/**
	 * ≥ı ºªØimageLoader
	 */
	public void initImageLoader(){
		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
										.memoryCache(new LruMemoryCache(5*1024*1024))
										.memoryCacheSize(10*1024*1024)
										.discCache(new UnlimitedDiscCache(cacheDir))
										.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
										.build();
		ImageLoader.getInstance().init(config);
	}
	
	public DisplayImageOptions getOptions(int drawableId){
		return new DisplayImageOptions.Builder()
		.showImageOnLoading(drawableId)
		.showImageForEmptyUri(drawableId)
		.showImageOnFail(drawableId)
		.resetViewBeforeLoading(true)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}
}

