package mod.nethertweaks.world;
 
import com.ibm.icu.impl.Differ;

import mod.nethertweaks.Config;
import mod.nethertweaks.handler.BucketNFluidHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter.PortalPosition;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeHell;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
 
public class WorldHandler{
     
	public final static String key = "ntm.firstSpawn";
	public final static String coodX = "ntm.cood.x";
	public final static String coodY = "ntm.cood.y";
	public final static String coodZ = "ntm.cood.z";
    
	//HELLWORLD
	
	public static void addWaterMobs()
	{
		EntityRegistry.addSpawn(EntitySquid.class, 50, 1, 10, EnumCreatureType.WATER_CREATURE, BiomeDictionary.getBiomes(Type.NETHER).toArray(new Biome[0]));
	}
	
    @SubscribeEvent
    public void respawn(PlayerEvent.PlayerRespawnEvent event) {
		EntityPlayer player = event.player;
		int range = 32;
    	
    	if(player.world.getWorldType() instanceof WorldTypeHellworld) {
    		teleportPlayer(player);
    		if (!WorldDataNTM.spawnLocas.containsKey(player.getUUID(player.getGameProfile())))
    		{
	    		BlockPos posplayer = player.getPosition();
	    		int yDifferenz = 0;
	    		if (posplayer.getY() < range)
	    		{
	    			yDifferenz = range - posplayer.getY();
	    		}
	    		Iterable<BlockPos> posi = PortalPosition.getAllInBox(posplayer.down(range - yDifferenz).east(range).south(range), posplayer.up(range + yDifferenz).west(range).north(range));
	    		
	    		for(BlockPos pos : posi)
	    		{
	    			if (player.world.getBlockState(pos).getBlock() == Blocks.PORTAL)
	    			{
	    				player.setPosition(pos.getX(), pos.getY(), pos.getZ());
	    				//player.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), player.rotationYaw, player.rotationPitch);
	    			}
	    		}
    		}
    		else
    		{
    			BlockPos pos = WorldDataNTM.spawnLocas.get(player.getUUID(player.getGameProfile()));
				player.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), player.rotationYaw, player.rotationPitch);
    		}
		}
    }
    
    @SubscribeEvent
	public void changeToNether(PlayerEvent.PlayerChangedDimensionEvent event) {
    	EntityPlayer player = event.player;
    	
		if(player.world.getWorldType() instanceof WorldTypeHellworld  && event.toDim == 0 && !player.world.isRemote) {
			teleportPlayer(player);
		}
		else if(!(player.world.getWorldType() instanceof WorldTypeHellworld) && event.fromDim == -1 && !player.world.isRemote) {
			if(Config.nethDim != 0)
				player.changeDimension(Config.nethDim);
		}
	}
	
	@SubscribeEvent
	public void firstSpawn(PlayerEvent.PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		
		boolean isRemote = player.world.isRemote;
		NBTTagCompound tag = player.getEntityData();
		
		teleportPlayer(player);	
	}
	
	private void teleportPlayer(EntityPlayer player) {
		
		if(player.dimension != -1)
		{
			if(!(player.world.getWorldType() instanceof WorldTypeHellworld)) return;
			if(!player.getEntityData().hasKey(key)){
				player.setPortal(player.getPosition());
				player.getEntityData().setBoolean(key, true);
			}
			if(!player.getEntityData().getBoolean(key))
			{
				player.setPortal(player.getPosition());
				player.getEntityData().setBoolean(key, true);
			}
			player.changeDimension(-1);
		}
	}
	
	//Enitity Interaction
    
    @SubscribeEvent
    public void getMilk(net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract event){
    	if(!event.getWorld().isRemote){
    	
	    	if(event.getTarget() instanceof EntityCow){
	    		int zahl = event.getEntityPlayer().inventory.getSlotFor(event.getItemStack());
	    		
		    	if(event.getItemStack().getItem() == BucketNFluidHandler.BUCKETSTONE){
		    			event.getEntityPlayer().inventory.decrStackSize(zahl, 1);
		    			event.getEntityPlayer().inventory.addItemStackToInventory(new ItemStack(BucketNFluidHandler.BUCKETSTONEMILK, 1));
		    	}else
		    	
		    	if(event.getItemStack().getItem() == BucketNFluidHandler.BUCKETWOOD){
		    			event.getEntityPlayer().inventory.decrStackSize(zahl, 1);
		    			event.getEntityPlayer().inventory.addItemStackToInventory(new ItemStack(BucketNFluidHandler.BUCKETWOODMILK, 1));
		    	}
	    	}
    	}
    }
    
    //WORLD DATA
    
    @SubscribeEvent
	public void LoadPlayerList(WorldEvent.Load event) {
		if(!(event.getWorld().isRemote)) {
			WorldSaveData worldsave;
			worldsave = WorldSaveData.get(event.getWorld());
		}
	}
	
	@SubscribeEvent
	public void UnloadPlayerList(WorldEvent.Unload event) {
		if(!(event.getWorld().isRemote)) {
			WorldSaveData worldsave;
			worldsave = WorldSaveData.get(event.getWorld());
		}
	}
	
	@SubscribeEvent
	public void SavePlayerList(WorldEvent.Save event) {
		if(!(event.getWorld().isRemote)) {
			WorldSaveData worldsave;
			worldsave = WorldSaveData.get(event.getWorld());
		}
	}
}
