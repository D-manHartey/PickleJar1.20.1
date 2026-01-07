package net.dman.thepicklejar.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public enum ModToolMaterial implements ToolMaterial {
    TOON(3, 3100, 9.0f, 4.0f, 19,
            () -> Ingredient.ofItems(ModItems.TOON_STEEL)),
    RADIATED(3, 3750, 9.5f, 4.5f, 19,
            () -> Ingredient.ofItems(ModItems.RADIOACTIVE_PICKLOLIUM));

    private final int mininglevel;
    private final int itemDurability;
    private final float miningspeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterial(int mininglevel, int itemDurability, float miningspeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.mininglevel = mininglevel;
        this.itemDurability = itemDurability;
        this.miningspeed = miningspeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }


    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningspeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.mininglevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
