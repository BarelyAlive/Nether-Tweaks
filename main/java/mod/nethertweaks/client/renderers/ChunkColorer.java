package mod.nethertweaks.client.renderers;

import mod.nethertweaks.items.ItemChunk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ChunkColorer implements IItemColor {

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		int pixel = 0xFFFFFFFF;
        if (!(stack.getItem() instanceof ItemChunk))
        {
        	return pixel;
        }
        ItemChunk chunk = (ItemChunk) stack.getItem();
        if (tintIndex == 0)
        { 
	        ItemStack b = chunk.getResult(stack.getItemDamage());
	        if (b.isEmpty())
	        {
	        	return pixel;
	        }
	        try {
	        	IBakedModel res = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(b);
	        	for(int i = 0; i < res.getParticleTexture().getFrameCount(); i++)
	        	{
	        		if(res.getParticleTexture().getFrameTextureData(0)[0].length == 256)
	        		{
	    		        return ((res.getParticleTexture().getFrameTextureData(0)[i][140] & 0xFFFFFFFF) | 0x00000000);
	        		}
	        	}
	        	return pixel;
			} catch (Exception e) {
	        	e.printStackTrace();
	        	return pixel;
			}
        }
        return pixel;
	}

}
