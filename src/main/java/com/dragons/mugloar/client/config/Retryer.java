package com.dragons.mugloar.client.config;

import feign.RetryableException;

import java.util.concurrent.TimeUnit;

public class Retryer implements feign.Retryer {

    private final int maxAttempts;
    private final long backoff;
    private int attempt;

    public Retryer() {
        this(2000, 5000, 3);
    }

    public Retryer(long period, long maxPeriod, int maxAttempts) {
        this.attempt = 1;
        this.maxAttempts = maxAttempts;
        this.backoff = Math.min(period, maxPeriod);
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        if (attempt++ >= maxAttempts) {
            throw e;
        }
        try {
            TimeUnit.MILLISECONDS.sleep(backoff);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public feign.Retryer clone() {
        return new Retryer(backoff, backoff, maxAttempts);
    }

}