package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import mod.nethertweaks.Config;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.barrel.modes.block.BarrelModeBlock;
import mod.nethertweaks.barrel.modes.compost.BarrelModeCompost;
import mod.nethertweaks.barrel.modes.fluid.BarrelModeFluid;
import mod.nethertweaks.barrel.modes.fluid.BarrelModeFluidTransform;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

public class BlockBarrel extends Block implements ITileEntityProvider {

	private AxisAlignedBB boundingBox = new AxisAlignedBB(0.0625f, 0, 0.0625f, 0.9375f, 1f, 0.9375f);
	private int tier;
	
	public int getTier() {
		return tier;
	}

	public BlockBarrel(int tier, Material material)
	{
		super(material);
        this.tier = tier;
		this.setHardness(2.0f);
		setUnlocalizedName(INames.BARREL + tier);
		setRegistryName(NetherTweaksMod.MODID, INames.BARREL + tier);
		setCreativeTab(NetherTweaksMod.tabNTM);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileBarrel) {
			TileBarrel barrel = (TileBarrel) te;
			
			if (barrel.getMode() != null && barrel.getMode().getName().equals("block")) {
				ItemStack stack = barrel.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
				if (stack != null)
					Util.dropItemInWorld(te, null, stack, 0);
			}
		}
		
		super.breakBlock(world, pos, state);
	}
	@SuppressWarnings("deprecation")
    @Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
	{
	    TileEntity te = world.getTileEntity(pos);
	    
	    if(te != null && te instanceof TileBarrel)
	    {
	    	TileBarrel tile = (TileBarrel) te;
	        if(tile.getMode() instanceof BarrelModeBlock)
	        {
	            BarrelModeBlock mode = (BarrelModeBlock) tile.getMode();
	            
	            if(mode.getBlock() != null)
	            {
	                return Block.getBlockFromItem(mode.getBlock().getItem()).getStateFromMeta(mode.getBlock().getMeta()).getLightValue();
	            }
	        }
            else if(tile.getMode() instanceof BarrelModeFluid)
            {
                BarrelModeFluid mode = (BarrelModeFluid) tile.getMode();
                
                if(mode.getFluidHandler(tile).getFluidAmount() > 0)
                {
                    return Util.getLightValue(mode.getFluidHandler(tile).getFluid());
                }
            }
            else if(Config.enableBarrelTransformLighting)
            {
                if(tile.getMode() instanceof BarrelModeCompost)
                {
                    BarrelModeCompost mode = (BarrelModeCompost) tile.getMode();
                    
                    if(mode.getCompostState() != null)
                    {
                        int value = mode.getCompostState().getLightValue() / 2;
                        
                        return Math.round(Util.weightedAverage(value / 2, value, mode.getProgress()));
                    }
                }
    	        else if(tile.getMode() instanceof BarrelModeFluidTransform)
    	        {
    	            BarrelModeFluidTransform mode = (BarrelModeFluidTransform) tile.getMode();
    	            
    	            int inputValue = Util.getLightValue(mode.getInputStack());
    	            int outputValue = Util.getLightValue(mode.getOutputStack());
    	            
    	            return Math.round(Util.weightedAverage(outputValue, inputValue, mode.getProgress()));
    	        }
            }
	    }
	    
	    return super.getLightValue(state, world, pos);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
			return true;

		return ((TileBarrel) worldIn.getTileEntity(pos)).onBlockActivated(worldIn, pos, state, playerIn, facing, hitX, hitY, hitZ);
}
	
	@Override
	 public boolean isFullBlock(IBlockState state)
    {
        return false;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileBarrel(this);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return boundingBox;
	}
    
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

}