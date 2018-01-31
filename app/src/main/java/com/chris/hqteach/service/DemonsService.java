package com.chris.hqteach.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DemonsService extends JobService {
    public DemonsService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JobInfo.Builder builder = new JobInfo.Builder(1,new ComponentName(this,DemonsService.class));
        builder.setPeriodic(5000);
        JobInfo jobInfo = builder.build();
        JobScheduler jobScheduler = (JobScheduler)getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        startService(new Intent(this,HQService.class));

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}
