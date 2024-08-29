package com.dragons.mugloar.service;

import lombok.Getter;

import java.util.concurrent.Callable;

@Getter
public class CallableWrapper<V> implements Callable<V> {
    private final Callable<V> original;
    private final String description;

    public CallableWrapper(Callable<V> callable, String description) {
        this.original = callable;
        this.description = description;
    }

    @Override
    public V call() throws Exception {
        return original.call();
    }

}
