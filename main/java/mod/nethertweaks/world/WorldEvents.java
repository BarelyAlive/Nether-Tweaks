package mod.nethertweaks.world;

import mod.nethertweaks.config.Config;
import mod.nethertweaks.handler.ItemHandler;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.helper.BucketHelper;
import mod.sfhcore.helper.NotNull;
import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter.PortalPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class WorldEvents
{
	public final static String key = "ntm.firstSpawn";
	public final static String coodX = "ntm.cood.x";
	public final static String coodY = "ntm.cood.y";
	public final static String coodZ = "ntm.cood.z";
	public static boolean is_hellworld = false;

	//HELLWORLD
	
	@SubscribeEvent
    public void salt(PlayerInteractEvent.RightClickBlock event)
	{
		boolean activated = false;
		BlockPos clicked = event.getPos();
		ItemStack heldItem = event.getItemStack();
		World world = event.getEntity().getEntityWorld();
		boolean vaporize = world.provider.doesWaterVaporize();
		
		if (world.isRemote || !Config.enableSaltRecipe || !vaporize || event.getEntity() == null) return;
		if (!BucketHelper.isBucketWithFluidMaterial(heldItem, Material.WATER)) return;
		if (world.getBlockState(clicked).getBlock().onBlockActivated(world, clicked, world.getBlockState(clicked), event.getEntityPlayer(), event.getHand(), event.getFace(), (float)event.getHitVec().x, (float)event.getHitVec().y, (float)event.getHitVec().z))
		{
			activated = true;
			event.setCanceled(true);
		}
		if (!activated)
		{
			EntityItem salt = new EntityItem(world, clicked.getX(), clicked.getY() + 1.0d, clicked.getZ(), new ItemStack(ItemHandler.SALT, 2));
			switch (event.getFace()) {
			case UP:
				salt.getPosition().up();
				break;
			case NORTH:
				salt.getPosition().north();
				break;
			case EAST:
				salt.getPosition().east();
				break;
			case SOUTH:
				salt.getPosition().south();
				break;
			case WEST:
				salt.getPosition().west();
				break;
			case DOWN:
				salt.getPosition().down();
				break;
			}
			world.spawnEntity(salt);
		}
    }

    @SubscribeEvent
    public void respawn(PlayerEvent.PlayerRespawnEvent event) {
		EntityPlayer player = event.player;
		int range = 32;

    	if(player.world.getWorldType() instanceof WorldTypeHellworld) {
    		is_hellworld = true;
    		teleportPlayer(player);
    		if (!WorldSpawnLocation.lastSpawnLocations.containsKey(player.getUUID(player.getGameProfile())))
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
	    			}
	    		}
    		}
    		else
    		{
    			PlayerPosition pos = WorldSpawnLocation.lastSpawnLocations.get(player.getUUID(player.getGameProfile()));
				player.setPositionAndUpdate(pos.getPos().getX() + 0.5, pos.getPos().getY(), pos.getPos().getZ() + 0.5);
				player.setPositionAndRotation(pos.getPos().getX() + 0.5, pos.getPos().getY(), pos.getPos().getZ() + 0.5, pos.getYaw(), pos.getAng());
    		}
		}
    	else
    	{
    		is_hellworld = false;
    	}
    }

    @SubscribeEvent
	public void changeToHomeDim(PlayerEvent.PlayerChangedDimensionEvent event)
    {
    	teleportPlayer(event.player);
	}

	@SubscribeEvent
	public void firstSpawn(PlayerEvent.PlayerLoggedInEvent event)
	{
		teleportPlayer(event.player);
	}

	//Enitity Interaction
    @SubscribeEvent
    public void getMilk(net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract event)
    {
    	if(event.getTarget() instanceof EntityCow)
    	{
    		if(! NotNull.checkNotNull(event.getItemStack()))
    			return;
    		
    		ItemStack stack = event.getItemStack();
    		Item item = stack.getItem();
    		EntityPlayer player = event.getEntityPlayer();
    		
	    	if(item == BucketHandler.getBucketFromFluid(null, "wood"))
	    	{
	    		stack.shrink(1);
	    		player.addItemStackToInventory(new ItemStack(BucketHandler.getBucketFromFluid(FluidRegistry.getFluid("milk"), "wood")));
	    	}
	    	else if(item == BucketHandler.getBucketFromFluid(null, "stone"))
	    	{
	    		stack.shrink(1);
	    		player.addItemStackToInventory(new ItemStack(BucketHandler.getBucketFromFluid(FluidRegistry.getFluid("milk"), "stone")));
	    	}
    	}
    }

    //WORLD DATA
    
    @SubscribeEvent
	public void LoadPlayerList(WorldEvent.Load event) {
		if(!(event.getWorld().isRemote)) {
			WorldSaveData worldsave;
			worldsave = WorldSaveData.get(event.getWorld());
			
			WorldSpawnLocation.setLastSpawnLocations(worldsave.getLastSpawnLocations());
			WorldSpawnLocation.setBonfireInfo(worldsave.getBonfireInfo());
		}
		
    	if(event.getWorld().getWorldType() instanceof WorldTypeHellworld)
    	{
    		is_hellworld = true;
    		DimensionManager.unregisterDimension(1);
    		DimensionType.register("the_end", "_end", 1, WorldProviderEnd.class, true);
    		DimensionManager.registerDimension(1, DimensionType.valueOf("the_end"));
    	}
    	else
    	{
    		is_hellworld = false;
    	}
	}

	@SubscribeEvent
	public void UnloadPlayerList(WorldEvent.Unload event) {
		if(!(event.getWorld().isRemote)) {
			WorldSaveData worldsave;
			worldsave = WorldSaveData.get(event.getWorld());
			
			worldsave.setLastSpawnLocations(WorldSpawnLocation.getLastSpawnLocations());
			worldsave.setBonfireInfo(WorldSpawnLocation.getBonfireInfo());
			worldsave.markDirty();
		}
	}

	@SubscribeEvent
	public void SavePlayerList(WorldEvent.Save event) {
		if(!(event.getWorld().isRemote)) {
			WorldSaveData worldsave;
			worldsave = WorldSaveData.get(event.getWorld());
			
			worldsave.setLastSpawnLocations(WorldSpawnLocation.getLastSpawnLocations());
			worldsave.setBonfireInfo(WorldSpawnLocation.getBonfireInfo());
			worldsave.markDirty();
		}
	}
	//*********************************************************************************************************************

	private void teleportPlayer(EntityPlayer player) {

		if(player.dimension == 0)
		{
			if(!(player.world.getWorldType() instanceof WorldTypeHellworld)) return;
			if(!player.getEntityData().hasKey(key) || !player.getEntityData().getBoolean(key))
			{
				player.setPortal(player.getPosition());
				player.getEntityData().setBoolean(key, true);
			}
			player.changeDimension(-1);
		}
	}

	public static void addWaterMobs()
	{
		EntityRegistry.addSpawn(EntitySquid.class, 25, 1, 10, EnumCreatureType.WATER_CREATURE, BiomeDictionary.getBiomes(Type.NETHER).toArray(new Biome[0]));
	}
}
