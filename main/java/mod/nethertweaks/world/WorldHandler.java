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
         
    @SubscribeEvent
    public void changeDimensionByWorldType(WorldTypeEvent event) throws IOException, FileNotFoundException, ArrayIndexOutOfBoundsException {
         
        if(event.getWorldType() instanceof WorldTypeHellworld) {
             
            this.isHellworldType = true;
             
            DimensionManager.unregisterDimension(0);
            DimensionType.register("Overworld", "Provider", 0, WorldProviderSurfaceNTM.class, true);
            DimensionManager.registerDimension(0, DimensionType.getById(0));
             
            DimensionManager.unregisterDimension(-1);
            DimensionType.register("Nether", "Provider", -1, WorldProviderNetherNTM.class, true);
            DimensionManager.registerDimension(-1, DimensionType.getById(-1));
             
            DimensionManager.unregisterDimension(1);
            DimensionType.register("End", "Provider", 1, WorldProviderEndNTM.class, true);
            DimensionManager.registerDimension(1, DimensionType.getById(1));
             
        }
         
    }    
    
    
    @SubscribeEvent
	public void changeToNether(PlayerEvent.PlayerChangedDimensionEvent event) {
		if(event.player.world.getWorldType() instanceof WorldTypeHellworld  && event.toDim == 0 && !event.player.world.isRemote) {
			event.player.changeDimension(-1);
		}
		else if(!(event.player.world.getWorldType() instanceof WorldTypeHellworld) && event.fromDim == -1 && !event.player.world.isRemote) {
			event.player.changeDimension(Config.nethDim);
		}
	}
	
	@SubscribeEvent
	public void firstSpawn(PlayerEvent.PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		
		boolean isRemote = event.player.world.isRemote;
		NBTTagCompound tag = player.getEntityData();
		
		if(!(this.isHellworldType)) return;
		if(isRemote) return;
		if(!tag.hasKey(key))		teleportPlayer(player);
		if(!tag.getBoolean(key))	teleportPlayer(player);
		return;
	}
	
	private void teleportPlayer(EntityPlayer player2) {
		
		EntityPlayerMP player = (EntityPlayerMP) player2;
		if(!player2.getEntityData().hasKey(key) || !player2.getEntityData().getBoolean(key)){
			player2.setPortal(player2.getPosition());
			player2.setSpawnChunk(player2.getPosition(), true, -1);
		}
			
		player2.getEntityData().setBoolean(key, true);
		
		if(player2.dimension != -1) player2.changeDimension(-1);
		
		
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
