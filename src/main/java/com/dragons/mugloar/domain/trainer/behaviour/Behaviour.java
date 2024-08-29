package com.dragons.mugloar.domain.trainer.behaviour;

import java.util.List;

public interface Behaviour<T, K> {

    T behave(List<T> ts, K k);
}
