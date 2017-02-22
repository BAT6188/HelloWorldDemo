package com.goldmsg.ftputil;

import com.caucho.hessian.client.HessianProxyFactory;
import com.goldmsg.ftputil.bean.FtpConnectBean;
import com.goldmsg.ftputil.bean.GetFileBean;
import com.goldmsg.ftputil.commonUtils.ClientException;
import com.goldmsg.ftputil.commonUtils.FileDao;
import com.goldmsg.ftputil.commonUtils.FtpUtils;
import com.goldmsg.storage.protocol.ReplicaTaskService;
import com.goldmsg.storage.protocol.StorageService;
import com.goldmsg.storage.protocol.bean.ReplicaTask;
import com.goldmsg.storage.protocol.bean.ReplicaTaskException;
import com.goldmsg.storage.protocol.bean.ServerInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2017/1/5
 * Time: 15:15
 */
public class StorageImp extends FileUpload implements Runnable {

    public static Logger logger = LogManager.getLogger(StorageImp.class);
    //    public String url = "http://10.10.20.60:8092/openapi/workstation/v1/uploadfile?wsId=" + FtpUploadMain.xmlConfiguration.getConf("wsId", "test") + "&authKey=goldmsg";
    String url = "http://10.10.6.154:9504/" + ReplicaTaskService.class.getName();
    String rootPath = formatRootPath(FtpUploadMain.xmlConfiguration.getConf("workstation.data.root.path", "D:/GM-ZAP/"));
    FTPClient ftpClient = null;

    public StorageImp() {
        init();
    }

    public void init() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this, 2, 2, SECONDS);//this对象本身
    }

    @Override
    public void run() {
        try {
            //获取需要上传的重要文件
            GetFileBean getFileBean = getFileBean();
            if (Integer.parseInt(getFileBean.getCode()) == 0 && getFileBean.getBody().size() > 0) {
                for (GetFileBean.Body body : getFileBean.getBody()) {
                    //初始化FtpBasebean
                    FtpConnectBean ftpConnectBean = initFtp(body);
                    //获取ftp连接
                    ftpClient = FtpUtils.connectFtp(ftpConnectBean);
                    //ftp上传文件，提交重要文件上传的状态
                    postFileStatus(body, ftpClient);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            FtpUtils.closeFtp(ftpClient);
        }

    }

    @Override
    public GetFileBean getFileBean() throws IOException, ClientException, ReplicaTaskException {
        HessianProxyFactory factory = new HessianProxyFactory();
        ReplicaTaskService replicaTaskService = (ReplicaTaskService) factory.create(ReplicaTaskService.class, url);
        List<ReplicaTask> replicaTasks = replicaTaskService.queryReplicaTask("test", 10);
        GetFileBean getFileBean = new GetFileBean();
        List<GetFileBean.Body> bodyList = new LinkedList<>();
        for (ReplicaTask replicaTask : replicaTasks) {
            GetFileBean.Body body = new GetFileBean.Body();
            body.setFileId(replicaTask.getFileEntityId().getFileId().getUserFileid());
            body.setStorageId(replicaTask.getFileEntityId().getFileId().getStorageId());
            body.setTaskId(replicaTask.getTaskId());
            body.setUploadProtocol(replicaTask.getTaskProtocol());
            body.setUploadUrl(replicaTask.getTaskUrl());
            bodyList.add(body);
        }
        getFileBean.setBody(bodyList);
        getFileBean.setCode("0");
        return getFileBean;
    }

    @Override
    public void postFileStatus(GetFileBean.Body body, FTPClient ftpClient) throws SQLException, ClientException, MalformedURLException, ReplicaTaskException {
        //连接数据库获取重要文件文件信息
        List<Map> fileList = new FileDao().queryFile(body.getFileId());
        for (Map map : fileList) {
            File file = new File(rootPath + map.get("path").toString());
            //ftp上传文件
            try {
                FtpUtils.upload(file,ftpClient);
            } catch (Exception e) {
                logger.error("上传文件失败", e);
            }
        }
        HessianProxyFactory factory = new HessianProxyFactory();
        ReplicaTaskService replicaTaskService = (ReplicaTaskService) factory.create(ReplicaTaskService.class, url);
        ReplicaTask replicaTasks = replicaTaskService.syncTaskResult(body.getTaskId(), body.getStorageId(), 0, 0);
    }

    public String formatRootPath(String str) {
        if (!(str.endsWith("/") || str.endsWith("\\"))) {
            str = str + "/";
        }
        return str;
    }

    /**
     * 初始化FtpBasebean
     *
     * @param body 获取需要上传的重要文件响应参数
     */
    public FtpConnectBean initFtp(GetFileBean.Body body) {
        FtpConnectBean ftpConnectBean = new FtpConnectBean();
        Map<String, String> taskUrlMap = new Gson().fromJson(body.getUploadUrl(), new TypeToken<Map<String, String>>() {
        }.getType());
        String host = taskUrlMap.get("ip").toString();
        int port = Integer.parseInt(String.valueOf(taskUrlMap.get("port")));
        String username = taskUrlMap.get("username").toString();
        String password = taskUrlMap.get("password").toString();
        ftpConnectBean.setHost(FtpUploadMain.xmlConfiguration.getConf("host", host));
        ftpConnectBean.setPort(FtpUploadMain.xmlConfiguration.getConf("port", port));
        ftpConnectBean.setUsername(FtpUploadMain.xmlConfiguration.getConf("username", username));
        ftpConnectBean.setPassword(FtpUploadMain.xmlConfiguration.getConf("password", password));
        return ftpConnectBean;
    }
}
