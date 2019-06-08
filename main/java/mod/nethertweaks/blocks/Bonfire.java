package mod.nethertweaks.blocks;

import java.util.*;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.GuiHandlerNTM;
import mod.nethertweaks.world.BonfireInfo;
import mod.nethertweaks.world.WorldSpawnLoc;
import mod.sfhcore.proxy.IVariantProvider;
import mod.sfhcore.vars.PlayerPosition;
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
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Bonfire extends Block
{
	//public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public Bonfire() {
		super(Material.ROCK);
		setLightLevel(12);
		setRegistryName(NetherTweaksMod.MODID, INames.BONFIRE);
		setCreativeTab(NetherTweaksMod.TABNTM);
        //this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	private int l = 0;
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
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if (!WorldSpawnLoc.bonfire_info.containsKey(pos))
		{
			WorldSpawnLoc.bonfire_info.put(pos, new BonfireInfo(placer.getUniqueID()));
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isBlockLoaded(pos)) return false;
		if(playerIn.isSneaking()) return false;
		
		/*
		BonfireInfo binfo;
		if (!WorldSpawnLoc.bonfire_info.containsKey(pos))
		{
			binfo = new BonfireInfo();
		}
		else
		{
			binfo = WorldSpawnLoc.bonfire_info.get(pos);
		}
		
		for (BonfireInfo entry : WorldSpawnLoc.bonfire_info.values())
		{
			if (entry.hasPlayer(playerIn))
			{
				entry.removePlayer(playerIn);
			}
		}
			
		binfo.addPlayer(playerIn);
		WorldSpawnLoc.lastSpawnLocas.put(playerIn.getUUID(playerIn.getGameProfile()), new PlayerPosition(new BlockPos(playerIn), playerIn.rotationYaw, playerIn.rotationPitch));
		if(worldIn.isRemote)
		    playerIn.sendMessage(new TextComponentString(playerIn.getName() + " rested at: " + playerIn.getPosition() + "!"));
		
		WorldSpawnLoc.bonfire_info.put(pos, binfo.copy());
		*/
		playerIn.openGui(NetherTweaksMod.instance, GuiHandlerNTM.idBonfire, worldIn, pos.getX(), pos.getY(), pos.getZ());
		
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
		if (WorldSpawnLoc.bonfire_info.containsKey(pos))
		{
			BonfireInfo binfo = WorldSpawnLoc.bonfire_info.get(pos);
			
			List<UUID> player_list = binfo.getLastPlayerSpawn();
			if (player_list.size() != 0)
			{
				for(UUID entry : player_list)
				{
					if (WorldSpawnLoc.lastSpawnLocas.containsKey(entry))
					{
						EntityPlayer player = worldIn.getPlayerEntityByUUID(entry);
						if (worldIn.isRemote)
							player.sendMessage(new TextComponentString(player.getName() + "'s point of rest is lost!"));
						WorldSpawnLoc.lastSpawnLocas.remove(entry);
					}
				}
			}
			WorldSpawnLoc.bonfire_info.remove(pos);
		}
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
}
