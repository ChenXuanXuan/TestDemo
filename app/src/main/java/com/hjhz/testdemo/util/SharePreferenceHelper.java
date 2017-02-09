package com.hjhz.testdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author: 周希远
 * @类   说   明:	
 * @version 1.0
 * @创建时间：2015年4月14日 下午3:01:29
 * 
 */
public class SharePreferenceHelper {
	public static SharePreferenceHelper INSTANCE;
	private SharedPreferences preferences;
	private SharePreferenceHelper(){}

	public static synchronized SharePreferenceHelper getInstance(Context context){
		if (INSTANCE == null){
			INSTANCE = new SharePreferenceHelper();
			INSTANCE.preferences = PreferenceManager.getDefaultSharedPreferences(context);
		}
		return INSTANCE;
	}

	/**
	 * 储存String值
	 *
	 * @param key
	 * @param value
	 */
	public void setStringValue(String key, String value){
		preferences.edit().putString(key, value).commit();
	}

	/**
	 * 获取String值
	 *
	 * @param key
	 * @return
	 */
	public String getStringValue(String key){
		return preferences.getString(key, "");
	}

	/**
	 * 获取String值
	 *
	 * @param key
	 * @return
	 */
	public String getStringValue(String key, String devalue){
		return preferences.getString(key, devalue);
	}

	/**
	 * 储存Boolean值
	 *
	 * @param key
	 * @param value
	 */
	public void setBooleanValue(String key, Boolean value){
		preferences.edit().putBoolean(key, value).commit();
	}

	/**
	 * 获取Boolean值
	 *
	 * @param key
	 * @return
	 */
	public boolean getBooleanValue(String key){
		return preferences.getBoolean(key, false);
	}

	/**
	 * 获取Long值
	 *
	 * @param key
	 * @return
	 */
	public long getLongValue(String key){
		return preferences.getLong(key, 0L);
	}

	/**
	 * 获取Boolean值
	 *
	 * @param key
	 * @return
	 */
	public boolean getBooleanValue(String key, boolean defaultValue){
		return preferences.getBoolean(key, defaultValue);
	}
	
	public void delectValue(String key){
		preferences.edit().remove(key).commit();
	}

	/**
	 * 储存int值
	 *
	 * @param key
	 * @param value
	 */
	public void setIntValue(String key, int value){
		preferences.edit().putInt(key, value).commit();
	}

	public int getIntValue(String key, int value){
		return preferences.getInt(key, value);
	}

	public void remove(String name){
		preferences.edit().remove(name).commit();
	}

	public void setLongValue(String key, long value){
		preferences.edit().putLong(key, value).commit();
	}

	public float getFloatValue(String key, float value){
		return preferences.getFloat(key, value);
	}
}