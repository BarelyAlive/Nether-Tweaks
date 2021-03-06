package mod.nethertweaks.proxy;

import mod.nethertweaks.block.tile.TileAshBonePile;
import mod.nethertweaks.block.tile.TileBarrel;
import mod.nethertweaks.block.tile.TileCrucibleStone;
import mod.nethertweaks.block.tile.TileSieve;
import mod.nethertweaks.client.renderers.RenderAshBonePile;
import mod.nethertweaks.client.renderers.RenderBarrel;
import mod.nethertweaks.client.renderers.RenderCrucible;
import mod.nethertweaks.client.renderers.RenderProjectileStone;
import mod.nethertweaks.client.renderers.RenderSieve;
import mod.nethertweaks.entities.ProjectileStone;
import mod.nethertweaks.init.OreHandler;
import mod.nethertweaks.modules.thirst.ThirstStats;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy{

	public static final ThirstStats CLIENT_STATS = new ThirstStats();

	@Override
	public void preInit() {
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
	}

	@Override
	public void init() {
		//        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemDrink.BottleColorHandler(), DRINKS);
		//        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemCanteen.CanteenColorHandler(), CANTEEN);
		//        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemCup.CupColorHandler(), CUP);
	}

	@Override
	public void postInit() {
		// TODO Auto-generated method stub

	}
}
