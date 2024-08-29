package com.dragons.mugloar.trainer.behaviour;

import com.dragons.mugloar.domain.trainer.behaviour.HealthPotHoarderBehaviour;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.dragons.mugloar.ValueBuilder.buildGameState;
import static com.dragons.mugloar.ValueBuilder.buildItem;
import static com.dragons.mugloar.domain.shop.Item.HP_POT_ID;

import org.assertj.core.api.Assertions;

class HealthPotHoarderBehaviourTest {

    private final HealthPotHoarderBehaviour healthPotHoarderBehaviour = new HealthPotHoarderBehaviour();

    @Test
    public void behave_outOfAvailableItems_findHpPot() {
        var hpPotion = buildItem(HP_POT_ID, 50);
        var noiseItem = buildItem("diamond_ring", 0);
        var noiseItem1 = buildItem("op_sword", 110);

        var selectedItem = healthPotHoarderBehaviour.behave(Arrays.asList(hpPotion, noiseItem, noiseItem1), buildGameState(100));

        Assertions.assertThat(selectedItem).isEqualTo(hpPotion);
    }

    @Test
    public void behave_whenBroke_cantBuyAnything() {
        var hpPotion = buildItem(HP_POT_ID, 50);

        var selectedItem = healthPotHoarderBehaviour.behave(Collections.singletonList(hpPotion), buildGameState(25));
        Assertions.assertThat(selectedItem).isNull();
    }

    @Test
    public void behave_nothingIsAvailable_cantBuyAnything() {
        var selectedItem = healthPotHoarderBehaviour.behave(Collections.emptyList(), buildGameState(25));
        Assertions.assertThat(selectedItem).isNull();

        selectedItem = healthPotHoarderBehaviour.behave(null, buildGameState(25));
        Assertions.assertThat(selectedItem).isNull();
    }

}