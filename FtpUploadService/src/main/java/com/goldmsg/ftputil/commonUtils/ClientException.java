package com.goldmsg.ftputil.commonUtils;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2016/12/29
 * Time: 15:43
 */
public class ClientException extends Exception {

    public ClientException() {

        super();

    }

    public ClientException(String msg) {

        super(msg);

    }

    public ClientException(String msg, Throwable cause) {

        super(msg, cause);

    }

    public ClientException(Throwable cause) {

        super(cause);

    }

}