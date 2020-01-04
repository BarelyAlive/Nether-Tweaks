package mod.nethertweaks.world;

import static mod.nethertweaks.NetherTweaksMod.GSON_INSTANCE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import mod.nethertweaks.config.Config;
import mod.nethertweaks.enchantments.EnchantmentEfficiency;
import mod.nethertweaks.enchantments.EnchantmentFortune;
import mod.nethertweaks.enchantments.EnchantmentLuckOfTheSea;
import mod.nethertweaks.entities.EntityItemLava;
import mod.nethertweaks.init.HammerHandler;
import mod.nethertweaks.init.ModBlocks;
import mod.nethertweaks.init.ModItems;
import mod.nethertweaks.init.ModOreRegistration;
import mod.nethertweaks.modules.thirst.GuiThirstBar;
import mod.nethertweaks.modules.thirst.ThirstStats;
import mod.nethertweaks.network.MessageMovementSpeed;
import mod.nethertweaks.network.bonfire.MessageBonfireGetList;
import mod.nethertweaks.proxy.ClientProxy;
import mod.nethertweaks.proxy.ServerProxy;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.nethertweaks.registry.registries.base.types.Drinkable;
import mod.sfhcore.helper.BucketHelper;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.CreateFluidSourceEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHook
{
	public final static String KEY = "ntm.firstSpawn";

	public static void preInit()
	{
		MinecraftForge.EVENT_BUS.register(new ModBlocks());
		MinecraftForge.EVENT_BUS.register(new ModItems());
		MinecraftForge.EVENT_BUS.register(new EventHook());
		MinecraftForge.EVENT_BUS.register(new ModOreRegistration());
		MinecraftForge.EVENT_BUS.register(new HammerHandler());
	}

	public static void postInit()
	{
		addWaterMobs();
	}

	//HELLWORLD
	@SubscribeEvent
	public void createSalt(final PlayerInteractEvent.RightClickBlock event)
	{
		BlockPos pos = event.getPos();
		final World world = event.getWorld();
		final IBlockState state = world.getBlockState(pos);
		final boolean vaporize = world.provider.doesWaterVaporize();
		final FluidStack f = FluidUtil.getFluidContained(event.getItemStack());

		if (!Config.enableSaltRecipe || !vaporize || event.getEntityPlayer().isSneaking()
				|| !BucketHelper.isBucketWithFluidMaterial(event.getItemStack(), Material.WATER) || !f.getFluid().doesVaporize(f)) return;

		for(final String fluidName : Config.blacklistSalt)
			if(f.getFluid().getName().equals(fluidName)) return;

		if (state.getBlock().onBlockActivated(world, pos, state, event.getEntityPlayer(), event.getHand(), event.getFace(), (float)event.getHitVec().x, (float)event.getHitVec().y, (float)event.getHitVec().z))
			event.setCanceled(true);
		else if(!world.isRemote)
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
			final EntityItem salt = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.SALT, 1));
			world.spawnEntity(salt);
		}
	}

	@SubscribeEvent
	public void noSource(final CreateFluidSourceEvent event)
	{
		final IBlockState state = event.getState();
		final World world = event.getWorld();

		if(!world.isRemote && Hellworld.isHellworld(world))
			if(state.getMaterial() == Material.WATER && !Config.waterSources)
				event.setResult(Result.DENY);
	}

	@SubscribeEvent
	public void respawn(final PlayerEvent.PlayerRespawnEvent event) {
		final EntityPlayer player = event.player;
		final int range = 32;

		if(Hellworld.isHellworld(player.world)) {
			teleportPlayer(player);
			if (!WorldSpawnLocation.getLastSpawnLocations().containsKey(EntityPlayer.getUUID(player.getGameProfile())))
			{
				final BlockPos posplayer = player.getPosition();
				int yDifferenz = 0;
				if (posplayer.getY() < range)
					yDifferenz = range - posplayer.getY();
				final Iterable<BlockPos> posi = BlockPos.getAllInBox(posplayer.down(range - yDifferenz).east(range).south(range), posplayer.up(range + yDifferenz).west(range).north(range));

				for(final BlockPos pos : posi)
					if (player.world.getBlockState(pos).getBlock() == Blocks.PORTAL)
						player.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
			}
			else
			{
				final PlayerPosition pos = WorldSpawnLocation.getLastSpawnLocations().get(EntityPlayer.getUUID(player.getGameProfile()));
				player.setPositionAndUpdate(pos.getPos().getX() + 0.5, pos.getPos().getY(), pos.getPos().getZ() + 0.5);
				player.setPositionAndRotation(pos.getPos().getX() + 0.5, pos.getPos().getY(), pos.getPos().getZ() + 0.5, pos.getYaw(), pos.getAng());
			}
		}
	}

	@SubscribeEvent
	public void dropItem(final EntityJoinWorldEvent event)
	{
		if(event.getEntity().dimension != -1) return;
		if (event.getEntity() instanceof EntityItemLava) return;

		if (event.getEntity() instanceof EntityItem)
		{
			final EntityItem item = (EntityItem) event.getEntity();
			if(item.getItem().getItem() == Items.IRON_SWORD)
			{
				event.setCanceled(false);
				final EntityItemLava new_item = new EntityItemLava(
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

	//HELLWORLD

	@SubscribeEvent
	public void firstSpawn(final PlayerEvent.PlayerLoggedInEvent event)
	{
		teleportPlayer(event.player);
		NetworkHandler.INSTANCE.sendTo(new MessageBonfireGetList(WorldSpawnLocation.getLastSpawnLocations(), WorldSpawnLocation.getBonfireInfo()), (EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public void changeToHomeDim(final PlayerEvent.PlayerChangedDimensionEvent event)
	{
		teleportPlayer(event.player);
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
			final ThirstStats stats = ServerProxy.getStatsByUUID(event.player.getUniqueID());
			if(stats != null)
				stats.update(event.player);
		} else
			NetworkHandler.INSTANCE.sendToServer(new MessageMovementSpeed(event.player, ClientProxy.CLIENT_STATS));
	}

	@SubscribeEvent
	public void onAttack(final AttackEntityEvent attack) {
		if (!attack.getEntityPlayer().world.isRemote) {
			final ThirstStats stats = ServerProxy.getStatsByUUID(attack.getEntityPlayer().getUniqueID());
			stats.addExhaustion(0.5f);
		}
		attack.setResult(Result.DEFAULT);
	}

	@SubscribeEvent
	public void onHurt(final LivingHurtEvent hurt) {
		if (hurt.getEntity() instanceof EntityPlayer) {
			final EntityPlayer player = (EntityPlayer) hurt.getEntity();
			if (!player.world.isRemote) {
				final ThirstStats stats = ServerProxy.getStatsByUUID(player.getUniqueID());
				stats.addExhaustion(0.4f);
			}
		}
		hurt.setResult(Result.DEFAULT);
	}

	@SubscribeEvent
	public void onBlockBreak(final BlockEvent.BreakEvent event) {
		final EntityPlayer player = event.getPlayer();
		if(player != null)
			if(!player.world.isRemote) {
				final ThirstStats stats = ServerProxy.getStatsByUUID(player.getUniqueID());
				stats.addExhaustion(0.03f);
			}
		event.setResult(Result.DEFAULT);
	}

	public void playedCloned(final net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
		if(!event.getEntityPlayer().world.isRemote)
			if(event.isWasDeath()) {
				final ThirstStats stats = ServerProxy.getStatsByUUID(event.getEntityPlayer().getUniqueID());
				stats.resetStats();
			}
	}

	@SubscribeEvent
	public void onLoadPlayerData(final net.minecraftforge.event.entity.player.PlayerEvent.LoadFromFile event) {
		if (!event.getEntityPlayer().world.isRemote) {
			final EntityPlayer player = event.getEntityPlayer();
			final File saveFile = event.getPlayerFile("nethertweaksmod");
			if(!saveFile.exists())
				ServerProxy.registerPlayer(player, new ThirstStats());
			else
				try {
					final FileReader reader = new FileReader(saveFile);
					final ThirstStats stats = GSON_INSTANCE.fromJson(reader, ThirstStats.class);
					if (stats == null)
						ServerProxy.registerPlayer(player, new ThirstStats());
					else
						ServerProxy.registerPlayer(player, stats);
				} catch (final IOException e) {
					e.printStackTrace();
				}
		}
	}

	@SubscribeEvent
	public void onSavePlayerData(final net.minecraftforge.event.entity.player.PlayerEvent.SaveToFile event) {
		if (!event.getEntityPlayer().world.isRemote) {
			final ThirstStats stats = ServerProxy.getStatsByUUID(event.getEntityPlayer().getUniqueID());
			final File saveFile = new File(event.getPlayerDirectory(), event.getPlayerUUID() + ".nethertweaksmod");
			try {
				final String write = GSON_INSTANCE.toJson(stats);
				saveFile.createNewFile();
				final BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
				writer.write(write);
				writer.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SubscribeEvent
	public void onFinishUsingItem(final LivingEntityUseItemEvent.Finish event)
	{
		if (!Config.enableThirst) return;

		if (!event.getEntity().world.isRemote && event.getEntityLiving() instanceof EntityPlayer) {
			final ItemStack eventItem = event.getItem();
			// have to increment count because if count == 0, then ItemAir is returned instead of the item that was just consumed.
			eventItem.setCount(eventItem.getCount() + 1);
			final EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			if (NTMRegistryManager.DRINK_REGISTRY.containsItem(eventItem)) {
				final Drinkable drink = NTMRegistryManager.DRINK_REGISTRY.getItem(eventItem);

				final ThirstStats stats = ServerProxy.getStatsByUUID(player.getUniqueID());
				stats.addStats(drink.getThirstReplenish(), drink.getSaturationReplenish());
				stats.attemptToPoison(drink.getPoisonChance());
			}

			eventItem.setCount(eventItem.getCount() - 1);
		}
	}
	//*********************************************************************************************************************

	@SubscribeEvent
	public void registerEnchantments(final RegistryEvent.Register<Enchantment> event)
	{
		event.getRegistry().register(new EnchantmentEfficiency());
		event.getRegistry().register(new EnchantmentLuckOfTheSea());
		event.getRegistry().register(new EnchantmentFortune());
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

	private static void addWaterMobs()
	{
		if(Config.spawnWaterMobs)
			EntityRegistry.addSpawn(EntitySquid.class, 25, 1, 10, EnumCreatureType.WATER_CREATURE, BiomeDictionary.getBiomes(Type.NETHER).toArray(new Biome[0]));
	}
}
