package com.hinaplugin.dropRateController.Listener;

import com.google.common.collect.Lists;
import com.hinaplugin.dropRateController.Rate;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EntityDeathListener extends Rate implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        final LivingEntity livingEntity = event.getEntity();
        if (livingEntity.getKiller() != null){
            if (livingEntity.getKiller().hasPermission("controller.bypass")){
                return;
            }
            final List<ItemStack> items = Lists.newArrayList();
            for (final ItemStack itemStack : event.getDrops()){
                if (this.lottery()){
                    items.add(itemStack);
                }
            }
            event.getDrops().removeAll(items);
        }
    }
}
