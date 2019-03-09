package mod.nethertweaks.items;

import mod.nethertweaks.BucketLoader;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.NTMBlocks;
import mod.sfhcore.items.CustomItem;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.client.event.sound.SoundEvent.SoundSourceEvent;
import net.minecraftforge.fml.common.Loader;

public class Crystal extends CustomItem{
	
	public static final Block bob = Blocks.OBSIDIAN;
	
	public Crystal(String name) {
		super(null, 1, NetherTweaksMod.tabNetherTweaksMod, false, 5, name);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	/**
     * Called when a Block is right-clicked with this Item
     */
    @SuppressWarnings("incomplete-switch")
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(player.isSneaking()){
			if(player.inventory.hasItemStack(new ItemStack(Items.WATER_BUCKET))){
				player.inventory.clearMatchingItems(Items.WATER_BUCKET, 0, 1, null);
				player.inventory.addItemStackToInventory(new ItemStack(BucketLoader.bucketDemonWater));
			}
			if(player.inventory.hasItemStack(new ItemStack(BucketLoader.bucketStoneWater))){
					player.inventory.clearMatchingItems(BucketLoader.bucketStoneWater, 0, 1, null);
					player.inventory.addItemStackToInventory(new ItemStack(BucketLoader.bucketStoneDemonWater));
			}
			if(player.inventory.hasItemStack(new ItemStack(BucketLoader.bucketWoodWater))){
				player.inventory.clearMatchingItems(BucketLoader.bucketWoodWater, 0, 1, null);
				player.inventory.addItemStackToInventory(new ItemStack(BucketLoader.bucketWoodDemonWater));
			}
		}
		
		if (player.isSneaking() && player.getActiveItemStack().getItem() == NTMItems.itemSanctuaryCrystal){
			
		
		//Structure ok?
		RayTraceResult rtr = new RayTraceResult(player);
		pos = rtr.getBlockPos();
		
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
					return EnumActionResult.FAIL;
				}
				zahl++;
			}
			zahl = -3;
			zahl2--;
			
		}
		
		if(block.equals(Blocks.ENCHANTING_TABLE)){
			
			worldIn.playSound(player, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x-3, y+2, z+3), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(player, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x, y+2, z+3), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(player, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x+3, y+2, z+3), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(player, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x-3, y+2, z), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(player, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x+3, y+2, z), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(player, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x-3, y+2, z-3), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(player, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x, y+2, z-3), Blocks.FIRE.getDefaultState());
			for(int i = 0; i < 600; i++){
				System.out.println();
			}
			worldIn.playSound(player, pos, new net.minecraft.util.SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1.0f, 1.0f);
			worldIn.setBlockState(pos.add(x+3, y+2, z-3), Blocks.FIRE.getDefaultState());
		}else{
			return EnumActionResult.FAIL;
		}
		
	}else{
		return EnumActionResult.FAIL;
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
		
		if(!player.capabilities.isCreativeMode){
			player.inventory.clearMatchingItems(this, 0, 1, null);
		}
	}
		return EnumActionResult.SUCCESS;
	}

}
