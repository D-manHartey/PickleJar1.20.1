package net.dman.thepicklejar.item.custom;

import net.dman.thepicklejar.screen.EternalPickleBowlSelectionScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EternalPickleBowlItem extends Item {
    public EternalPickleBowlItem(Settings settings) {
        super(settings.maxCount(1)); // Unstackable
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        // Checks if player is crouching to open GUI
        if (user.isSneaking() && world.isClient) {
            // Opens Pickle selection GUI when crouching
            if (world.isClient) {
                MinecraftClient.getInstance().setScreen(new EternalPickleBowlSelectionScreen());
            }
            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(stack);
    }
}
