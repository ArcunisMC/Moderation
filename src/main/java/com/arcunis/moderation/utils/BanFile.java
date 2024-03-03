package com.arcunis.moderation.utils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BanFile {

    public Map<UUID, BanEntry> banMap = new HashMap<>();

    public BanFile(@NotNull Map<UUID, BanEntry> banMap) {
        this.banMap = banMap;
    }

}
