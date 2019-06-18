package mod.nethertweaks.blocks;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.GuiHandlerNTM;
import mod.nethertweaks.world.BonfireInfo;
import mod.nethertweaks.world.WorldSpawnLocation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class Bonfire extends Block
{
	public Bonfire() {
		super(Material.ROCK);
		setLightLevel(12.0F);
		setRegistryName(NetherTweaksMod.MODID, INames.BONFIRE);
		setCreativeTab(NetherTweaksMod.TABNTM);
	}
	
	private int l = 0;
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		l++;
		if (rand.nextDouble() < 0.1D)
        {
            worldIn.playSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }
		switch(l) {
		case 1:
			worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX()+0.5D, pos.getY(), pos.getZ()+0.5D, rand.nextDouble()%0.04D, rand.nextDouble()%0.08D, rand.nextDouble()%0.04D);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX()+0.5D, pos.getY(), pos.getZ()+0.5D, -(rand.nextDouble()%0.04D), rand.nextDouble()%0.08D, rand.nextDouble()%0.04D);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX()+0.5D, pos.getY(), pos.getZ()+0.5D, rand.nextDouble()%0.04D, rand.nextDouble()%0.08D, rand.nextDouble()%0.04D);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX()+0.5D, pos.getY(), pos.getZ()+0.5D, -(rand.nextDouble()%0.04D), rand.nextDouble()%0.08D, rand.nextDouble()%0.04D);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX()+0.5D, pos.getY(), pos.getZ()+0.5D, rand.nextDouble()%0.04D, rand.nextDouble()%0.08D, -(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX()+0.5D, pos.getY(), pos.getZ()+0.5D, -(rand.nextDouble()%0.04D), rand.nextDouble()%0.08D, -(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX()+0.5D, pos.getY(), pos.getZ()+0.5D, rand.nextDouble()%0.04D, rand.nextDouble()%0.08D, -(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX()+0.5D, pos.getY(), pos.getZ()+0.5D, -(rand.nextDouble()%0.04D), rand.nextDouble()%0.08D, -(rand.nextDouble()%0.04D));
			l = 0;
        	break;
		}
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if (!WorldSpawnLocation.bonfire_info.containsKey(pos))
		{
			WorldSpawnLocation.bonfire_info.put(pos, new BonfireInfo(placer.getUniqueID()));
		}
		worldIn.scheduleUpdate(pos, state.getBlock(), 1);
	}
	
	private BlockPos testPosition(World world, final BlockPos destination)
	{		
		boolean north = world.isAirBlock(destination.north()) && world.isAirBlock(destination.north().up()) && world.isSideSolid(destination.north().down(), EnumFacing.UP);
		boolean east = world.isAirBlock(destination.east()) && world.isAirBlock(destination.east().up()) && world.isSideSolid(destination.east().down(), EnumFacing.UP);
		boolean south = world.isAirBlock(destination.south()) && world.isAirBlock(destination.south().up()) && world.isSideSolid(destination.south().down(), EnumFacing.UP);
		boolean west = world.isAirBlock(destination.west()) && world.isAirBlock(destination.west().up()) && world.isSideSolid(destination.west().down(), EnumFacing.UP);
		
		boolean northEast = world.isAirBlock(destination.north().east()) && world.isAirBlock(destination.north().east().up()) && world.isSideSolid(destination.north().east().down(), EnumFacing.UP);
		boolean southEast = world.isAirBlock(destination.east().south()) && world.isAirBlock(destination.east().south().up()) && world.isSideSolid(destination.east().south().down(), EnumFacing.UP);
		boolean southWest = world.isAirBlock(destination.south().west()) && world.isAirBlock(destination.south().west().up()) && world.isSideSolid(destination.south().west().down(), EnumFacing.UP);
		boolean northWest = world.isAirBlock(destination.west().north()) && world.isAirBlock(destination.west().north().up()) && world.isSideSolid(destination.west().north().down(), EnumFacing.UP);
		
		if(north) return destination.north();
		if(east) return destination.east();
		if(south) return destination.south();
		if(west) return destination.west();
		
		if(northEast) return destination.north().east();
		if(southEast) return destination.south().east();
		if(southWest) return destination.south().west();
		if(northWest) return destination.north().west();
		
		return null;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		
		BlockPos resultPos = testPosition(worldIn, pos);
		
		if(resultPos != null)
		{
			BonfireInfo info = WorldSpawnLocation.bonfire_info.get(pos);
			
			info.setSpawnPos(resultPos);
			
			WorldSpawnLocation.bonfire_info.put(pos, info);
		}
		
		worldIn.scheduleUpdate(pos, state.getBlock(), 10);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isBlockLoaded(pos)) return false;
		if(playerIn.isSneaking()) return false;
		
		playerIn.openGui(NetherTweaksMod.instance, GuiHandlerNTM.ID_BONFIRE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		
		return true;
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		super.onBlockHarvested(worldIn, pos, state, player);
		this.onBlockDestroy(worldIn, pos);
	}
	
	
	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
		this.onBlockDestroy(worldIn, pos);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
		this.onBlockDestroy(worldIn, pos);
	}
	
	private void onBlockDestroy(World worldIn, BlockPos pos)
	{
		if (WorldSpawnLocation.bonfire_info.containsKey(pos))
		{
			BonfireInfo binfo = WorldSpawnLocation.bonfire_info.get(pos);
			
			List<UUID> player_list = binfo.getLastPlayerSpawn();
			if (player_list.size() != 0)
			{
				for(UUID entry : player_list)
				{
					if (WorldSpawnLocation.lastSpawnLocations.containsKey(entry))
					{
						EntityPlayer player = worldIn.getPlayerEntityByUUID(entry);
						if (worldIn.isRemote)
							player.sendMessage(new TextComponentString(player.getName() + "'s point of rest is lost!"));
						WorldSpawnLocation.lastSpawnLocations.remove(entry);
					}
				}
			}
			WorldSpawnLocation.bonfire_info.remove(pos);
		}
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
    public boolean isTopSolid(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
    @Override
    @Deprecated
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
