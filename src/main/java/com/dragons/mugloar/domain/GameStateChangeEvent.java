package com.dragons.mugloar.domain;

import com.dragons.mugloar.domain.quest.QuestSolved;
import com.dragons.mugloar.domain.shop.ItemPurchased;

import java.time.Instant;

public record GameStateChangeEvent(
        String gameId,
        ChangeOriginEnum eventOrigin,
        Integer score,
        boolean success,
        int gold,
        int lives,
        int turn,
        String message,
        Instant datetime
) {

    static public GameStateChangeEvent itemPurchasedEvent(GameState gameState, ItemPurchased itemPurchased) {
        return new GameStateChangeEvent(
                gameState.getGameId(),
                ChangeOriginEnum.ITEM_PURCHASED,
                gameState.getScore(),
                itemPurchased.shoppingSuccess(),
                gameState.getGold(),
                gameState.getLives(),
                gameState.getTurn(),
                null,
                Instant.now()
        );
    }

    static public GameStateChangeEvent questSolvedEvent(GameState gameState, QuestSolved questSolved) {
        return new GameStateChangeEvent(
                gameState.getGameId(),
                ChangeOriginEnum.QUEST_COMPLETED,
                gameState.getScore(),
                questSolved.success(),
                gameState.getGold(),
                gameState.getLives(),
                gameState.getTurn(),
                questSolved.message(),
                Instant.now()
        );
    }

    public static GameStateChangeEvent gameOverEvent(GameState gameState) {
        return new GameStateChangeEvent(
                gameState.getGameId(),
                ChangeOriginEnum.GAME_OVER,
                gameState.getScore(),
                true,
                gameState.getGold(),
                gameState.getLives(),
                gameState.getTurn(),
                "Game over",
                Instant.now()
        );
    }

    public static GameStateChangeEvent gameStartEvent(GameState gameState) {
        return new GameStateChangeEvent(
                gameState.getGameId(),
                ChangeOriginEnum.GAME_START,
                gameState.getScore(),
                true,
                gameState.getGold(),
                gameState.getLives(),
                gameState.getTurn(),
                "Game start",
                Instant.now()
        );
    }
}

enum ChangeOriginEnum {
    ITEM_PURCHASED, GAME_OVER, GAME_START, QUEST_COMPLETED
}