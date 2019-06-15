package mod.nethertweaks.blocks;
 
import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileHellmart;
import mod.nethertweaks.handler.GuiHandlerNTM;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Hellmart extends BlockContainer
{
	public Hellmart()
	{
		super(Material.ROCK);
		setSoundType(SoundType.STONE);
		setCreativeTab(NetherTweaksMod.TABNTM);
		setRegistryName(NetherTweaksMod.MODID, INames.HELLMART);
		setResistance(17.5f);
		setHardness(2.0F);
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(!world.isBlockLoaded(pos)) return false;
    	if(world.isRemote) return true;
    	if(player.isSneaking()) return false;
    	  	
    	TileHellmart te = (TileHellmart) world.getTileEntity(pos);
    	if(te == null) return false;
		if(!(te instanceof TileHellmart)) return false;
		
		player.openGui(NetherTweaksMod.instance, GuiHandlerNTM.ID_HELLMART, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileHellmart();
	}
}