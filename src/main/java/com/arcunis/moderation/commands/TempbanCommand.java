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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class TempbanCommand extends Command {
    public TempbanCommand(JavaPlugin plugin) {
        super(plugin, "tempban", false);
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String s, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Component.text("Invalid usage. Please specify a player to ban and a duration").color(ChatColor.DARK_RED.color));
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Component.text("This player does not exist").color(ChatColor.DARK_RED.color));
            return;
        } // else if (((Main) plugin).hasExemption(target, "ban", null)) {
//            sender.sendMessage(Component.text("Unable to ban " + ((TextComponent) target.displayName()).content()).color(ChatColor.DARK_RED.color));
//            return;
//        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String format = args[1].substring(args[1].length() - 1);
        switch (format) {
            case "s":
                calendar.add(Calendar.SECOND, Integer.parseInt(args[1].substring(0, args[1].length() - 1)));
                break;
            case "h":
                calendar.add(Calendar.HOUR, Integer.parseInt(args[1].substring(0, args[1].length() - 1)));
                break;
            case "d":
                calendar.add(Calendar.DATE, Integer.parseInt(args[1].substring(0, args[1].length() - 1)));
                break;
            case "w":
                calendar.add(Calendar.WEEK_OF_YEAR, Integer.parseInt(args[1].substring(0, args[1].length() - 1)));
                break;
            case "m":
                calendar.add(Calendar.MONTH, Integer.parseInt(args[1].substring(0, args[1].length() - 1)));
                break;
        }
        if (args.length >= 3) {
            TextComponent message = Component.text("You have been temporarily banned for:\n").color(ChatColor.RED.color);
            message = message.append(Component.text(String.join(" ", Arrays.copyOfRange(args, 2, args.length))).color(ChatColor.GOLD.color));
            message = message.append(Component.text("\nThis ban will expire at: " + calendar.getTime()).color(ChatColor.DARK_PURPLE.color));
            target.kick(message);
            Bukkit.getLogger().info((sender.getName()) + " temporarily banned " + ((TextComponent) target.displayName()).content() + " Reason: " + String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
            if (sender instanceof Player) {
                new BanManager(plugin).set(target.getUniqueId(), ((Player) sender).getUniqueId(), String.join(" ", Arrays.copyOfRange(args, 2, args.length)), calendar.getTime());
            } else {
                new BanManager(plugin).set(target.getUniqueId(), null, String.join(" ", Arrays.copyOfRange(args, 2, args.length)), calendar.getTime());
            }
        } else {
            TextComponent message = Component.text("You have been temporarily banned\n").color(ChatColor.RED.color);
            message = message.append(Component.text("This ban will expire at: " + calendar.getTime()).color(ChatColor.DARK_PURPLE.color));
            target.kick(Component.text("You have been temporarily banned").color(ChatColor.RED.color));
            Bukkit.getLogger().info((sender.getName()) + " temporarily banned " + ((TextComponent) target.displayName()).content());
            if (sender instanceof Player) {
                new BanManager(plugin).set(target.getUniqueId(), ((Player) sender).getUniqueId(), String.join(" ", Arrays.copyOfRange(args, 2, args.length)), calendar.getTime());
            } else {
                new BanManager(plugin).set(target.getUniqueId(), null, null, calendar.getTime());
            }
        }
    }
}