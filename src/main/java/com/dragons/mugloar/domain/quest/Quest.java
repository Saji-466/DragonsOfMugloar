package com.dragons.mugloar.domain.quest;

import lombok.Builder;

@Builder
public record Quest(
        String id,
        int reward,
        int expiresIn,
        String encrypted,
        String description,
        Probability probability
) {}
