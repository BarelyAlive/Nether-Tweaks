package mod.nethertweaks.world;

import mod.nethertweaks.config.Config;
import mod.nethertweaks.entities.EntityItemLava;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.network.bonfire.MessageBonfireGetList;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.helper.BucketHelper;
import mod.sfhcore.helper.NotNull;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.helper.PlayerInventory;
import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class WorldEvents
{
	public final static String key = "ntm.firstSpawn";

	//HELLWORLD
	@SubscribeEvent
    public void createSalt(PlayerInteractEvent.RightClickBlock event)
	{
		boolean activated = false;
		BlockPos clicked = event.getPos();
		ItemStack heldItem = event.getItemStack();
		World world = event.getEntity().getEntityWorld();
		boolean vaporize = world.provider.doesWaterVaporize();
		FluidStack f = FluidUtil.getFluidContained(heldItem);
		
		if (world.isRemote || !Config.enableSaltRecipe || !vaporize || event.getEntity() == null
				|| !BucketHelper.isBucketWithFluidMaterial(heldItem, Material.WATER) || !f.getFluid().doesVaporize(f)) return;
		
		for(String fluidName : Config.blacklistSalt)
		{
			if(f.getFluid() == FluidRegistry.getFluid(fluidName)) return;
		}
		
		if (world.getBlockState(clicked).getBlock().onBlockActivated(world, clicked, world.getBlockState(clicked), event.getEntityPlayer(), event.getHand(), event.getFace(), (float)event.getHitVec().x, (float)event.getHitVec().y, (float)event.getHitVec().z))
		{
			activated = true;
			event.setCanceled(true);
		}
		if (!activated)
		{
			BlockPos pos =  new BlockPos(clicked.getX()+0.5D, clicked.getY()+0.5D, clicked.getZ()+0.5D);
			
			switch (event.getFace()) {
				case UP:
					pos = pos.up();
					break;
				case NORTH:
					pos = pos.north();
					break;
				case EAST:
					pos = pos.east();
					break;
				case SOUTH:
					pos = pos.south();
					break;
				case WEST:
					pos = pos.west();
					break;
				case DOWN:
					pos = pos.down();
					break;
			}
			EntityItem salt = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemHandler.SALT, 2));
			world.spawnEntity(salt);
		}
    }

    @SubscribeEvent
    public void respawn(PlayerEvent.PlayerRespawnEvent event) {
		EntityPlayer player = event.player;
		int range = 32;

    	if(player.world.getWorldType() instanceof WorldTypeHellworld) {
    		teleportPlayer(player);
    		if (!WorldSpawnLocation.lastSpawnLocations.containsKey(EntityPlayer.getUUID(player.getGameProfile())))
    		{
	    		BlockPos posplayer = player.getPosition();
	    		int yDifferenz = 0;
	    		if (posplayer.getY() < range)
	    		{
	    			yDifferenz = range - posplayer.getY();
	    		}
	    		Iterable<BlockPos> posi = BlockPos.getAllInBox(posplayer.down(range - yDifferenz).east(range).south(range), posplayer.up(range + yDifferenz).west(range).north(range));

	    		for(BlockPos pos : posi)
	    		{
	    			if (player.world.getBlockState(pos).getBlock() == Blocks.PORTAL)
	    			{
	    				player.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
	    			}
	    		}
    		}
    		else
    		{
    			PlayerPosition pos = WorldSpawnLocation.lastSpawnLocations.get(EntityPlayer.getUUID(player.getGameProfile()));
				player.setPositionAndUpdate(pos.getPos().getX() + 0.5, pos.getPos().getY(), pos.getPos().getZ() + 0.5);
				player.setPositionAndRotation(pos.getPos().getX() + 0.5, pos.getPos().getY(), pos.getPos().getZ() + 0.5, pos.getYaw(), pos.getAng());
    		}
		}
    }

    @SubscribeEvent
	public void changeToHomeDim(PlayerEvent.PlayerChangedDimensionEvent event)
    {
    	teleportPlayer(event.player);
	}

    @SubscribeEvent
    public void dropItem(EntityJoinWorldEvent event)
    {
    	if(event.getEntity().dimension != -1) return;
    	if (event.getEntity() instanceof EntityItemLava) return;

    	if (event.getEntity() instanceof EntityItem)
    	{
    		EntityItem item = (EntityItem) event.getEntity();
    		if(item.getItem().getItem() == Items.IRON_SWORD)
    		{
        		event.setCanceled(false);
        		EntityItemLava new_item = new EntityItemLava(
        				item.getEntityWorld(),
        				item.getPosition().getX(),
        				item.getPosition().getY(),
        				item.getPosition().getZ(),
        				item.getItem()
        			);
        		new_item.copyLocationAndAnglesFrom(item);
        		new_item.readFromNBT(item.writeToNBT(new NBTTagCompound()));
        		item.world.spawnEntity(new_item);
        		item.lifespan = 1;
    		}
    	}
    }

	@SubscribeEvent
	public void firstSpawn(PlayerEvent.PlayerLoggedInEvent event)
	{
		teleportPlayer(event.player);
		NetworkHandler.INSTANCE.sendTo(new MessageBonfireGetList(WorldSpawnLocation.getLastSpawnLocations(), WorldSpawnLocation.getBonfireInfo()), (EntityPlayerMP) event.player);
	}

	//Enitity Interaction
    @SubscribeEvent
    public void getMilk(PlayerInteractEvent.EntityInteract event)
    {
    	if(event.getTarget() instanceof EntityCow)
    	{
    		if(!NotNull.checkNotNull(event.getItemStack())) return;
			
    		ItemStack stack = event.getItemStack();
    		Item item = stack.getItem();
    		EntityPlayer player = event.getEntityPlayer();

	    	if(item == BucketHandler.getBucketFromFluid(null, "wood"))
	    	{
	    		stack.shrink(1);
	    		PlayerInventory.tryAddItem(player, new ItemStack(BucketHandler.getBucketFromFluid(FluidRegistry.getFluid("milk"), "wood")));
	    	}
	    	else if(item == BucketHandler.getBucketFromFluid(null, "stone"))
	    	{
	    		stack.shrink(1);
	    		PlayerInventory.tryAddItem(player, new ItemStack(BucketHandler.getBucketFromFluid(FluidRegistry.getFluid("milk"), "stone")));
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
    		DimensionManager.unregisterDimension(1);
    		DimensionType.register("the_end", "_end", 1, WorldProviderEnd.class, true);
    		DimensionManager.registerDimension(1, DimensionType.valueOf("the_end"));
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
