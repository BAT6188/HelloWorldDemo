package com.common.conf;

import java.util.Map;
import java.util.Properties;

/*
 * 用于设置spring配置文件中的占位符
 */
public class SingletonProperty extends Properties {
	
	
    public static class SingletonPropertyHolder {
        static SingletonProperty instance = new SingletonProperty();
    }

    public static SingletonProperty getInstance() {
        return SingletonPropertyHolder.instance;
    }
	
	private SingletonProperty(){}
    
	public void put(Map<String, String> tMap) {
		for(Map.Entry<String, String> entry : tMap.entrySet()) {
			String value = entry.getValue();
			if(value==null) {
				value = "";
			}
			put(entry.getKey(), value);
		}
	}

	public int getProperty(String key, int defauleValue) {
		String value = getProperty(key);
		if(value==null) {
			return defauleValue;
		} else {
			return Integer.parseInt(value);
		}
	}
	
	public void setProperty(String key, int defauleValue) {
		setProperty(key,defauleValue+"");
	}
	
}
