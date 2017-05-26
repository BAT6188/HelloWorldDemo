package com.goldmsg.ftputil.bean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2017/1/3
 * Time: 8:44
 */

/**
 * 获取需要上传的重要文件响应字段
 */
public class GetFileBean {
    public String code;
    public String message;
    public List<Body> body;

    public static class Body {
        public String taskId;
        public String fileId;
        public String storageId;
        public String uploadProtocol;
        public String uploadUrl;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getStorageId() {
            return storageId;
        }

        public void setStorageId(String storageId) {
            this.storageId = storageId;
        }

        public String getUploadProtocol() {
            return uploadProtocol;
        }

        public void setUploadProtocol(String uploadProtocol) {
            this.uploadProtocol = uploadProtocol;
        }

        public String getUploadUrl() {
            return uploadUrl;
        }

        public void setUploadUrl(String uploadUrl) {
            this.uploadUrl = uploadUrl;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Body> getBody() {
        return body;
    }

    public void setBody(List<Body> body) {
        this.body = body;
    }
}

