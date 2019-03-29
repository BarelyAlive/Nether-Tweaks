package mod.nethertweaks.world;
 
import java.io.FileNotFoundException;
import java.io.IOException;

import mod.nethertweaks.Config;
import mod.nethertweaks.handler.RecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.event.terraingen.BiomeEvent.GetWaterColor;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import mod.nethertweaks.Config;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.RecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.event.terraingen.BiomeEvent.GetWaterColor;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 
public class WorldHandler{
     
	protected static boolean isHellworldType = false;
	final String key = "ntm.firstSpawn";
	final String key2 = "ntm.secondSpawn";
	final String coodX = "ntm.cood.x";
	final String coodY = "ntm.cood.y";
	final String coodZ = "ntm.cood.z";
    
    @SubscribeEvent
    public void respawn(PlayerEvent.PlayerRespawnEvent pre) {
    	if(pre.player.world.getWorldType() instanceof WorldTypeHellworld && pre.player.addedToChunk) {
    		teleportPlayer(pre.player);
		}
    }
    
    @SubscribeEvent
	public void changeToNether(PlayerEvent.PlayerChangedDimensionEvent event) {
		if(event.player.world.getWorldType() instanceof WorldTypeHellworld  && event.toDim == 0 && !event.player.world.isRemote) {
			teleportPlayer(event.player);
		}
		else if(!(event.player.world.getWorldType() instanceof WorldTypeHellworld) && event.fromDim == -1 && !event.player.world.isRemote) {
			if(Config.nethDim != 0)
			event.player.changeDimension(Config.nethDim);
		}
	}
	
	@SubscribeEvent
	public void firstSpawn(PlayerEvent.PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		
		boolean isRemote = event.player.world.isRemote;
		NBTTagCompound tag = player.getEntityData();
		
		if(!(event.player.world.getWorldType() instanceof WorldTypeHellworld)) return;
		if(isRemote) return;
		if(!tag.hasKey(key))		teleportPlayer(player);
		if(!tag.getBoolean(key))	teleportPlayer(player);
		return;
	}
	
	private void teleportPlayer(EntityPlayer player2) {
		
		EntityPlayerMP player = (EntityPlayerMP) player2;
		
		if(!player2.getEntityData().hasKey(key2) || !player2.getEntityData().getBoolean(key2)) {
			if(player2.getEntityData().getBoolean(key)) {
				player2.getEntityData().setBoolean(key2, true);
			}
		}
		
		if(!player2.getEntityData().hasKey(key) || !player2.getEntityData().getBoolean(key)){
			player2.setPortal(player2.getPosition());
			player2.getEntityData().setBoolean(key, true);
		}
				
		if(player2.dimension != -1) player2.changeDimension(-1);
		
		if(player2.getEntityData().getBoolean(key2) && player.addedToChunk) {
			BlockPos pos = new BlockPos(player2);
			
			player.setSpawnChunk(pos, true, -1);
			player.getEntityData().setInteger(coodX, pos.getX());
			player.getEntityData().setInteger(coodY, pos.getY());
			player.getEntityData().setInteger(coodZ, pos.getZ());
		}
}
    
    @SubscribeEvent
    public void getMilk(net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract event){
    	if(!event.getWorld().isRemote){
    	
	    	if(event.getTarget() instanceof EntityCow){
	    		int zahl = event.getEntityPlayer().inventory.getSlotFor(event.getItemStack());
	    		
		    	if(event.getItemStack().getItem() == BucketNFluidHandler.bucketStone){
		    			event.getEntityPlayer().inventory.decrStackSize(zahl, 1);
		    			event.getEntityPlayer().inventory.addItemStackToInventory(new ItemStack(BucketNFluidHandler.bucketStoneMilk, 1));
		    	}else
		    	
		    	if(event.getItemStack().getItem() == BucketNFluidHandler.bucketWood){
		    			event.getEntityPlayer().inventory.decrStackSize(zahl, 1);
		    			event.getEntityPlayer().inventory.addItemStackToInventory(new ItemStack(BucketNFluidHandler.bucketWoodMilk, 1));
		    	}
	    	}
    	}
    }
}
