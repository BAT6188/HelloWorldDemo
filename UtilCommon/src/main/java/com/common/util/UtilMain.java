package com.common.util;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.common.conf.Constant.MYSQL_IP;
import static com.common.conf.Constant.QPID_GPS_QUEUENAME;

/**
 * Created by zhouhaiming on 2017-5-22 10:40
 * Email: dg_chow@163.com
 *
 * @Description: 使用properties读取工具类
 */
public class UtilMain {
    private static final Logger LOG = LoggerFactory.getLogger(UtilMain.class);
    public static LocalXmlXmlConfiguration xmlConf = new LocalXmlXmlConfiguration();
    public static LocalPropertiesConfiguration propertiesConf = new LocalPropertiesConfiguration();

    public static void main(String[] args) {
        String etcDir = args[0];
        if (!(etcDir.endsWith("/") || etcDir.endsWith("\\"))) {
            etcDir = etcDir + "/";
        }

        //初始化日志
        String etcRootPath = System.getProperty("user.dir") + "/etc";
        PropertyConfigurator.configureAndWatch(etcRootPath + "/log4j.properties", 60000);
        //初始化xml配置文件
        xmlConf.addResource(etcDir + "common-site.xml");
        //初始化properties配置文件
        propertiesConf.addResource(etcDir + "conf.properties");
        LOG.info("配置文件参数读取完毕");
        System.out.println(propertiesConf.getStringValueByKey(MYSQL_IP));
        System.out.println(propertiesConf.getStringValueByKey(QPID_GPS_QUEUENAME));
        xmlConf.getConf("mysql.pwd","mysql.pwd");

    }
}
