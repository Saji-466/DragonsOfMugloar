package com.dragons.mugloar.trainer.behaviour;

import com.dragons.mugloar.ValueBuilder;
import com.dragons.mugloar.domain.quest.Probability;
import com.dragons.mugloar.domain.quest.Quest;
import com.dragons.mugloar.domain.trainer.behaviour.ReliableQuestSolveBehaviour;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.dragons.mugloar.ValueBuilder.buildQuest;
import static com.dragons.mugloar.ValueBuilder.buildGameState;

class ReliableQuestSolveBehaviourTest {

    private final ReliableQuestSolveBehaviour reliableQuestSolveBehaviour = new ReliableQuestSolveBehaviour();

    @Test
    void behave_fromAllTrapQuest_returnsFirst() {
        var trapQuest = buildQuest("lowestDifficulty999", 999, Probability.SURE_THING, "retrieve super awesome diamond from jef");
        var trapQuest1 = buildQuest("lowestDifficulty999", 999, Probability.SURE_THING, "retrieve super awesome diamond from jef");
        var adToSolve = reliableQuestSolveBehaviour.behave(Arrays.asList(trapQuest, trapQuest1), buildGameState(0));
        Assertions.assertThat(adToSolve).usingRecursiveAssertion().isEqualTo(trapQuest);
    }

    @Test
    void behave_fromAvailableQuests_ignoresTrapQuests() {
        var trapQuest = buildQuest("lowestDifficulty999", 999, Probability.SURE_THING, "retrieve super awesome diamond from jef");
        var midHighestReward = ValueBuilder.buildQuest("lowestDifficulty100", 100, Probability.PIECE_OF_CAKE);
        var adToSolve = reliableQuestSolveBehaviour.behave(Arrays.asList(trapQuest, midHighestReward), buildGameState(0));
        Assertions.assertThat(adToSolve).usingRecursiveAssertion().isEqualTo(midHighestReward);
    }

    @ParameterizedTest
    @MethodSource("provideQuestsForDifficultySelect")
    void behave_fromAvailableQuest_selectsLowestRiskHighestReward(List<Quest> availableAds, Quest expectedQuest) {

        var adToSolve = reliableQuestSolveBehaviour.behave(availableAds, buildGameState(0));
        Assertions.assertThat(adToSolve).usingRecursiveAssertion().isEqualTo(expectedQuest);
    }

    private static Stream<Arguments> provideQuestsForDifficultySelect() {
        var easyHighestReward = ValueBuilder.buildQuest("lowestDifficulty100", 100, Probability.SURE_THING);
        var easyLowestReward = ValueBuilder.buildQuest("lowestDifficulty99", 99, Probability.SURE_THING);
        var midHighestReward = ValueBuilder.buildQuest("MidDifficulty100", 100, Probability.PIECE_OF_CAKE);
        return Stream.of(
                Arguments.of(Arrays.asList(midHighestReward, easyHighestReward, easyLowestReward), easyHighestReward),
                Arguments.of(Arrays.asList(midHighestReward, easyLowestReward), easyLowestReward),
                Arguments.of(Collections.singletonList(midHighestReward), midHighestReward),
                Arguments.of(Arrays.asList(easyLowestReward, easyHighestReward), easyHighestReward),
                Arguments.of(Collections.singletonList(midHighestReward), midHighestReward),
                Arguments.of(Collections.emptyList(), null)

        );
    }
}