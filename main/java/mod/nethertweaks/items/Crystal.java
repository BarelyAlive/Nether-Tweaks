package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.NTMBlocks;
import mod.sfhcore.Constants;
import mod.sfhcore.items.ItemThing;
import mod.sfhcore.proxy.IVariantProvider;
import net.java.games.input.Keyboard;
import net.minecraft.block.Block;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.client.event.sound.SoundEvent.SoundSourceEvent;
import net.minecraftforge.fml.common.Loader;

public class Crystal extends ItemThing implements IVariantProvider{
	
	public static final Block bob = Blocks.OBSIDIAN;
	
	public Crystal() {
		super(null, 1, NetherTweaksMod.tabNetherTweaksMod);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		if(playerIn.isSneaking()){
			if(playerIn.inventory.hasItemStack(new ItemStack(Items.WATER_BUCKET))){
				playerIn.inventory.clearMatchingItems(Items.WATER_BUCKET, 0, 1, null);
				playerIn.inventory.addItemStackToInventory(new ItemStack(NTMItems.bucketDemonWater));
			}
			if(Loader.isModLoaded(Constants.ModIdCHAUST)){
				if(playerIn.inventory.hasItemStack(new ItemStack(NTMItems.bucketStoneWater))){
					playerIn.inventory.clearMatchingItems(NTMItems.bucketStoneWater, 0, 1, null);
					playerIn.inventory.addItemStackToInventory(new ItemStack(NTMItems.bucketStoneDemonWater));
				}
				if(playerIn.inventory.hasItemStack(new ItemStack(NTMItems.bucketWoodWater))){
					playerIn.inventory.clearMatchingItems(NTMItems.bucketWoodWater, 0, 1, null);
					playerIn.inventory.addItemStackToInventory(new ItemStack(NTMItems.bucketWoodDemonWater));
				}

			}
		}
		
		if (playerIn.isSneaking() && itemStackIn.getItem() == NTMItems.itemSanctuaryCrystal){
			
		
		//Structure ok?
		RayTraceResult rtr = new RayTraceResult(playerIn);
		BlockPos pos = rtr.getBlockPos();
		
		//enchantment table
		Block[] block = new Block[25];
		
		int x = 3;
		int y = 2;
		int z = 3;
		for(int i = 25; i < 25; i++){
			block[i] = worldIn.getBlockState(pos.add(x, y, z)).getBlock();
			
			if(x >= -3){
				x--;
			}
			if(y >= 0 && x == -3){
				y--;
			}
			if(z >= -3 && y == 0){
				z-=3;
			}
		}
		
		//block 2, 3, 5, 6, 8, 9, 11, 12, 14, 15, 17, 18, 20, 21, 23, 24 equals Obsidian
		//block 1, 4, 7, 10, 13, 16, 19, 21 equals glowstone
		//block 0 is enchantment table
		//rest is dirt
		if(	bob.equals(block[2]) &&bob.equals(block[3]) &&bob.equals(block[5]) &&bob.equals(block[6]) 
			&&bob.equals(block[8]) &&bob.equals(block[9]) &&bob.equals(block[11]) &&bob.equals(block[12])
			&&bob.equals(block[14]) &&bob.equals(block[15]) &&bob.equals(block[17]) &&bob.equals(block[18]) 
			&&bob.equals(block[20]) &&bob.equals(block[21]) &&bob.equals(block[23]) &&bob.equals(block[24])
			&&Blocks.GLOWSTONE.equals(block[1]) &&Blocks.GLOWSTONE.equals(block[4]) &&Blocks.GLOWSTONE.equals(block[7]) &&Blocks.GLOWSTONE.equals(block[10])
			&&Blocks.GLOWSTONE.equals(block[13]) &&Blocks.GLOWSTONE.equals(block[16]) &&Blocks.GLOWSTONE.equals(block[19]) &&Blocks.GLOWSTONE.equals(block[22])){
		
		//earth
		int zahl = -3;
		int zahl2 = 3;
		
		for(int numba = 7; numba > 0; numba--){
		//rows
			
			for(int i = 0; i < 7; i++){
				if(worldIn.getBlockState(pos.add(x+zahl, y-1, z+zahl2)) != Blocks.DIRT.getDefaultState()){
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
				}
				zahl++;
			}
			zahl = -3;
			zahl2--;
			
		}
		
		if(block.equals(Blocks.ENCHANTING_TABLE)){
			
			worldIn.playSound(playerIn, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x-3, y+2, z+3), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(playerIn, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x, y+2, z+3), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(playerIn, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x+3, y+2, z+3), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(playerIn, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x-3, y+2, z), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(playerIn, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x+3, y+2, z), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(playerIn, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x-3, y+2, z-3), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(playerIn, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x, y+2, z-3), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(playerIn, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x+3, y+2, z-3), Blocks.FIRE.getDefaultState());
		}else{
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
		}
		
	}else{
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
	}
		//Auswirkungen
		int zahl = -3;
		int zahl2 = 3;
		
		for(int numba = 7; numba > 0; numba--){
			//rows
				
			for(int i = 0; i < 7; i++){
				worldIn.setBlockState(pos.add(x+zahl, y-1, z+zahl2), NTMBlocks.blockHolyEarth.getDefaultState());
				zahl++;
			}
			zahl = -3;
			zahl2--;
				
		}
		
		if(!playerIn.capabilities.isCreativeMode){
			playerIn.inventory.clearMatchingItems(this, 0, 1, null);
		}
	}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
            ret.add(new ImmutablePair<Integer, String>(0, "type=normal"));
        return ret;
    }
}
