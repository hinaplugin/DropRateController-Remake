package com.hinaplugin.dropRateController.Listener;

import com.google.common.collect.Lists;
import com.hinaplugin.dropRateController.Rate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LootGenerateListener extends Rate implements Listener {

    @EventHandler
    public void onLootGenerate(LootGenerateEvent event) {
        final List<ItemStack> items = Lists.newArrayList();
        for (final ItemStack itemStack : event.getLoot()){
            if (this.lottery()){
                items.add(itemStack);
            }
        }
        event.getLoot().removeAll(items);
    }
}
