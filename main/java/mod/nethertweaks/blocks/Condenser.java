package mod.nethertweaks.blocks;
 
import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileCondenser;
import mod.nethertweaks.config.BlocksItems;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.handler.GuiHandler;
import mod.sfhcore.blocks.CubeContainerHorizontal;
import mod.sfhcore.util.TankUtil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
 
public class Condenser extends CubeContainerHorizontal
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public Condenser()
    {
        super(Material.ROCK, new ResourceLocation(NetherTweaksMod.MODID, INames.CONDENSER));
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setResistance(17.5f);
        setHardness(3.5f);
        setCreativeTab(NetherTweaksMod.TABNTM);
    }
    
    @Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ)
    {
		if(!world.isBlockLoaded(pos)) return false;
		TileCondenser te = (TileCondenser) world.getTileEntity(pos);
		if(te == null) return false;
		if(!(te instanceof TileCondenser)) return false;
    	if(world.isRemote) return true;
    	if(player.isSneaking()) return false;
    	
    	boolean success;
    	
		if (!BlocksItems.enableDistilledWater) {
			success = TankUtil.drainWaterIntoBottle(te, player, te.getTank());
			if (success)
				return true;
		}
		
		IFluidHandlerItem item = FluidUtil.getFluidHandler(player.getHeldItem(hand));
    	
    	if (item != null) {
			success = FluidUtil.interactWithFluidHandler(player, hand, te.getTank());
			if(success) return true;
		}
		player.openGui(NetherTweaksMod.instance, GuiHandler.ID_CONDENSER, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileCondenser();
    }
}