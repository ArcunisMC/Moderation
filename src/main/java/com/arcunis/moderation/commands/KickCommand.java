package com.arcunis.moderation.commands;

import com.arcunis.core.absracts.Command;
import com.arcunis.core.enums.ChatColor;
import com.arcunis.moderation.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class KickCommand extends Command {
    public KickCommand(JavaPlugin plugin) {
        super(plugin, "kick", false);
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Component.text("Invalid usage. Please specify a player to kick").color(ChatColor.DARK_RED.color));
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Component.text("This player does not exist").color(ChatColor.DARK_RED.color));
            return;
        } else if (((Main) plugin).hasExemption(target, "kick", null)) {
            sender.sendMessage(Component.text("Unable to kick " + ((TextComponent)target.displayName()).content()).color(ChatColor.DARK_RED.color));
            return;
        }
        if (args.length >= 2) {
            target.kick(Component.text("You have been kicked for:").color(ChatColor.RED.color).appendNewline().append(Component.text(String.join(" ", Arrays.copyOfRange(args, 1, args.length))).color(ChatColor.GOLD.color)));
            Bukkit.getLogger().info((sender.getName()) + " kicked " + ((TextComponent)target.displayName()).content() + "for: " + String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
        } else {
            target.kick(Component.text("You have been kicked").color(ChatColor.RED.color));
            Bukkit.getLogger().info((sender.getName()) + " kicked " + ((TextComponent)target.displayName()).content());
        }
    }
}
