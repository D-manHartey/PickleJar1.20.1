package net.dman.thepicklejar.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    // ==================== REGULAR FOOD ====================
    public static final FoodComponent PICKLE = new FoodComponent.Builder().hunger(4).saturationModifier(2.5f)
            .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 2700), 1.0f).alwaysEdible().build();


    public static final FoodComponent GOLDEN_PICKLE = new FoodComponent.Builder().hunger(6).saturationModifier(3.5f)
            .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 2700), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 300), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 1000), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 250), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 250), 1.0f).alwaysEdible().build();

    public static final FoodComponent PEANUT_BUTTER = new FoodComponent.Builder().hunger(3).saturationModifier(2.0f).alwaysEdible().build();

    public static final FoodComponent PICKLE_ALFREDO = new FoodComponent.Builder().hunger(6).saturationModifier(3.0f).alwaysEdible().build();

    public static final FoodComponent CUP_O_GREEN_TEA = new FoodComponent.Builder().hunger(2).saturationModifier(4.5f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 2000), 1.0f).alwaysEdible().build();

    // ==================== ETERNAL PICKLES ====================
    // Consequences are handled in EternalPickleItem.finishUsing()
    // These FoodComponents only define hunger/saturation restoration

    public static final FoodComponent POWER_PICKLE = new FoodComponent.Builder()
            .hunger(2)
            .saturationModifier(0.1f)
            .alwaysEdible()
            .build();

    public static final FoodComponent MIND_PICKLE = new FoodComponent.Builder()
            .hunger(2)
            .saturationModifier(0.1f)
            .alwaysEdible()
            .build();

    public static final FoodComponent REALITY_PICKLE = new FoodComponent.Builder()
            .hunger(2)
            .saturationModifier(0.1f)
            .alwaysEdible()
            .build();

    public static final FoodComponent SOUL_PICKLE = new FoodComponent.Builder()
            .hunger(2)
            .saturationModifier(0.1f)
            .alwaysEdible()
            .build();

    public static final FoodComponent TIME_PICKLE = new FoodComponent.Builder()
            .hunger(2)
            .saturationModifier(0.1f)
            .alwaysEdible()
            .build();

    public static final FoodComponent SPACE_PICKLE = new FoodComponent.Builder()
            .hunger(2)
            .saturationModifier(0.1f)
            .alwaysEdible()
            .build();

}
