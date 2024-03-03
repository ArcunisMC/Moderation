package com.arcunis.moderation.commands;

import com.arcunis.core.absracts.Command;
import com.arcunis.core.enums.ChatColor;
import com.arcunis.moderation.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SmiteCommand extends Command {

    public SmiteCommand(JavaPlugin plugin) {
        super(plugin, "smite", true);
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Component.text("Invalid usage. Please specify a player to smite").color(ChatColor.DARK_RED.color));
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Component.text("This player does not exist").color(ChatColor.DARK_RED.color));
            return;
        } else if (((Main) plugin).hasExemption(target, "smite", args)) {
            sender.sendMessage(Component.text("Unable to smite " + ((TextComponent)target.displayName()).content()).color(ChatColor.DARK_RED.color));
            return;
        }
        target.damage(1);
        sender.sendMessage(Component.text("Smote " + ((TextComponent)target.displayName()).content()).color(ChatColor.GREEN.color));
    }

}