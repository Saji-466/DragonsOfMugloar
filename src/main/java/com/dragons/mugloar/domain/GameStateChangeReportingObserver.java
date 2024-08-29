package com.dragons.mugloar.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.Set;

public class GameStateChangeReportingObserver implements Observer<GameStateChangeEvent> {
    private static final Logger logger = LoggerFactory.getLogger(GameStateChangeReportingObserver.class);

    private static final Set<ChangeOriginEnum> reportableOrigins = EnumSet.of(
            ChangeOriginEnum.GAME_START,
            ChangeOriginEnum.GAME_OVER
    );

    @Override
    public void observe(GameStateChangeEvent state) {
        if (logger.isDebugEnabled()) {
            logger.info(state.toString());
        } else if (reportableOrigins.contains(state.eventOrigin())) {
            logger.info(state.toString());
        }
    }
}
