package com.dragons.mugloar.domain.trainer.behaviour;

import com.dragons.mugloar.domain.GameState;
import com.dragons.mugloar.domain.shop.Item;

import java.util.List;

import static com.dragons.mugloar.domain.shop.Item.HP_POT_ID;

public class HealthPotHoarderBehaviour implements Behaviour<Item, GameState> {

    @Override
    public Item behave(List<Item> items, GameState gameState) {
        if (items == null) {
            return null;
        }
        return items.stream().filter(item -> HP_POT_ID.equals(item.id()) && item.cost() <= gameState.getGold()).findFirst().orElse(null);
    }
}
