package mod.nethertweaks.client.renderers;

import mod.nethertweaks.blocks.AshBonePile;
import mod.nethertweaks.blocks.tile.TileAshBonePile;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.world.WorldSpawnLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class RenderAshBonePile extends TileEntitySpecialRenderer<TileAshBonePile> {
	
	@Override
	public void render(TileAshBonePile te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (te != null)
		{
			boolean is_lit = false;
			if (WorldSpawnLocation.bonfire_info.containsKey(new BlockPos(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ())))
			{
				is_lit = true;
			}
			else
			{
				is_lit = te.isLit();
			}
			if (is_lit)
			{
				GlStateManager.pushAttrib();
                GlStateManager.pushMatrix();

                GlStateManager.translate(x, y, z);
                GlStateManager.disableRescaleNormal();
                RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
                RenderHelper.enableStandardItemLighting();
                GlStateManager.enableLighting();
                GlStateManager.pushMatrix();
                {
                    GlStateManager.translate(0.5, 0.65, 0.5);
                    if (Minecraft.getMinecraft().world.getBlockState(te.getPos()).getBlock() == BlockHandler.ASH_BONE_PILE) {
                        if (Minecraft.getMinecraft().world.getBlockState(te.getPos()).getValue(AshBonePile.FACING) == EnumFacing.NORTH) {
                            GlStateManager.rotate(0, 0, 1, 0);
                        }
                        else if (Minecraft.getMinecraft().world.getBlockState(te.getPos()).getValue(AshBonePile.FACING) == EnumFacing.EAST) {
                            GlStateManager.rotate(90, 0, 1, 0);
                        }
                        else if (Minecraft.getMinecraft().world.getBlockState(te.getPos()).getValue(AshBonePile.FACING) == EnumFacing.SOUTH) {
                            GlStateManager.rotate(180, 0, 1, 0);
                        }
                        else if (Minecraft.getMinecraft().world.getBlockState(te.getPos()).getValue(AshBonePile.FACING) == EnumFacing.WEST) {
                            GlStateManager.rotate(270, 0, 1, 0);
                        }
                    }
                    GlStateManager.rotate(-130, 0, 0, 1);
                    GlStateManager.scale(1, 1, 1);
                    renderItem.renderItem(new ItemStack(ItemHandler.COILED_SWORD), ItemCameraTransforms.TransformType.NONE);
                }
                GlStateManager.popMatrix();
                GlStateManager.popMatrix();
                GlStateManager.popAttrib();
			}
		}
		
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}
	
}
