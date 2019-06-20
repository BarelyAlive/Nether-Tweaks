package mod.nethertweaks.handler;

import mod.nethertweaks.blocks.container.ContainerBonfire;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.blocks.container.ContainerFreezer;
import mod.nethertweaks.blocks.container.ContainerHellmart;
import mod.nethertweaks.blocks.container.ContainerNetherrackFurnace;
import mod.nethertweaks.blocks.gui.GuiBonfire;
import mod.nethertweaks.blocks.gui.GuiCondenser;
import mod.nethertweaks.blocks.gui.GuiFreezer;
import mod.nethertweaks.blocks.gui.GuiHellmart;
import mod.nethertweaks.blocks.gui.GuiNetherrackFurnace;
import mod.nethertweaks.blocks.tile.TileCondenser;
import mod.nethertweaks.blocks.tile.TileFreezer;
import mod.nethertweaks.blocks.tile.TileHellmart;
import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerNTM implements IGuiHandler
{
	public static final int ID_FURNACE 	 = 0;
	public static final int ID_CONDENSER = 1;
	public static final int ID_FREEZER   = 2;
	public static final int ID_HELLMART  = 3;
	public static final int ID_BONFIRE 	 = 4;
	
	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
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
				return new ContainerHellmart(player.inventory, (TileHellmart)world.getTileEntity(new BlockPos(x, y, z)));
			case ID_BONFIRE:
				return new ContainerBonfire();
			default:
				return null;
		}
	}
		
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
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
