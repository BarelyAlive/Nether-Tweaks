package mod.nethertweaks.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NTMFuelHandler{

	@SubscribeEvent
	public int setBurnTime(FurnaceFuelBurnTimeEvent e) {
				
		if(e.getItemStack().getUnlocalizedName().contains("bucket_stone_lava")){
			
			return 20000;
			
		}
		
		if(e.getItemStack().getUnlocalizedName().contains("ItemBase_10")){
			//200 Ticks gleich 1 Vorgang
			
			return 12800;
			
			
		}
		if(e.getItemStack().getUnlocalizedName().contains("block_basic_0")){
			//200 Ticks gleich 1 Vorgang
			
			return 16000;
			
			
		}
		if(e.getItemStack().getUnlocalizedName().contains("block_basic_1")){
			//200 Ticks gleich 1 Vorgang
			
			return 128000;
			
			
		}
		return 0;
	}
	
}
