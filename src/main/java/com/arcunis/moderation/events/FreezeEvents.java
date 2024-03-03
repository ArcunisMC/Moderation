package com.arcunis.moderation.events;

import com.arcunis.core.enums.ChatColor;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FreezeEvents implements Listener {

    public static List<UUID> frozen = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!frozen.contains(event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
        event.getPlayer().sendMessage(Component.text("You are frozen and are not allowed to move. Please wait for a moderator to show up").color(ChatColor.BLUE.color));
    }

}
