package com.chow.schedule.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2016/12/14
 * Time: 15:19
 */

/**
 * 计划定时任务
 * JDKDemo
 */
public class BeeperControlMain implements Runnable {
   public static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);


    public static void main(String[] args) {
        BeeperControlMain beeperControl = new BeeperControlMain();
        executorService.scheduleAtFixedRate(beeperControl, 1, 1, SECONDS);//this对象本身
    }

    public void run() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
        System.out.println(Thread.currentThread().getName() + "\t我是小蜜蜂..beep\t" + simpleDateFormat.format(new Date()));
        Random random = new Random();
        try {
            int a = random.nextInt(2);
            int b = 1;
            System.out.println(b / a);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
