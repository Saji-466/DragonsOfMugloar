package com.dragons.mugloar.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.dragons.mugloar.ValueBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

class GameStateTest {

    @Test
    void updateItemPurchased_invalidTurnUpdate_throwsException() {
        var gameState = buildGameState(10, 10, 1);

        Assertions.assertThatThrownBy(() -> gameState.updateItemPurchased(buildItemPurchased(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Out of order turn update");
    }

    @Test
    void updateItemPurchased_onItemPurchased_updatesStats() {
        var gameState = buildGameState(10, 0, 1);
        gameState.updateItemPurchased(buildItemPurchased(2, 100, 20, 30));

        assertThat(gameState.getTurn()).isEqualTo(2);
        assertThat(gameState.getGold()).isEqualTo(100);
        assertThat(gameState.getLevel()).isEqualTo(30);
        assertThat(gameState.getScore()).isEqualTo(1);
        assertThat(gameState.getGameId()).isEqualTo("gameId");
    }

    @Test
    void updateItemPurchased_onItemPurchased_producesGameChangeEvents() {
        var observer = new TestObserver();
        var gameState = buildGameState(10, 0, 1, Collections.singletonList(observer));
        gameState.updateItemPurchased(buildItemPurchased(2, 100, 20, 30));

        assertThat(observer.gameSates).hasSize(2);
        assertThat(observer.gameSates.get(0).eventOrigin()).isEqualTo(ChangeOriginEnum.GAME_START);
        assertThat(observer.gameSates.get(1).eventOrigin()).isEqualTo(ChangeOriginEnum.ITEM_PURCHASED);
    }

    @Test
    void updateQuestSolved_onQuestSolved_producesGameChangeEvents() {
        var observer = new TestObserver();
        var gameState = buildGameState(10, 0, 10, Collections.singletonList(observer));
        gameState.updateQuestSolved(buildQuestSolved(2));

        assertThat(observer.gameSates).hasSize(2);
        assertThat(observer.gameSates.get(0).eventOrigin()).isEqualTo(ChangeOriginEnum.GAME_START);
        assertThat(observer.gameSates.get(1).eventOrigin()).isEqualTo(ChangeOriginEnum.QUEST_COMPLETED);
    }

    @Test
    void updateQuestSolved_onDeath_producesGameOverEvent() {
        var observer = new TestObserver();
        var gameState = buildGameState(10, 0, 1, Collections.singletonList(observer));
        gameState.updateQuestSolved(buildQuestSolved(2, 1, 0, 0));

        assertThat(observer.gameSates).hasSize(3);
        assertThat(observer.gameSates.get(0).eventOrigin()).isEqualTo(ChangeOriginEnum.GAME_START);
        assertThat(observer.gameSates.get(1).eventOrigin()).isEqualTo(ChangeOriginEnum.QUEST_COMPLETED);
        assertThat(observer.gameSates.get(2).eventOrigin()).isEqualTo(ChangeOriginEnum.GAME_OVER);
    }

    @Test
    void updateQuestSolved_invalidTurnUpdate_throwsException() {
        var gameState = buildGameState(10, 10, 1);

        Assertions.assertThatThrownBy(() -> gameState.updateQuestSolved(buildQuestSolved(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Out of order turn update");
    }

    @Test
    void updateQuestSolved_onQuestSolved_updatesStats() {
        var gameState = buildGameState(10, 1, 1);

        gameState.updateQuestSolved(buildQuestSolved(2, 100, 20, 1000));

        assertThat(gameState.getTurn()).isEqualTo(2);
        assertThat(gameState.getGold()).isEqualTo(100);
        assertThat(gameState.getLives()).isEqualTo(20);
        assertThat(gameState.getScore()).isEqualTo(1000);
        assertThat(gameState.getLevel()).isEqualTo(1);
        assertThat(gameState.getGameId()).isEqualTo("gameId");
    }

    @Test
    void isGameOver_whenLivesOverZero_notGameOver() {
        var notGameOver = buildGameState(10, 10, 1);
        assertThat(notGameOver.isGameOver()).isFalse();
    }

    @Test
    void isGameOver_whenLivesZero_gameOver() {
        var notGameOver = buildGameState(10, 10, 1);
        notGameOver.updateQuestSolved(buildQuestSolved(11, 100, 0, 200));
        assertThat(notGameOver.isGameOver()).isTrue();
    }

    @Test
    void initializeGame_whenLivesLessThenOne_cannotInitialize() {
        Assertions.assertThatThrownBy(() -> GameState.initializeGame("gameId", 0, 10, 10, 1, 1, Collections.emptyList()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Game cannot start with 0 or less lives");
    }

    @Test
    void initializeGame_whenGameIdMissing_cannotInitialize() {
        Assertions.assertThatThrownBy(() -> GameState.initializeGame("", 10, 10, 10, 1, 1, Collections.emptyList()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Game Id cannot be empty");
    }

}

class TestObserver implements Observer<GameStateChangeEvent> {
    public final List<GameStateChangeEvent> gameSates = new ArrayList<>();

    @Override
    public void observe(GameStateChangeEvent state) {
        gameSates.add(state);
    }
}