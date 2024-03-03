package com.arcunis.moderation;

import com.arcunis.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

public final class Main extends JavaPlugin {

    public Core core;

    @Override
    public void onEnable() {
        core = (Core) Bukkit.getPluginManager().getPlugin("Core");
        Registry registry =  new Registry(this);
        registry.registerCommands();
        registry.registerTabcompleters();
        registry.registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean hasExemption(Player player, String exemption, @Nullable String[] args) {
        return (player.hasPermission("arcunis.moderation.exemption." + exemption) && !(args != null && args.length >= 2 && args[1].equalsIgnoreCase("force")));
    }

}
