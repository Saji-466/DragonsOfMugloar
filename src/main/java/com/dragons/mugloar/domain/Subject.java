package com.dragons.mugloar.domain;

public interface Subject<T> {
    void addObserver(Observer<T> observer);

    void removeObserver(Observer<T> observer);
}