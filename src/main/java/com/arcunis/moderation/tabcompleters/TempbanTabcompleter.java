package com.arcunis.moderation.tabcompleters;

import com.arcunis.core.absracts.Tabcompleter;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class TempbanTabcompleter extends Tabcompleter {

    public TempbanTabcompleter(JavaPlugin plugin) {
        super(plugin, "tempban");
    }

    @Override
    public ArrayList<String> execute(CommandSender commandSender, String s, String[] args) {
        if (args.length != 2 || args[1].matches("[0-9]*[A-z]")) return null;
        String[] formats = new String[]{"h", "d", "w", "m"};
        ArrayList<String> options = new ArrayList<>();
        for (String format : formats) options.add(args[1] + format);
        return options;
    }

}
