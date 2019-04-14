package mod.nethertweaks.blocks;
 
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileHellmart;
import mod.nethertweaks.interfaces.INames;
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

public class Hellmart extends BlockContainer {
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	public Hellmart() {
		super(Material.WOOD);
		setSoundType(SoundType.WOOD);
		this.setCreativeTab(NetherTweaksMod.tabNTM);
		setUnlocalizedName(INames.HELLMART);
		setRegistryName(NetherTweaksMod.MODID, INames.HELLMART);
		this.setHardness(1.0F);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		}
		TileEntity te = world.getTileEntity(pos);
		if(!(te instanceof TileHellmart)) {
			return false;
		}
		player.openGui(NetherTweaksMod.instance, 3, world, pos.getX(), pos.getY(), pos.getZ());
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