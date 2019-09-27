package com.joshuahunschejones;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HealthCheck extends com.codahale.metrics.health.HealthCheck {
    @Inject
    public HealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
