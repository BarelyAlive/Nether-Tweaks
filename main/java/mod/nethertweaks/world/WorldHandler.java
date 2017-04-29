package mod.nethertweaks.world;
 
import java.io.*;

import mod.nethertweaks.Config;
import mod.nethertweaks.ForgeSubscribe;
import mod.nethertweaks.RecipeLoader;
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
     
    boolean isHellworldType;
         
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
		if(this.isHellworldType == true && event.toDim == 0 && !event.player.worldObj.isRemote) {
			event.player.changeDimension(-1);
		}
	}
	
	@SubscribeEvent
	public void firstSpawn(PlayerEvent.PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		EntityPlayerMP playermp = (EntityPlayerMP) event.player;
		
		boolean isRemote = event.player.worldObj.isRemote;
		NBTTagCompound tag = player.getEntityData();
		
		if(!(this.isHellworldType)) return;
		if(isRemote) return;
		if(!tag.hasKey("ntm.firstSpawn"))		teleportPlayer(playermp, player);
		if(!tag.getBoolean("ntm.firstSpawn"))	teleportPlayer(playermp, player);
		return;
	}
	
	private void teleportPlayer(EntityPlayerMP player, EntityPlayer player2) {
		
		player.getEntityData().setBoolean("ntm.firstSpawn", true);
		
		player.setPortal(player.getPosition());
		player.changeDimension(-1);
		
	}
     
}
