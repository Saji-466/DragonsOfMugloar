package com.dragons.mugloar.service;

import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@AllArgsConstructor
public class TaskRunnerService {
    private static final Logger logger = LoggerFactory.getLogger(TaskRunnerService.class);

    private ExecutorService executorService;

    public <T> void runTasksInParallel(List<CallableWrapper<T>> tasks) throws InterruptedException {
        CompletionService<T> completionService = new ExecutorCompletionService<>(executorService);
        Map<Future<T>, CallableWrapper<T>> futureToCallableMap = new ConcurrentHashMap<>();

        for (CallableWrapper<T> wrappedCallable : tasks) {
            Future<T> future = completionService.submit(wrappedCallable);
            futureToCallableMap.put(future, wrappedCallable);
        }

        for (int i = 0; i < tasks.size(); i++) {
            Future<T> future = completionService.take();
            try {
                future.get();
            } catch (ExecutionException e) {
                Future<T> failedFuture = futureToCallableMap.keySet().stream()
                        .filter(f -> f.isDone() && f.equals(future))
                        .findFirst()
                        .orElseThrow();

                CallableWrapper<T> correspondingWrapper = futureToCallableMap.get(failedFuture);
                logger.error("Task {} failed - {}", correspondingWrapper.getDescription(), e.getLocalizedMessage());
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        this.executorService.shutdown();
    }
}