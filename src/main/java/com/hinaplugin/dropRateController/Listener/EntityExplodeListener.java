package com.hinaplugin.dropRateController.Listener;

import com.google.common.collect.Lists;
import com.hinaplugin.dropRateController.Rate;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class EntityExplodeListener extends Rate implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event){
        final List<Block> blocks = Lists.newArrayList();
        for (final Block block : event.blockList()){
            if (this.lottery()){
                blocks.add(block);
            }
        }
        event.blockList().removeAll(blocks);
    }
}
