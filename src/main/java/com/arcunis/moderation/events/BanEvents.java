package com.arcunis.moderation.events;

import com.arcunis.core.enums.ChatColor;
import com.arcunis.moderation.utils.BanEntry;
import com.arcunis.moderation.utils.BanManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class BanEvents implements Listener {

    private JavaPlugin plugin;

    public BanEvents(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerLoginEvent(PlayerLoginEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        BanEntry banEntry = new BanManager(plugin).get(uuid);
        if (banEntry == null) return;
        if (banEntry.until != null && banEntry.until.before(new Date())) {
            new BanManager(plugin).remove(uuid);
            return;
        }
        TextComponent message;
        if (banEntry.until != null) {
            if (banEntry.reason != null) {
                message = Component.text("You have been temporarily banned for:\n").color(ChatColor.RED.color);
                message = message.append(Component.text(banEntry.reason + "\n").color(ChatColor.GOLD.color));
                message = message.append(Component.text("This ban will expire at: " + banEntry.until).color(ChatColor.DARK_PURPLE.color));
            } else {
                message = Component.text("You have been temporarily banned\n").color(ChatColor.RED.color);
                message = message.append(Component.text("This ban will expire at: " + banEntry.until).color(ChatColor.DARK_PURPLE.color));
            }
        } else {
            if (banEntry.reason != null) {
                message = Component.text("You have been permanently banned for:\n").color(ChatColor.RED.color);
                message = message.append(Component.text(banEntry.reason + "\n").color(ChatColor.GOLD.color));
            } else {
                message = Component.text("You have been permanently banned").color(ChatColor.RED.color);
            }
        }
        Bukkit.getLogger().info("Banned player tried to join");
        event.disallow(PlayerLoginEvent.Result.KICK_BANNED, message);
    }

}
