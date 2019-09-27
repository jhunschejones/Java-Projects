package com.joshuahunschejones;

import com.codahale.metrics.health.HealthCheck;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppHealthCheck extends HealthCheck {
    @Inject
    public AppHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
