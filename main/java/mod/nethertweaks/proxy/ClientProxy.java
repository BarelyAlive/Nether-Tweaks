package mod.nethertweaks.proxy;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileAshBonePile;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.blocks.tile.TileCrucibleStone;
import mod.nethertweaks.blocks.tile.TileSieve;
import mod.nethertweaks.client.renderers.RenderAshBonePile;
import mod.nethertweaks.client.renderers.RenderBarrel;
import mod.nethertweaks.client.renderers.RenderCrucible;
import mod.nethertweaks.client.renderers.RenderProjectileStone;
import mod.nethertweaks.client.renderers.RenderSieve;
import mod.nethertweaks.entities.ProjectileStone;
import mod.nethertweaks.handler.GuiHandler;
import mod.nethertweaks.handler.OreHandler;
import mod.nethertweaks.modules.thirst.ThirstStats;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {

	public ThirstStats clientStats = new ThirstStats();

	@Override
	public void preInit() {
		super.preInit();

		//TESR
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrucibleStone.class, new RenderCrucible());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSieve.class, new RenderSieve());
		ClientRegistry.bindTileEntitySpecialRenderer(TileBarrel.class, new RenderBarrel());
		ClientRegistry.bindTileEntitySpecialRenderer(TileAshBonePile.class, new RenderAshBonePile());

		//Ores
		OreHandler.disableOre("minecraft:redstone");
		OreHandler.disableOre("minecraft:coal");

		//Graphics
		RenderingRegistry.registerEntityRenderingHandler(ProjectileStone.class, new RenderProjectileStone.Factory());
		NetworkRegistry.INSTANCE.registerGuiHandler(NetherTweaksMod.getInstance(), new GuiHandler());
	}

	@Override
	public void init() {
		super.init();
		//        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemDrink.BottleColorHandler(), DRINKS);
		//        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemCanteen.CanteenColorHandler(), CANTEEN);
		//        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemCup.CupColorHandler(), CUP);
	}
}
