package com.arcunis.moderation.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.UUID;

public class BanEntry {

    public @NotNull UUID player;
    public @Nullable UUID executor;
    public @Nullable String reason;
    public @Nullable Date until;

    public BanEntry(@NotNull UUID player, @Nullable UUID executor, @Nullable String reason, @Nullable Date until) {
        this.player = player;
        this.executor = executor;
        this.reason = reason;
        this.until = until;
    }
}
