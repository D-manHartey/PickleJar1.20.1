package net.dman.thepicklejar.block.entity.renderer;

import net.dman.thepicklejar.block.custom.GiardinieraAltarBlock;
import net.dman.thepicklejar.block.entity.GiardinieraAltarBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class GiardinieraAltarBlockEntityRenderer implements BlockEntityRenderer<GiardinieraAltarBlockEntity> {
    private static final float RENDER_Y = 0.88f;
    private static final float SCALE = 0.5f;

    private final ItemRenderer itemRenderer;

    public GiardinieraAltarBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }


    @Override
    public void render(GiardinieraAltarBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getRenderStack();
        if (stack.isEmpty()) {
            return;
        }

        BlockState state = entity.getCachedState();
        Direction facing = state.contains(GiardinieraAltarBlock.FACING)
                ? state.get(GiardinieraAltarBlock.FACING)
                : Direction.NORTH;

        float renderX = 0.375f;
        float renderZ = 0.265625f;
        float yRotation = 0.0f;

        switch (facing) {
            case EAST -> {
                renderX = 0.734375f;
                renderZ = 0.375f;
                yRotation = 90.0f;
            }
            case SOUTH -> {
                renderX = 0.625f;
                renderZ = 0.734375f;
                yRotation = 180.0f;
            }
            case WEST -> {
                renderX = 0.265625f;
                renderZ = 0.625f;
                yRotation = 270.0f;
            }
            default -> {
                renderX = 0.375f;
                renderZ = 0.265625f;
                yRotation = 0.0f;
            }
        }

        matrices.push();
        matrices.translate(renderX, RENDER_Y, renderZ);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yRotation));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0f));
        matrices.scale(SCALE, SCALE, SCALE);

        this.itemRenderer.renderItem(
                stack, ModelTransformationMode.FIXED,
                light, OverlayTexture.DEFAULT_UV, matrices,
                vertexConsumers, entity.getWorld(), 0
        );

        matrices.pop();
    }
}
