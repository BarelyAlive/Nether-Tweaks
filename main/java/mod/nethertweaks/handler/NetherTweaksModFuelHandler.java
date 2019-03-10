package mod.nethertweaks.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NetherTweaksModFuelHandler{

	@SubscribeEvent
	public int setBurnTime(FurnaceFuelBurnTimeEvent e) {
		
		if(fuel.toString().contains("BucketStoneLava")){
			
			return 20000;
			
		}
		
		if(fuel.toString().contains("ItemBase_10")){
			//200 Ticks gleich 1 Vorgang
			
			return 12800;
			
			
		}
		if(fuel.toString().contains("BlockBasic_1")){
			//200 Ticks gleich 1 Vorgang
			
			return 128000;
			
			
		}
		if(fuel.toString().contains("BlockBasic_0")){
			//200 Ticks gleich 1 Vorgang
			
			return 16000;
			
			
		}
		return 0;
	}
	
}
