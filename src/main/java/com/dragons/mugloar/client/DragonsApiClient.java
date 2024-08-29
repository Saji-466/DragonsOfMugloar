package com.dragons.mugloar.client;

import com.dragons.mugloar.client.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "dragonsApiClient", url = "${game.api.url}", configuration = FeignConfig.class)
public interface DragonsApiClient {

    @PostMapping("/game/start")
    @ResponseBody
    GameStatusResponse getGameStart();

    @GetMapping("/{gameId}/messages")
    List<Ad> getAds(@PathVariable("gameId") String gameId);

    @PostMapping("/{gameId}/solve/{adId}")
    SolveAttemptResponse solveQuest(@PathVariable("gameId") String gameId, @PathVariable("adId") String adId);

    @GetMapping("/{gameId}/shop")
    List<Item> getShopItems(@PathVariable("gameId") String gameId);

    @PostMapping("/{gameId}/shop/buy/{itemId}")
    ShoppingResultResponse buyItem(@PathVariable("gameId") String gameId, @PathVariable("itemId") String adId);

}
