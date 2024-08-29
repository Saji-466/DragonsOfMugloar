package com.dragons.mugloar;

import com.dragons.mugloar.domain.GameState;
import com.dragons.mugloar.service.GameFactory;
import com.dragons.mugloar.service.TaskRunnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableFeignClients
public class MugloarApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MugloarApplication.class);

    @Autowired
    private GameFactory gameFactory;

    @Autowired
    private TaskRunnerService taskRunnerService;

    public static void main(String[] args) {
        SpringApplication.run(MugloarApplication.class, args).close();
    }

    @Override
    public void run(String... args) throws Exception {
        int parallelGames = args.length > 0 ? extractGameCount(args[0]) : 1;
        List<Callable<GameState>> games = IntStream.range(0, parallelGames).mapToObj(
                i -> gameFactory.initializeGame()
        ).collect(Collectors.toUnmodifiableList());
        taskRunnerService.runTasksInParallel(games);
    }

    private int extractGameCount(String arg) {
        try {
            var number = Integer.parseInt(arg);
            if (number < 1) {
                logger.info("Game count should be greater than 0. Defaulting to 1.");
                return 1;
            }
            return number;
        } catch (Exception exception) {
            logger.info("Could not parse argument: {} as an integer. Defaulting to 1.", arg);
            return 1;
        }
    }
}
