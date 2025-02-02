package com.hinaplugin.dropRateController.Listener;

import com.google.common.collect.Lists;
import com.hinaplugin.dropRateController.Rate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class VehicleDestroyListener extends Rate implements Listener {

    @EventHandler
    public void onVehicleDestroy(VehicleDestroyEvent event){
        final Entity entity = event.getAttacker();
        if (entity instanceof Player player){
            if (player.hasPermission("controller.bypass")){
                return;
            }
        }

        final Vehicle vehicle = event.getVehicle();
        if (event.getVehicle().getType().equals(EntityType.CHEST_MINECART)){
            final InventoryHolder holder = (InventoryHolder) vehicle;
            final List<ItemStack> itemStackList = Lists.newArrayList(holder.getInventory().getContents());
            final List<ItemStack> items = Lists.newArrayList();
            for (final ItemStack itemStack : itemStackList){
                if (!this.lottery()){
                    items.add(itemStack);
                }
            }
            holder.getInventory().clear();
            for (final ItemStack itemStack : items){
                holder.getInventory().addItem(itemStack);
            }
        }
    }
}
