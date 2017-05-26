package com.chow.schedule.service;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2016/12/16
 * Time: 17:44
 */

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 *一个删除文件的的定时任务
 * */
public class ScheduleJobMain {
    public static void main(String[] args) {
        ScheduleJob scheduleJob=new ScheduleJob();
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(5);
        scheduledExecutorService.scheduleAtFixedRate(scheduleJob, 1, 1, SECONDS);//this对象本身
    }
}
