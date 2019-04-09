package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileBonfire;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Bonfire extends BlockContainer {
	
	//public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public Bonfire() {
		super(Material.ROCK);
		setLightLevel(15);
		setUnlocalizedName(INames.BONFIRE);
		setRegistryName("nethertweaksmod", INames.BONFIRE);
		setCreativeTab(NetherTweaksMod.tabNTM);
        //this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	int l = 0;
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		l++;
		if (rand.nextDouble() < 0.1D)
        {
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }
		switch(l) {
		case 1:
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)-(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)-(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)-(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)-(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			l = 0;
        	break;
		}
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isBlockLoaded(pos)) {
			if (playerIn.onGround && playerIn.dimension == -1) {
				TileEntity bonfire = worldIn.getTileEntity(pos);

				if (bonfire instanceof TileBonfire) {
					((TileBonfire) bonfire).setSpawnLocationForPlayer(playerIn, pos);
				}
			} 
		}
		return false;
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if (worldIn.isBlockLoaded(pos)) {
			TileEntity bonfire = worldIn.getTileEntity(pos);
			if (bonfire instanceof TileBonfire) {
				((TileBonfire) bonfire).deleteSpawnLocationsIfDestroyed();
			} 
		}
	}
	
	/*
	@Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
    */
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
    	return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
    	return new TileBonfire();
    }

}
