package mod.nethertweaks.init;

import java.util.Objects;

import mod.nethertweaks.block.container.ContainerBonfire;
import mod.nethertweaks.block.container.ContainerCondenser;
import mod.nethertweaks.block.container.ContainerFreezer;
import mod.nethertweaks.block.container.ContainerHellmart;
import mod.nethertweaks.block.container.ContainerNetherrackFurnace;
import mod.nethertweaks.block.gui.GuiBonfire;
import mod.nethertweaks.block.gui.GuiCondenser;
import mod.nethertweaks.block.gui.GuiFreezer;
import mod.nethertweaks.block.gui.GuiHellmart;
import mod.nethertweaks.block.gui.GuiNetherrackFurnace;
import mod.nethertweaks.block.tile.TileCondenser;
import mod.nethertweaks.block.tile.TileFreezer;
import mod.nethertweaks.block.tile.TileHellmart;
import mod.nethertweaks.block.tile.TileNetherrackFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	public static final int ID_FURNACE 	 = 0;
	public static final int ID_CONDENSER = 1;
	public static final int ID_FREEZER   = 2;
	public static final int ID_HELLMART  = 3;
	public static final int ID_BONFIRE 	 = 4;

	@Override
	public Container getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z)
	{
		switch (ID)
		{
		case ID_FURNACE:
			return new ContainerNetherrackFurnace(player.inventory, (TileNetherrackFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		case ID_CONDENSER:
			return new ContainerCondenser(player.inventory, (TileCondenser)world.getTileEntity(new BlockPos(x, y, z)));
		case ID_FREEZER:
			return new ContainerFreezer(player.inventory, (TileFreezer)world.getTileEntity(new BlockPos(x, y, z)));
		case ID_HELLMART:
			return new ContainerHellmart(player.inventory, (TileHellmart) Objects.requireNonNull(world.getTileEntity(new BlockPos(x, y, z))));
		case ID_BONFIRE:
			return new ContainerBonfire();
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z)
	{
		switch (ID)
		{
		case ID_FURNACE:
			return new GuiNetherrackFurnace(player.inventory, (TileNetherrackFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		case ID_CONDENSER:
			return new GuiCondenser(player.inventory, (TileCondenser) world.getTileEntity(new BlockPos(x, y, z)));
		case ID_FREEZER:
			return new GuiFreezer(player.inventory, (TileFreezer) world.getTileEntity(new BlockPos(x, y, z)));
		case ID_HELLMART:
			return new GuiHellmart(player.inventory, (TileHellmart) world.getTileEntity(new BlockPos(x, y, z)));
		case ID_BONFIRE:
			return new GuiBonfire(new BlockPos(x, y, z), world, player);
		default:
			return null;
		}
	}
}
