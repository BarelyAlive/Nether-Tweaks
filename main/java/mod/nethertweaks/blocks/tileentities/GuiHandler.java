package mod.nethertweaks.blocks.tileentities;

import mod.nethertweaks.NetherTweaksMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {

	public GuiHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler(NetherTweaksMod.instance, this);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if(tileEntity instanceof TileEntityCondenser) return new ContainerCondenser(player.inventory, (TileEntityCondenser) tileEntity);
        else if(tileEntity instanceof TileEntityNetherrackFurnace) return new ContainerNetherrackFurnace(player.inventory, (TileEntityNetherrackFurnace) tileEntity);
        else if(tileEntity instanceof TileEntityFreezer) return new ContainerFreezer(player.inventory, (TileEntityFreezer) tileEntity);
        else return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if(tileEntity instanceof TileEntityCondenser) return new GuiCondenser(player.inventory, (TileEntityCondenser) tileEntity);
        else if(tileEntity instanceof TileEntityNetherrackFurnace) return new GuiNetherrackFurnace(player.inventory, (TileEntityNetherrackFurnace) tileEntity);
        else if(tileEntity instanceof TileEntityFreezer) return new GuiFreezer(player.inventory, (TileEntityFreezer) tileEntity);
        else return null;
	}

}
