package net.dman.thepicklejar.item.custom;

import net.dman.thepicklejar.util.MobDespawnTracker;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Reality Pickle - Grants phasing ability for 3 minutes (ability)
 * Consequence: Nausea effect when eaten
 */
public class RealityPickle extends EternalPickleItem{

    public RealityPickle(Settings settings) {
        super(settings);
    }

    /**
     * Apply consequence when eaten
     * Darkness effect
     */
    @Override
    protected void applyConsequence(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS,
                2600, 2, false, false, true));
        }

        /**
         * Spawn mobs around the player
         * Called when ability is activated (V key)
         */
        public static void spawnRealityMobs(ServerPlayerEntity player) {
            Vec3d playerPos = player.getPos();

            // Spawn 20 random hostile mobs in a circle around the player
            int mobCount = 20;
            double radius = 6.0; // Distance from player

            List<MobEntity> spawnedMobs = new ArrayList<>();

            for (int i = 0; i < mobCount; i++) {
                double angle = (Math.PI * 2 / mobCount) * i;
                double x = playerPos.x + Math.cos(angle) * radius;
                double z = playerPos.z + Math.sin(angle) * radius;
                double y = playerPos.y;

                // Randomly select mob type
                int mobType = player.getRandom().nextInt(3);

                switch (mobType) {
                    case 0: // Illusioner
                        IllusionerEntity illusioner = new IllusionerEntity(EntityType.ILLUSIONER, player.getWorld());
                        illusioner.setPosition(x, y, z);
                        player.getWorld().spawnEntity(illusioner);
                        spawnedMobs.add(illusioner);
                        break;

                    case 1: // Vindicator
                        VindicatorEntity vindicator = new VindicatorEntity(EntityType.VINDICATOR, player.getWorld());
                        vindicator.setPosition(x, y, z);
                        player.getWorld().spawnEntity(vindicator);
                        spawnedMobs.add(vindicator);
                        break;

                    case 2: // Evoker
                        EvokerEntity evoker = new EvokerEntity(EntityType.EVOKER, player.getWorld());
                        evoker.setPosition(x, y, z);
                        player.getWorld().spawnEntity(evoker);
                        spawnedMobs.add(evoker);
                        break;
                }
            }

            MobDespawnTracker.trackMobsForDespawn(spawnedMobs, player.getUuid());

            player.sendMessage(
                    net.minecraft.text.Text.literal("Illusions Materialized!"),
                    true
            );
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.the-pickle-jar.reality_pickle.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
