package com.dragons.mugloar.client.dto;

import com.dragons.mugloar.domain.quest.QuestSolved;

public record SolveAttemptResponse(
    boolean success,
    int lives,
    int gold,
    int score,
    int turn,
    String message
) {
    public QuestSolved toQuestSolved() {
        return new QuestSolved(
            success,
            lives,
            gold,
            score,
            turn,
            message);
    }
}