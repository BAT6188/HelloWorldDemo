package com.common.conf;

/**
 * 支持Key-Value配置
 */
public interface XmlConfiguration {

    public void addResource(String xmlFilePath);

    public String[] getItemsName();

    public void clear();

    public void setConf(String key, String value);

    public void setConf(String key, int value);

    public void setConf(String key, boolean value);

    public boolean getConf(String key, boolean defauleValue);

    public int getConf(String key, int defauleValue);

    public String getConf(String key, String defauleValue);

    public boolean hasConf(String key);
}
