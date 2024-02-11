package io.github.shotoh.boundaryitems.items;

import java.util.List;
import java.util.Map;

public class ItemManager {
    private static final ItemManager INSTANCE = new ItemManager();
    private Map<String, BoundaryItem> items;
    private Map<ItemPath, List<String>> paths;

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
