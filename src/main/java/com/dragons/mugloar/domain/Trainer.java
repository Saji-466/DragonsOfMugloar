package com.dragons.mugloar.domain;

import com.dragons.mugloar.domain.shop.Item;
import com.dragons.mugloar.domain.quest.Quest;

import java.util.List;

public interface Trainer {

    Item selectItem(List<Item> availableItems, GameState gameState);
    Quest selectQuest(List<Quest> availableQuests, GameState gameState);
}
