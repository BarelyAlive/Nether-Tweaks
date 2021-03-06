package mod.nethertweaks.client.renderers;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import mod.nethertweaks.entities.ProjectileStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderProjectileStone extends Render<ProjectileStone> {
	public RenderProjectileStone(final RenderManager renderManager) {
		super(renderManager);
	}

	private static void bufferCuboid(final BufferBuilder buffer, double size, final double minU, final double minV, final double maxU, final double maxV, final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
		size /= 16.0;

		double xMin = Math.min(x1, x2);
		double xMax = Math.max(x1, x2);

		double yMin = Math.min(y1, y2);
		double yMax = Math.max(y1, y2);

		double zMin = Math.min(z1, z2);
		double zMax = Math.max(z1, z2);

		final double xUMin = minU + (maxU - minU) * (xMin + 8.0) / 16.0;
		final double xUMax = minU + (maxU - minU) * (xMax + 8.0) / 16.0;

		//double yUMin = minU + (maxU - minU) * (yMin + 8.0) / 16.0;
		//double yUMax = minU + (maxU - minU) * (yMax + 8.0) / 16.0;

		final double zUMin = minU + (maxU - minU) * (zMin + 8.0) / 16.0;
		final double zUMax = minU + (maxU - minU) * (zMax + 8.0) / 16.0;

		//double xVMin = minV + (maxV - minV) * (xMin + 8.0) / 16.0;
		//double xVMax = minV + (maxV - minV) * (xMax + 8.0) / 16.0;

		final double yVMin = minV + (maxV - minV) * (yMin + 8.0) / 16.0;
		final double yVMax = minV + (maxV - minV) * (yMax + 8.0) / 16.0;

		final double zVMin = minV + (maxV - minV) * (zMin + 8.0) / 16.0;
		final double zVMax = minV + (maxV - minV) * (zMax + 8.0) / 16.0;

		xMin *= size;
		xMax *= size;

		yMin *= size;
		yMax *= size;

		zMin *= size;
		zMax *= size;

		buffer.pos(xMin, yMin, zMin).tex(zUMin, yVMax).normal(-1, 0, 0).endVertex();
		buffer.pos(xMin, yMin, zMax).tex(zUMax, yVMax).normal(-1, 0, 0).endVertex();
		buffer.pos(xMin, yMax, zMax).tex(zUMax, yVMin).normal(-1, 0, 0).endVertex();
		buffer.pos(xMin, yMax, zMin).tex(zUMin, yVMin).normal(-1, 0, 0).endVertex();

		buffer.pos(xMax, yMin, zMin).tex(zUMax, yVMax).normal(1, 0, 0).endVertex();
		buffer.pos(xMax, yMax, zMin).tex(zUMax, yVMin).normal(1, 0, 0).endVertex();
		buffer.pos(xMax, yMax, zMax).tex(zUMin, yVMin).normal(1, 0, 0).endVertex();
		buffer.pos(xMax, yMin, zMax).tex(zUMin, yVMax).normal(1, 0, 0).endVertex();

		buffer.pos(xMin, yMin, zMin).tex(xUMin, zVMax).normal(0, -1, 0).endVertex();
		buffer.pos(xMax, yMin, zMin).tex(xUMax, zVMax).normal(0, -1, 0).endVertex();
		buffer.pos(xMax, yMin, zMax).tex(xUMax, zVMin).normal(0, -1, 0).endVertex();
		buffer.pos(xMin, yMin, zMax).tex(xUMin, zVMin).normal(0, -1, 0).endVertex();

		buffer.pos(xMin, yMax, zMin).tex(xUMin, zVMin).normal(0, 1, 0).endVertex();
		buffer.pos(xMin, yMax, zMax).tex(xUMin, zVMax).normal(0, 1, 0).endVertex();
		buffer.pos(xMax, yMax, zMax).tex(xUMax, zVMax).normal(0, 1, 0).endVertex();
		buffer.pos(xMax, yMax, zMin).tex(xUMax, zVMin).normal(0, 1, 0).endVertex();

		buffer.pos(xMin, yMin, zMin).tex(xUMax, yVMax).normal(0, 0, -1).endVertex();
		buffer.pos(xMin, yMax, zMin).tex(xUMax, yVMin).normal(0, 0, -1).endVertex();
		buffer.pos(xMax, yMax, zMin).tex(xUMin, yVMin).normal(0, 0, -1).endVertex();
		buffer.pos(xMax, yMin, zMin).tex(xUMin, yVMax).normal(0, 0, -1).endVertex();

		buffer.pos(xMin, yMin, zMax).tex(xUMin, yVMax).normal(0, 0, 1).endVertex();
		buffer.pos(xMax, yMin, zMax).tex(xUMax, yVMax).normal(0, 0, 1).endVertex();
		buffer.pos(xMax, yMax, zMax).tex(xUMax, yVMin).normal(0, 0, 1).endVertex();
		buffer.pos(xMin, yMax, zMax).tex(xUMin, yVMin).normal(0, 0, 1).endVertex();
	}

	private static TextureAtlasSprite getTexture(final IBlockState state) {
		return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
	}

	@Override
	protected ResourceLocation getEntityTexture(@Nonnull final ProjectileStone stone) {
		return new ResourceLocation("minecraft:blocks/stone");
	}

	@Override
	public void doRender(@Nonnull final ProjectileStone entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
		final TextureAtlasSprite texture = getTexture(Blocks.STONE.getDefaultState());

		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder buffer = tessellator.getBuffer();

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);

		final double minU = texture.getMinU();
		final double maxU = texture.getMaxU();
		final double minV = texture.getMinV();
		final double maxV = texture.getMaxV();

		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);

		final double size = 0.5;

		//bufferCuboid(buffer, 1, minU, minV, maxU, maxV, -8, -8, -8, 8, 8, 8);

		bufferCuboid(buffer, size, minU, minV, maxU, maxV, -4, -2, -2, -2, 2, 2);
		bufferCuboid(buffer, size, minU, minV, maxU, maxV, 4, 2, 2, 2, -2, -2);
		bufferCuboid(buffer, size, minU, minV, maxU, maxV, -2, -4, -2, 2, -2, 2);
		bufferCuboid(buffer, size, minU, minV, maxU, maxV, 2, 4, 2, -2, 2, -2);
		bufferCuboid(buffer, size, minU, minV, maxU, maxV, -2, -2, -4, 2, 2, -2);
		bufferCuboid(buffer, size, minU, minV, maxU, maxV, 2, 2, 4, -2, -2, 2);

		tessellator.draw();

		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}

	public static class Factory implements IRenderFactory<ProjectileStone> {
		@Override
		public Render<ProjectileStone> createRenderFor(final RenderManager manager) {
			return new RenderProjectileStone(manager);
		}
	}
}
