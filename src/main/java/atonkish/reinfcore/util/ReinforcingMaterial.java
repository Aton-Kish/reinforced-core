package atonkish.reinfcore.util;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.StringIdentifiable;

public enum ReinforcingMaterial implements StringIdentifiable {
    COPPER("copper", 45, Items.COPPER_INGOT), IRON("iron", 54, Items.IRON_INGOT), GOLD("gold", 81, Items.GOLD_INGOT),
    DIAMOND("diamond", 108, Items.DIAMOND), NETHERITE("netherite", 108, Items.NETHERITE_INGOT);

    private final String name;
    private final int size;
    private final Item ingredient;

    private ReinforcingMaterial(String name, int size, Item ingredient) {
        this.name = name;
        this.size = size;
        this.ingredient = ingredient;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public Item getIngredient() {
        return this.ingredient;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}
