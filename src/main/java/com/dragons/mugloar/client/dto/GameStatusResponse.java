package com.dragons.mugloar.client.dto;

public record GameStatusResponse(
        String gameId,
        int lives,
        int gold,
        int level,
        int score,
        int turn
) {
}