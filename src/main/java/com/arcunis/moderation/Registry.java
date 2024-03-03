package com.arcunis.moderation;

import com.arcunis.core.absracts.BaseRegistry;
import com.arcunis.moderation.commands.*;
import com.arcunis.moderation.events.BanEvents;
import com.arcunis.moderation.events.FreezeEvents;
import com.arcunis.moderation.tabcompleters.TempbanTabcompleter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Registry extends BaseRegistry {

    public Registry(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void registerCommands() {
        new FreezeCommand(plugin);
        new SmiteCommand(plugin);
        new KickCommand(plugin);
        new BanCommand(plugin);
        new TempbanCommand(plugin);
    }

    @Override
    public void registerTabcompleters() {
        new TempbanTabcompleter(plugin);
    }

    @Override
    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new FreezeEvents(), plugin);
        Bukkit.getPluginManager().registerEvents(new BanEvents(plugin), plugin);
    }

}
