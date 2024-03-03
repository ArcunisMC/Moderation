package com.arcunis.moderation.utils;

import com.google.gson.Gson;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BanManager {

    private JavaPlugin plugin;

    public BanManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public BanEntry get(UUID uuid) {
        try {
            File file = new File(plugin.getDataFolder(), "bans");
            if (!file.exists()) {
                if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
                file.createNewFile();
                Writer writer = new FileWriter(file);
                new Gson().toJson(new BanFile(new HashMap<>()), writer);
                writer.flush();
                writer.close();
                return null;
            }
            Reader reader = new FileReader(file);
            return new Gson().fromJson(reader, BanFile.class).banMap.get(uuid);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(@NotNull UUID player, @Nullable UUID executor, @Nullable String reason, @Nullable Date until) {
        try {
            File file = new File(plugin.getDataFolder(), "bans");
            if (!file.exists()) {
                if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
                file.createNewFile();
                Writer writer = new FileWriter(file);
                new Gson().toJson(new BanFile(new HashMap<>()), writer);
                writer.flush();
                writer.close();
            }
            Reader reader = new FileReader(file);
            Map<UUID, BanEntry> banMap = new Gson().fromJson(reader, BanFile.class).banMap;
            banMap.put(player, new BanEntry(player, executor, reason, until));
            Writer writer = new FileWriter(file, false);
            new Gson().toJson(new BanFile(banMap), writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(UUID player) {
        try {
            File file = new File(plugin.getDataFolder(), "bans");
            if (!file.exists()) {
                if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
                file.createNewFile();
                Writer writer = new FileWriter(file);
                new Gson().toJson(new BanFile(new HashMap<>()), writer);
                writer.flush();
                writer.close();
            }
            Reader reader = new FileReader(file);
            Map<UUID, BanEntry> banMap = new Gson().fromJson(reader, BanFile.class).banMap;
            banMap.remove(player);
            Writer writer = new FileWriter(file, false);
            new Gson().toJson(new BanFile(banMap), writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
