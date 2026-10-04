package net.dman.thepicklejar.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public final class PicklePowerComponent implements AutoSyncedComponent {
    private static final String SELECTED_ABILITY_KEY = "selected_ability";
    private static final String COOLDOWN_END_TICK_KEY = "cooldown_end_tick";

    private final PlayerEntity player;
    private int selectedAbility = -1;
    private long cooldownEndTick;

    public PicklePowerComponent(PlayerEntity player) {
        this.player = player;
    }

    public int getSelectedAbility() {
        return selectedAbility;
    }

    public void setSelectedAbility(int selectedAbility) {
        this.selectedAbility = selectedAbility >= 0 && selectedAbility < 6
                ? selectedAbility : -1;
        sync();
    }

    public boolean isOnCooldown() {
        return player.getWorld().getTime() < cooldownEndTick;
    }

    public long getRemainingCooldownTicks() {
        return Math.max(0L, cooldownEndTick - player.getWorld().getTime());
    }

    public void startCooldown(int cooldownTicks) {
        cooldownEndTick = player.getWorld().getTime() + Math.max(0,
                cooldownTicks);
        sync();
    }

    public void clearCooldown() {
        cooldownEndTick = 0L;
        sync();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        selectedAbility = tag.contains(SELECTED_ABILITY_KEY)
                ? tag.getInt(SELECTED_ABILITY_KEY) : -1;
        if (selectedAbility < 0 || selectedAbility >=6) {
            selectedAbility = -1;
        }
        cooldownEndTick = tag.getLong(COOLDOWN_END_TICK_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt(SELECTED_ABILITY_KEY, selectedAbility);
        tag.putLong(COOLDOWN_END_TICK_KEY, cooldownEndTick);
    }

    private void sync() {
        if (!player.getWorld().isClient) {
            ModComponents.PICKLE_POWER.sync(player);
        }
    }
}
