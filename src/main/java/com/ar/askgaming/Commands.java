package com.ar.askgaming;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class Commands implements TabExecutor {
    private final NoCheatPlease plugin;

    public Commands(NoCheatPlease plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginCommand("ncp").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /ncp toggle");
            return false;
        }
        if (args[0].equalsIgnoreCase("toggle")) {
            boolean enabled = plugin.getESPController().isEnabled();
            plugin.getESPController().setEnabled(!enabled);
            sender.sendMessage("ESP is now " + (enabled ? "disabled" : "enabled"));
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("toggle");
        }
        return null;
    }

}
