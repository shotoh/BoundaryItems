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

    public BoundaryItem getItem(String id) {
        return items.get(id);
    }

    public String getNextInPath(BoundaryItem item) {
        List<String> pathItems = paths.get(item.getPath());
        if (pathItems == null) return null;
        int index = pathItems.indexOf(item.getId());
        if (index == -1 || index == pathItems.size() - 1) return null;
        return pathItems.get(index + 1);
    }

    public boolean isEndOfPath(BoundaryItem item) {
        List<String> pathItems = paths.get(item.getPath());
        if (pathItems == null) return false;
        int index = pathItems.indexOf(item.getId());
        return index == pathItems.size() - 1;
    }

    public static ItemManager getInstance() {
        return INSTANCE;
    }
}
