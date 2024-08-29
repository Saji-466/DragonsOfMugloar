package com.dragons.mugloar.domain;

import com.dragons.mugloar.domain.quest.QuestSolved;
import com.dragons.mugloar.domain.shop.ItemPurchased;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameState implements Subject<GameStateChangeEvent> {

    private String gameId;

    private int lives;
    private int gold;
    private int level;
    private int score;
    private int turn;

    private final List<Observer<GameStateChangeEvent>> observerList = new ArrayList<>();

    private GameState(String gameId, int lives, int gold, int level, int score, int turn, List<Observer<GameStateChangeEvent>> observerList) {
        this.gameId = gameId;
        this.lives = lives;
        this.gold = gold;
        this.level = level;
        this.score = score;
        this.turn = turn;
        this.observerList.addAll(observerList);
        this.observerList.forEach(o -> o.observe(GameStateChangeEvent.gameStartEvent(this)));
    }

    public static GameState initializeGame(String gameId, int lives, int gold, int level, int score, int turn, List<Observer<GameStateChangeEvent>> observerList) {
        if (gameId == null || gameId.isEmpty()) {
            throw new IllegalArgumentException("Game Id cannot be empty");
        }
        if (lives <= 0) {
            throw new IllegalArgumentException("Game cannot start with 0 or less lives");
        }

        return new GameState(gameId, lives, gold, level, score, turn, observerList);
    }

    public void updateItemPurchased(ItemPurchased itemPurchased) {
        validateUpdate(itemPurchased.turn());

        this.gold = itemPurchased.gold();
        this.level = itemPurchased.level();
        this.turn = itemPurchased.turn();
        this.lives = itemPurchased.lives();

        var changeEvent = GameStateChangeEvent.itemPurchasedEvent(this, itemPurchased);
        this.observerList.forEach(o -> o.observe(changeEvent));
    }

    public void updateQuestSolved(QuestSolved questSolved) {
        validateUpdate(questSolved.turn());

        this.lives = questSolved.lives();
        this.score = questSolved.score();
        this.gold = questSolved.gold();
        this.turn = questSolved.turn();

        var changeEvent = GameStateChangeEvent.questSolvedEvent(this, questSolved);
        this.observerList.forEach(o -> o.observe(changeEvent));
        if (this.isGameOver()) {
            this.observerList.forEach(o -> o.observe(GameStateChangeEvent.gameOverEvent(this)));
        }
    }

    private void validateUpdate(int newTurn) {
        if (this.isGameOver()) {
            throw new IllegalArgumentException("Game finished, cannot update state");
        }
        if (this.turn > newTurn) {
            throw new IllegalArgumentException("Out of order turn update");
        }
    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    @Override
    public void addObserver(Observer<GameStateChangeEvent> observer) {
        this.observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer<GameStateChangeEvent> observer) {
        this.observerList.remove(observer);
    }
}
