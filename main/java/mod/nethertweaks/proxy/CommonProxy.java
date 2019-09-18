package mod.nethertweaks.proxy;

import java.util.HashMap;
import java.util.UUID;

import mod.nethertweaks.Constants;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.capabilities.NTMCapabilities;
import mod.nethertweaks.compatibility.Compatibility;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.entities.NTMEntities;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.HammerHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.handler.JsonRecipeHandler;
import mod.nethertweaks.handler.OreHandler;
import mod.nethertweaks.handler.SmeltingNOreDictHandler;
import mod.nethertweaks.modules.thirst.ThirstStats;
import mod.nethertweaks.registries.manager.NTMDefaultRecipes;
import mod.nethertweaks.registries.registries.BarrelModeRegistry;
import mod.nethertweaks.world.EventHook;
import mod.nethertweaks.world.WorldGeneratorNTM;
import mod.sfhcore.util.LogUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public HashMap<UUID, ThirstStats> loadedPlayers = new HashMap<>();

	public void preInit()
	{
		LogUtil.setup(Constants.MODID, NetherTweaksMod.configDirectory);

		Config.init();

		Compatibility.init();

		NTMCapabilities.init();
		NTMEntities.init();

		ItemHandler.init();
		BlockHandler.init();
		BucketNFluidHandler.registerFluids();
		BucketNFluidHandler.registerBuckets();

		GameRegistry.registerWorldGenerator(new WorldGeneratorNTM(), 1);

		MinecraftForge.EVENT_BUS.register(new EventHook());
		MinecraftForge.EVENT_BUS.register(new HammerHandler());

		// Disable all copper ores except all ores from thermal foundation
		/*
    	OreHandler.disableOre("copper");
    	OreHandler.enableOre("thermalfoundation:ore");
		 */
	}

	public void init()
	{
		SmeltingNOreDictHandler.load();
	}

	public void postInit()
	{
		//Mobs
		if(Config.spawnWaterMobs) EventHook.addWaterMobs();

		OreHandler.registerFurnaceRecipe();
		BarrelModeRegistry.registerDefaults();
		NTMDefaultRecipes.registerDefaults();
		JsonRecipeHandler.loadJasonVorhees(NetherTweaksMod.configDirectory);
	}

	public ThirstStats getStatsByUUID(final UUID uuid) {
		ThirstStats stats = loadedPlayers.get(uuid);
		if (stats == null) {
			System.out.println("[Nether Tweaks Mod] Error: Attempted to access non-existent player with UUID: " + uuid);
			return null;
		}
		return stats;
	}

	public void registerPlayer(final EntityPlayer player, final ThirstStats stats) {
		UUID uuid = player.getUniqueID();
		if (loadedPlayers.containsKey(uuid))
			// Player already loaded from previous login session where the
			// server was not closed since the players last login.
			return;
		loadedPlayers.put(uuid, stats);
	}
}
