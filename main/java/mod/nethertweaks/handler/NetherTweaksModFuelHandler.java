package mod.nethertweaks.handler;

import mod.nethertweaks.blocks.NTMBlocks;
import mod.nethertweaks.items.NTMItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class NetherTweaksModFuelHandler implements IFuelHandler{

	@Override
	public int getBurnTime(ItemStack fuel) {
		
		if(fuel.getItem() == NTMItems.bucketStoneLava){
			
			return 20000;
			
		}
		
		if(fuel.getItem() == NTMItems.itemBase && fuel.getItemDamage() == 10){
			//200 Ticks gleich 1 Vorgang
			
			return 12800;
			
			
		}
		if(fuel.getItem() == Item.getItemFromBlock(NTMBlocks.blockBasic) && fuel.getItemDamage() == 1){
			//200 Ticks gleich 1 Vorgang
			
			return 128000;
			
			
		}
		if(fuel.getItem() == Item.getItemFromBlock(NTMBlocks.blockBasic) && fuel.getItemDamage() == 0){
			//200 Ticks gleich 1 Vorgang
			
			return 16000;
			
			
		}
		return 0;
	}
	
}
