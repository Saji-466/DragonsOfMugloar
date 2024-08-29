package com.dragons.mugloar.service;

import com.dragons.mugloar.client.DragonsApiClient;

import com.dragons.mugloar.client.dto.Ad;
import com.dragons.mugloar.domain.quest.Quest;
import com.dragons.mugloar.domain.quest.QuestSolved;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AdBoard {

    private final DragonsApiClient dragonsApiClient;

    public List<Quest> listAvailableQuest(String gameId) {
        var ads = dragonsApiClient.getAds(gameId);
        return ads.stream().map(Ad::toQuest).toList();
    }

    public QuestSolved solveQuest(String gameId, Quest quest) {
        var solveResult = dragonsApiClient.solveQuest(gameId, quest.id());
        return solveResult.toQuestSolved();
    }
}
