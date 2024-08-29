package com.dragons.mugloar.domain.shop;

import lombok.Builder;

@Builder
public record Item(
        String id,
        int cost
) {
    public static final String HP_POT_ID = "hpot";
}
