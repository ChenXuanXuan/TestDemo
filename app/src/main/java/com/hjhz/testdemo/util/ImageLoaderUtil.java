package com.hjhz.testdemo.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.hjhz.testdemo.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageLoaderUtil {
	
	private static ImageLoaderUtil imageLoaderUtil;
	public static boolean isImageLoaderInit=false;
	public static ImageLoader imageLoader = ImageLoader.getInstance() ;
	public static DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.big_default)
			.showImageOnFail(R.drawable.big_default)
			.resetViewBeforeLoading(true).cacheInMemory(true)
			.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY).displayer(new SimpleBitmapDisplayer()).build();

	public static ImageLoader getImageLoader(Context context){
		if(!isImageLoaderInit){
			   ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
					   .threadPoolSize(5)//线程池内加载的数量
					   .denyCacheImageMultipleSizesInMemory()
							   //.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
					   .memoryCache(new WeakMemoryCache())
							   //.memoryCacheSize(2 * 1024 * 1024)
					   .discCacheSize(50 * 1024 * 1024)
					   .denyCacheImageMultipleSizesInMemory()
					   .discCacheFileNameGenerator(new Md5FileNameGenerator())
					   .tasksProcessingOrder(QueueProcessingType.LIFO)
					   .discCacheFileCount(100)
					   .writeDebugLogs().imageDownloader(new BaseImageDownloader(context, 10000, 20000))
					   .build();
			imageLoader.init(config);
			isImageLoaderInit=true;
		}
		return imageLoader;
	}

	/**
	 * 获取网落图片资源
	 * @param url
	 * @return
	 */
	public static Bitmap getHttpBitmap(String url){
        URL myFileURL;  
        Bitmap bitmap=null;  
        try{
			myFileURL = new URL(url);
			//获得连接
			HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
			//设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			//连接设置获得数据流
			conn.setDoInput(true);
			//不使用缓存
			conn.setUseCaches(false);
			//这句可有可无，没有影响
			//conn.connect();
			//得到数据流
			InputStream is = conn.getInputStream();
			//解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			//关闭数据流
			is.close();
		}catch(Exception e){
            e.printStackTrace();  
        }  
          
        return bitmap;  
          
    }  
}
