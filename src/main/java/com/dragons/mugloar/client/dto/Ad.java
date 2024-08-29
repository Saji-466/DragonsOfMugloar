package com.dragons.mugloar.client.dto;

import com.dragons.mugloar.domain.quest.Probability;
import com.dragons.mugloar.domain.quest.Quest;

import java.util.HashMap;
import java.util.Map;

import static com.dragons.mugloar.domain.quest.Probability.UNKNOWN;

public record Ad(
        String adId,
        String message,
        int reward,
        int expiresIn,
        String encrypted,
        String probability
) {
    private static final Map<String, Probability> probabilityMap = new HashMap<>() {
        {
            put("SURE THING", Probability.SURE_THING);
            put("PIECE OF CAKE", Probability.PIECE_OF_CAKE);
            put("WALK IN THE PARK", Probability.WALK_IN_THE_PARK);
            put("QUITE LIKELY", Probability.QUITE_LIKELY);
            put("RATHER DETRIMENTAL", Probability.RATHER_DETRIMENTAL);
            put("GAMBLE", Probability.GAMBLE);
            put("PLAYING WITH FIRE", Probability.PLAYING_WITH_FIRE);
            put("RISKY", Probability.RISKY);
            put("SUICIDE MISSION", Probability.SUICIDE_MISSION);
            put("IMPOSSIBLE", Probability.IMPOSSIBLE);
            put("HMMM....", Probability.HMMM);

        }
    };

    public Quest toQuest() {
        var mappedProbability = probabilityMap.computeIfAbsent(probability.toUpperCase(), k -> UNKNOWN);
        return new Quest(adId, reward, expiresIn, encrypted, message, mappedProbability);
    }
}