import com.goldmsg.ftputil.bean.FtpConnectBean;

import com.goldmsg.ftputil.commonUtils.FtpUtils;
import com.goldmsg.storage.common.conf.LocalXmlConfiguration;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;


/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2017/1/5
 * Time: 9:13
 */
public class TestFtpUploading {
    public static LocalXmlConfiguration xmlConfiguration = new LocalXmlConfiguration();
    public static FTPClient ftpClient = null;

    public static void main(String[] args) {
        String etcDir = args[0];
        if (!(etcDir.endsWith("/") || etcDir.endsWith("\\"))) {
            etcDir = etcDir + "/";
        }
        PropertyConfigurator.configure(etcDir + "log4j.properties");
        xmlConfiguration.addResource(etcDir + "ftputil-site.xml");
//        DBUtils.getDataSource();

        FtpConnectBean ftpConnectBean = new FtpConnectBean();
        ftpConnectBean.setHost("10.10.20.190");
        ftpConnectBean.setPort(21);
        ftpConnectBean.setUsername("test");
        ftpConnectBean.setPassword("test");
        //获取ftp连接
        try {

            ftpClient = FtpUtils.connectFtp(ftpConnectBean);
            //ftp上传文件，提交重要文件上传的状态
            File file = new File("D:/GM-ZAP/hello2.txt");
            System.out.println("hello");
            FtpUtils.UploadStatus d = FtpUtils.upload(file, ftpClient);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FtpUtils.closeFtp(ftpClient);
        }

    }


}
