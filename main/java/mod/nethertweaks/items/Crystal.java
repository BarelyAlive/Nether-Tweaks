package mod.nethertweaks.items;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.sfhcore.items.CustomItem;
import net.java.games.input.Keyboard;
import net.minecraft.block.Block;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
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
		super(null, 1, NetherTweaksMod.tabNetherTweaksMod, false, 1, name);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	/**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ItemStack itemstack = playerIn.getHeldItem(handIn);
    	
		if(playerIn.isSneaking()){
			if(playerIn.inventory.hasItemStack(new ItemStack(Items.WATER_BUCKET))){
				playerIn.inventory.clearMatchingItems(Items.WATER_BUCKET, 0, 1, null);
				playerIn.inventory.addItemStackToInventory(new ItemStack(BucketNFluidHandler.bucketDemonWater));
			}
			if(playerIn.inventory.hasItemStack(new ItemStack(BucketNFluidHandler.bucketStoneWater))){
					playerIn.inventory.clearMatchingItems(BucketNFluidHandler.bucketStoneWater, 0, 1, null);
					playerIn.inventory.addItemStackToInventory(new ItemStack(BucketNFluidHandler.bucketStoneDemonWater));
			}
			if(playerIn.inventory.hasItemStack(new ItemStack(BucketNFluidHandler.bucketWoodWater))){
				playerIn.inventory.clearMatchingItems(BucketNFluidHandler.bucketWoodWater, 0, 1, null);
				playerIn.inventory.addItemStackToInventory(new ItemStack(BucketNFluidHandler.bucketWoodDemonWater));
			}
		}
		
		if (playerIn.getHeldItem(handIn).getUnlocalizedName().contains("sanctuary_crystal")){
			
		
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
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
				}
				zahl++;
			}
			zahl = -3;
			zahl2--;
			
		}
		
		if(block[0] == Blocks.ENCHANTING_TABLE){
			
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
		}
		
	}else{
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
	}
		//Auswirkungen
		int zahl = -3;
		int zahl2 = 3;
		
		for(int numba = 7; numba > 0; numba--){
			//rows
				
			for(int i = 0; i < 7; i++){
				worldIn.setBlockState(pos.add(x+zahl, y-1, z+zahl2), BlockHandler.HOLYEARTH.getDefaultState());
				zahl++;
			}
			zahl = -3;
			zahl2--;
				
		}
		
		if(!playerIn.capabilities.isCreativeMode){
			playerIn.inventory.clearMatchingItems(this, 0, 1, null);
		}
	}
		
		if(playerIn.getHeldItem(handIn).getUnlocalizedName().contains("ender_crystal")) {
				
	        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
	        playerIn.getCooldownTracker().setCooldown(this, 20);
	
	        if (!worldIn.isRemote)
	        {
	            EntityEnderPearl entityenderpearl = new EntityEnderPearl(worldIn, playerIn);
	            entityenderpearl.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
	            worldIn.spawnEntity(entityenderpearl);
	        }
	
	        playerIn.addStat(StatList.getObjectUseStats(this));
	        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
	}

}
