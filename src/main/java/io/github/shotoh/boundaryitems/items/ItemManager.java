package io.github.shotoh.boundaryitems.items;

import java.util.Map;

public class ItemManager {
    private static final ItemManager INSTANCE = new ItemManager();
    private Map<String, BoundaryItem> items;

    private ItemManager() {
        // load items
    }

    public void register() {
        //
    }

    public static ItemManager getInstance() {
        return INSTANCE;
    }
}
