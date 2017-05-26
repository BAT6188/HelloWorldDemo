package com.chow.schedule.service.quartz;

import org.quartz.*;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2017/3/17
 * Time: 9:38
 */
public class HelloJob implements Job {
    public HelloJob() {
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        System.out.println("1111");

    }
}

