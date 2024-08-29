package com.dragons.mugloar;

import com.dragons.mugloar.domain.GameState;
import com.dragons.mugloar.domain.GameStateChangeEvent;
import com.dragons.mugloar.domain.Observer;
import com.dragons.mugloar.domain.quest.QuestSolved;
import com.dragons.mugloar.domain.shop.Item;
import com.dragons.mugloar.domain.quest.Probability;
import com.dragons.mugloar.domain.quest.Quest;
import com.dragons.mugloar.domain.shop.ItemPurchased;

import java.util.Collections;
import java.util.List;

public class ValueBuilder {

    public static Quest buildQuest(String id, int reward, Probability probability) {
        return buildQuest(id, reward, probability, null);
    }

    public static Quest buildQuest(String id, int reward, Probability probability, String description) {
        return Quest.builder().id(id).reward(reward).probability(probability).description(description).build();
    }

    public static Item buildItem(String id, int cost) {
        return Item.builder().id(id).cost(cost).build();
    }

    public static GameState buildGameState(int gold) {
        return GameState.initializeGame("gameId", 10, gold, 1, 1, 1, Collections.emptyList());
    }

    public static GameState buildGameState(int gold, int turn, int lives) {
        return GameState.initializeGame("gameId", 10, gold, lives, 1, turn, Collections.emptyList());
    }

    public static GameState buildGameState(int gold, int turn, int lives, List<Observer<GameStateChangeEvent>> observerList) {
        return GameState.initializeGame("gameId", lives, gold, 10, 1, turn, observerList);
    }

    public static ItemPurchased buildItemPurchased(int turn) {
        return buildItemPurchased(turn, 0, 0, 0);
    }

    public static ItemPurchased buildItemPurchased(int turn, int gold, int lives, int level) {
        return ItemPurchased.builder()
                .turn(turn)
                .gold(gold)
                .lives(lives)
                .level(level)
                .build();
    }

    public static QuestSolved buildQuestSolved(int turn) {
        return buildQuestSolved(turn, 0, 10, 0);
    }

    public static QuestSolved buildQuestSolved(int turn, int gold, int lives, int score) {
        return QuestSolved.builder()
                .turn(turn)
                .gold(gold)
                .lives(lives)
                .score(score)
                .build();
    }
}
