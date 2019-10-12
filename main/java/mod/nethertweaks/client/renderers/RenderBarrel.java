package mod.nethertweaks.client.renderers;

import mod.nethertweaks.block.tile.TileBarrel;
import mod.sfhcore.client.renderers.ModelVertex;
import mod.sfhcore.client.renderers.SpriteColor;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.animation.FastTESR;

public class RenderBarrel extends FastTESR<TileBarrel> {
	private static final ModelVertex[] model = new ModelVertex[4];

	static {
		model[0] = new ModelVertex(EnumFacing.UP, 0.125, 0.875, 0.125, 0, 0);
		model[1] = new ModelVertex(EnumFacing.UP, 0.875, 0.875, 0.125, 1, 0);
		model[2] = new ModelVertex(EnumFacing.UP, 0.875, 0.875, 0.875, 1, 1);
		model[3] = new ModelVertex(EnumFacing.UP, 0.125, 0.875, 0.875, 0, 1);
	}

	@Override
	public void renderTileEntityFast(final TileBarrel te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float partial, final BufferBuilder buffer)
	{
		if (te == null || te.getMode() == null) return;

		// Fill Level
		float fill = te.getMode().getFilledLevelForRender(te);

		if (fill == 0) return;

		final SpriteColor sprite = te.getMode().getSpriteColor(te);
		buffer.setTranslation(x, y, z);
		addSpriteColor(te, sprite, buffer, te.getMode().getFilledLevelForRender(te));
		buffer.setTranslation(0, 0, 0);

	}

	private void addSpriteColor(final TileBarrel te, final SpriteColor sprite, final BufferBuilder buffer, final float fill) {
		if (sprite == null || fill <= 0f) return;

		final BlockPos pos = te.getPos();
		// Light levels
		final int mixedBrightness = te.getWorld().getBlockState(pos).getPackedLightmapCoords(te.getWorld(), te.getPos());
		final int skyLight = mixedBrightness >> 16 & 0xFFFF;
		final int blockLight = mixedBrightness & 0xFFFF;
		// Texturing
		TextureAtlasSprite icon = sprite.getSprite();
		mod.sfhcore.texturing.Color color = sprite.getColor();

		// Draw
		for (final ModelVertex vert : model) {
			for (final VertexFormatElement e : buffer.getVertexFormat().getElements())
				switch (e.getUsage()) {
				case COLOR:
					buffer.color(color.r, color.g, color.b, color.a);
					break;

				case NORMAL:
					buffer.normal(vert.face.getFrontOffsetX(), vert.face.getFrontOffsetY(), vert.face.getFrontOffsetZ());
					break;

				case POSITION:
					final double vertX = vert.x;
					final double vertZ = vert.z;

					buffer.pos(vertX, fill, vertZ);
					break;

				case UV:
					if (e.getIndex() == 1)
						buffer.lightmap(skyLight, blockLight);
					else
						buffer.tex(icon.getInterpolatedU(vert.u), icon.getInterpolatedV(16.0 - vert.v));
					break;

				default:
					break;
				}
			buffer.endVertex();
		}
	}
}