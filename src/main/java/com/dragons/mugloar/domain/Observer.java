package com.dragons.mugloar.domain;

public interface Observer<T> {
    void observe(T state);
}