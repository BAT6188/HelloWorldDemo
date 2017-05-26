package com.goldmsg.ftputil;

import com.goldmsg.ftputil.commonUtils.DBUtils;
import com.goldmsg.storage.common.conf.LocalXmlConfiguration;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2016/12/29
 * Time: 16:21
 */
public class FtpUploadMain {
    public static LocalXmlConfiguration xmlConfiguration = new LocalXmlConfiguration();
    public static void main(String[] args) {
        String etcDir = args[0];
        if (!(etcDir.endsWith("/") || etcDir.endsWith("\\"))) {
            etcDir = etcDir + "/";
        }
        PropertyConfigurator.configure(etcDir + "log4j.properties");
        xmlConfiguration.addResource(etcDir + "ftputil-site.xml");
        DBUtils.getDataSource();
        if (args[1].toString().equals("0")){
            new WorkstationImp();
        }else if (args[1].toString().equals("1")){
            new StorageImp();
        }
    }
}
