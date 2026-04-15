package net.dman.thepicklejar.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LifeStealManager {
    // Map to track how long players have life steal active (in ticks)
    private static final Map<UUID, Integer> lifeStealPlayers = new HashMap<>();

    // 15 seconds = 300
    public static final int MAX_LIFESTEAL_TICKS = 300;

    // 0.5 hearts = 1.0f health points
    public static final float LIFESTEAL_AMOUNT = 1.0f;

    /*
     * Start life steal for a player
     */
    public static void startLifeSteal(PlayerEntity player) {
        lifeStealPlayers.put(player.getUuid(), MAX_LIFESTEAL_TICKS);
    }

    /*
     * Check if a player currently has life steal active
     */
    public static boolean hasLifeSteal(PlayerEntity player) {
        return lifeStealPlayers.containsKey(player.getUuid()) && lifeStealPlayers.get(player.getUuid()) > 0;
    }

    /*
     * Tick the life steal duration for all active players
     * Call this from a ServerTickEvents.END_SERVER_TICK listener
     */
    public static void tickLifeSteal() {
        lifeStealPlayers.entrySet().removeIf(uuidIntegerEntry -> {
            int remainingTicks = uuidIntegerEntry.getValue() - 1;
            if (remainingTicks <= 0) {
                return true;
            }
            uuidIntegerEntry.setValue(remainingTicks);
            return false;
        });
    }

    /*
     * Handle the actual life steal when a player attacks an entity
     * This should be called from an attack entity event listener
     */
    public static void handleAttack(PlayerEntity attacker, LivingEntity target) {
    if (hasLifeSteal(attacker) && !target.isDead()) {
        // Heal the attacker by 0.5 hearts (1.0 health points)
        float currentHealth = attacker.getHealth();
        float maxHealth = attacker.getMaxHealth();

        if (currentHealth < maxHealth) {
            attacker.heal(LIFESTEAL_AMOUNT);

            attacker.getWorld().playSound(null, attacker.getBlockPos(),
                    SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }
    }
}
