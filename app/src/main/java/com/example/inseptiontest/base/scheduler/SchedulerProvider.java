package com.example.inseptiontest.base.scheduler;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler ui();

    Scheduler io();
}
