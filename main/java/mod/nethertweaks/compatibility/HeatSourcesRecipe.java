package mod.nethertweaks.compatibility;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.RenderTickCounter;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HeatSourcesRecipe implements IRecipeWrapper {
	private final List<ItemStack> inputs;
	private final BlockInfo blockInfo;
	private final String heatAmountString;

	private static final Item torch = Item.getItemFromBlock(Blocks.TORCH);

	public HeatSourcesRecipe(final BlockInfo blockInfo, final int heatAmount) {
		this.blockInfo = blockInfo;


		ItemStack item = blockInfo.getItemStack();

		if (item.getItem() == torch)
			item = new ItemStack(torch, 1, 0);

		if (item.isEmpty()) {
			Fluid fluid = null;
			final Block block = blockInfo.getBlock();
			if (block instanceof IFluidBlock)
				fluid = ((IFluidBlock) block).getFluid();
			if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
				fluid = FluidRegistry.LAVA;
			if (block == Blocks.WATER || block == Blocks.FLOWING_WATER)
				fluid = FluidRegistry.WATER;
			if (fluid != null)
				item = FluidUtil.getFilledBucket(new FluidStack(fluid, 1000));
			if (block == Blocks.FIRE)
				item = new ItemStack(Items.FLINT_AND_STEEL, 1);
		}


		inputs = new ArrayList<>(Collections.singleton(item));
		heatAmountString = heatAmount + "x";
	}

	@Override
	public void getIngredients(@Nonnull final IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.ITEM, inputs);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawInfo(final Minecraft minecraft, final int recipeWidth, final int recipeHeight, final int mouseX, final int mouseY) {
		minecraft.fontRenderer.drawString(heatAmountString, 24, 12, Color.gray.getRGB());

		// int reqX = (int) Math.ceil((float)(maxPos.getX() - minPos.getX() +2) / 2.0f);
		// int reqZ = (int) Math.ceil((float)(maxPos.getZ() - minPos.getZ() +2) / 2.0f);


		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, 0F, 216.5F);


		// minecraft.fontRenderer.drawString(recipe.getDimensionsString(), 153-mc.fontRenderer.getStringWidth(recipe.getDimensionsString()), 19 * 5 + 10, 0x444444);

		GlStateManager.popMatrix();

		final float angle = RenderTickCounter.getRenderTicks() * 45.0f / 128.0f;

		// When we want to render translucent blocks we might need this
		final BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

		// Init GlStateManager
		final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
		textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		GlStateManager.disableFog();
		GlStateManager.disableLighting();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.enableBlend();
		GlStateManager.enableCull();
		GlStateManager.enableAlpha();
		if (Minecraft.isAmbientOcclusionEnabled())
			GlStateManager.shadeModel(7425);
		else
			GlStateManager.shadeModel(7424);

		GlStateManager.pushMatrix();

		// Center on recipe area
		GlStateManager.translate(70, 30, 20);

		// Shift it a bit down so one can properly see 3d
		GlStateManager.rotate(-25.0f, 1.0f, 0.0f, 0.0f);

		// Rotate per our calculated time
		GlStateManager.rotate(angle, 0.0f, 1.0f, 0.0f);

		// Scale down to gui scale
		GlStateManager.scale(16.0f, -16.0f, 16.0f);

		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder buffer = tessellator.getBuffer();

		buffer.setTranslation(-.5, 0, -.5);

		GlStateManager.enableCull();

		//IBlockState crucible = ModBlocks.crucibleStone.getDefaultState().withProperty(Block.FIRED, true);
		final IBlockState state = blockInfo.getBlockState();

		final BlockPos pos = new BlockPos(0, 0, 0);
		/*if (blockInfo.getBlock() instanceof IFluidBlock || blockInfo.getBlock() instanceof BlockLiquid) {
            pos = pos.up();
        }*/


		// Aaaand render
		buffer.begin(7, DefaultVertexFormats.BLOCK);
		GlStateManager.disableAlpha();
		//this.renderBlock(blockrendererdispatcher, buffer, BlockRenderLayer.SOLID, crucible, new BlockPos(0, 1, 0), minecraft.world);

		renderBlock(blockrendererdispatcher, buffer, BlockRenderLayer.SOLID, state, pos, minecraft.world);
		GlStateManager.enableAlpha();
		renderBlock(blockrendererdispatcher, buffer, BlockRenderLayer.CUTOUT_MIPPED, state, pos, minecraft.world);
		renderBlock(blockrendererdispatcher, buffer, BlockRenderLayer.CUTOUT, state, pos, minecraft.world);
		GlStateManager.shadeModel(7425);
		renderBlock(blockrendererdispatcher, buffer, BlockRenderLayer.TRANSLUCENT, state, pos, minecraft.world);
		tessellator.draw();

		buffer.setTranslation(0, 0, 0);
		GlStateManager.popMatrix();
	}

	@SideOnly(Side.CLIENT)
	public void renderBlock(final BlockRendererDispatcher blockrendererdispatcher, final BufferBuilder buffer, final BlockRenderLayer renderLayer, final IBlockState blockState, final BlockPos pos, final IBlockAccess access) {

		if (!blockState.getBlock().canRenderInLayer(blockState, renderLayer))
			return;

		ForgeHooksClient.setRenderLayer(renderLayer);

		try {
			blockrendererdispatcher.renderBlock(blockState, pos, access, buffer);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		ForgeHooksClient.setRenderLayer(null);
	}
}