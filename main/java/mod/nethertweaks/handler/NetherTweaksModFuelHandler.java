package mod.nethertweaks.handler;

import mod.nethertweaks.Config;
import mod.nethertweaks.Konstanten;
import mod.nethertweaks.NTMBlocks;
import mod.nethertweaks.NTMItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class NetherTweaksModFuelHandler implements IFuelHandler{

	@Override
	public int getBurnTime(ItemStack fuel) {
		
		//200 Ticks gleich 1 Vorgang
		if(fuel.getItem() == NTMItems.bucketStoneLava){
			
			return 20000;			
		}	
		
		if(fuel.getItem() == Konstanten.HELLFAYAH.getItem() && fuel.getItemDamage() == Konstanten.HELLFAYAH.getItemDamage()){
			
			return Config.burnTimeHellfayah;	
		}
		
		if(fuel.getItem() == Konstanten.HELLFAYAHBLOCK.getItem() && fuel.getItemDamage() == Konstanten.HELLFAYAHBLOCK.getItemDamage()){
			
			return Config.burnTimeHellfayahBlock;				
		}
		
		if(fuel.getItem() == Konstanten.CHARCOALBLOCK.getItem() && fuel.getItemDamage() == Konstanten.CHARCOALBLOCK.getItemDamage()){
			
			return 16000;			
		}
		
		return 0;
	}
	
}
