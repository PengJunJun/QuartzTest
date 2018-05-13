package com.pengjunjun;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by pengjunjun on 2018/5/13.
 */
public class DelayTask implements Job {
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.print(jobExecutionContext.getJobDetail().getKey().getName());
    }
}
