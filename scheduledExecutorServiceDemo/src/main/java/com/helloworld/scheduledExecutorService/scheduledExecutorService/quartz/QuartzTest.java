package com.helloworld.scheduledExecutorService.scheduledExecutorService.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2017/3/17
 * Time: 9:20
 */
public class QuartzTest {

    public static void main(String[] args) throws InterruptedException {

        try {
            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

            Scheduler sched = schedFact.getScheduler();

// define the job and tie it to our HelloJob class
            JobDetail job = newJob(HelloJob.class)
                    .withIdentity("job1", "group1")
                    .build();

// compute a time that is on the next round minute
            Date runTime = evenMinuteDate(new Date());

// Trigger the job to run on the next round minute
            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startAt(runTime)
                    .build();

            // Tell quartz to schedule the job using our trigger
            sched.scheduleJob(job, trigger);
            Thread.sleep(90L * 1000L);
            sched.shutdown(true);

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}