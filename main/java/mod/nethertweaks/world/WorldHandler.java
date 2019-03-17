package mod.nethertweaks.world;
 
import java.io.*;

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
    public void perfectJoin(net.minecraftforge.event.entity.EntityJoinWorldEvent event){
    	
    	if(event.getEntity() instanceof EntityPlayer){
    		EntityPlayer player = (EntityPlayer) event.getEntity();
        	System.out.println(player);
        	
	        if(player.dimension != -1 && isHellworldType == true) {
	        	player.preparePlayerToSpawn();
	            player.changeDimension(-1);
	        }
    	
	    	if(isHellworldType == true){
	            
	        	if(player.dimension == 0){
	        		player.changeDimension(Config.nethDim);
	        	}
	        	if(player.dimension == 1){
	        		player.changeDimension(Config.endDim);
	        	}
	        }
    	}
    }
     
}
