package mod.nethertweaks.world;

import static mod.nethertweaks.NetherTweaksMod.gsonInstance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileAshBonePile;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.blocks.tile.TileCondenser;
import mod.nethertweaks.blocks.tile.TileCrucibleStone;
import mod.nethertweaks.blocks.tile.TileFreezer;
import mod.nethertweaks.blocks.tile.TileHellmart;
import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import mod.nethertweaks.blocks.tile.TileSieve;
import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.entities.EntityItemLava;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.modules.thirst.GuiThirstBar;
import mod.nethertweaks.modules.thirst.ThirstStats;
import mod.nethertweaks.network.MessageMovementSpeed;
import mod.nethertweaks.network.bonfire.MessageBonfireGetList;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registry.types.Drinkable;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.helper.BucketHelper;
import mod.sfhcore.helper.NotNull;
import mod.sfhcore.helper.PlayerInventory;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.registries.Registry;
import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.CreateFluidSourceEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHook
{
	public final static String KEY = "ntm.firstSpawn";

	//HELLWORLD
	@SubscribeEvent
	public void createSalt(final PlayerInteractEvent.RightClickBlock event)
	{
		boolean activated = false;
		BlockPos pos = event.getPos();
		ItemStack heldItem = event.getItemStack();
		World world = event.getEntity().getEntityWorld();
		boolean vaporize = world.provider.doesWaterVaporize();
		FluidStack f = FluidUtil.getFluidContained(heldItem);

		if (world.isRemote || !Config.enableSaltRecipe || !vaporize || event.getEntity() == null
				|| !BucketHelper.isBucketWithFluidMaterial(heldItem, Material.WATER) || !f.getFluid().doesVaporize(f)) return;

		for(String fluidName : Config.blacklistSalt)
			if(f.getFluid().getName().equals(fluidName)) return;

		if (world.getBlockState(pos).getBlock().onBlockActivated(world, pos, world.getBlockState(pos), event.getEntityPlayer(), event.getHand(), event.getFace(), (float)event.getHitVec().x, (float)event.getHitVec().y, (float)event.getHitVec().z))
		{
			activated = true;
			event.setCanceled(true);
		}
		if (!activated)
		{
			pos.add(0.5D, 0.5D, 0.5D);

			switch (event.getFace())
			{
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
			EntityItem salt = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemHandler.SALT, 1));
			world.spawnEntity(salt);
		}
	}

	@SubscribeEvent
	public void noSource(final CreateFluidSourceEvent event)
	{
		BlockPos pos = event.getPos();
		IBlockState state = event.getState();
		World world = event.getWorld();

		if(!world.isRemote && Hellworld.isHellworld(world))
			if(state.getMaterial() == Material.WATER && !Config.waterSources)
				event.setResult(Result.DENY);
	}

	@SubscribeEvent
	public void respawn(final PlayerEvent.PlayerRespawnEvent event) {
		EntityPlayer player = event.player;
		int range = 32;

		if(Hellworld.isHellworld(player.world)) {
			teleportPlayer(player);
			if (!WorldSpawnLocation.lastSpawnLocations.containsKey(EntityPlayer.getUUID(player.getGameProfile())))
			{
				BlockPos posplayer = player.getPosition();
				int yDifferenz = 0;
				if (posplayer.getY() < range)
					yDifferenz = range - posplayer.getY();
				Iterable<BlockPos> posi = BlockPos.getAllInBox(posplayer.down(range - yDifferenz).east(range).south(range), posplayer.up(range + yDifferenz).west(range).north(range));

				for(BlockPos pos : posi)
					if (player.world.getBlockState(pos).getBlock() == Blocks.PORTAL)
						player.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
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
	public void changeToHomeDim(final PlayerEvent.PlayerChangedDimensionEvent event)
	{
		teleportPlayer(event.player);
	}

	@SubscribeEvent
	public void dropItem(final EntityJoinWorldEvent event)
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
	public void firstSpawn(final PlayerEvent.PlayerLoggedInEvent event)
	{
		teleportPlayer(event.player);
		NetworkHandler.INSTANCE.sendTo(new MessageBonfireGetList(WorldSpawnLocation.getLastSpawnLocations(), WorldSpawnLocation.getBonfireInfo()), (EntityPlayerMP) event.player);
	}

	//Enitity Interaction
	@SubscribeEvent
	public void getMilk(final PlayerInteractEvent.EntityInteract event)
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
	public void LoadPlayerList(final WorldEvent.Load event) {
		if(!event.getWorld().isRemote) {
			WorldSaveData worldsave;
			worldsave = WorldSaveData.get(event.getWorld());

			WorldSpawnLocation.setLastSpawnLocations(worldsave.getLastSpawnLocations());
			WorldSpawnLocation.setBonfireInfo(worldsave.getBonfireInfo());
		}

		if(event.getWorld().getWorldType() instanceof Hellworld)
		{
			DimensionManager.unregisterDimension(1);
			DimensionType.register("the_end", "_end", 1, WorldProviderEnd.class, true);
			DimensionManager.registerDimension(1, DimensionType.valueOf("the_end"));
		}
	}

	@SubscribeEvent
	public void UnloadPlayerList(final WorldEvent.Unload event) {
		if(!event.getWorld().isRemote) {
			WorldSaveData worldsave;
			worldsave = WorldSaveData.get(event.getWorld());

			worldsave.setLastSpawnLocations(WorldSpawnLocation.getLastSpawnLocations());
			worldsave.setBonfireInfo(WorldSpawnLocation.getBonfireInfo());
			worldsave.markDirty();
		}
	}

	@SubscribeEvent
	public void SavePlayerList(final WorldEvent.Save event) {
		if(!event.getWorld().isRemote) {
			WorldSaveData worldsave;
			worldsave = WorldSaveData.get(event.getWorld());

			worldsave.setLastSpawnLocations(WorldSpawnLocation.getLastSpawnLocations());
			worldsave.setBonfireInfo(WorldSpawnLocation.getBonfireInfo());
			worldsave.markDirty();
		}
	}

	//ThIRST

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderGameOverlayEvent(final RenderGameOverlayEvent event) {
		GuiThirstBar.onRenderGameOverlayEvent(event);
	}

	@SubscribeEvent
	public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
		if(!event.player.world.isRemote) {
			ThirstStats stats = NetherTweaksMod.getProxy().getStatsByUUID(event.player.getUniqueID());
			if(stats != null)
				stats.update(event.player);
		} else
			NetworkHandler.INSTANCE.sendToServer(new MessageMovementSpeed(event.player, NetherTweaksMod.getClientProxy().clientStats));
	}

	@SubscribeEvent
	public void onAttack(final AttackEntityEvent attack) {
		if (!attack.getEntityPlayer().world.isRemote) {
			ThirstStats stats = NetherTweaksMod.getProxy().getStatsByUUID(attack.getEntityPlayer().getUniqueID());
			stats.addExhaustion(0.5f);
		}
		attack.setResult(Result.DEFAULT);
	}

	@SubscribeEvent
	public void onHurt(final LivingHurtEvent hurt) {
		if (hurt.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) hurt.getEntity();
			if (!player.world.isRemote) {
				ThirstStats stats = NetherTweaksMod.getProxy().getStatsByUUID(player.getUniqueID());
				stats.addExhaustion(0.4f);
			}
		}
		hurt.setResult(Result.DEFAULT);
	}

	@SubscribeEvent
	public void onBlockBreak(final BlockEvent.BreakEvent event) {
		EntityPlayer player = event.getPlayer();
		if(player != null)
			if(!player.world.isRemote) {
				ThirstStats stats = NetherTweaksMod.getProxy().getStatsByUUID(player.getUniqueID());
				stats.addExhaustion(0.03f);
			}
		event.setResult(Result.DEFAULT);
	}

	public void playedCloned(final net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
		if(!event.getEntityPlayer().world.isRemote)
			if(event.isWasDeath()) {
				ThirstStats stats = NetherTweaksMod.getProxy().getStatsByUUID(event.getEntityPlayer().getUniqueID());
				stats.resetStats();
			}
	}

	@SubscribeEvent
	public void onLoadPlayerData(final net.minecraftforge.event.entity.player.PlayerEvent.LoadFromFile event) {
		if (!event.getEntityPlayer().world.isRemote) {
			EntityPlayer player = event.getEntityPlayer();
			File saveFile = event.getPlayerFile("nethertweaksmod");
			if(!saveFile.exists())
				NetherTweaksMod.getProxy().registerPlayer(player, new ThirstStats());
			else
				try {
					FileReader reader = new FileReader(saveFile);
					ThirstStats stats = gsonInstance.fromJson(reader, ThirstStats.class);
					if (stats == null)
						NetherTweaksMod.getProxy().registerPlayer(player, new ThirstStats());
					else
						NetherTweaksMod.getProxy().registerPlayer(player, stats);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	@SubscribeEvent
	public void onSavePlayerData(final net.minecraftforge.event.entity.player.PlayerEvent.SaveToFile event) {
		if (!event.getEntityPlayer().world.isRemote) {
			ThirstStats stats = NetherTweaksMod.getProxy().getStatsByUUID(event.getEntityPlayer().getUniqueID());
			File saveFile = new File(event.getPlayerDirectory(), event.getPlayerUUID() + ".nethertweaksmod");
			try {
				String write = gsonInstance.toJson(stats);
				saveFile.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
				writer.write(write);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SubscribeEvent
	public void onFinishUsingItem(final LivingEntityUseItemEvent.Finish event)
	{
		if (!Config.enableThirst) return;

		if (!event.getEntity().world.isRemote && event.getEntityLiving() instanceof EntityPlayer) {
			ItemStack eventItem = event.getItem();
			// have to increment count because if count == 0, then ItemAir is returned instead of the item that was just consumed.
			eventItem.setCount(eventItem.getCount() + 1);
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			if (NTMRegistryManager.DRINK_REGISTRY.containsItem(eventItem)) {
				Drinkable drink = NTMRegistryManager.DRINK_REGISTRY.getItem(eventItem);

				ThirstStats stats = NetherTweaksMod.getProxy().getStatsByUUID(player.getUniqueID());
				stats.addStats(drink.getThirstReplenish(), drink.getSaturationReplenish());
				stats.attemptToPoison(drink.getPoisonChance());
			}

			eventItem.setCount(eventItem.getCount() - 1);
		}
	}
	//*********************************************************************************************************************
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModels(ModelRegistryEvent event) {
    	if(BlocksItems.enableBarrelStone)		
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.STONE_BARREL), 0, new ModelResourceLocation(Constants.STONE_BARREL, "inventory"));
		if(BlocksItems.enableBarrelWood) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.OAK_BARREL), 0, new ModelResourceLocation(Constants.OAK_BARREL, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.BIRCH_BARREL), 0, new ModelResourceLocation(Constants.BIRCH_BARREL, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.SPRUCE_BARREL), 0, new ModelResourceLocation(Constants.SPRUCE_BARREL, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.JUNGLE_BARREL), 0, new ModelResourceLocation(Constants.JUNGLE_BARREL, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.ACACIA_BARREL), 0, new ModelResourceLocation(Constants.ACACIA_BARREL, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.DARK_OAK_BARREL), 0, new ModelResourceLocation(Constants.DARK_OAK_BARREL, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.ELDER_BARREL), 0, new ModelResourceLocation(Constants.ELDER_BARREL, "inventory"));
		}
		if(BlocksItems.enableSieve) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.STONE_SIEVE), 0, new ModelResourceLocation(Constants.STONE_SIEVE, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.OAK_SIEVE), 0, new ModelResourceLocation(Constants.OAK_SIEVE, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.BIRCH_SIEVE), 0, new ModelResourceLocation(Constants.BIRCH_SIEVE, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.SPRUCE_SIEVE), 0, new ModelResourceLocation(Constants.SPRUCE_SIEVE, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.JUNGLE_SIEVE), 0, new ModelResourceLocation(Constants.JUNGLE_SIEVE, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.ACACIA_SIEVE), 0, new ModelResourceLocation(Constants.ACACIA_SIEVE, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.DARK_OAK_SIEVE), 0, new ModelResourceLocation(Constants.DARK_OAK_SIEVE, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.ELDER_SIEVE), 0, new ModelResourceLocation(Constants.ELDER_SIEVE, "inventory"));
		}
		if(BlocksItems.enableAshBonePile)		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.ASH_BONE_PILE), 0, new ModelResourceLocation(Constants.ASH_BONE_PILE, "inventory"));
		if(BlocksItems.enableFreezer)			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.FREEZER), 0, new ModelResourceLocation(Constants.FREEZER, "inventory"));
		if(BlocksItems.enableHellmart)			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.HELLMART), 0, new ModelResourceLocation(Constants.HELLMART, "inventory"));
		if(BlocksItems.enableCondenser) 		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.CONDENSER), 0, new ModelResourceLocation(Constants.CONDENSER, "inventory"));
		if(BlocksItems.enableNetherrackFurnace)	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.NETHERRACK_FURNACE), 0, new ModelResourceLocation(Constants.NETHERRACK_FURNACE, "inventory"));
		if(BlocksItems.enableCrucible) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.UNFIRED_CRUCIBLE), 0, new ModelResourceLocation("unfired_" + Constants.CRUCIBLE, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.CRUCIBLE), 0, new ModelResourceLocation(Constants.CRUCIBLE, "inventory"));
		}
		
		if(BlocksItems.enableHellfayahOre) 		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.HELLFAYAH_ORE), 0, new ModelResourceLocation(Constants.HELLFAYAH_ORE, "inventory"));
		if(BlocksItems.enableHellfayahBlock) 	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.BLOCK_OF_HELLFAYAH), 0, new ModelResourceLocation(Constants.BLOCK_OF_HELLFAYAH, "inventory"));
		if(BlocksItems.enableSaltBlock) 		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.BLOCK_OF_SALT), 0, new ModelResourceLocation(Constants.BLOCK_OF_SALT, "inventory"));
		if(BlocksItems.enableDust) 				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.DUST), 0, new ModelResourceLocation(Constants.DUST, "inventory"));
		if(BlocksItems.enableStwH) 				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.STWH), 0, new ModelResourceLocation(Constants.STWH, "inventory"));
		if(BlocksItems.enableElderTree) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.ELDER_SAPLING), 0, new ModelResourceLocation(Constants.ELDER_SAPLING, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.ELDER_LOG), 0, new ModelResourceLocation(Constants.ELDER_LOG, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.ELDER_LEAVES), 0, new ModelResourceLocation(Constants.ELDER_LEAVES, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.ELDER_PLANKS), 0, new ModelResourceLocation(Constants.ELDER_PLANKS, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.ELDER_SLAB), 0, new ModelResourceLocation(Constants.ELDER_SLAB, "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.ELDER_SLAB_DOUBLE), 0, new ModelResourceLocation(Constants.ELDER_SLAB_DOUBLE, "inventory"));
		}
		if(BlocksItems.enableNetherrackGravel)	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.NETHERRACK_GRAVEL), 0, new ModelResourceLocation(Constants.NETHERRACK_GRAVEL, "inventory"));
		if(BlocksItems.enableMeanVine) 			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.MEAN_VINE), 0, new ModelResourceLocation(Constants.MEAN_VINE, "inventory"));
		if(BlocksItems.enableStoneDoor) 		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.STONE_DOOR), 0, new ModelResourceLocation(Constants.STONE_DOOR, "inventory"));
		if(BlocksItems.enableElderDoor)			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockHandler.ELDER_DOOR), 0, new ModelResourceLocation(Constants.ELDER_DOOR, "inventory"));
    }
	
	//*********************************************************************************************************************

	private void teleportPlayer(final EntityPlayer player) {

		if(player.dimension == 0)
		{
			if(!Hellworld.isHellworld(player.world)) return;
			if(!player.getEntityData().hasKey(KEY) || !player.getEntityData().getBoolean(KEY))
			{
				player.setPortal(player.getPosition());
				player.getEntityData().setBoolean(KEY, true);
			}
			player.changeDimension(-1);
		}
	}

	public static void addWaterMobs()
	{
		EntityRegistry.addSpawn(EntitySquid.class, 25, 1, 10, EnumCreatureType.WATER_CREATURE, BiomeDictionary.getBiomes(Type.NETHER).toArray(new Biome[0]));
	}
}
