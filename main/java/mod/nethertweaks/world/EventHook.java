package mod.nethertweaks.world;

import static mod.nethertweaks.NetherTweaksMod.gsonInstance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.enchantments.EnchantmentEfficiency;
import mod.nethertweaks.enchantments.EnchantmentFortune;
import mod.nethertweaks.enchantments.EnchantmentLuckOfTheSea;
import mod.nethertweaks.entities.EntityItemLava;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.modules.thirst.GuiThirstBar;
import mod.nethertweaks.modules.thirst.ThirstStats;
import mod.nethertweaks.network.MessageMovementSpeed;
import mod.nethertweaks.network.bonfire.MessageBonfireGetList;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registry.types.Drinkable;
import mod.sfhcore.SFHCore;
import mod.sfhcore.handler.BucketHandler;
import mod.sfhcore.helper.BucketHelper;
import mod.sfhcore.helper.NotNull;
import mod.sfhcore.helper.PlayerInventory;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
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
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.DimensionManager;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

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
	public void registerModels(final ModelRegistryEvent event) {
		if(BlocksItems.enableBarrelStone)
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.STONE_BARREL, BlockHandler.STONE_BARREL.getRegistryName());
		if(BlocksItems.enableBarrelWood) {
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.OAK_BARREL, BlockHandler.OAK_BARREL.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.BIRCH_BARREL, BlockHandler.BIRCH_BARREL.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.SPRUCE_BARREL, BlockHandler.SPRUCE_BARREL.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.JUNGLE_BARREL, BlockHandler.JUNGLE_BARREL.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.ACACIA_BARREL, BlockHandler.ACACIA_BARREL.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.DARK_OAK_BARREL, BlockHandler.DARK_OAK_BARREL.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.ELDER_BARREL, BlockHandler.ELDER_BARREL.getRegistryName());
		}
		if(BlocksItems.enableSieve) {
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.STONE_SIEVE, BlockHandler.STONE_SIEVE.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.OAK_SIEVE, BlockHandler.OAK_SIEVE.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.BIRCH_SIEVE, BlockHandler.BIRCH_SIEVE.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.SPRUCE_SIEVE, BlockHandler.SPRUCE_SIEVE.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.JUNGLE_SIEVE, BlockHandler.JUNGLE_SIEVE.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.ACACIA_SIEVE, BlockHandler.ACACIA_SIEVE.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.DARK_OAK_SIEVE, BlockHandler.DARK_OAK_SIEVE.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.ELDER_SIEVE, BlockHandler.ELDER_SIEVE.getRegistryName());
		}
		if(BlocksItems.enableAshBonePile)		SFHCore.proxy.tryHandleBlockModel(BlockHandler.ASH_BONE_PILE, BlockHandler.ASH_BONE_PILE.getRegistryName());
		if(BlocksItems.enableFreezer)			SFHCore.proxy.tryHandleBlockModel(BlockHandler.FREEZER, BlockHandler.FREEZER.getRegistryName());
		if(BlocksItems.enableHellmart)			SFHCore.proxy.tryHandleBlockModel(BlockHandler.HELLMART, BlockHandler.HELLMART.getRegistryName());
		if(BlocksItems.enableCondenser) 		SFHCore.proxy.tryHandleBlockModel(BlockHandler.CONDENSER, BlockHandler.CONDENSER.getRegistryName());
		if(BlocksItems.enableNetherrackFurnace)	SFHCore.proxy.tryHandleBlockModel(BlockHandler.NETHERRACK_FURNACE, BlockHandler.NETHERRACK_FURNACE.getRegistryName());
		if(BlocksItems.enableCrucible) {
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.UNFIRED_CRUCIBLE, new ModelResourceLocation("unfired_" + BlockHandler.CRUCIBLE.getRegistryName()));
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.CRUCIBLE, BlockHandler.CRUCIBLE.getRegistryName());
		}

		if(BlocksItems.enableHellfayahOre) 		SFHCore.proxy.tryHandleBlockModel(BlockHandler.HELLFAYAH_ORE, BlockHandler.HELLFAYAH_ORE.getRegistryName());
		if(BlocksItems.enableHellfayahBlock) 	SFHCore.proxy.tryHandleBlockModel(BlockHandler.BLOCK_OF_HELLFAYAH, BlockHandler.BLOCK_OF_HELLFAYAH.getRegistryName());
		if(BlocksItems.enableSaltBlock) 		SFHCore.proxy.tryHandleBlockModel(BlockHandler.BLOCK_OF_SALT, BlockHandler.BLOCK_OF_SALT.getRegistryName());
		if(BlocksItems.enableDust) 				SFHCore.proxy.tryHandleBlockModel(BlockHandler.DUST, BlockHandler.DUST.getRegistryName());
		if(BlocksItems.enableStwH) 				SFHCore.proxy.tryHandleBlockModel(BlockHandler.STWH, BlockHandler.STWH.getRegistryName());
		if(BlocksItems.enableElderTree) {
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.ELDER_SAPLING, BlockHandler.ELDER_SAPLING.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.ELDER_LOG, BlockHandler.ELDER_LOG.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.ELDER_LEAVES, BlockHandler.ELDER_LEAVES.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.ELDER_PLANKS, BlockHandler.ELDER_PLANKS.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.ELDER_SLAB, BlockHandler.ELDER_SLAB.getRegistryName());
			SFHCore.proxy.tryHandleBlockModel(BlockHandler.ELDER_SLAB_DOUBLE, BlockHandler.ELDER_SLAB_DOUBLE.getRegistryName());
		}
		if(BlocksItems.enableNetherrackGravel)	SFHCore.proxy.tryHandleBlockModel(BlockHandler.NETHERRACK_GRAVEL, BlockHandler.NETHERRACK_GRAVEL.getRegistryName());
		if(BlocksItems.enableMeanVine) 			SFHCore.proxy.tryHandleBlockModel(BlockHandler.MEAN_VINE, BlockHandler.MEAN_VINE.getRegistryName());
		if(BlocksItems.enableStoneDoor) 		SFHCore.proxy.tryHandleBlockModel(BlockHandler.STONE_DOOR, BlockHandler.STONE_DOOR.getRegistryName());
		if(BlocksItems.enableElderDoor)			SFHCore.proxy.tryHandleBlockModel(BlockHandler.ELDER_DOOR, BlockHandler.ELDER_DOOR.getRegistryName());

		//Items
		if(BlocksItems.enableStoneBar)	  		SFHCore.proxy.tryHandleItemModel(ItemHandler.STONE_BAR, ItemHandler.STONE_BAR.getRegistryName());
		if(BlocksItems.enablePortalCore)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.PORTAL_CORE, ItemHandler.PORTAL_CORE.getRegistryName());
		if(BlocksItems.enableEndBox)		  	SFHCore.proxy.tryHandleItemModel(ItemHandler.END_BOX, ItemHandler.END_BOX.getRegistryName());
		if(BlocksItems.enableSalt)		  		SFHCore.proxy.tryHandleItemModel(ItemHandler.SALT, ItemHandler.SALT.getRegistryName());
		if(BlocksItems.enableHellfayah)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.HELLFAYAH, ItemHandler.HELLFAYAH.getRegistryName());
		if(BlocksItems.enableEnderInfusedFrame)	SFHCore.proxy.tryHandleItemModel(ItemHandler.ENDER_INFUSED_FRAME, ItemHandler.ENDER_INFUSED_FRAME.getRegistryName());
		if(BlocksItems.enableString)		  	SFHCore.proxy.tryHandleItemModel(ItemHandler.STRING, ItemHandler.STRING.getRegistryName());
		if(BlocksItems.enablePorcelainClay) 	SFHCore.proxy.tryHandleItemModel(ItemHandler.PORCELAIN_CLAY, ItemHandler.PORCELAIN_CLAY.getRegistryName());
		if(BlocksItems.enablePowderOfLight) 	SFHCore.proxy.tryHandleItemModel(ItemHandler.POWDER_OF_LIGHT, ItemHandler.POWDER_OF_LIGHT.getRegistryName());
		if(BlocksItems.enableAsh) 				SFHCore.proxy.tryHandleItemModel(ItemHandler.ASH, ItemHandler.ASH.getRegistryName());
		if(BlocksItems.enableWoodChippings)		SFHCore.proxy.tryHandleItemModel(ItemHandler.WOOD_CHIPPINGS, ItemHandler.WOOD_CHIPPINGS.getRegistryName());
		if(BlocksItems.enableCoiledSword)		SFHCore.proxy.tryHandleItemModel(ItemHandler.COILED_SWORD, ItemHandler.COILED_SWORD.getRegistryName());

		if(BlocksItems.enableMushroomSpores)	SFHCore.proxy.tryHandleItemModel(ItemHandler.MUSHROOM_SPORES, ItemHandler.MUSHROOM_SPORES.getRegistryName());
		if(BlocksItems.enableGrassSeeds)		SFHCore.proxy.tryHandleItemModel(ItemHandler.GRASS_SEEDS, ItemHandler.GRASS_SEEDS.getRegistryName());
		if(BlocksItems.enableCactusSeeds)		SFHCore.proxy.tryHandleItemModel(ItemHandler.CACTUS_SEEDS, ItemHandler.CACTUS_SEEDS.getRegistryName());
		if(BlocksItems.enableSugarcaneSeeds)	SFHCore.proxy.tryHandleItemModel(ItemHandler.SUGARCANE_SEEDS, ItemHandler.SUGARCANE_SEEDS.getRegistryName());

		if(BlocksItems.enableCrystalLight)		SFHCore.proxy.tryHandleItemModel(ItemHandler.CRYSTAL_OF_LIGHT, ItemHandler.CRYSTAL_OF_LIGHT.getRegistryName());

		if(BlocksItems.enablePebbleStone)		SFHCore.proxy.tryHandleItemModel(ItemHandler.PEBBLE_STONE, ItemHandler.PEBBLE_STONE.getRegistryName());
		if(BlocksItems.enablePebbleGranite)		SFHCore.proxy.tryHandleItemModel(ItemHandler.PEBBLE_GRANITE, ItemHandler.PEBBLE_GRANITE.getRegistryName());
		if(BlocksItems.enablePebbleDiorite)		SFHCore.proxy.tryHandleItemModel(ItemHandler.PEBBLE_DIORITE, ItemHandler.PEBBLE_DIORITE.getRegistryName());
		if(BlocksItems.enablePebbleAndesite)	SFHCore.proxy.tryHandleItemModel(ItemHandler.PEBBLE_ANDESITE, ItemHandler.PEBBLE_ANDESITE.getRegistryName());

		if(BlocksItems.enableStringMeshes)		SFHCore.proxy.tryHandleItemModel(ItemHandler.MESH_STRING, ItemHandler.MESH_STRING.getRegistryName());
		if(BlocksItems.enableFlintMeshes) 		SFHCore.proxy.tryHandleItemModel(ItemHandler.MESH_FLINT, ItemHandler.MESH_FLINT.getRegistryName());
		if(BlocksItems.enableIronMeshes)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.MESH_IRON, ItemHandler.MESH_IRON.getRegistryName());
		if(BlocksItems.enableDiamondMeshes)		SFHCore.proxy.tryHandleItemModel(ItemHandler.MESH_DIAMOND, ItemHandler.MESH_DIAMOND.getRegistryName());

		if(BlocksItems.enableDollBat)  			SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_BAT, ItemHandler.DOLL_BAT.getRegistryName());
		if(BlocksItems.enableDollChicken)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_CHICKEN, ItemHandler.DOLL_CHICKEN.getRegistryName());
		if(BlocksItems.enableDollCow)  			SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_COW, ItemHandler.DOLL_COW.getRegistryName());
		if(BlocksItems.enableDollDonkey)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_DONKEY, ItemHandler.DOLL_DONKEY.getRegistryName());
		if(BlocksItems.enableDollHorse)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_HORSE, ItemHandler.DOLL_HORSE.getRegistryName());
		if(BlocksItems.enableDollLlama)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_LLAMA, ItemHandler.DOLL_LLAMA.getRegistryName());
		if(BlocksItems.enableDollMule)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_MULE, ItemHandler.DOLL_MULE.getRegistryName());
		if(BlocksItems.enableDollOcelot)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_OCELOT, ItemHandler.DOLL_OCELOT.getRegistryName());
		if(BlocksItems.enableDollParrot)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_PARROT, ItemHandler.DOLL_PARROT.getRegistryName());
		if(BlocksItems.enableDollPig)  			SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_PIG, ItemHandler.DOLL_PIG.getRegistryName());
		if(BlocksItems.enableDollPolarBear)  	SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_POLAR_BEAR, ItemHandler.DOLL_POLAR_BEAR.getRegistryName());
		if(BlocksItems.enableDollRabbit)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_RABBIT, ItemHandler.DOLL_RABBIT.getRegistryName());
		if(BlocksItems.enableDollRedMooshroom)	SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_RED_MOOSHROOM, ItemHandler.DOLL_RED_MOOSHROOM.getRegistryName());
		if(BlocksItems.enableDollSheep)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_SHEEP, ItemHandler.DOLL_SHEEP.getRegistryName());
		if(BlocksItems.enableDollVillager)		SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_VILLAGER, ItemHandler.DOLL_VILLAGER.getRegistryName());
		if(BlocksItems.enableDollWolf)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.DOLL_WOLF, ItemHandler.DOLL_WOLF.getRegistryName());

		if(BlocksItems.enableGrabberWood)		SFHCore.proxy.tryHandleItemModel(ItemHandler.GRABBER_WOOD, ItemHandler.GRABBER_WOOD.getRegistryName());
		if(BlocksItems.enableGrabberGold)		SFHCore.proxy.tryHandleItemModel(ItemHandler.GRABBER_GOLD, ItemHandler.GRABBER_GOLD.getRegistryName());
		if(BlocksItems.enableGrabberStone)		SFHCore.proxy.tryHandleItemModel(ItemHandler.GRABBER_STONE, ItemHandler.GRABBER_STONE.getRegistryName());
		if(BlocksItems.enableGrabberIron)		SFHCore.proxy.tryHandleItemModel(ItemHandler.GRABBER_IRON, ItemHandler.GRABBER_IRON.getRegistryName());
		if(BlocksItems.enableGrabberDiamond)	SFHCore.proxy.tryHandleItemModel(ItemHandler.GRABBER_DIAMOND, ItemHandler.GRABBER_DIAMOND.getRegistryName());
		if(BlocksItems.enableFlintNBlaze)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.FLINT_N_BLAZE, ItemHandler.FLINT_N_BLAZE.getRegistryName());
		if(BlocksItems.enableHammerWood)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.HAMMER_WOOD, ItemHandler.HAMMER_WOOD.getRegistryName());
		if(BlocksItems.enableHammerGold)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.HAMMER_GOLD, ItemHandler.HAMMER_GOLD.getRegistryName());
		if(BlocksItems.enableHammerStone)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.HAMMER_STONE, ItemHandler.HAMMER_STONE.getRegistryName());
		if(BlocksItems.enableHammerIron)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.HAMMER_IRON, ItemHandler.HAMMER_IRON.getRegistryName());
		if(BlocksItems.enableHammerDiamond)  	SFHCore.proxy.tryHandleItemModel(ItemHandler.HAMMER_DIAMOND, ItemHandler.HAMMER_DIAMOND.getRegistryName());

		if(BlocksItems.enableElderTree)			SFHCore.proxy.tryHandleItemModel(ItemHandler.ITEM_ELDER_SLAB, ItemHandler.ITEM_ELDER_SLAB.getRegistryName());

		if(BlocksItems.enableJerky)  			SFHCore.proxy.tryHandleItemModel(ItemHandler.COOKED_JERKY, ItemHandler.COOKED_JERKY.getRegistryName());
		if(BlocksItems.enableCanteen)  			SFHCore.proxy.tryHandleItemModel(ItemHandler.CANTEEN, ItemHandler.CANTEEN.getRegistryName());

		if(BlocksItems.enableStoneDoor)  		SFHCore.proxy.tryHandleItemModel(ItemHandler.ITEM_STONE_DOOR, ItemHandler.ITEM_STONE_DOOR.getRegistryName());
		if(BlocksItems.enableElderDoor)			SFHCore.proxy.tryHandleItemModel(ItemHandler.ITEM_ELDER_DOOR, ItemHandler.ITEM_ELDER_DOOR.getRegistryName());
	}

	@SubscribeEvent
	public static void registerEnchantments(final IForgeRegistry<Enchantment> registry)
	{
		registry.register(new EnchantmentEfficiency());
		registry.register(new EnchantmentLuckOfTheSea());
		registry.register(new EnchantmentFortune());
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
