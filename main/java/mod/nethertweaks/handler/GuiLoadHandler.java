package mod.nethertweaks.handler;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.blocks.container.ContainerFreezer;
import mod.nethertweaks.blocks.container.ContainerNetherrackFurnace;
import mod.nethertweaks.blocks.tileentities.TileEntityCondenser;
import mod.nethertweaks.blocks.tileentities.TileEntityFreezer;
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.sfhcore.handler.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiLoadHandler{
	
	public static GuiHandler gh = new GuiHandler(NetherTweaksMod.instance);
	public static int condenser_id;
	public static int freezer_id;
	public static int netherrack_furnace_id;
	
	public static void addGuiToHandler() {
		condenser_id = gh.addGUIRelation(TileEntityCondenser.class, ContainerCondenser.class);
		freezer_id = gh.addGUIRelation(TileEntityFreezer.class, ContainerFreezer.class);
		netherrack_furnace_id = gh.addGUIRelation(TileEntityNetherrackFurnace.class, ContainerNetherrackFurnace.class);
	}
}
