package com.dragons.mugloar.domain.trainer.behaviour;

import com.dragons.mugloar.domain.GameState;
import com.dragons.mugloar.domain.quest.Quest;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReliableQuestSolveBehaviour implements Behaviour<Quest, GameState> {

    public static final Set<Pattern> TRAP_QUESTS = Set.of(
            Pattern.compile("super awesome diamond")
    );

    @Override
    public Quest behave(List<Quest> quests, GameState gameState) {
        if (quests.isEmpty()) {
            return null;
        }
        var questsByProbability = quests.stream().filter(quest ->
                TRAP_QUESTS.stream().filter(pattern -> pattern.matcher(
                                Optional.ofNullable(quest.description()).orElse("")
                        ).find()
                ).findFirst().isEmpty()
        ).collect(Collectors.groupingBy(Quest::probability));

        if (questsByProbability.isEmpty()) {
            return quests.getFirst();
        }
        var lowestAvailableDifficulty = questsByProbability.keySet().stream().min(Comparator.comparingInt(p -> p.value)).get();
        return questsByProbability.get(lowestAvailableDifficulty).stream()
                .max(Comparator.comparingInt(Quest::reward)).orElse(null);
    }
}
