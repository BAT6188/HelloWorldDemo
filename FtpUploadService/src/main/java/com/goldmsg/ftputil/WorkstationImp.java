package com.goldmsg.ftputil;

import com.alibaba.fastjson.JSONObject;
import com.goldmsg.ftputil.bean.FtpConnectBean;
import com.goldmsg.ftputil.bean.GetFileBean;
import com.goldmsg.ftputil.commonUtils.ClientException;
import com.goldmsg.ftputil.commonUtils.FileDao;
import com.goldmsg.ftputil.commonUtils.FtpUtils;
import com.goldmsg.ftputil.commonUtils.HttpUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2016/12/29
 * Time: 16:28
 */
public class WorkstationImp extends FileUploadAbstract implements Runnable {
    public static Logger logger = LogManager.getLogger(WorkstationImp.class);
    public String url = "http://10.10.20.60:8092/openapi/workstation/v1/uploadfile?wsId=" + FtpUploadMain.xmlConfiguration.getConf("wsId", "test") + "&authKey=goldmsg";
    String rootPath = formatRootPath(FtpUploadMain.xmlConfiguration.getConf("workstation.data.root.path", "D:/GM-ZAP/"));
    FTPClient ftpClient =null;

    public WorkstationImp() {
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
                     ftpClient=FtpUtils.connectFtp(ftpConnectBean);
                        //ftp上传文件，提交重要文件上传的状态
                        postFileStatus(body,ftpClient);

                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            FtpUtils.closeFtp(ftpClient);
        }
    }

    /**
     * 获取需要上传的重要文件
     */
    @Override
    public GetFileBean getFileBean() throws IOException, ClientException {
        CloseableHttpResponse httpResponse = HttpUtils.getHttpResponse(HttpUtils.getHttpclient(), url);
        String jsonData = HttpUtils.getHttpEntityString(httpResponse);
        GetFileBean getFileBean = HttpUtils.parseJsonWithGson(jsonData, GetFileBean.class);
        return getFileBean;
    }

    /**
     * ftp上传文件，提交重要文件上传状态
     */
    @Override
    public void postFileStatus(GetFileBean.Body body,FTPClient ftpClient) throws SQLException, ClientException {
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
            JSONObject params = new JSONObject();
            params.put("taskId", body.getTaskId());
            params.put("fileId", body.getFileId());
            params.put("wsId", FtpUploadMain.xmlConfiguration.getConf("wsId", "test"));
            params.put("storageId", body.getStorageId());
            params.put("uploadStatus", 0);//上传状态码：0表示上传成功，
            params.put("path", file.getName());
            params.put("mediaType", Integer.parseInt(map.get("type").toString()));
            params.put("fileName", map.get("file_name").toString());
            params.put("size", Long.parseLong(map.get("size").toString()));
            params.put("duration", Integer.parseInt(map.get("duration").toString()));
            params.put("captureTime", map.get("capture_time").toString());
            CloseableHttpResponse httpResponse2 = null;
            try {
                httpResponse2 = HttpUtils.postHttpResponse(HttpUtils.getHttpclient(), url, params);
            } catch (IOException e) {
                logger.error("提交重要文件上传状态失败", e);
            }
            String jsonData2 = null;
            try {
                jsonData2 = HttpUtils.getHttpEntityString(httpResponse2);
            } catch (IOException e) {
                logger.error("json to GetFileBean 反序列化失败", e);
            }
            GetFileBean getFileBean2 = HttpUtils.parseJsonWithGson(jsonData2, GetFileBean.class);
            if (Integer.parseInt(getFileBean2.getCode()) != 0) {
                logger.info("taskId:" + body.getTaskId() + "提交重要文件上传状态失败" + getFileBean2.getCode());
            }
        }
    }

    /**
     * 初始化FtpBasebean，从ftpUrl中抽取ip、端口、账号、密码参数
     * ftp://root:root@192.168.11.11:21
     *
     * @param body  获取需要上传的重要文件响应参数
     */
    public FtpConnectBean initFtp(GetFileBean.Body body) {
        FtpConnectBean ftpConnectBean = new FtpConnectBean();
        String uploadUrl = body.getUploadUrl();
        String[] url = uploadUrl.split(":");
        String host = url[2].substring(url[2].indexOf("@"));
        Integer port = Integer.parseInt(url[3]);
        String username = url[1].substring(url[1].lastIndexOf("/") + 1);
        String password = url[2].substring(0, url[2].indexOf("@"));
        ftpConnectBean.setHost(FtpUploadMain.xmlConfiguration.getConf("host", host));
        ftpConnectBean.setPort(FtpUploadMain.xmlConfiguration.getConf("port", port));
        ftpConnectBean.setUsername(FtpUploadMain.xmlConfiguration.getConf("username", username));
        ftpConnectBean.setPassword(FtpUploadMain.xmlConfiguration.getConf("password", password));
        return ftpConnectBean;
    }


    public String formatRootPath(String str) {
        if (!(str.endsWith("/") || str.endsWith("\\"))) {
            str = str + "/";
        }
        return str;
    }


}
