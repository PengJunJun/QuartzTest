package com.pengjunjun;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;


public class QuartzJobManager {
    private static final String JOB_GROUP_NAME = "jobGroupName";
    private static final String TRIGGER_GROUP_NAME = "jobTriggerGroupNaem";
    public static QuartzJobManager taskManager;
    private Scheduler scheduler;

    public static QuartzJobManager getInstance() {
        if (taskManager == null) {
            synchronized (QuartzJobManager.class) {
                if (taskManager == null) {
                    taskManager = new QuartzJobManager();
                }
            }
        }
        return taskManager;
    }

    private QuartzJobManager() {
        StdSchedulerFactory factory = new StdSchedulerFactory();
        try {
            scheduler = factory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加生成合同任务
     *
     * @param jobName
     * @param triggerName
     */
    public void addGenContractJob(String jobName, String triggerName) {
        addJob(jobName, GenContractJob.class, getGenContractTrigger(triggerName));
    }

    /**
     * 添加查询合同任务
     *
     * @param jobName
     * @param triggerName
     */
    public void addQueryContractJob(String jobName, String triggerName) {
        addJob(jobName, QueryContractJob.class, getQueryContractTrigger(triggerName));
    }

    /**
     * 添加查询合同详情任务
     *
     * @param jobName
     * @param triggerName
     */
    public void addGetContractDescJob(String jobName, String triggerName) {
        addJob(jobName, QueryContractDetailJob.class, getContractDetailTrigger(triggerName));
    }

    private void addJob(String jobName, Class<? extends Job> jobClass, Trigger trigger) {
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除任务
     *
     * @param jobName
     */
    public void stopJob(String jobName) {
        try {
            scheduler.deleteJob(new JobKey(jobName, JOB_GROUP_NAME));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取生成合同触发器
     *
     * @param triggerName
     * @return
     */
    private Trigger getGenContractTrigger(String triggerName) {
        return TriggerBuilder.newTrigger().withIdentity(triggerName, TRIGGER_GROUP_NAME).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(3)).build();
    }

    /**
     * 获取查询合同触发器
     *
     * @param triggerName
     * @return
     */
    private Trigger getQueryContractTrigger(String triggerName) {
        return TriggerBuilder.newTrigger().withIdentity(triggerName, TRIGGER_GROUP_NAME).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(10).withRepeatCount(3)).build();
    }

    /**
     * 获取查询合同详情触发器
     *
     * @param triggerName
     * @return
     */
    private Trigger getContractDetailTrigger(String triggerName) {
        return TriggerBuilder.newTrigger().withIdentity(triggerName, TRIGGER_GROUP_NAME).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24).withRepeatCount(3)).build();
    }
}
