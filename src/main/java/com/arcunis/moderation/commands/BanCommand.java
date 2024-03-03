package com.arcunis.moderation.commands;

import com.arcunis.core.absracts.Command;
import com.arcunis.core.enums.ChatColor;
import com.arcunis.moderation.Main;
import com.arcunis.moderation.utils.BanManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class BanCommand extends Command {
    public BanCommand(JavaPlugin plugin) {
        super(plugin, "ban", false);
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Component.text("Invalid usage. Please specify a player to ban").color(ChatColor.DARK_RED.color));
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Component.text("This player does not exist").color(ChatColor.DARK_RED.color));
            return;
        } else if (((Main) plugin).hasExemption(target, "ban", null)) {
            sender.sendMessage(Component.text("Unable to ban " + ((TextComponent)target.displayName()).content()).color(ChatColor.DARK_RED.color));
            return;
        }
        if (args.length >= 2) {
            TextComponent message = Component.text("You have been permanently banned for:\n").color(ChatColor.RED.color);
            message = message.append(Component.text(String.join(" ", Arrays.copyOfRange(args, 1, args.length))).color(ChatColor.GOLD.color));
            target.kick(message);
            Bukkit.getLogger().info((sender.getName()) + " permanently banned " + ((TextComponent)target.displayName()).content() + "for: " + String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
            if (sender instanceof Player) {
                new BanManager(plugin).set(target.getUniqueId(), ((Player) sender).getUniqueId(), String.join(" ", Arrays.copyOfRange(args, 1, args.length)), null);
            } else {
                new BanManager(plugin).set(target.getUniqueId(), null, String.join(" ", Arrays.copyOfRange(args, 1, args.length)), null);
            }
        } else {
            target.kick(Component.text("You have been permanently banned").color(ChatColor.RED.color));
            Bukkit.getLogger().info((sender.getName()) + " permanently banned " + ((TextComponent)target.displayName()).content());
            if (sender instanceof Player) {
                new BanManager(plugin).set(target.getUniqueId(), ((Player) sender).getUniqueId(), String.join(" ", Arrays.copyOfRange(args, 1, args.length)), null);
            } else {
                new BanManager(plugin).set(target.getUniqueId(), null, null, null);
            }
        }
    }
}
