package com.common.util;

import com.common.conf.XmlConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouhaiming on 2017-5-10 20:32
 * Email: dg_chow@163.com
 *
 * @Description: Xml读取工具类
 */

public class LocalXmlXmlConfiguration implements XmlConfiguration {
    protected Map<String, String> conf = new HashMap<String, String>();

    public LocalXmlXmlConfiguration() {

    }

    public LocalXmlXmlConfiguration(String xmlFilePath) {
        addResource(xmlFilePath);
    }

    @Override
    public synchronized void addResource(String xmlFilePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ;
            Document xmlDoc = builder.parse(xmlFilePath);

            NodeList nodeProp = xmlDoc.getElementsByTagName("property");
            for (int i = 0; i < nodeProp.getLength(); i++) {
                String tPropName = xmlDoc.getElementsByTagName("name").item(i).getFirstChild().getNodeValue();
                Node nodeValue = xmlDoc.getElementsByTagName("value").item(i).getFirstChild();
                String tPropValue = null;
                if (nodeValue != null) {
                    tPropValue = nodeValue.getNodeValue();
                }
                conf.put(tPropName, tPropValue);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getItemsName() {
        return conf.keySet().toArray(new String[0]);
    }

    @Override
    public void clear() {
        conf.clear();
    }

    @Override
    public synchronized void setConf(String key, String value) {
        conf.put(key, value);
    }

    @Override
    public void setConf(String key, int value) {
        String valueString = String.valueOf(value);
        setConf(key, valueString);
    }

    @Override
    public void setConf(String key, boolean value) {
        String valueString = String.valueOf(value);
        setConf(key, valueString);
    }

    @Override
    public String getConf(String key, String defauleValue) {
        if (conf.containsKey(key)) {
            return conf.get(key);
        } else {
            return defauleValue;
        }
    }

    @Override
    public int getConf(String key, int defauleValue) {
        if (conf.containsKey(key)) {
            String ret = conf.get(key);
            if (ret == null) {
                return defauleValue;
            }
            return Integer.valueOf(ret);
        } else {
            return defauleValue;
        }
    }

    @Override
    public boolean getConf(String key, boolean defauleValue) {
        if (conf.containsKey(key)) {
            String ret = conf.get(key);
            if (ret == null) {
                return defauleValue;
            }
            return Boolean.valueOf(ret);
        } else {
            return defauleValue;
        }
    }

    @Override
    public boolean hasConf(String key) {
        return conf.containsKey(key);
    }
}
