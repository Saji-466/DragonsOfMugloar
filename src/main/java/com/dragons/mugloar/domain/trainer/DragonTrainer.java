package com.dragons.mugloar.domain.trainer;

import com.dragons.mugloar.domain.GameState;
import com.dragons.mugloar.domain.Trainer;
import com.dragons.mugloar.domain.shop.Item;
import com.dragons.mugloar.domain.quest.Quest;
import com.dragons.mugloar.domain.trainer.behaviour.Behaviour;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DragonTrainer implements Trainer {

    private final Behaviour<Item, GameState> itemPickerBehaviour;
    private final Behaviour<Quest, GameState> questGameStateBehaviour;

    @Override
    public Item selectItem(List<Item> availableItems, GameState gameState) {
        return itemPickerBehaviour.behave(availableItems, gameState);
    }

    @Override
    public Quest selectQuest(List<Quest> availableQuests, GameState gameState) {
        return questGameStateBehaviour.behave(availableQuests, gameState);
    }
}
