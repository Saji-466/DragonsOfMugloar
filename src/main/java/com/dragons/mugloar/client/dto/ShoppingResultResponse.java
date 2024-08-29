package com.dragons.mugloar.client.dto;

import com.dragons.mugloar.domain.shop.ItemPurchased;

public record ShoppingResultResponse(
    boolean shoppingSuccess,
    int gold,
    int lives,
    int level,
    int turn
) {

    public ItemPurchased toItemPurchased() {
        return new ItemPurchased(
            shoppingSuccess,
            gold,
            lives,
            level,
            turn);
    }
}