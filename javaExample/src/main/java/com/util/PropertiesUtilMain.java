package com.util;

import com.config.ProjectEtcPath;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhouhaiming on 2017-5-22 10:40
 * Email: dg_chow@163.com
 *
 * @Description: 使用properties读取工具类
 */
public class PropertiesUtilMain {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesUtilMain.class);

    public static PropertiesUtil propUtil;
    public static void main(String[] args) {
        String etcRootPath = System.getProperty("user.dir") + "/etc";
        PropertyConfigurator.configureAndWatch(etcRootPath + "/log4j.properties", 60000);
        ProjectEtcPath.getInstance().setEtcRootPath(etcRootPath);
        init();
    }
    public static void init() {
        initConf();
    }
    private static void initConf() {
        propUtil = new PropertiesUtil(ProjectEtcPath.getInstance().getConfPath());
        LOG.info("配置文件参数读取完毕");
    }


}
