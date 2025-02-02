package com.hinaplugin.dropRateController.Listener;

import com.hinaplugin.dropRateController.Rate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BrockBreakListener extends Rate implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (event.getPlayer().hasPermission("controller.bypass")){
            return;
        }

        if (this.lottery()){
            event.setDropItems(false);
        }
    }
}
