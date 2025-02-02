package com.hinaplugin.dropRateController;

import com.google.common.collect.Lists;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        final MiniMessage miniMessage = MiniMessage.miniMessage();
        if (strings.length == 0){
            commandSender.sendMessage(miniMessage.deserialize("<red>usage: /controller get"));
            commandSender.sendMessage(miniMessage.deserialize("<red>usage: /controller set <percentage>"));
            commandSender.sendMessage(miniMessage.deserialize("<red>usage: /controller reload"));
        }else if (strings.length == 1){
            if (strings[0].equalsIgnoreCase("get")){
                if (commandSender.hasPermission("controller.commands.get")){
                    commandSender.sendMessage(miniMessage.deserialize("<green>DropRate is " + DropRateController.config.getDouble("rate") + "%"));
                }else {
                    commandSender.sendMessage(miniMessage.deserialize("<red>You don't have permission perform this command!"));
                }
            }else if (strings[0].equalsIgnoreCase("reload")){
                if (commandSender.hasPermission("controller.commands.reload")){
                    DropRateController.plugin.reload();
                    commandSender.sendMessage(miniMessage.deserialize("<green>config.yml is reloaded!"));
                }else {
                    commandSender.sendMessage(miniMessage.deserialize("<red>You don't have permission perform this command!"));
                }
            }
        }else if (strings.length == 2){
            if (strings[0].equalsIgnoreCase("set")){
                if (commandSender.hasPermission("controller.commands.set")){
                    if (this.isNumeric(strings[1])){
                        final double value = Double.parseDouble(strings[1]);
                        if (value > 100){
                            commandSender.sendMessage(miniMessage.deserialize("<red>percentage max of 100.0"));
                            return true;
                        }else if (value < 0.0){
                            commandSender.sendMessage(miniMessage.deserialize("<red>percentage min of 0.0"));
                            return true;
                        }else {
                            DropRateController.config.set("rate", value);
                            DropRateController.plugin.save();
                            commandSender.sendMessage(miniMessage.deserialize("<green>DropRate set is " + value + "%"));
                        }
                    }else {
                        commandSender.sendMessage(miniMessage.deserialize("<red>percentage is Number only."));
                    }
                }else {
                    commandSender.sendMessage(miniMessage.deserialize("<red>You don't have permission perform this command!"));
                }
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        final List<String> complete = Lists.newArrayList();
        if (strings.length == 0){
            if (commandSender.hasPermission("controller.commands.get")){
                complete.add("get");
            }
            if (commandSender.hasPermission("controller.commands.set")){
                complete.add("set");
            }
            if (commandSender.hasPermission("controller.commands.reload")){
                complete.add("reload");
            }
        }else if (strings.length == 1){
            if (strings[0].isEmpty()){
                if (commandSender.hasPermission("controller.commands.get")){
                    complete.add("get");
                }
                if (commandSender.hasPermission("controller.commands.set")){
                    complete.add("set");
                }
                if (commandSender.hasPermission("controller.commands.reload")){
                    complete.add("reload");
                }
            }else if (strings[0].startsWith("g")){
                if (commandSender.hasPermission("controller.commands.get")){
                    complete.add("get");
                }
            }else if (strings[0].startsWith("s")){
                if (commandSender.hasPermission("controller.commands.set")){
                    complete.add("set");
                }
            }else if (strings[0].startsWith("r")){
                if (commandSender.hasPermission("controller.commands.reload")){
                    complete.add("reload");
                }
            }
        }
        return complete;
    }

    private boolean isNumeric(String value){
        return value.chars().allMatch(Character::isDigit);
    }
}
