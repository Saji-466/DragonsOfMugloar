package com.dragons.mugloar.domain.quest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Probability {


    SURE_THING(1),

    PIECE_OF_CAKE(2),

    WALK_IN_THE_PARK(3),

    QUITE_LIKELY(4),

    RATHER_DETRIMENTAL(5),

    GAMBLE(6),

    PLAYING_WITH_FIRE(7),

    RISKY(8),

    SUICIDE_MISSION(9),

    IMPOSSIBLE(10),

    HMMM(100),
    
    UNKNOWN(101);

    public final int value;
}
