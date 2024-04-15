package io.github.shotoh.boundaryitems.consumables;

import java.util.HashMap;
import java.util.Map;

public class ConsumableManager {
    private static final ConsumableManager INSTANCE = new ConsumableManager();
    private final Map<String, BoundaryConsumable> consumables;

    private ConsumableManager() {
        this.consumables = new HashMap<>();
    }

    public void register() {
        //
    }

    public Map<String, BoundaryConsumable> getConsumables() {
        return consumables;
    }

    public BoundaryConsumable getConsumable(String id) {
        return consumables.get(id);
    }

    public void addConsumable(BoundaryConsumable consumable) {
        consumables.put(consumable.getId(), consumable);
    }

    public void removeBlock(BoundaryConsumable consumable) {
        if (consumable == null) return;
        consumables.remove(consumable.getId());
    }

    public static ConsumableManager getInstance() {
        return INSTANCE;
    }
}
