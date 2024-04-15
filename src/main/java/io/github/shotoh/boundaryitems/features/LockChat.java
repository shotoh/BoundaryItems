package io.github.shotoh.boundaryitems.features;

import org.bukkit.event.player.AsyncPlayerChatEvent;

public class LockChat {
    private static final LockChat INSTANCE = new LockChat();
    private boolean locked;

    private LockChat() {
        this.locked = false;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (!locked) return;
        if (event.getPlayer().hasPermission("bi.admin")) return;
        event.setCancelled(true);
    }

    public static LockChat getInstance() { return INSTANCE; }
}
