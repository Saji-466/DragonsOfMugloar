package com.dragons.mugloar.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

@AllArgsConstructor
public class TaskRunnerService {
    private static final Logger logger = LoggerFactory.getLogger(TaskRunnerService.class);

    private ExecutorService executorService;

    public <T> void runTasksInParallel(List<Callable<T>> tasks) throws InterruptedException {
        CompletionService<T> completionService = new ExecutorCompletionService<>(executorService);
        tasks.forEach(completionService::submit);

        for (int i = 0; i < tasks.size(); i++) {
            Future<T> future = completionService.take();
            try {
                future.get();
            } catch (ExecutionException ee) {
                logger.error("Game failed: {}", ee.getLocalizedMessage());
            }
        }
    }
}