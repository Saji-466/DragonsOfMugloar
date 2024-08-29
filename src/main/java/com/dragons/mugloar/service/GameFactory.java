package com.dragons.mugloar.service;

import com.dragons.mugloar.client.DragonsApiClient;
import com.dragons.mugloar.domain.GameState;
import com.dragons.mugloar.domain.GameStateChangeReportingObserver;
import com.dragons.mugloar.domain.trainer.DragonTrainer;
import com.dragons.mugloar.domain.trainer.behaviour.HealthPotHoarderBehaviour;
import com.dragons.mugloar.domain.trainer.behaviour.ReliableQuestSolveBehaviour;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class GameFactory {
    private DragonsApiClient dragonsApiClient;
    private AdBoard adBoard;
    private ItemShop itemShop;

    public Game initializeGame() {
        var gameStartResponse = dragonsApiClient.getGameStart();
        var reportingObserver = new GameStateChangeReportingObserver();
        var gameState = GameState.initializeGame(
                gameStartResponse.gameId(),
                gameStartResponse.lives(),
                gameStartResponse.gold(),
                gameStartResponse.lives(),
                gameStartResponse.score(),
                gameStartResponse.turn(),
                Collections.singletonList(reportingObserver)
        );

        var playSafeTrainer = new DragonTrainer(new HealthPotHoarderBehaviour(), new ReliableQuestSolveBehaviour());
        return new Game(gameState, playSafeTrainer, adBoard, itemShop);
    }
}
