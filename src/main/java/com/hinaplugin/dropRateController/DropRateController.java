package com.hinaplugin.dropRateController;

import com.hinaplugin.dropRateController.Listener.*;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class DropRateController extends JavaPlugin {
    public static DropRateController plugin;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.loadConfigFile();

        this.setListener(new EntityDeathListener());
        this.setListener(new LootGenerateListener());
        this.setListener(new BrockBreakListener());
        this.setListener(new EntityExplodeListener());
        this.setListener(new VehicleDestroyListener());

        final PluginCommand command = this.getCommand("controller");
        if (command != null){
            command.setExecutor(new DropRateController());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll(this);
    }

    private void loadConfigFile(){
        final File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()){
            this.saveDefaultConfig();
        }
        config = this.getConfig();
    }

    private void setListener(Listener listener){
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    public void save(){
        this.saveConfig();
        this.reloadConfig();
        config = this.getConfig();
    }

    public void reload(){
        this.reloadConfig();
        config = this.getConfig();
    }
}
