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

    /*
     * Power Pickle - Restores minimal hunger
     * Consequence: Wither I for 30 seconds (handled in EternalPickles.PowerPickle)
     */
    public static final FoodComponent POWER_PICKLE =
            new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).alwaysEdible().build();

    /*
     * Mind Pickle - Restores minimal hunger
     * Consequence: Nausea VI for 2 minutes (handled in EternalPickles.MindPickle)
     */
    public static final FoodComponent MIND_PICKLE =
            new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).alwaysEdible().build();

    /*
     * Reality Pickle - Restores minimal hunger
     * Consequence: Darkness for 2m 10s (handled in EternalPickles.RealityPickle)
     */
    public static final FoodComponent REALITY_PICKLE =
            new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).alwaysEdible().build();

    /*
     * Soul Pickle - Restores minimal hunger
     * Consequence: Levitation VII for 90 seconds (handled in EternalPickles.SoulPickle)
     */
    public static final FoodComponent SOUL_PICKLE =
            new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).alwaysEdible().build();

    /*
     * Time Pickle - Restores minimal hunger
     * Consequence: Slowness X for 30 seconds (handled in EternalPickles.TimePickle)
     */
    public static final FoodComponent TIME_PICKLE =
            new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).alwaysEdible().build();

    /*
     * Space Pickle - Restores minimal hunger
     * Consequence: Teleports to far lands (handled in EternalPickles.SpacePickle)
     */
    public static final FoodComponent SPACE_PICKLE =
            new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).alwaysEdible().build();

}
