package com.pengjunjun;

import org.quartz.*;

/**
 * Created by pengjunjun on 2018/5/13.
 */
public class Main {

    public static void main(String[] args) throws SchedulerException {

//        StdSchedulerFactory factory = new StdSchedulerFactory();
//
//        Scheduler scheduler = factory.getScheduler();
//
//        Job job = new DelayTask();
//
//        JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity("job1", "group1").build();
//
//        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).withRepeatCount(10)).build();
//
//        scheduler.scheduleJob(jobDetail,trigger);
//
//        scheduler.start();

        QuartzJobManager.getInstance().addJob("job1","trigger1",new DelayTask());

        QuartzJobManager.getInstance().addJob("job2","trigger2",new DelayTask(),0,20);


        QuartzJobManager.getInstance().stopJob("job1");
    }
}
