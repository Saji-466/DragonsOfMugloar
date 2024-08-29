package com.dragons.mugloar.domain.quest;

import lombok.Builder;

@Builder
public record QuestSolved(
        boolean success,
        int lives,
        int gold,
        int score,
        int turn,
        String message
) {
}
