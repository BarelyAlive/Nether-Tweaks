package mod.nethertweaks.world;
 
import java.io.*;

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
 
public class WorldHandler{
     
    protected static boolean isHellworldType = false;
    final String key = "ntm.firstSpawn";
    
    @SubscribeEvent
    public void respawn(PlayerEvent.PlayerRespawnEvent pre) {
    	if(pre.player.worldObj.getWorldType() instanceof WorldTypeHellworld && pre.player.addedToChunk) {
    		teleportPlayer(pre.player);
		}
	}
    
	@SubscribeEvent
	public void changeToNether(PlayerEvent.PlayerChangedDimensionEvent event) {
		if(event.player.worldObj.getWorldType() instanceof WorldTypeHellworld  && event.toDim == 0 && !event.player.worldObj.isRemote) {
			event.player.changeDimension(-1);
		}
		else if(!(event.player.worldObj.getWorldType() instanceof WorldTypeHellworld) && event.fromDim == -1 && !event.player.worldObj.isRemote) {
			event.player.changeDimension(Config.nethDim);
		}
	}
	
	@SubscribeEvent
	public void firstSpawn(PlayerEvent.PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		
		boolean isRemote = event.player.worldObj.isRemote;
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
     
}
