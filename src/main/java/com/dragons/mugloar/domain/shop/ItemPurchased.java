package com.dragons.mugloar.domain.shop;

import lombok.Builder;

@Builder
public record ItemPurchased(
        boolean shoppingSuccess,
        int gold,
        int lives,
        int level,
        int turn
) {
}
