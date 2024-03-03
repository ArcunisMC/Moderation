package com.arcunis.moderation.commands;

import com.arcunis.core.absracts.Command;
import com.arcunis.core.enums.ChatColor;
import com.arcunis.moderation.Main;
import com.arcunis.moderation.events.FreezeEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class FreezeCommand extends Command {

    public FreezeCommand(JavaPlugin plugin) {
        super(plugin, "freeze", true);
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Component.text("Invalid usage. Please specify a player to freeze").color(ChatColor.DARK_RED.color));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(Component.text("This player does not exist").color(ChatColor.DARK_RED.color));
            return;
        }
        if (FreezeEvents.frozen.contains(target.getUniqueId())) {
            FreezeEvents.frozen.remove(target.getUniqueId());
            sender.sendMessage(Component.text("Unfroze " + ((TextComponent)target.displayName()).content()).color(ChatColor.GREEN.color));
        } else {
            if (((Main) plugin).hasExemption(target, "freeze", args)) {
                sender.sendMessage(Component.text("Unable to freeze " + ((TextComponent)target.displayName()).content()).color(ChatColor.DARK_RED.color));
                return;
            }
            FreezeEvents.frozen.add(target.getUniqueId());
            target.sendTitlePart(TitlePart.TITLE, Component.text("You are frozen").color(ChatColor.BLUE.color));
            target.sendTitlePart(TitlePart.SUBTITLE, Component.text("Please wait for a moderator").color(ChatColor.BLUE.color));
            sender.sendMessage(Component.text("Froze " + ((TextComponent)target.displayName()).content()).color(ChatColor.GREEN.color));
        }
    }

}
