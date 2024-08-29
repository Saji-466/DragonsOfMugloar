package com.dragons.mugloar.client.dto;

public record Item(
        String id,
        String name,
        int cost
) {
    public com.dragons.mugloar.domain.shop.Item toItem() {
        return new com.dragons.mugloar.domain.shop.Item(id, cost);
    }
}

