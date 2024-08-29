package com.dragons.mugloar.service;

import com.dragons.mugloar.client.DragonsApiClient;
import com.dragons.mugloar.domain.shop.Item;
import com.dragons.mugloar.domain.shop.ItemPurchased;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemShop {

    private final DragonsApiClient dragonsApiClient;

    private final List<Item> availableItem = new ArrayList<>();

    public List<Item> getItemsOnSale(String gameId) {
        if (availableItem.isEmpty()) {
            var items = dragonsApiClient.getShopItems(gameId);
            this.availableItem.addAll(items.stream().map(com.dragons.mugloar.client.dto.Item::toItem).toList());

        }

        return availableItem;
    }

    public ItemPurchased submitPurchaseOrder(String gameId, Item item) {
        var buyResult = dragonsApiClient.buyItem(gameId, item.id());
        return buyResult.toItemPurchased();
    }
}
