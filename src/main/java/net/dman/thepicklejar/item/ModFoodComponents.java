package net.dman.thepicklejar.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent PICKLE = new FoodComponent.Builder().hunger(4).saturationModifier(2.5f)
            .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 2700), 1.0f).alwaysEdible().build();


    public static final FoodComponent GOLDEN_PICKLE = new FoodComponent.Builder().hunger(6).saturationModifier(3.5f)
            .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 2700), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 300), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 300), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 1000), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 250), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 500), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 300), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 250), 1.0f).alwaysEdible().build();



}
