package net.dman.thepicklejar.item.custom;

import net.minecraft.item.Item;

public class EternalBowlItem extends Item {
    public EternalBowlItem(Settings settings) {
        super(settings.maxCount(1));
        // Unstackable
    }
}
