package mod.nethertweaks.world;

import com.ibm.icu.impl.Differ;

import java.util.*;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.helper.NotNull;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Teleporter.PortalPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeHell;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class WorldHandler{

	public final static String key = "ntm.firstSpawn";
	public final static String coodX = "ntm.cood.x";
	public final static String coodY = "ntm.cood.y";
	public final static String coodZ = "ntm.cood.z";

	//HELLWORLD
	
	@SubscribeEvent
    public void salt(net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock event)
	{
		if (!event.getEntity().getEntityWorld().isRemote)
		{
			final ItemInfo info = new ItemInfo(event.getItemStack());
			final ItemInfo bucket1 = new ItemInfo(Items.WATER_BUCKET);
			final ItemInfo bucket2 = new ItemInfo(BucketHandler.getBucketFromFluid(FluidRegistry.WATER, "wood"));
			final ItemInfo bucket3 = new ItemInfo(BucketHandler.getBucketFromFluid(FluidRegistry.WATER, "stone"));
			
			if (ItemStack.areItemsEqual(info.getItemStack(), bucket1.getItemStack()) ||
					ItemStack.areItemsEqual(info.getItemStack(), bucket2.getItemStack()) ||
					ItemStack.areItemsEqual(info.getItemStack(), bucket3.getItemStack()))
			{
				if (event.getEntity() instanceof EntityPlayer && event.getEntity().dimension == -1)
				{
					BlockPos clicked = event.getPos();
					World world = event.getEntity().getEntityWorld();
					EntityItem salt = new EntityItem(world, clicked.getX(), clicked.getY()+1, clicked.getZ(),
							new ItemStack(ItemHandler.ITEM_BASE, 2, 5));
					world.spawnEntity(salt);
				}
			} 
		}
    }

    @SubscribeEvent
    public void respawn(PlayerEvent.PlayerRespawnEvent event) {
		EntityPlayer player = event.player;
		int range = 32;

    	if(player.world.getWorldType() instanceof WorldTypeHellworld) {
    		teleportPlayer(player);
    		if (!WorldSpawnLoc.spawnLocas.containsKey(player.getUUID(player.getGameProfile())))
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
    			PlayerPosition pos = WorldSpawnLoc.spawnLocas.get(player.getUUID(player.getGameProfile()));
				player.setPositionAndUpdate(pos.getPos().getX() + 0.5, pos.getPos().getY(), pos.getPos().getZ() + 0.5);
				player.setPositionAndRotation(pos.getPos().getX() + 0.5, pos.getPos().getY(), pos.getPos().getZ() + 0.5, pos.getYaw(), pos.getAng());
    		}
		}
    }

    @SubscribeEvent
	public void changeToHomeDim(PlayerEvent.PlayerChangedDimensionEvent event)
    {
    	EntityPlayer player = event.player;

    	if(player.dimension != -1) teleportPlayer(player);
	}

	@SubscribeEvent
	public void firstSpawn(PlayerEvent.PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;

		if(player.dimension != -1) teleportPlayer(player);
	}

	//Enitity Interaction
    @SubscribeEvent
    public void getMilk(net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract event)
    {
    	//if(!event.getWorld().isRemote)
    	//{
    		//Had to use ID instead of "instanceof EntityCow" because it could cause problems with MooFluids etc.
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
    	//}
    }

    //WORLD DATA

    @SubscribeEvent
	public void LoadPlayerList(WorldEvent.Load event) {
		if(!(event.getWorld().isRemote)) {
			WorldSaveData worldsave;
			worldsave = WorldSaveData.get(event.getWorld());
			
			WorldSpawnLoc.setSpawnLocations(worldsave.getSpawnLocations());
		}
	}

	@SubscribeEvent
	public void UnloadPlayerList(WorldEvent.Unload event) {
		if(!(event.getWorld().isRemote)) {
			WorldSaveData worldsave;
			worldsave = WorldSaveData.get(event.getWorld());
			
			worldsave.setSpawnLocations(WorldSpawnLoc.getSpawnLocations());
			worldsave.markDirty();
		}
	}

	@SubscribeEvent
	public void SavePlayerList(WorldEvent.Save event) {
		if(!(event.getWorld().isRemote)) {
			WorldSaveData worldsave;
			worldsave = WorldSaveData.get(event.getWorld());
			
			worldsave.setSpawnLocations(WorldSpawnLoc.getSpawnLocations());
			worldsave.markDirty();
		}
	}
	//*********************************************************************************************************************

	private void teleportPlayer(EntityPlayer player) {

		if(player.dimension != -1)
		{
			if(!(player.world.getWorldType() instanceof WorldTypeHellworld)) return;
			if(!player.getEntityData().hasKey(key) || !player.getEntityData().getBoolean(key)){
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
