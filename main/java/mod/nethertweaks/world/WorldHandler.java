package mod.nethertweaks.world;
 
import mod.nethertweaks.Config;
import mod.nethertweaks.handler.BucketNFluidHandler;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter.PortalPosition;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
 
public class WorldHandler{
     
	public final static String key = "ntm.firstSpawn";
	public final static String coodX = "ntm.cood.x";
	public final static String coodY = "ntm.cood.y";
	public final static String coodZ = "ntm.cood.z";
    
	//HELLWORLD
	
    @SubscribeEvent
    public void respawn(PlayerEvent.PlayerRespawnEvent event) {
		EntityPlayer player = event.player;
    	
    	if(player.world.getWorldType() instanceof WorldTypeHellworld) {
    		teleportPlayer(player);
    		if (!WorldDataNTM.spawnLocas.containsKey(player.getUUID(player.getGameProfile())))
    		{
	    		BlockPos posplayer = player.getPosition();
	    		Iterable<BlockPos> posi = PortalPosition.getAllInBox(posplayer.down(64).east(64).south(64), posplayer.up(64).west(64).north(64));
	    		
	    		for(BlockPos pos : posi)
	    		{
	    			if (player.world.getBlockState(pos).getBlock() == Blocks.PORTAL)
	    			{
	    				player.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), player.rotationYaw, player.rotationPitch);
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
		teleportPlayer(player);	
	}
	
	private void teleportPlayer(EntityPlayer player2) {
		
		if(player2.dimension != -1)
		{
			player2.changeDimension(-1);
		}
	}
	
	//Enitity Interaction
    
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
