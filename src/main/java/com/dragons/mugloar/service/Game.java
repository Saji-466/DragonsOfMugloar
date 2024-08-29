package com.dragons.mugloar.service;

import com.dragons.mugloar.domain.GameState;
import com.dragons.mugloar.domain.Trainer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.concurrent.Callable;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Game implements Callable<GameState> {
    private GameState state;
    private Trainer trainer;
    private AdBoard adBoard;
    private ItemShop itemShop;

    public GameState playGame() {
        while (!this.state.isGameOver()) {
            doShopping();
            doQuesting();
        }
        return this.state;
    }

    private void doShopping() {
        var itemsOnSale = itemShop.getItemsOnSale(this.state.getGameId());
        var itemToBuy = trainer.selectItem(itemsOnSale, state);

        while (itemToBuy != null) {
            Optional.of(itemToBuy).map(item -> itemShop.submitPurchaseOrder(this.state.getGameId(), item)).map(r -> {
                        this.state.updateItemPurchased(r);
                        return r;
                    }
            );
            itemToBuy = trainer.selectItem(itemsOnSale, state);
        }
    }

    private void doQuesting() {
        var ads = adBoard.listAvailableQuest(this.state.getGameId());
        var questToSolve = trainer.selectQuest(ads, state);
        var questSolved = adBoard.solveQuest(this.state.getGameId(), questToSolve);
        if (questSolved == null) {
            throw new IllegalStateException("Could not find solvable quest");
        }
        this.state.updateQuestSolved(questSolved);
    }

    @Override
    public GameState call() {
        return playGame();
    }
}
