package mod.nethertweaks.client.renderers;

import mod.nethertweaks.item.ItemChunk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ChunkColorer implements IItemColor {

	@Override
	public int colorMultiplier(final ItemStack stack, final int tintIndex) {
		int pixel = 0xFFFFFFFF;
        if (!(stack.getItem() instanceof ItemChunk))
        {
        	return pixel;
        }
        ItemChunk chunk = (ItemChunk) stack.getItem();
        if (tintIndex == 0)
        { 
	        return chunk.getColor();
        }
        return pixel;
	}

}
