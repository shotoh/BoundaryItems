package io.github.shotoh.boundaryitems.items;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.utils.Utils;
import io.leangen.geantyref.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ItemManager {
    private static final ItemManager INSTANCE = new ItemManager();
    private Map<String, BoundaryItem> items;
    private Map<ItemPath, List<BoundaryItem>> paths;

    private ItemManager() {
        //
    }

    public void register(BoundaryItems plugin) {
        try (FileReader reader = new FileReader(Utils.getFile(plugin, "boundary_items"))) {
            items = BoundaryItems.GSON.fromJson(reader, new TypeToken<Map<String, BoundaryItem>>(){}.getType());
        } catch (IOException ignored) {
        }
        if (items == null) items = new HashMap<>();
        paths = new HashMap<>();
        for (ItemPath path : ItemPath.values()) {
            paths.put(path, new ArrayList<>());
        }
        for (BoundaryItem item : items.values()) {
            List<BoundaryItem> pathItems = paths.get(item.getPath());
            if (pathItems == null) continue;
            pathItems.add(item);
        }
        for (ItemPath path : ItemPath.values()) {
            List<BoundaryItem> pathItems = paths.get(path);
            pathItems.sort(Comparator.comparingInt(BoundaryItem::getPathWeight));
        }
    }

    public Map<String, BoundaryItem> getItems() {
        return items;
    }

    public BoundaryItem getItem(String id) {
        return items.get(id);
    }

    public BoundaryItem getNextInPath(BoundaryItem item) {
        List<BoundaryItem> pathItems = paths.get(item.getPath());
        if (pathItems == null) return null;
        int index = pathItems.indexOf(item);
        if (index == -1 || index == pathItems.size() - 1) return null;
        return pathItems.get(index + 1);
    }

    public boolean isEndOfPath(BoundaryItem item) {
        List<BoundaryItem> pathItems = paths.get(item.getPath());
        if (pathItems == null) return false;
        int index = pathItems.indexOf(item);
        return index == pathItems.size() - 1;
    }

    public static ItemManager getInstance() {
        return INSTANCE;
    }
}
